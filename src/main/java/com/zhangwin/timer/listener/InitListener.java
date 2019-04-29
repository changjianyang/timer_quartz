package com.zhangwin.timer.listener;

import com.zhangwin.timer.jobs.InitJob;
import com.zhangwin.timer.jobs.QuartzManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

/**
 * @author yangchangjian
 * @version 1.0
 * @Description:
 * @datatime 2019/3/14 20:08
 */
@Service
public class InitListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private QuartzManager manager;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        manager.addJob("init1", "Init", "triInit1", "triInit", InitJob.class, "*/10 * * * * ?", null);
    }
}
