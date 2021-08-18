package com.example.demo;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyThread extends Thread {
    private final Counter testCounter;

    public MyThread(MeterRegistry meterRegistry) {
        testCounter = meterRegistry.counter("myCounter");
        this.start();
    }

    @Override
    public void run(){
        while (true) {
            testCounter.increment();

            try {
                sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
