package com.lethanh98.process;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import reactor.core.publisher.FluxSink;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

@Component
public class NewUserEventProcessor implements ApplicationListener<UserEvent>,
        Consumer<FluxSink<UserEvent>> {
    private final Executor executor = Executors.newFixedThreadPool(20);
    private final BlockingQueue<UserEvent> queue = new LinkedBlockingQueue<>();

    NewUserEventProcessor() {
    }

    @Override
    public void accept(FluxSink<UserEvent> userEventFluxSink) {
        this.executor.execute(() -> {
            while (true)
                try {
                    UserEvent eventSen = queue.take();
                    userEventFluxSink.next(eventSen);
                } catch (InterruptedException e) {
                    ReflectionUtils.rethrowRuntimeException(e);
                }
        });
    }

    @Override
    public void onApplicationEvent(UserEvent event) {
        this.queue.offer(event);
    }
}
