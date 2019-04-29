package com.zhangwin.timer.jobs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhangwin.timer.utils.DateUtil;
import com.zhangwin.timer.utils.HttpClientUtils;
import com.zhangwin.timer.vo.StaticValue;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

import static org.quartz.JobBuilder.newJob;

/**
 * @author yangchangjian
 * @version 1.0
 * @Description: 推送的任务
 * @datatime 2019/3/15 11:10
 */
@Service
public class PushJob implements Job {

    @Autowired
    private RestTemplate template;

    @Value(value = "${tianqiUrl}")
    private String tianqiUrl;

    @Value("${pushUrl}")
    private String pushUrl;

    @Override
    public void execute(JobExecutionContext context) {
        try {
            boolean flag = false;
            //获取job中传入的数据
            System.out.println(context.getTrigger().getJobDataMap().get("data") + "---triggers数据");
            JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
            String text = jobDataMap.get("data").toString();
            JSONObject data = JSON.parseObject(text);
            //周末
            String week = data.getString("week");
            //节假日
            String holiday = data.getString("holiday");
            //任务数据
            String jobData = data.get("jobData").toString();
            //特殊日期数据
            Object jobDay = data.get("jobDay");
            //jobname
            //获取今天是日期
            // 正常工作日对应结果为 0,
            // 法定节假日对应结果为 1,
            // 节假日调休补班对应的结果为 2，
            // 休息日对应结果为 3
            String yyyymmdd = DateUtil.formatDate(new Date(), DateUtil.yyyymmdd);
            String yyyy_mm_dd = DateUtil.formatDate(new Date(), DateUtil.yyyy_mm_dd);
            JSONObject todayObj;
            if (StaticValue.staticValueMap.get(yyyymmdd) == null) {
                //重新获取，并添加
                todayObj = HttpClientUtils.httpGet(tianqiUrl + "?data=" + yyyymmdd);
                StaticValue.staticValueMap.put(yyyymmdd, todayObj);
            } else {
                todayObj = (JSONObject) StaticValue.staticValueMap.get(yyyymmdd);
            }
            //今天是什么日子{"code":10000,"data":0}
            String today = todayObj.getString("data");
            //判断那些天不推送{
            if (jobDay != null) {
                JSONArray days = JSON.parseArray(jobDay.toString());
                for (Object day : days) {
                    if (yyyy_mm_dd.equals(day.toString())) {
                        System.out.println("特殊日期不做任何操作");
                        return;
                    }
                }
            }
            //判断今天需不需要推送
            switch (today) {
                case "0":
                    flag = true;
                    System.out.println("工作日");
                    // 推送
                    break;
                case "1":
                    if (holiday != null && !"".equals(holiday)) {
                        if ("1".equals(holiday)) {
                            //做
                            flag = true;
                        } else {
                            flag = false;
                        }

                    }
                    System.out.println("节假日");
                    break;
                case "2":
                    flag = true;
                    System.out.println("节假日补班");
                    break;
                case "3":
                    if (week != null && !"".equals(week)) {
                        if ("1".equals(week)) {
                            flag = true;
                        } else {
                            flag = false;
                        }
                    }
                    System.out.println("休息日");
                    break;
            }
            if (flag) {
                System.out.println("----");
                System.out.println(this.pushUrl);
                System.out.println(jobData);
                System.out.println("=====");
                HttpClientUtils.sendPost(this.pushUrl, "jobdata=" + jobData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("job发生异常");
        }
    }
}
