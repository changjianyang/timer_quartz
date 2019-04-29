package com.zhangwin.timer.repository;

import com.zhangwin.timer.vo.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yangchangjian
 * @version 1.0
 * @Description:
 * @datatime 2019/3/14 17:50
 */
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, String> {

    //通过job状态查询所有job
    @Query(value = "select * from schedule_jobs where job_status = :jobStatus and job_cron is not null and job_cron !=''", nativeQuery = true)
    List<Schedule> findByJobStatusEqule(@Param("jobStatus") String jobStatus);

}
