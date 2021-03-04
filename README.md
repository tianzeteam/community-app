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
- 图片审核公共方法
```
com.smart.home.cloud.qcloud.auditor.ImageAuditor.auditorResult
```
- 文本审核公共方法
```
com.smart.home.cloud.qcloud.auditor.ContentAuditor.auditorResult
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

# es 安装(centos7)
```
cd /home/app/community/server/es
解压：tar -xzvf elasticsearch-6.8.3.tar.gz
cd /elasticsearch-6.8.3/bin
groupadd esgroup
useradd esuser -g esgroup -p root
cd /home/app/community/server/es
chown -R esuser:esgroup elasticsearch-6.8.3
su esuser
./bin/elasticsearch
后台运行：./bin/elasticsearch -d
测试连接：curl 127.0.0.1:9200
```
# nodejs+npm 安装(centos7)
```
curl --silent --location https://rpm.nodesource.com/setup_10.x | bash -
yum install -y nodejs
npm install -g cnpm --registry=https://registry.npm.taobao.org
npm install
node -v
npm -v
```
# maven 安装(centos7)
```
官网地址: http://maven.apache.org/download.cgi
cd /usr/local
tar -zxvf apache-maven-3.6.1-bin.tar.gz
vi /etc/profile
export MAVEN_HOME=/usr/local/apache-maven-3.6.1
export PATH=$MAVEN_HOME/bin:$PATH 
source /etc/profile
mvn -v 
```

# elasticsearch-head 安装(centos7)
```
(1)elasticsearch-head是一款开源软件，被托管在github上面，所以如果我们要使用它，必须先安装git，通过git获取elasticsearch-head
(2)运行elasticsearch-head会用到grunt，而grunt需要npm包管理器，所以nodejs是必须要安装的
(3)elasticsearch5.0之后，elasticsearch-head不做为插件放在其plugins目录下了。
使用git拷贝elasticsearch-head到本地
cd /usr/local/
 git clone git://github.com/mobz/elasticsearch-head.git
(4)安装elasticsearch-head依赖包
[root@localhost local]# npm install -g grunt-cli
[root@localhost _site]# cd /usr/local/elasticsearch-head/
[root@localhost elasticsearch-head]# cnpm install
(5)修改Gruntfile.js
[root@localhost _site]# cd /usr/local/elasticsearch-head/
[root@localhost elasticsearch-head]# vi Gruntfile.js
在connect-->server-->options下面添加：hostname:’*’，允许所有IP可以访问
(6)修改elasticsearch-head默认连接地址
[root@localhost elasticsearch-head]# cd /usr/local/elasticsearch-head/_site/
[root@localhost _site]# vi app.js
将this.base_uri = this.config.base_uri || this.prefs.get("app-base_uri") || "http://localhost:9200";中的localhost修改成你es的服务器地址
(7)配置elasticsearch允许跨域访问
打开elasticsearch的配置文件elasticsearch.yml，在文件末尾追加下面两行代码即可：
http.cors.enabled: true
http.cors.allow-origin: "*"
(8)打开9100端口
[root@localhost elasticsearch-head]# firewall-cmd --zone=public --add-port=9100/tcp --permanent
重启防火墙
[root@localhost elasticsearch-head]# firewall-cmd --reload
(9)启动elasticsearch
(10)启动elasticsearch-head
[root@localhost _site]# cd /usr/local/elasticsearch-head/
[root@localhost elasticsearch-head]# nohup node_modules/grunt/bin/grunt server  &
(11)访问elasticsearch-head
关闭防火墙：systemctl stop firewalld.service
浏览器输入网址：http://1.15.72.79:9100/
```

# ES中文分词 安装(centos7)
```
同期版本下载地址：https://github-releases.githubusercontent.com/2993595/91df8f00-d80e-11e9-9727-19c6dc1901e0?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAIWNJYAX4CSVEH53A%2F20210302%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20210302T120626Z&X-Amz-Expires=300&X-Amz-Signature=cea71ef1d1fea738cfbdbb262fd0dcfc71d30a03c101a051c1cec75f8a8b906e&X-Amz-SignedHeaders=host&actor_id=19146484&key_id=0&repo_id=2993595&response-content-disposition=attachment%3B%20filename%3Delasticsearch-analysis-ik-6.8.3.zip&response-content-type=application%2Foctet-stream
cd /home/app/community/server/es/elasticsearch-6.8.3/plugins
把下载好的es中文分词ZIP包上传至这个目录
unzip elasticsearch-analysis-ik-6.8.3.zip
cd ../bin
./elasticsearch -d  
测试验证：
POST JSON格式请求  http://1.15.72.79:9200/_analyze    
参数：{
    "analyzer":"ik_max_word",
    "text":"中华人"
}
```