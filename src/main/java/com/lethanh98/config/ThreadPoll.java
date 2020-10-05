package com.lethanh98.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@EnableAsync
@Configuration
public class ThreadPoll {
    public int checkGwThreadPoll = 5;

    @Bean(name = "defaultThreadPoll")
    public ThreadPoolExecutor defaultThreadPoll() {
        ThreadPoolExecutor threadCheckOCSTransGw = (ThreadPoolExecutor) Executors.newFixedThreadPool(checkGwThreadPoll);
        threadCheckOCSTransGw.setKeepAliveTime(10L, TimeUnit.MINUTES);
        threadCheckOCSTransGw.allowCoreThreadTimeOut(true);
        return threadCheckOCSTransGw;
    }

    @Bean(name = "threadPollSaveLink")
    public ThreadPoolExecutor ThreadPollSaveLink() {
        ThreadPoolExecutor threadCheckOCSTransGw = (ThreadPoolExecutor) Executors.newFixedThreadPool(checkGwThreadPoll);
        threadCheckOCSTransGw.setKeepAliveTime(10L, TimeUnit.MINUTES);
        threadCheckOCSTransGw.allowCoreThreadTimeOut(true);
        return threadCheckOCSTransGw;
    }
}
