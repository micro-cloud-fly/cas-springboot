# 第一 生成keystore文件
```shell
keytool -genkey -v -alias tomcat -keyalg RSA -keystore /Users/java0904/tomcat.keystore
```
其中<code>/Users/java0904/tomcat.keystore</code>是保存的地址
顺利的话应该提示(我这里设置的密码口令是123456，后面会多次用到)
```shell
java0904@weigongdeMacBook-Pro ~ % keytool -genkey -v -alias tomcat -keyalg RSA -keystore /Users/java0904/tomcat.keystore

输入密钥库口令:  
再次输入新口令: 
您的名字与姓氏是什么?
  [Unknown]:  java.cas.juhe.cn
您的组织单位名称是什么?
  [Unknown]:  java.cas.juhe.cn
您的组织名称是什么?
  [Unknown]:  java.cas.juhe.cn
您所在的城市或区域名称是什么?
  [Unknown]:  suzhou
您所在的省/市/自治区名称是什么?
  [Unknown]:  jiangsu
该单位的双字母国家/地区代码是什么?
  [Unknown]:  cn
CN=java.cas.juhe.cn, OU=java.cas.juhe.cn, O=java.cas.juhe.cn, L=suzhou, ST=jiangsu, C=cn是否正确?
  [否]:  y

正在为以下对象生成 2,048 位RSA密钥对和自签名证书 (SHA256withRSA) (有效期为 90 天):
	 CN=java.cas.juhe.cn, OU=java.cas.juhe.cn, O=java.cas.juhe.cn, L=suzhou, ST=jiangsu, C=cn
输入 <tomcat> 的密钥口令
	(如果和密钥库口令相同, 按回车):  
[正在存储/Users/java0904/tomcat.keystore]

Warning:
JKS 密钥库使用专用格式。建议使用 "keytool -importkeystore -srckeystore /Users/java0904/tomcat.keystore -destkeystore /Users/java0904/tomcat.keystore -deststoretype pkcs12" 迁移到行业标准格式 PKCS12。
java0904@weigongdeMacBook-Pro ~ % keytool -importkeystore -srckeystore /Users/java0904/tomcat.keystore -destkeystore /Users/java0904/tomcat.keystore -deststoretype pkcs12
输入源密钥库口令:  
已成功导入别名 tomcat 的条目。
已完成导入命令: 1 个条目成功导入, 0 个条目失败或取消

Warning:
已将 "/Users/java0904/tomcat.keystore" 迁移到 Non JKS/JCEKS。将 JKS 密钥库作为 "/Users/java0904/tomcat.keystore.old" 进行了备份。
java0904@weigongdeMacBook-Pro ~ % 

```
### 此时在文件夹下面将会生成两个文件，如下所示
```shell

-rw-r--r--    1 java0904  staff       2631  3  4 16:39 tomcat.keystore
-rw-r--r--    1 java0904  staff       2288  3  4 16:39 tomcat.keystore.old
```
# 第二 生成cer文件，即在本地生成证书
```shell
keytool -export -trustcacerts -alias tomcat -file /Users/java0904/tomcat.cer -keystore /Users/java0904/tomcat.keystore
```
顺利的话应该提示
```shell
java0904@weigongdeMacBook-Pro ~ % keytool -export -trustcacerts -alias tomcat -file /Users/java0904/tomcat.cer -keystore /Users/java0904/tomcat.keystore
输入密钥库口令:  
存储在文件 </Users/java0904/tomcat.cer> 中的证书
java0904@weigongdeMacBook-Pro ~ % 

```
# 第三 将生成的证书导入到jdk中
```shell
sudo keytool -import -trustcacerts -alias tomcat -file /Users/java0904/tomcat.cer -keystore "/Library/Java/JavaVirtualMachines/jdk1.8.0_221.jdk/Contents/Home/jre/lib/security/cacerts" -storepass changeit
```
如果你之前导入过这个别名，那么会提示你
```shell

java0904@weigongdeMacBook-Pro ~ % keytool -export -trustcacerts -alias tomcat -file /Users/java0904/tomcat.cer -keystore /Users/java0904/tomcat.keystore
输入密钥库口令:  
存储在文件 </Users/java0904/tomcat.cer> 中的证书
java0904@weigongdeMacBook-Pro ~ % sudo keytool -import -trustcacerts -alias tomcat -file /Users/java0904/tomcat.cer -keystore "/Library/Java/JavaVirtualMachines/jdk1.8.0_221.jdk/Contents/Home/jre/lib/security/cacerts"
Password:
Sorry, try again.
Password:
输入密钥库口令:  
keytool 错误: java.lang.Exception: 证书未导入, 别名 <tomcat> 已经存在
```
此时可以用如下命令查看已经存在的证书
```shell
sudo keytool -list -file /Users/java0904/tomcat.cer -keystore "/Library/Java/JavaVirtualMachines/jdk1.8.0_221.jdk/Contents/Home/jre/lib/security/cacerts" -storepass changeit
```
此时我需要删除已经存在的，重新覆盖，可以用如下命令删除
```shell
sudo keytool -delete -alias tomcat -file /Users/java0904/tomcat.cer -keystore "/Library/Java/JavaVirtualMachines/jdk1.8.0_221.jdk/Contents/Home/jre/lib/security/cacerts" -storepass changeit
```
正确的话，应该会提示
```shell
java0904@weigongdeMacBook-Pro ~ % sudo keytool -import -trustcacerts -alias tomcat -file /Users/java0904/tomcat.cer -keystore "/Library/Java/JavaVirtualMachines/jdk1.8.0_221.jdk/Contents/Home/jre/lib/security/cacerts" -storepass changeit
所有者: CN=java.cas.juhe.cn, OU=java.cas.juhe.cn, O=java.cas.juhe.cn, L=suzhou, ST=jiangsu, C=cn
发布者: CN=java.cas.juhe.cn, OU=java.cas.juhe.cn, O=java.cas.juhe.cn, L=suzhou, ST=jiangsu, C=cn
序列号: 225de191
有效期为 Thu Mar 04 16:38:55 CST 2021 至 Wed Jun 02 16:38:55 CST 2021
证书指纹:
	 MD5:  E9:2D:58:0A:95:79:E5:92:1D:DB:14:62:AA:B6:88:48
	 SHA1: 1A:3E:1D:9C:90:F5:60:C5:84:46:C1:12:2B:9A:2C:8D:E7:2E:C2:FC
	 SHA256: 80:C4:C8:BA:8A:15:4F:5F:D7:FA:50:EE:97:50:C5:01:3F:21:98:2D:05:20:1F:9B:B6:EC:9C:AA:CC:A8:40:93
签名算法名称: SHA256withRSA
主体公共密钥算法: 2048 位 RSA 密钥
版本: 3

扩展: 

#1: ObjectId: 2.5.29.14 Criticality=false
SubjectKeyIdentifier [
KeyIdentifier [
0000: 56 5E 21 1B 36 1B 41 62   C3 8C DC BD A7 1A 06 0C  V^!.6.Ab........
0010: CB C0 54 32                                        ..T2
]
]

是否信任此证书? [否]:  y
证书已添加到密钥库中

```
# 第四，配置tomcat
在conf/server.xml文件中加入
```shell
<Connector port="8443" protocol="HTTP/1.1" SSLEnabled="true"  
              maxThreads="150" scheme="https" secure="true"  
              clientAuth="false" sslProtocol="TLS"   
       keystoreFile="/Users/java0904/tomcat.keystore"  
       keystorePass="123456" />  
```
# 第五，启动cas-server服务
下载cas-server服务，大家可以网上搜一下cas-server war包，把这个war包放到tomcat下的webapp目录
# 第六，启动tomcat
```shell
java0904@weigongdeMacBook-Pro bin % pwd
/Users/java0904/Downloads/apache-tomcat-9.0.30/bin
java0904@weigongdeMacBook-Pro bin % 
java0904@weigongdeMacBook-Pro bin % ./startup.sh 
Using CATALINA_BASE:   /Users/java0904/Downloads/apache-tomcat-9.0.30
Using CATALINA_HOME:   /Users/java0904/Downloads/apache-tomcat-9.0.30
Using CATALINA_TMPDIR: /Users/java0904/Downloads/apache-tomcat-9.0.30/temp
Using JRE_HOME:        /Library/Internet Plug-Ins/JavaAppletPlugin.plugin/Contents/Home
Using CLASSPATH:       /Users/java0904/Downloads/apache-tomcat-9.0.30/bin/bootstrap.jar:/Users/java0904/Downloads/apache-tomcat-9.0.30/bin/tomcat-juli.jar
Tomcat started.
```
此时测试https://localhost:8443/看看是否可以访问，可以的话，则证明支持https协议
，此时再访问https://localhost:8443/cas/login，将得到如下页面
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210304171904282.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3NpbGtfamF2YQ==,size_16,color_FFFFFF,t_70)
用户名是casuser，密码是Mellon
此时配置hosts文件
```shell
127.0.0.1 java.cas.juhe.cn
```
此时用域名去访问
[https://java.cas.juhe.cn:8443/cas/login](https://java.cas.juhe.cn:8443/cas/login)一样是可以访问的;
### 服务端的配置更改，很关键
#### 改HTTPSandIMAPS-10000001.json
接下来需要在/Users/java0904/Downloads/apache-tomcat-9.0.30/webapps/cas/WEB-INF/classes/services这个目录下面，修改HTTPSandIMAPS-10000001.json这个文件，增加http的支持，如下
```json
{
  "@class" : "org.apereo.cas.services.RegexRegisteredService",
  "serviceId" : "^(https|http|imaps)://.*",
  "name" : "HTTPS and IMAPS",
  "id" : 10000001,
  "description" : "This service definition authorizes all application urls that support HTTPS and IMAPS protocols.",
  "evaluationOrder" : 10000
}
```
#### 改application.properties文件
接下来修改/Users/java0904/Downloads/apache-tomcat-9.0.30/webapps/cas/WEB-INF/classes目录下面的application.properties文件，添加如下代码

```shell
cas.tgc.secure=false
cas.serviceRegistry.initFromJson=true
```
# 编辑客户端的代码
我这里用的是springboot，pom文件是
### pom.xml
```pom
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.2.2.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.example</groupId>
    <artifactId>wiki-cas</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>war</packaging>
    <name>wiki-cas</name>
    <description>Demo project for Spring Boot</description>
    <properties>
        <java.version>1.8</java.version>
        <thymeleaf.version>3.0.11.RELEASE</thymeleaf.version>
        <thymeleaf-layout-dialect.version>2.1.1</thymeleaf-layout-dialect.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </dependency>


        <dependency>
            <groupId>net.unicon.cas</groupId>
            <artifactId>cas-client-autoconfig-support</artifactId>
            <version>2.3.0-GA</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-cas</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-taglibs</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-test</artifactId>
            <version>2.4.0</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
    <build>
        <finalName>wiki-cas</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>
```
### application.properties文件
```java
server.port=8088
#cas服务端的地址
cas.server-url-prefix=https://java.cas.juhe.cn:8443/cas
#cas服务端的登录地址
cas.server-login-url=https://java.cas.juhe.cn:8443/cas/login
#当前服务器的地址(客户端)
cas.client-host-url=http://java.cas.juhe.cn:8088
#Ticket校验器使用Cas30ProxyReceivingTicketValidationFilter
cas.validation-type=cas3
```
### 启动入口文件
```java
package com.example;

import net.unicon.cas.client.configuration.EnableCasClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@ComponentScan("com.example.controller")
//#启动CAS @EnableCasClient
@EnableCasClient
public class WikiCasApplication {

    public static void main(String[] args) {
        SpringApplication.run(WikiCasApplication.class, args);
    }
}
```
### controller
```java
package com.example.controller;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class CasTest1 {

    @RequestMapping("/test1")
    public String test1(){
        return "test1....";
    }
}
```
# 测试
请求访问[http://java.cas.juhe.cn:8088/test1](http://java.cas.juhe.cn:8088/test1)，将会发生跳转
如下![在这里插入图片描述](https://img-blog.csdnimg.cn/20210304175814477.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3NpbGtfamF2YQ==,size_16,color_FFFFFF,t_70)
输入用户名密码casUser，Mellon，将会看到页面跳转
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210304175900245.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3NpbGtfamF2YQ==,size_16,color_FFFFFF,t_70)
# 完整代码上传至
