package com.lethanh98.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GC {
    @Scheduled(fixedRate = 20 * (1000 * 60))
    public void gcMem() {
        log.info("Run GC");
        log.info("Before gc Available processors (cores): {} ", Runtime.getRuntime().availableProcessors());
        log.info("Before gc Free memory (bytes): {} ", Runtime.getRuntime().freeMemory());

        long maxMemory = Runtime.getRuntime().maxMemory();
        log.info("Before gc Maximum memory (bytes): {}", (maxMemory == Long.MAX_VALUE ? "no limit" : maxMemory));
        log.info("Before gc Total memory (bytes): {} ", Runtime.getRuntime().totalMemory());
        System.gc();
        log.info("After gc Available processors (cores): {} ", Runtime.getRuntime().availableProcessors());
        log.info("After gc Free memory (bytes): {} ", Runtime.getRuntime().freeMemory());

        maxMemory = Runtime.getRuntime().maxMemory();
        log.info("After gc Maximum memory (bytes): {}", (maxMemory == Long.MAX_VALUE ? "no limit" : maxMemory));
        log.info("After gc Total memory (bytes): {} ", Runtime.getRuntime().totalMemory());
        log.info("End run GC");

    }
}
