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


### 为什么要使用MVC

#### mvc的优缺点

**优点**

* 耦合性低
  * 视图层和业务层分离，这样就允许更改视图层代码而不用重新编译模型和控制器代码，同样，一个应用的业务流程或者业务规则的改变只需要改动MVC的模型层即可。因为模型与控制器和视图相分离，所以很容易改变应用程序的数据层和业务规则。
  * 模型是自包含的，并且与控制器和视图相分离，所以很容易改变应用程序的数据层和业务规则。如果把数据库从MySQL移植到[Oracle](https://baike.baidu.com/item/Oracle)，或者改变基于RDBMS数据源到[LDAP](https://baike.baidu.com/item/LDAP)，只需改变模型即可。一旦正确的实现了模型，不管数据来自数据库或是LDAP服务器，视图将会正确的显示它们。由于运用MVC的应用程序的三个部件是相互独立，改变其中一个不会影响其它两个，所以依据这种设计思想能构造良好的[松耦合](https://baike.baidu.com/item/%E6%9D%BE%E8%80%A6%E5%90%88)的构件。
* 生命周期成本低
  * MVC是开发和维护用户接口的技术含量降低
* 部署快
  * 使用MVC模式使开发时间得到相当大的所见，使得我们这个项目里后端集中精力于业务逻辑，前端集中精力于表现形式
* 可维护性高
  * 分离视图层和业务逻辑层也使得WEB应用更易于维护和修改

**缺点**

* 视图与控制器间的过于紧密的连接
* 视图对模型数据的低效率访问
* 没有明确的定义

#### 使用MVC的原因

* 在我们这个项目中，前端的逻辑稍微的简单一些，不太涉及到数据不能及时显示出现空白的问题，虽然有高并发的要求，前端基本上只用来展示数据和页面跳转，数据的存储以及处理全都是在后端，考虑到MVC的一些优缺点，我们就决定使用MVC来构建整个项目。


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