```
                                           _ _                                 
  ___ ___  _ __ ___  _ __ ___  _   _ _ __ (_) |_ _   _        __ _ _ __  _ __  
 / __/ _ \| '_ ` _ \| '_ ` _ \| | | | '_ \| | __| | | |_____ / _` | '_ \| '_ \ 
| (_| (_) | | | | | | | | | | | |_| | | | | | |_| |_| |_____| (_| | |_) | |_) |
 \___\___/|_| |_| |_|_| |_| |_|\__,_|_| |_|_|\__|\__, |      \__,_| .__/| .__/ 
                                                 |___/            |_|   |_|    
```
# 接口访问地址
```
http://ip:port/smart/doc.html
```
# 如何启动
- 修改配置文件，改成自己的数据库地址/用户名/密码， 改成自己的ES服务器地址/用户名/密码
- 启动类：com.smart.home.Application， 默认启动端口是80，可以通过修改配置文件变更端口

# 开发注意事项
- 如果是可以匿名访问的接口，请在controller方法上加上@AnonAccess注解
- 如果需要某个角色才能访问的接口，请在controller方法加上@RoleAccess注解，参数是角色编码数组
- controller层代码全部在api模块里，pc包下面是后端管理对接的的接口，app是app端的接口，common是通用接口
- cloudservice模块下存放和云服务器交互的代码，比如阿里云OSS，阿里云短信服务啥的
- common模块存放公共的代码和工具类
- core模块存放 mapper/dao，entity，service代码，和数据库打交道的代码都在这里

# 特别强调
- 一律不准自己修改数据库表结构，全部通过liquibase方式修改，请参考core/resources/db.changelog下现有的配置方式

# 公共方法
- 新增一条审核历史记录
```
com.smart.home.modules.other.service.AuditHistoryService.create
```
- 点赞/取消赞统一入口service
```
com.smart.home.service.LikeService
```
- 踩/取消踩统一入口service
```
com.smart.home.service.StmapService
```
