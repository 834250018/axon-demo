## Axon Project 

axon框架demo

跑起来需要本地启动mongodb

模仿着旧公司项目搭建的axon框架,目录结构没有整理

使用了lombok插件

里面还有一个别人的netty源码,可以直接使用

2019/7/17 加入sonarQube配置 4bugs,10Vulnerabilities,106CodeSmells

加入https协议,同时支持http

生成本地测试用证书keytool -genkey -alias tomcat -keypass 123456 -keyalg RSA -keysize 1024 -validity 365 -keystore e:/tomcat.keystore -storepass 123456

2019/7/20 引入logback框架,对日志跟异常切面调整