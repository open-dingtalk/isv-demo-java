#准备工作

1.具有公网IP的服务器，假设IP地址为a.b.c.d（如果未来您开发的应用要上架钉钉应用市场，需要将应用部署到钉钉云上，参考：[上钉钉云指南](https://open-doc.dingtalk.com/docs/doc.htm?spm=a219a.7629140.0.0.vVu3R9&treeId=175&articleId=107625&docType=1)）。

2.搭建JAVA环境，注意：<font color=red>需要安装[JCE补丁](https://stackoverflow.com/questions/6481627/java-security-illegal-key-size-or-default-parameters#answers)，否则会出异常java.security.InvalidKeyException:illegal Key Size和『计算解密文字错误』</font>。

&nbsp;&nbsp;&nbsp;在官方网站下载并安装JCE无限制权限策略文件步骤：
&nbsp;&nbsp;&nbsp;JDK6的下载地址：[http://www.oracle.com/technetwork/java/javase/downloads/jce-6-download-429243.html](http://www.oracle.com/technetwork/java/javase/downloads/jce-6-download-429243.html)

&nbsp;&nbsp;&nbsp;JDK7的下载地址：[http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html](http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html)

&nbsp;&nbsp;&nbsp;JDK8的下载地址：[http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html](http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html)

&nbsp;&nbsp;&nbsp;下载后解压，可以看到local_policy.jar和US_export_policy.jar以及readme.txt。如果安装的是JRE，将两个jar文件放到%JRE_HOME%\lib\security目录下覆盖原来的文件，如果安装的是JDK，将两个jar文件放到%JDK_HOME%\jre\lib\security目录下覆盖原来文件。

&nbsp;&nbsp;&nbsp;库地址：[https://github.com/hetaoZhong/ding-isv-access/blob/master/web/lib/lippi-oapi-encrpt.jar](https://github.com/hetaoZhong/ding-isv-access/blob/master/web/lib/lippi-oapi-encrpt.jar)

&nbsp;&nbsp;&nbsp;ISV回调代码示例：[https://github.com/hetaoZhong/ding-isv-access/blob/master/web/src/main/java/com/dingtalk/isv/access/web/controller/suite/callback/SuiteCallBackController.java](https://github.com/hetaoZhong/ding-isv-access/blob/master/web/src/main/java/com/dingtalk/isv/access/web/controller/suite/callback/SuiteCallBackController.java)

3.部署TOMCAT服务器，假设端口为8080。

4.安装MySQL数据库，需要提前新建名称为ding_ isv_access数据库（如果数据库名称要修改，需要自己修改后面数据库导入脚本的数据库名字）。

5.安装Maven，项目构建管理工具。

#部署代码

##下载代码

```
git clone https://github.com/hetaoZhong/ding-isv-access.git
```

请开发者使用jdk1.6或以上版本。 

##导入数据库文件

提示：需要提前新建ding_isv_access数据库，详见“准备工作”第4步。

```
mysql -u root -p ding_isv_access < db_sql.sql
```

此步骤可能会遇到如下报错：

> COLLATION 'utf8_general_ci' is not valid for CHARACTER SET 'utf8mb4'

请将db_sql.sql以文本形式打开，并将文件中所有“utf8md4”字符串全部替换为“uft8”。

##获取验证回调地址参数

进入[钉钉开发者平台](http://open-dev.dingtalk.com)，[创建ISV套件](https://open-doc.dingtalk.com/docs/doc.htm?treeId=366&articleId=104943&docType=1)并获取以下参数：

1.  Token: 随意填写任意字符串。
2.  数据加密密钥: 点击自动生成。

##修改auto-config.xml配置

修改./web/src/main/webapp/META-INF/autoconfig/auto-config.xml文件中的项目log目录地址、ISV应用程序的回调地址。

##清理auto-config

```
rm -rf ~/antx.properties
```

##编译

```
mvn clean package -Dmaven.test.skip=true
```

编译时会进行配置工作，请按提示修改不符合的项目：

![maven配置](https://img.alicdn.com/tfs/TB1VMgFhf2H8KJjy0FcXXaDlFXa-899-624.png)

注意： demo工程暂不支持JDK9环境。 如果当前您的JDK版本为JDK9，maven可能会打包出错。建议将系统Java版本修改为JDK9以下。 

##部署

在对demo工程进行部署前，需要进行以下几个步骤：
（1）修改deploy-ding-isv-access.sh中tomac地址和工程地址为对应的自己的地址；
（2）修改deploy-ding-isv-access.sh权限为可执行权限：

```
chmod 755 ./deploy-ding-isv-access.sh
```

（3）执行脚本：

```
./deploy-ding-isv-access.sh
```

如果配置无误，打开浏览器输入地址：http://a.b.c.d:8080/ding-isv-access/checkpreload.htm（其中a.b.c.d为您将工程部署在的公网服务器地址），会看到浏览器中显示success，说明项目已经部署成功。

#创建套件

##第一步

进入[钉钉开发者后台](http://open-dev.dingtalk.com)并创建ISV套件，如下填写参数的值：

1.Token：任意字符串（获取验证回调地址参数步骤填写的）

2.数据加密密钥：点击自动生成 （获取验证回调地址参数步骤自动生成的）
 
3.IP白名单：a.b.c.d

![shoepic](https://img.alicdn.com/tfs/TB18m8eeMvD8KJjy0FlXXagBFXa-1358-1328.png)

##第二步

1.填写回调URL：http://a.b.c.d:8080/ding-isv-access/suite/callback/${suiteKey} 
  <font color=red>&nbsp;&nbsp; ${suiteKey}需要替换为页面显示真实的SuiteKey</font>
  
2.进入套件管理页面，记下套件名称、Token、数据加密密钥、套件Key、套件secret，如下图所示：

![xxtu](https://img.alicdn.com/tfs/TB1NU8aXamgSKJjSsplXXaICpXa-2626-772.png)

3.在MySQL数据库中插入下面信息，注意替换相应字段值：

```
insert into isv_suite(id, gmt_create, gmt_modified, suite_name, suite_key,
suite_secret, encoding_aes_key, token, event_receive_url)
values(1, NOW(), NOW(), '套件名称', 'suiteKey', 'suiteSecret','EncodingAESKey', 'Token', '');
```

4.修改biz/src/main/resources/spring-jdbc.xml文件中数据库的url、用户名username和密码password项为自己的相关数据。

5.点击“验证有效性”按钮。如下图所示，若验证结果为检查成功，则套件就已经创建成功了。

![shoepic2](https://img.alicdn.com/tfs/TB1CNdaeS_I8KJjy0FoXXaFnVXa-1212-1402.png)

现在ISV套件已经成功创建，如果遇到问题，可在对应的log文件中检查相关信息:
 
1.{TOMCAT_DIR}/logs/localhost.log 工程加载日志

2.{LOG_DIR}/ding-isv-access.log tomcat的ding-isv-access工程启动情况

3.{LOG_DIR}/biz/http_request_helper.log 所有通过httpclient开放平台请求的http记录。

4.{LOG_DIR}/biz/http_invoke.log 所有通过sdk开放平台请求的http记录

5.{LOG_DIR}/biz/task.log 所有quartz任务日志。包括定时生成suitetoken

6.{LOG_DIR}/biz/suite_callback.log 所有开放平台调用套件回调信息的日志

7.{LOG_DIR}/biz/monitor.log suitetoken是否正常接收,正常更新


#激活套件

为了能够在企业工作台打开并使用微应用，还需要完成套件的激活，激活步骤如下:

1.在开发者后台的套件管理-消息推送管理里面，点击“立即推送”，显示Ticket推送状态为推送成功后（如下图所示），在套件管理页面点击创建微应用，主页地址填为`http://a.b.c.d:8080/ding-isv-access/microapp.html?corpId=$CORPID$`。

![](https://img.alicdn.com/tfs/TB1pACmdlUSMeJjSszcXXbnwVXa-2538-808.png)

2.在套件管理页面点击创建测试企业，建立一个企业用于授权激活套件，如下图所示：

![](https://img.alicdn.com/tfs/TB1t4undlUSMeJjSszcXXbnwVXa-2600-1136.png)

3.返回套件管理页面，在授权管理中选择刚创建的企业对应用进行授权，如下图所示：

![](https://img.alicdn.com/tfs/TB1YPirdgMPMeJjy1XdXXasrXXa-1270-553.png)

4.如果配置无误，页面会提示几秒后后刷新页面，可看到企业授权成功。由于后端代码流控设置，需要等待一段时间（大概30分左右）才能看到授权成功的企业列表。

5.返回测试企业列表，点击已授权成功的测试企业的“登录管理”按钮，会提示您如何登录测试企业（您在钉钉客户端会找不到该测试企业）。注意：初次用测试企业账号登录OA后台，一定要从原管理账号登录入口进入，如下图所示。

![](https://img.alicdn.com/tfs/TB1cjpeXaagSKJjy0FhXXcrbFXa-2340-1346.png)

6.返回打开钉钉客户端，在最中间的“工作”tab顶栏切换选择创建的测试企业，可以看到刚创建的微应用已经在列表中可以访问。