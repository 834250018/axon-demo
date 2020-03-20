package com.ywy.learn.command.user;

import com.ywy.learn.command.user.api.event.AuthRemovedEvent;
import com.ywy.learn.command.user.api.event.UserRemovedEvent;
import com.ywy.learn.common.api.gateway.MetaDataGateway;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.messaging.MetaData;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户被删除saga,实现用户删除后的其他业务,比如用户注销之后,需要删除授权中心(另一个聚合根)相关授权
 *
 * @author ve
 * @date 2019/8/25 2:46
 */
@Saga
public class UserRemovedSaga {

    static {
        // todo 项目重启时应当检查saga是否完成
    }

    @Autowired
    transient MetaDataGateway metaDataGateway;

    /**
     * saga事务的第一个节点,需要以@StartSaga标识事务开始
     *
     * @param event
     * @param metaData
     */
    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    public void handle(UserRemovedEvent event, MetaData metaData) {
/*
//            1.创建授权中心删除指定cert命令
        AuthRemoveCommand command = new AuthRemoveCommand();

        // 2.为此saga设置关联,用于匹配第二节点与后续事件
        SagaLifecycle.associateWith("userId", event.getId());

//            3.发送此命令,进入下一节点
        // todo 后续handle没写
//        metaDataGateway.send(command, metaData);*/
    }

    /**
     * saga事务的第二个节点,若是最后一个事件,以注解@EndSaga标识事务结束,否则需要显性指定事务结束SagaLifecycle.end();
     *
     * @param event
     * @param metaData
     */
    @EndSaga
    @SagaEventHandler(associationProperty = "id", keyName = "userId")
    // 此处keyName与上面的"userId"相同,associationProperty则是userId(即找到event.getUserId()进行匹配关联saga事务第一节点)
    public void handle(AuthRemovedEvent event, MetaData metaData) {
        // ok, do nothing.
    }
}
