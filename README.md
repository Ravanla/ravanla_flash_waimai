# flash-waimai
- 一个简单的外卖系统，包括手机端，后台管理，api
- 基于spring boot和vue的前后端分离的外卖系统
- 包含手机端，后台管理功能
- 本项目主要供交流学习，不建议商用。
## 技术选型
- 核心框架：Spring Boot
- 数据库层：Spring data jpa/Spring data mongodb
- 数据库连接池：Druid
- 缓存：Ehcache
- 前端：Vue.js
- 数据库：mysql5.5以上,Mongodb建议4.0(不要使用4.2及其已上版本，否则有部分api需要自行调整)

## 模块
- flash-waimai-mobile 手机端站点
- flash-waimai-manage后台管理系统
- flash-waimai-api java接口服务
- flash-waimai-core 底层核心模块
- flash-waimai-generate 代码生成模块

### flash-waimai-api
waimai项目是一个基于Java语言实现的Web应用程序：

waimai项目使用了`Spring Boot`框架进行开发，应用主类是`ApiApplication.java`。
waimai项目使用了`Shiro`框架进行安全认证和授权，配置文件在`config/ShiroConfig.java`中。
waimai项目使用了`Swagger2`框架进行API文档生成，配置文件在`config/Swagger2Configuration.java`中。
waimai项目使用了`Ehcache`框架进行缓存管理，配置文件在`config/EhCacheConfig.java`中。
waimai项目使用了`FastJson`框架进行JSON序列化和反序列化，配置文件在`config/DefaultFastjsonConfig.java`中。
waimai项目包含许多`控制器`文件，按功能分为`business、cms、front、message和system`五大类。
waimai项目包含了一些`缓存监听器`、`配置监听器`以及`Web监听器`。
waimai项目包含了一些`工具类`文件，如`ApiConstants.java`和`SessionUtils.java`。
waimai项目是一个支持多`用户的电商网站`，包含`前台展示`、`后台管理`、`API服务`等功能模块。

根据waimai项目的目录结构，可以初步分析出waimai项目大致的功能和结构：

waimai项目是一个基于Java语言的Web应用程序，使用了Spring Boot框架进行开发和部署。

src/main/java 目录下的代码是项目的核心代码，其中 cn/enilu/flash/api 目录下是项目的API接口代码，包括 controller 控制层、config 配置层、interceptor 拦截器层、listener 监听器层、和 utils 工具类等模块。

src/main/resources 目录下是应用程序的配置文件和模板文件，包括数据库配置、日志配置、启动配置、模板文件等内容。

waimai项目涉及的具体功能模块有：用户管理、商品管理、订单管理、支付管理、系统管理、文件管理、运营管理、消息管理、会员管理等。其中，各个模块的具体实现代码在 controller 控制层中有所体现。

src/main/resources/templates 目录下的 config.xlsx 文件是一个Excel文件，可能是项目的配置文件或者是一些静态数据源。
#### config
1. CorsConfig.java：用于配置跨域资源共享（CORS）策略。
2. DefaultFastjsonConfig.java：用于配置Fastjson序列化和反序列化的一些参数。
3. EhCacheConfig.java：用于配置Ehcache缓存框架。
4. ShiroConfig.java：用于配置Shiro安全框架。
5. Swagger2Configuration.java：用于配置Swagger2 API文档生成器。
6. UserIDAuditorConfig.java：用于配置审计日志记录器。
7. WebConfig.java：用于配置Web应用程序上下文。

#### controller
##### business
1. ActivityController.java：用于处理`活动`相关的请求。
2. AddressController.java：用于处理`地址`相关的请求。
3. AdminController.java：用于处理`管理员`相关的请求。
4. CaptchaController.java：用于`生成验证验证码`。
5. CartController.java：用于处理`购物车`相关的请求。
6. DeliveryController.java：用于处理`配送`相关的请求。
7. EntryController.java：用于处理`入口页面`相关的请求。
8. ExplainController.java：用于处理`说明页面`相关的请求。
9. FoodController.java：用于处理`食品`相关的请求。
10. IndexController.java：用于处理`首页`相关的请求。
11. OrderController.java：用于处理`订单`相关的请求。
12. PaymentController.java：用于处理`支付`相关的请求。
13. PositionController.java：用于`获取地理位置`信息。
14. RatingController.java：用户`评价和评论功能`实现控制器
15. RestaurantController.java：`餐厅信息展示`和`搜索功能`实现控制器
16. ShopController.java: `门店信息展示`和`搜索功能`实现控制器
17. User2Controller: `用户信息管理功能`实现控制器 
18. WechatControlle: 微信公众号接口

##### cms
1. ArticleMgrController.java：用于处理`文章`管理相关的请求。
2. BannerMgrController.java：用于处理`轮播图`管理相关的请求。
3. ChannelMgrController.java：用于处理`频道`管理相关的请求。
4. ContactsController.java：用于处理`联系人`信息相关的请求。
5. FileMgrController.java：用于处理`文件`管理相关的请求。

这些Java类文件中，每一个都对应着一个具体业务逻辑，通过调用其他模块（如Service、Dao等）来完成具体业务逻辑，并将结果返回给客户端。

这些控制器是整个内容管理系统的核心部分，负责处理客户端请求并返回相应的结果。
例如，ArticleMgrController.java可以实现文章列表展示、添加、编辑和删除等功能；
BannerMgrController.java可以实现轮播图列表展示、添加、编辑和删除等功能；
ChannelMgrController.java可以实现频道列表展示、添加、编辑和删除等功能；
ContactsController.java可以实现联系人信息列表展示、添加、编辑和删除等功能；
FileMgrController.java可以实现文件上传和下载等功能。

##### officialsite
这个目录下的文件主要是用于实现前端页面相关的控制器。
具体来说，这个目录下包含了一些Java类文件，每个Java类文件都对应着一个具体的业务逻辑。

例如，在officialsite子目录中，OffcialSiteController.java可以处理`官网`相关的请求；

OffcialSiteProductController.java可以处理`官网商品`相关的请求；


##### message
##### system
#### interceptor
1. CorsInterceptor.java：用于处理跨域请求。
2. LogInterceptor.java：用于记录请求日志。
3. PermissionInterceptor.java：用于权限控制。
4. RateLimitInterceptor.java：用于限流控制。

这些拦截器可以在请求到达Controller之前或者之后进行一些处理，

例如记录日志、验证权限、限制访问频率等等。

这些拦截器可以通过配置文件来启用或禁用，并且可以按照一定的顺序进行调用。
##### listener
1. CacheListener.java：用于监听缓存事件。
2. ConfigListener.java：用于监听配置文件变化事件。
3. WebListener.java：用于监听Web应用程序生命周期事件。

这些监听器可以在特定的事件发生时执行一些操作，

例如在缓存中添加、删除或更新数据时执行一些操作，或者在配置文件发生变化时重新加载配置等等。

这些监听器可以通过配置文件来启用或禁用，并且可以按照一定的顺序进行调用。

##### utils
1. ApiConstants.java：用于定义API接口相关的常量。
2. SessionUtils.java：用于管理Session相关的操作。
3. JwtTokenUtil.java：用于生成和解析JWT Token。
4. PasswordUtils.java：用于加密和验证密码。

这些工具类可以在应用程序中被其他模块调用，

例如在Controller中调用ApiConstants.java中定义的常量、在Service中调用SessionUtils.java来管理Session等等。

这些工具类可以提高代码复用性和可维护性，并且可以使代码更加简洁易懂。

### flash-waimai-core

#### bean
在Java中，Bean是一种特殊的Java类，它用于封装数据。
在项目中，Bean通常用于定义应用程序中的数据模型。
如AppConfiguration.java和Const.java等，它们用于定义应用程序中的`常量、配置信息和数据模型`等。

使用Bean可以将数据和业务逻辑分离开来，使得代码更加清晰、易于维护。
同时，Bean还可以通过`getter和setter`方法来访问和修改数据，提高了代码的可读性和可维护性。

在项目中，Bean扮演着非常重要的角色。
它们提供了应用程序中各种数据模型的定义，并且`被其他组件（如Service、Controller等）`所使用。
因此，在开发过程中需要仔细设计和实现Bean，并且保证其正确性和稳定性。
#### EhcacheCache
在项目中，cache目录用于定义缓存相关的接口和实现类。

具体来说，该目录下的Cache.java接口定义了缓存相关的方法，

如put、get、remove等；而EhcacheCache.java则是Cache.java接口的实现类，用于使用Ehcache作为缓存。


需要注意的是，在使用缓存时需要考虑到缓存与数据库之间的一致性问题。

当数据库中的数据发生变化时，需要及时更新缓存中对应的数据。

否则，在读取数据时可能会出现脏数据或者不一致的情况。

因此，在设计和实现缓存时需要仔细考虑这些问题，并且保证其正确性和稳定性。

#### core
在项目中，core目录用于定义应用程序的核心组件，包括业务日志、用户权限和Shiro框架中用户信息等。

具体来说，该目录下的BussinessLog.java接口定义了业务日志相关的方法，如记录日志、查询日志等；
而Permission.java接口定义了用户权限相关的方法，如检查权限、获取权限等；
而ShiroUser.java则是Shiro框架中用户信息相关的Java类。

这些核心组件是应用程序中非常重要且基础性质的组成部分。它们提供了业务日志记录、用户权限管理以及Shiro框架中用户信息管理等核心功能。
在开发过程中需要仔细设计和实现这些组件，并且保证其正确性和稳定性。
##### aop
在项目中，aop目录用于定义AOP（面向切面编程）相关的组件。

具体来说，该目录下的LogAop.java类是一个切面类，用于在业务方法执行前后记录日志。

AOP是一种编程范式，它可以将应用程序中的横切关注点（如`日志记录、性能统计、安全控制`等）与业务逻辑分离开来。
通过使用AOP，可以将这些横切关注点独立出来，并且在需要时动态地将它们织入到业务逻辑中。这样可以提高代码的可重用性和可维护性。

在项目中，LogAop.java类使用了Spring AOP框架来实现AOP功能。
具体来说，它使用了@Before和@AfterReturning注解来分别表示在业务方法执行前和执行后需要执行的代码。
同时，它还使用了@Pointcut注解来定义切入点表达式，表示哪些方法需要被拦截并织入日志记录功能。

##### factory
在项目中，factory目录用于定义工厂类相关的组件。
具体来说，该目录下的Contrast.java、DictFieldWarpperFactory.java和UserFactory.java类
分别用于`生成字典字段包装器`、`用户对象`和`对比对象`。

工厂类是一种常见的设计模式，它可以将对象的创建过程封装起来，并且提供一个统一的接口来生成对象。
在应用程序中，工厂类可以提高代码的可重用性和可维护性。

在项目中，Contrast.java、DictFieldWarpperFactory.java和UserFactory.java类
分别使用了`工厂`方法模式和`抽象`工厂模式来实现工厂功能。
具体来说，它们都提供了一个`静态`方法或者`实例`方法来生成相应的对象，并且隐藏了对象创建过程的细节。


##### log
在项目中，日志是一种非常重要的组成部分。它可以记录应用程序的运行状态、错误信息和调试信息等，帮助开发人员快速定位和解决问题。

在项目中，日志记录功能由log4j2框架来实现。
具体来说，在log目录下定义了LogFactory.java、LogManager.java和LogTaskFactory.java类，
分别用于`生成Logger对象`、`管理Logger对象`和`生成异步日志`任务。

##### utils
在项目中，utils目录用于定义一些常用的工具类。具体来说，该目录下的
java类分别提供了`基本类型转换`、`JavaBean操作`、`集合操作`等常用功能。

这些工具类可以帮助开发人员快速实现一些常见的功能，并且提高代码的可重用性和可维护性。需要注意的是，在使用这些工具类时需要仔细考虑其对应用程序性能和内存占用的影响，并且保证其正确性和稳定性。同时，需要根据实际情况选择合适的工具类，并且避免滥用工具类导致代码复杂度增加。

- bean：包含了一些Java类，如AppConfiguration.java和Const.java等，用于定义应用程序中的`常量、配置信息和数据模型`等。
- core：包含了一些Java类，如BussinessLog.java和Permission.java等，用于实现应用程序的`核心功能`，如`日志记录和用户权限管理`等。
- dictmap：包含了一些Java类，如CfgDict.java和CommonDict.java等，用于实现`数据字典`相关的功能。
- factory：包含了一些Java类，如PageFactory.java和UserFactory.java等，用于实现`工厂模式`相关的功能。

#### dao
在项目中，dao目录用于定义数据访问对象（DAO）相关的组件。
具体来说，该目录下的BaseRepository.java、BaseRepositoryFactoryBean.java、
BaseRepositoryImpl.java、DaoConfiguration.java、MongoRepository.java
和MySQLDialect.java等类分别用于`定义DAO接口`、`实现DAO接口`、`配置DAO相关的Bean`等。

DAO是一种常见的设计模式，它可以将`数据访问逻辑封装`起来，并且提供一个统一的接口来访问数据。
在应用程序中，DAO可以提高代码的可重用性和可维护性。

在项目中，BaseRepository.java、BaseRepositoryFactoryBean.java和BaseRepositoryImpl.java类
分别使用了Spring Data JPA框架来实现DAO功能。
具体来说，它们提供了一些基本的CRUD（增删改查）操作，并且支持自定义查询方法。
同时，DaoConfiguration.java类用于配置Spring Data JPA相关的Bean。

#### security
在项目中，security目录用于定义安全相关的组件。
具体来说，该目录下的AccountInfo.java、ApiRealm.java、JwtToken.java、JwtUtil.java、ShiroFactroy.java和
UnauthorizedException.java等类
分别用于实现`用户认证和授权`、`生成和验证JWT令牌`等功能。

在应用程序中，安全是一个非常重要的问题。
为了保护应用程序和用户数据的安全性，需要采取一系列措施来防止恶意攻击和非法访问。
其中，用户认证和授权是最基本的安全措施之一。
通过使用`Shiro框架提供的API`，可以很方便地实现用户认证和授权功能。

同时，在分布式系统中，`JWT（JSON Web Token）令牌`已经成为一种流行的身份验证方式。
通过使用JwtUtil.java类提供的[API](https://juejin.cn/post/7018427997145268255)，可以很方便地生成和验证JWT令牌，并且保证其安全性。
#### service
在项目中，service目录用于定义服务层相关的组件。
具体来说，该目录下的ActionLogService.java、AppInfoService.java、BannerService.java、ChannelService.java、ContactsService.java、DictService.java、FileService.java、MenuService.java、NoticeService.java和RoleService.java
等类分别用于实现`业务逻辑`和`数据访问`等功能。

在应用程序中，服务层是实现业务逻辑的核心部分。
通过定义服务接口和实现类，可以将业务逻辑封装起来，并且提供一个统一的接口来访问服务。
同时，服务层还可以与数据访问层进行交互，从而实现对数据的操作。

在项目中，ActionLogService.java类用于`记录用户操作`日志，
AppInfoService.java类用于`管理应用程序`信息，
BannerService.java类用于`管理轮播图`信息，
ChannelService.java类用于管理`栏目`信息，
ContactsService.java类用于管理`联系人`信息，
DictService.java类用于管理`字典`信息，
FileService.java类用于管理`文件上传和下载`等功能，
MenuService.java类用于管理`菜单`信息，
NoticeService.java类用于管理`通知`信息，
RoleService.java类用于管理`角色`信息等。
#### utils
在项目中，utils目录用于定义工具类。具体来说，
该目录下的BeanUtil.java、DateUtil.java、FileUtil.java、HttpUtil.java、JsonUtil.java、RandomUtil.java、StringUtil.java和XmlUtil.java等类
分别用于实现`Java对象`和`JSON/XML数据`之间的`转换、日期时间处理、文件操作、HTTP请求和响应处理`等功能。

在应用程序中，工具类是一种常见的代码复用方式。
通过定义通用的工具类，可以避免重复编写相似的代码，并且提高代码的可读性和可维护性。

在项目中，BeanUtil.java类用于`实现Java对象之间`的属性拷贝，
DateUtil.java类用于`日期时间格式化`和解析，
FileUtil.java类用于`文件上传和下载`等功能，
HttpUtil.java类用于`发送HTTP请求`和`处理响应`结果，
JsonUtil.java类用于`JSON数据格式化和解析`，
RandomUtil.java类用于`生成随机数和字符串`等功能，
StringUtil.java类用于`字符串处理`等功能，
XmlUtil.java类用于`XML数据格式化和解析`。
#### warpper
在项目中，warpper目录用于定义包装器类。
具体来说，该目录下的BaseControllerWarpper.java、DeptWarpper.java、DictWarpper.java、LogWarpper.java、MenuWarpper.java、NoticeWrapper.java、RoleWarpper.java和UserWarpper.java等类
分别用于对`控制器返回`的数据进行`包装和处理`。

在应用程序中，包装器是一种常见的数据处理方式。
通过定义包装器类，可以对控制器返回的数据进行`二次加工和处理`，并且提高代码的可读性和可维护性。

在项目中，BaseControllerWarpper.java类是所有包装器类的`基类`，
DeptWarpper.java类用于对`部门信息`进行包装和处理，
DictWarpper.java类用于对`字典信息`进行包装和处理，
LogWarpper.java类用于对`操作日志信息`进行包装和处理，
MenuWarpper.java类用于对`菜单信息`进行包装和处理，
NoticeWrapper.java类用于对`通知信息`进行包装和处理，
RoleWarpper.java类用于对`角色信息`进行包装和处理，
UserWarpper.java类用于对`用户信息`进行包装和处理等。

### flash-waimai-generate
当然可以，flash-waimai-generate是一个用于生成代码的项目。
包含了一些必要的组件，如插件、pom.xml文件和各种源代码文件。
这个项目的主要功能是根据数据库表结构自动生成Java代码，
包括实体类、控制器、服务类和视图等。这样可以大大提高开发效率，减少手动编写重复代码的工作量。
#### 

#### 

#### 

根据waimai项目的目录结构树，可以推测出waimai项目是一个 Java Web 项目。下面是waimai项目可能的功能和业务模块：

业务模块：waimai项目包含了许多业务模块，例如 CMS（内容管理系统）、Message（消息管理系统）、System（系统管理系统）等等。

数据访问层：waimai项目有一个 DAO 层，包括 cms、message 和 system 三个模块，用于访问数据库。

服务层：waimai项目的服务层包括 cms、front、message、system 和 wechat 五个模块，提供了业务逻辑处理和数据操作的服务。

工具类：waimai项目的工具类包括 cache、factory 和 gps 三个模块，用于缓存、创建对象和处理 GPS 数据等。

安全模块：waimai项目包含了一个安全模块，用于处理`安全`相关的逻辑。

测试模块：waimai项目有一个测试模块，用于测试服务层的功能。

配置文件：waimai项目的配置文件包括主配置文件（resources 目录下的配置文件）和代码模板（resources/code 目录下的模板文件）。

总的来说，waimai项目是一个基于 Java 技术栈的 Web 应用程序，它提供了多个业务模块，数据访问层、服务层和工具类等组件，以及安全模块和测试模块等支持。

1. 数据库操作：该模块提供了一些数据库操作的工具类和接口，例如`BaseRepository`、`BaseService`等，用于简化数据库操作。

2. 缓存管理：该模块提供了一些缓存管理的工具类和接口，例如`CacheUtil`、`EhcacheUtil`等，用于简化缓存操作。

3. 工具类：该模块还提供了一些常用的工具类，例如DateUtil、StringUtil等，用于简化开发过程中的常见操作。

4. 异常处理：该模块还提供了一些异常处理相关的类和接口，例如GlobalExceptionHandler、BusinessException等，用于统一处理异常情况。

5. 实体类：该模块定义了一些实体类，例如`User、Role`等，在整个项目中被广泛使用。

6. 配置文件：该模块包含了一些配置文件，例如`application.yml、logback.xml`等，用于配置项目运行时所需的参数和日志输出方式。

7. bean：该包下定义了一些JavaBean类，例如AppConfiguration、ShiroUser等。

8. constant：该包下定义了一些常量类，例如Const、State等。

9. dictmap：该包下定义了一些字典映射相关的类和接口，例如CfgDict、CommonDict等。

10. exception：该包下定义了一些异常处理相关的类和接口，例如BusinessException、GlobalExceptionHandler等。

11. factory：该包下定义了一些工厂类和接口，例如CacheFactory、DataSourceFactory等。

12. log：该包下定义了一些日志相关的类和接口，例如BussinessLog、LogManager等。

13. permission：该包下定义了一些权限管理相关的类和接口，例如PermissionCheck、PermissionService等。

14. utils：该包下定义了一些工具类和接口，用于简化开发过程中的常见操作，例如DateUtil、StringUtil等。

- bean：该目录下定义了一些JavaBean类，例如AppConfiguration、ShiroUser等。

- constant：该目录下定义了一些常量类，例如Const、State等。

- dictmap：该目录下定义了一些字典映射相关的类和接口，例如CfgDict、CommonDict等。

- exception：该目录下定义了一些异常处理相关的类和接口，例如BusinessException、GlobalExceptionHandler等。

- factory：该目录下定义了一些工厂类和接口，例如CacheFactory、DataSourceFactory等。

- log：该目录下定义了一些日志相关的类和接口，例如BussinessLog、LogManager等。

- permission：该目录下定义了一些权限管理相关的类和接口，例如PermissionCheck、PermissionService等。

- utils：该目录下定义了一些工具类和接口，用于简化开发过程中的常见操作，例如DateUtil、StringUtil等。

[1] "flash-waimai-master...
[2] " ├─ import.s...
[3] " └─ De...
[4] " ├─ java│...
[5] " └─ flash│...

### flash-waimai-manage


## 快速开始
- 数据存储采用了mysql和mongodb，其中基础管理配置功能数据使用mysql，业务数据使用mongodb存储。
- 创建mysql数据库
```sql
CREATE DATABASE IF NOT EXISTS waimai DEFAULT CHARSET utf8 COLLATE utf8_general_ci; 
CREATE USER 'waimai'@'%' IDENTIFIED BY 'waiMAI@123';
GRANT ALL privileges ON waimai.* TO 'waimai'@'%';
flush privileges;
```
- mysql数据库创建好了之后，启动flash-waimai-api服务，会自动初始化数据，无需开发人员自己手动初始化数据
- 安装mongodb并创建数据库:flash-waimai
使用mongorestore命令  导入mongodb数据,由于测试数据量较大，打包放在了百度云盘：链接：https://pan.baidu.com/s/1lOvhN1-Y1M0-FZAwGHus7Q  提取码：4qz7 。下载后将文件解压到d:\\elm，如下命令导入数据：
                                              
```
mongorestore.exe -d flash-waimai d:\\elm
```
- 下载项目测试数据的图片（商家和食品图片）： 链接：https://pan.baidu.com/s/15uiA8hUCwdZv6Bycn1y_yg 提取码：cvas   ，将图片存放到t_sys_cfg表中system.file.upload.path配置的目录下
- 启动api服务：
    - 进入flash-waimai-api模块
    - 直接运行ApiApplication主类启动api服务
- 启动管理平台:
    - 进入flash-waimai-manage目录：
    - 运行 npm install --registry=https://registry.npm.taobao.org
    - 运行npm run dev
    - 启动成功后访问 http://localhost:9528 ,登录，用户名密码:admin/admin
- 启动手机端:
    - 进入flash-waimai-mobile目录：    
    - 运行 npm install --registry=https://registry.npm.taobao.org
    - 运行npm run dev
    - 启动成功后访问 http://localhost:8000

## 运行效果图
- 后台管理
![admin](doc/admin.gif)
- 手机端    
![mobile](doc/mobile.gif)

## 在线演示
- 查看演示系统请不要随意删除数据
- 后台管理：[http://waimai-admin.microapp.store](http://waimai-admin.microapp.store)
- 手机端:[http://waimai-mobile.microapp.store](http://waimai-mobile.microapp.store)

## 文档
[https://microapp.gitee.io/flash-waimai](https://microapp.gitee.io/flash-waimai)
## 开发进度
- flash-waimai-manage [初步完成]
- flash-waimai-mobile[完善中]

## 鸣谢
- 感谢[bailicangdu](https://github.com/bailicangdu),[enilu](https://github.com/enilu),本项目参考参考借鉴了[vue2-elm](https://github.com/bailicangdu/vue2-elm)，[web-flash](https://github.com/enilu/web-flash)，[vue2-manage](https://github.com/bailicangdu/vue2-manage)
- waimai项目克隆并扩展自[web-flash](https://github.com/enilu/web-flash),所以开发的时候多看看web-flash的[在线文档](http://enilu.gitee.io/web-flash)
- waimai项目不适用与商城系统解决方案，如果有商城系统需求，可以查看另外一个商城的开源系统[https://gitee.com/microapp/linjiashop](https://gitee.com/microapp/linjiashop)(支持H5,微信小程序,APP)

## 交流
- qq群： 936439613；qq群仅为方便网友互相交流，作者基本不会在群里回复，如果需要跟作者提问题，可以通过项目主页提issue；qq群禁止发广告，发者立删


#   r a v a n l a _ f l a s h _ w a i m a i 
 
 #   r a v a n l a _ f l a s h _ w a i m a i 
 
 