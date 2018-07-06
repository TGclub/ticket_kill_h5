# Web应用框架设计

## MVC模式

MVC模式即Model-View-Controller模式，应用于应用程序的分层开发

+ Model - 模型代表一个存储数据的对象。可以带有逻辑，在数据变化时更新控制器。即是业务逻辑处理的部分，实现了经典三层中BLL层和DAL层的功能。
+ View - 视图代表模型包含的数据的可视化。MVC中视图除了HTML基本元素之外，还包括XML等标识性语言。
+ Controller - 控制器作用于模型和视图上。其控制数据流向模型对象，并在数据变化时更新视图，它使视图和模型分离。用户在视图中提交申请表单时，控制器将调用合适的模型处理。

MVC对链接数据库、业务逻辑处理、显示解耦，使用3部分分离的方式组织代码，在改进客制化界面和交互时无需重写业务逻辑。

![MVC](https://raw.githubusercontent.com/ACERY1/webHomework/master/screenshots/MVC.png)

本项目目录对应

```
com
└── wizz
	└── seckill
		├── Config
		├── Controller
        ├── Exception
        ├── Mapper
        ├── Model
        ├── SeckillApplication.java
        ├── Service
        └── Util
```

+ Model 置于com.wizz.seckill.Model下
+ 其余部分为Controller，不包含View



## spring  boot 框架的使用

![Spring Bootçç¥è¯ç¹](https://raw.githubusercontent.com/rootsongjc/kubernetes-handbook/master/images/spring-boot-note-spots.png)

Features

- Create stand-alone Spring applications
- Embed Tomcat, Jetty or Undertow directly (no need to deploy WAR files)
- Provide opinionated 'starter' dependencies to simplify your build configuration
- Automatically configure Spring and 3rd party libraries whenever possible
- Provide production-ready features such as metrics, health checks and externalized configuration
- Absolutely no code generation and no requirement for XML configuration

You can also [join the Spring Boot community on Gitter](https://gitter.im/spring-projects/spring-boot)!