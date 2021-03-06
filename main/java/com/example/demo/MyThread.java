package com.example.demo;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MyThread extends Thread {
    private final Counter testCounter;
    private final AtomicInteger testGauge;

    public MyThread(MeterRegistry meterRegistry) {
        testGauge = meterRegistry.gauge("myGauge", new AtomicInteger(0));
        testCounter = meterRegistry.counter("myCounter");
        this.start();
    }

    @Override
    public void run() {
        long start = 0, end = 0;
        do {
            testCounter.increment();
            start = System.currentTimeMillis();

            try {
                sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            end = System.currentTimeMillis();
            testGauge.addAndGet(Math.toIntExact(end - start));
        } while (!(testCounter.count() > 1000.0));
    }
}
