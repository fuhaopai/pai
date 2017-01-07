#项目模块分层图
![image](https://github.com/fuhaojun/pai/blob/master/doc/image/module.png)
#系统结构设计图
![image](https://github.com/fuhaojun/pai/blob/master/doc/image/code.png)
#代码生成器
[someLabel](https://github.com/fuhaojun/pai/codegen.md)

#base-paren基础接口、工具和数据存储

-base-api:
 提供最为基础的常量和接口。还有一个关键类OnlineHolder中通过ThreadLocal<HttpSession>存储session，方便其他上层模块快速获取session，通过filter维护OnlineHolder类以便系统每一个线程都能拿到session中信息。

-base-core:
 依赖base-api，针对部分接口提供实现，并提供大量的工具类和基础服务类

-base-db:使用MyBatis技术对所有关系数据库提供访问支持，使用方言处理各种数据库的差异。支持数据库连接池和动态数据源切换

#service-parent这部分是平时工作中的一些积累，融合到本系统中，按需选择依赖

-service-mq:目前集成active-mq

-service-redis:缓存

-service-quartz:定时器

-service-solr：搜索

-service-getui:个推推送

-service-image:阿里云图片上传

-service-mail:邮件

#biz-parent具体的业务模块
-biz-frame:提供最基础的接口定义和一些常量。

-biz-auth:用户权限相关模块

-biz-article:（待开发，具体的文章相关模块）

-biz-func:(待开发，原型图发现-具体的每一个功能应该都会抽出一个业务模块出来)

#app-parent应用层
-app-web:提供基础的web层公共接口、抽象类和相关服务、支持（比如filter、context、Interceptor等）

-app-admin:后台web项目

-app-api:（待开发，为web,wap,app提供RESTful规范的接口）

-app-front:(待开发，为web提供controller对接api)

-app-wap:(待开发，同上)
