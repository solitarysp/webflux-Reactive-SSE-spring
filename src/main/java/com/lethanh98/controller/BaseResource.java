package com.lethanh98.controller;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.Resource;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Slf4j
public abstract class BaseResource {
    private final Long DEFERRED_TIMEOUT = 10000L;
    private final Long SERVICE_TIMEOUT = 10000L;

    private static ListeningExecutorService service;
    @Resource
    private MessageSource messageSource;

    public BaseResource() {
        if (service == null) {
            ThreadPoolExecutor createService = new ThreadPoolExecutor(30, 100000,
                    1L, TimeUnit.MINUTES,
                    new LinkedBlockingQueue<>());
            createService.setKeepAliveTime(1L, TimeUnit.MINUTES);
            createService.allowCoreThreadTimeOut(true);
            service = MoreExecutors.listeningDecorator(createService);
        }

    }


    public <T> ListenableFuture<?> asyncExecute(final Supplier<?> supplier) {
        return (service.submit(supplier::get));
    }

    public <T> DeferredResult<?> callBackResponse(ListenableFuture<?> future) {

        final DeferredResult<Object> deferredResult = new DeferredResult<>(DEFERRED_TIMEOUT);
        deferredResult.onTimeout(() -> {
            deferredResult.setResult(null);
        });
        Futures.addCallback(future, new FutureCallback<Object>() {
            public void onSuccess(Object model) {
                try {
                    deferredResult.setResult(future.get(SERVICE_TIMEOUT, TimeUnit.MILLISECONDS));
                } catch (Exception e) {
                    deferredResult.setResult(null);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                log.info(throwable.getMessage(), throwable);
                deferredResult.setResult(null);
            }
        });
        return deferredResult;
    }

    public String getMessage(String keyMessage) {
        return messageSource.getMessage(keyMessage, null, LocaleContextHolder.getLocale());
    }

}
