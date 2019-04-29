package com.zhangwin.timer.service.impl;

import com.zhangwin.timer.repository.ScheduleRepository;
import com.zhangwin.timer.service.ScheduleService;
import com.zhangwin.timer.vo.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yangchangjian
 * @version 1.0
 * @Description:
 * @datatime 2019/3/14 17:47
 */
@Service
public class ScheduleServieImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository repository;


    @Override
    public Iterable<Schedule> getJobData(String status) {
        Iterable<Schedule> repositoryAll = repository.findByJobStatusEqule(status);
        return repositoryAll;
    }

    public Iterable<Schedule> findByJobStatusEqule(String status) {
        Iterable<Schedule> repositoryAll = repository.findByJobStatusEqule("2");
        return repositoryAll;
    }

    @Override
    public boolean addJob(Schedule schedule) {
        try {
            repository.save(schedule);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void deleteJob(String jobName) {

    }

    @Override
    public void updateJob() {

    }
}
