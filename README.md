# Axon Demo Project 

### 前言

```
基于axon框架的demo.
```
### 环境

1. mongodb
2. redis
3. lombok插件

### 特征

1. 启动时生成唯一管理员admin,默认密码时admin123
2. 启动时在src/main/resources/cert文件夹下生成根证书
3. saga一般在聚合根模块下,若涉及多个聚合根可放到web层
4. 关于元数据MetaData
    1. 可以设置、归档、统计、隔离数据,如时间,作用域,操作人等等
    2. 多渠道时,数据创建的时候增加渠道id,查询时补充条件
    3. 记录每一次事件发生的时间
5. user申请签名证书
    1. 自己准备一对RSA密钥对(或使用公共接口"生成1024长度的RSA密钥对接口"生成)
    2. 登录调接口上传公钥,服务器返回签名证书
6. user签名证书登录
    1. 调用"签名证书登录接口"获取临时登录字符串,一分钟有效
    2. 使用签名私钥加密此字符串得到密文
    3. 使用账号+密文登录
5. user签名证书签名验签(在本系统当前根证书有效)
    1. 确保用户a已申请签名证书,且证书在有效期
    2. 用户a对文件进行sha256摘要
    3. 用户a使用私钥对摘要进行加密获得签名
    4. 用户a把文件跟签名发送给用户b
    5. 用户b调公共接口"验签接口"上传文件,签名以及用户a的邮箱
    6. 服务器返回true告知用户b"本系统证明:这是用户a的文件没差了!!"

### 历程

```
2019/7/2 整理了项目结果,把command跟query拆开

2019/7/17 加入sonarQube配置 4bugs,10Vulnerabilities,106CodeSmells

2019/7/20 引入logback框架,对日志跟异常切面调整

2019/8/25 增加一个saga事务的例子UserRemovedSaga.java

2020/1/2 整合FreeMarker
1.打开CodeGenerator.java 修改generateArt()方法中需要生成的模块跟聚合根,并执行
2.调整新生成的Aggregate、Entry、Command、Event的字段
3.若生成了新模块,在query/build.gradle、build.gradle、settings.gradle添加依赖

2020/1/5 生成admin模块并调整

2020/2/24 admin模块调整完善,登录

2020/2/26 通过一个简单的例子(user签名证书)简单阐述ca部分内容.
严格来说user没有意义,此系统的user表示除管理员(admin)以外的用户,默认以邮箱及验证码登录

2020/2/26 redis跟mongodb被黑了,切记设置密码,链接https://p0sec.net/index.php/archives/69/
配置mongodb角色权限https://www.cnblogs.com/mengyu/p/9071371.html
```

### redis docker搭建

```
拉取镜像docker pull redis
创建数据目录mkdir -p /docker/redis/data
创建配置文件vi /docker/redis/redis.conf(默认无密码;bind=127.0.0.1;databases=16,改成有密码,对外网访问)
    bind 0.0.0.0
    port 6379
    requirepass foo&`b0x7sQ8wfaredfoo&`b0x7sQ8wfaredfoo&`b0x7sQ8wfaredfoo&`b0x7sQ8wfared
启动容器
docker run --restart=always --name=redis -v /docker/redis/data/:/data -v /docker/redis/redis.conf:/etc/redis/redis.conf -d -p 6379:6379 redis /etc/redis/redis.conf
```
### mongodb docker搭建

```
拉取镜像docker pull mongo:4.0
运行容器docker run --restart=always --name mongo -d -p 27017:27017 -v /docker/mongo/db/:/data/db mongo
进入容器docker exec -it mongo /bin/bash
进入mongo客户端 mongo
进入admin库use admin
创建管理员用户db.createUser({user:"admin",pwd:"password",roles:["root"]})
进入query_db库use query_db
创建用户db.createUser({user: "query_db_user", pwd: "query_db_password", roles: [{ role: "readWrite", db: "query_db" }]})
进入command_db库use command_db
创建用户db.createUser({user: "command_db_user", pwd: "command_db_password", roles: [{ role: "readWrite", db: "command_db" }]})
退出mongo客户端exit
退出mongo容器exit
删除容器docker rm -f mongo
创建加密访问容器docker run --restart=always --name mongo -d -p 27017:27017 -v /docker/mongo/db/:/data/db mongo --auth
ok
```
### 启动异常排查
若显示以Q开头的类找不到,如java.lang.ClassNotFoundException: com.ywy.learn.query.entry.QUserEntry
maven执行->:query->other->compileQuerydsl生成查询类即可