package com.ywy.learn.infrastructure.callback;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 日志回调
 *
 * @author ve
 * @create 2018-02-26 下午5:57
 **/
@Slf4j
public class LogCommandCallback implements CommandCallback {

    @Autowired
    private CommandGateway commandGateway;

    @Override
    public void onSuccess(CommandMessage commandMessage, Object result) {
        /*
        System.out.println("==========");
        System.out.println(commandMessage.getCommandName());
        System.out.println(commandMessage.getPayloadType().getSimpleName());
        */
    }

    @Override
    public void onFailure(CommandMessage commandMessage, Throwable cause) {
        // log.error(commandMessage.toString(), cause);
        // commandGateway.send(new ErrorLogCreateCommand(null, commandMessage, cause), null);
        log.error(commandMessage.toString(), cause);
        throw new RuntimeException();
//        if (cause instanceof YnacErrorException) {
//            throw (YnacErrorException) cause;
//        } else {
//            throw new YnacErrorException(cause.toString());
//        }
    }
}
