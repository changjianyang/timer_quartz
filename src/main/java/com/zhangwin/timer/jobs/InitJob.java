package com.zhangwin.timer.jobs;

import com.alibaba.fastjson.JSONObject;
import com.zhangwin.timer.service.impl.ScheduleServieImpl;
import com.zhangwin.timer.vo.Schedule;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yangchangjian
 * @version 1.0
 * @Description: 获取数据库需要运行的任务，放入map中
 * @datatime 2019/3/14 20:04
 */
@Service
public class InitJob implements Job {

    Map<String, Object> jobMap = new ConcurrentHashMap<>();

    @Resource
    private ScheduleServieImpl servie;

    @Resource
    private QuartzManager jobManager;

    @Override
    public void execute(JobExecutionContext context) {
        this.findByJobStatus();
    }

    /**
     * 取数据 放入map
     *
     * @return
     */
    public void findByJobStatus() {
        Iterable<Schedule> schedules = servie.getJobData("1");
        //取出数据
        this.jobMap.clear();
        schedules.forEach(e -> {
            this.jobMap.put(e.getJobGroup() + "#" + e.getJobName(), e);
        });

        this.killNullJob();
        //判断数据库新数据与在运行的数据是否相等
        // TODO
        //遍历map
        //启动数据库中的job
        for (String key : this.jobMap.keySet()) {
            Schedule schedule = (Schedule) this.jobMap.get(key);
            System.out.println(schedule.toString());
            if (!this.jobManager.checkJob(schedule.getJobName(), schedule.getJobGroup())) {
                JSONObject data = new JSONObject();
                data.put("week", schedule.getJobWeekend());
                data.put("holiday", schedule.getJobHoliday());
                data.put("jobData", schedule.getJobData());
                data.put("jobDay", schedule.getJobDay());
                data.put("jobName", schedule.getJobName());
                data.put("jobGroup", schedule.getJobGroup());
                if (schedule.getJobName().contains("push")) {
                    this.jobManager.addJob(schedule.getJobName(), schedule.getJobGroup(), schedule.getJobName(), schedule.getJobGroup(), PushJob.class, schedule.getJobCron(), data);
                }
            }
        }

    }

    /**
     * 判断以运行的job状态是否改变
     */
    public void killNullJob() {
        try {
            Scheduler scheduler = jobManager.getScheduler();
            for (String groupName : scheduler.getJobGroupNames()) {
                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                    String jobName = jobKey.getName();
                    //数据库中已经停掉中的任务，需要干掉
                    Schedule schedule = (Schedule) this.jobMap.get(groupName + "#" + jobName);
                    if (schedule == null) {
                        if (!"Init#init1".equals(groupName + "#" + jobName)) {
                            this.jobManager.deleteJob(jobName, groupName);
                            this.jobManager.pauseJob(jobName, groupName);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
