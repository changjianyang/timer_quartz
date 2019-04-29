package com.zhangwin.timer.service;

import com.zhangwin.timer.vo.Schedule;

/**
 * @author yangchangjian
 * @version 1.0
 * @Description:
 * @datatime 2019/3/14 17:32
 */
public interface ScheduleService {

    /**
     * 获取所有需要运行的数据
     */
    Object getJobData(String status);

    /**
     * 添加任务数据
     *
     * @param schedule 任务
     * @return
     */
    boolean addJob(Schedule schedule);

    /**
     * 删除任务数据
     *
     * @param jobName 任务名
     */
    void deleteJob(String jobName);

    /**
     * 修改任务数据
     */
    void updateJob();

}
