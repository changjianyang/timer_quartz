package com.zhangwin.timer.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhangwin.timer.utils.cron.CronUtil;
import com.zhangwin.timer.utils.cron.TaskScheduleModel;
import com.zhangwin.timer.service.impl.ScheduleServieImpl;
import com.zhangwin.timer.utils.DateUtil;
import com.zhangwin.timer.vo.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

/**
 * @author yangchangjian
 * @version 1.0
 * @Description:
 * @datatime 2019/3/14 17:54
 */
@RestController
@RequestMapping(value = "job")
public class ScheduleController {

    @Autowired
    private TaskScheduleModel model;

    @Autowired
    private ScheduleServieImpl servie;


    @RequestMapping(value = "getAll")
    public Object getAll() {
        return servie.getJobData("1");
    }

    @RequestMapping(value = "add")
    public Object addJob() {
        Schedule schedule = new Schedule();
        schedule.setId(UUID.randomUUID().toString());
        //设置定时vo
        model.setJobType(1);
        model.setHour(19);
        model.setMinute(55);
        model.setSecond(00);
        String cropExp = CronUtil.createCronExpression(model);
        schedule.setJobCron(cropExp);

        schedule.setJobGroup("T");
        schedule.setJobName("T1");
        String date = DateUtil.formatDate(new Date());
        schedule.setJobTime(date);
        schedule.setTs(new Date());
        //设置数据
        JSONObject data = new JSONObject();
        JSONObject obj = new JSONObject();
        obj.put("name", "xixi");
        obj.put("age", 12);
        data.put("data", obj);
        schedule.setJobData(data.toString());
        return servie.addJob(schedule);
    }
}
