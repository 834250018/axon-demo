## Axon Demo Project 

* 前言
```
基于axon框架的demo.
```
* 启动环境
```
mongodb:localhost:27017 无账号密码,且创建两个库learn_query跟learn_command
lombok插件:idea安装lombok组件Version: 0.23-2018.1 使用最新版可能会有问题
```
补充
```
1. 加入https协议,同时支持http

2. 生成本地测试用证书
keytool -genkey -alias tomcat -keypass 123456 -keyalg RSA -keysize 1024 -validity 365 -keystore e:/tomcat.keystore -storepass 123456

3. 关于此项目各模块的依赖关系
web依赖query,query依赖各个聚合根模块api
saga事务中可能会涉及到别的聚合根模块api,正常业务下,Saga文件一般放到初始聚合根模块下,如UserRemoveSaga.java放入User.java同目录下,当然这个位置可以根据业务调整

4. 关于元数据MetaData
可以设置、归档、统计、隔离数据,如时间,作用域,操作人等等
    使用场景:
(1).项目跑起来给不同客户使用,可以在metaData中加一个项目标识,在查询的时候加入这个标识条件
(2).记录每一次事件发生的事件
...
```

* 历程
```
2019/7/2 整理了项目结果,把command跟query拆开

2019/7/17 加入sonarQube配置 4bugs,10Vulnerabilities,106CodeSmells

2019/7/20 引入logback框架,对日志跟异常切面调整

2019/8/25 增加一个saga事务的例子UserRemovedSaga.java
```