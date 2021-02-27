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
- 有趣/取消有趣统一入口service
```
com.smart.home.service.FunService
```
- 收藏/取消收藏统一入口service
```
com.smart.home.service.CollectService
```
- 新增一条举报记录
```
com.smart.home.modules.other.service.ReportHistoryService.create
```
# postgresql数据库安装(centos7)
```
# Install the repository RPM:
sudo yum install -y https://download.postgresql.org/pub/repos/yum/reporpms/EL-7-x86_64/pgdg-redhat-repo-latest.noarch.rpm

# Install PostgreSQL:
sudo yum install -y postgresql13-server

# Optionally initialize the database and enable automatic start:
sudo /usr/pgsql-13/bin/postgresql-13-setup initdb
sudo systemctl enable postgresql-13
sudo systemctl start postgresql-13

su - postgres
create user test_user with password 'abc123';            // 创建用户
create database test_db owner test_user;                 // 创建数据库
grant all privileges on database test_db to test_user;   // 授权
\q
/var/lib/pgsql/13/data/postgresql.conf // listen_address = '*'
/var/lib/pgsql/13/data/pg_hba.conf
systemctl restart postgresql-13.service
systemctl start postgresql-10.service     // 启动服务
systemctl stop postgresql-10.service      // 关闭服务
systemctl restart postgresql-10.service   // 重启服务
systemctl status postgresql-10.service    // 查看状态
```
# jdk8安装(centos7)
```
yum search java|grep jdk
yum install java-1.8.0-openjdk.x86_64
java -version
```
