package com.ywy.learn.common.api.gateway;

import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.messaging.MetaData;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 带上Metadata的命令网关
 *
 * @author ve
 * @create 2018-02-08 上午11:19
 **/
public interface MetaDataGateway {

    <C, R> void send(C command, CommandCallback<? super C, R> callback, MetaData metaData);

    <R> R sendAndWait(Object command, MetaData metaData) throws InterruptedException;

    <R> R sendAndWait(Object command, MetaData metaData, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException;

    <R> CompletableFuture<R> send(Object command, MetaData metaData);
}
