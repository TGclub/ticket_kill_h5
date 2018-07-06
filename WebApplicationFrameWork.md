# Web应用框架设计

## MVC模式

MVC模式即Model-View-Controller模式，应用于应用程序的分层开发

+ Model - 模型代表一个存储数据的对象。可以带有逻辑，在数据变化时更新控制器
+ View - 视图代表模型包含的数据的可视化
+ Controller - 控制器作用于模型和视图上。其控制数据流向模型对象，并在数据变化时更新视图，它使视图和模型分离。

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