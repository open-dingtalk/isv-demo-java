### 前言
我们在开发钉钉ISV应用的时候，会发现钉钉ISV套件必须部署有公网IP的服务器上，也就是在开发套件之前必须先准备购买阿里云等机器资源。在之后的开发中，由于ISV套件的回调地址必须是公网域名或IP之下，对于大部分开发者来说，开发者无法在本地调试远程代码，对于回调URL校验不通过之类问题无法追踪，只能不断远程部署来看log日志来调试修改，本文主要讲解如何解决这些问题。

### 解决方案
基于以上问题，我们设计了一套支持ISV套件本地化调试的方案，使用这套方案可以达到以下目的：
1. ISV套件开发阶段不需要购买阿里云等公网机器资源
2. 开发调试套件可以在本地（eclipse+Tomcat）进行，不需要部署到远程公网机器上

### 启动内网穿透
1.下载工具

```plain
git clone https://github.com/open-dingtalk/pierced.git
```

![image.png | center | 752x194](https://cdn.yuque.com/lark/2018/png/bd935f2f-c3f8-44de-a658-a104bfdafc24.png "")
启动工具，执行命令“./ding -config=./ding.cfg -subdomain=域名前缀 端口”，以mac为例：
```plain
cd mac_64
chmod 777 ./ding
./ding -config=./ding.cfg -subdomain=abcde 8080
```

启动后界面如下图所示：
![image.png | center | 752x201](https://cdn.yuque.com/lark/2018/png/ddfd9389-58b6-43b4-adf9-f8db52f25bba.png "")
参数说明：
| 参数 | 说明 |
| --- | --- |
| config | 钉钉提供内网穿透的配置文件，不可修改 |
| subdomain | 你需要使用的域名前缀，该前缀将会匹配到“vaiwan.com”前面，例如你的subdomain是abcde，你启动工具后将会将abcde.vaiwan.com映射到本地 |
| 端口 | 你需要代理的本地服务http-server端口，例如你本地端口为8080等 |

2.启动完这个客户端后，你访问http://abcde.vaiwan.com/xxxxx都会映射到 http://127.0.0.1:8080/xxxxx
注意：
> 1.你需要访问的域名是http://abcde.vaiwan.com/xxxxx 而不是http://abcde.vaiwan.com:8082/xxxxx
> 2.你启动命令的subdomain参数有可能被别人占用，尽量不要用常用字符，可以用自己公司名的拼音，例如我使用：alibaba、dingding等。
> 3.可以在本地起个http-server服务，放置一个index.html文件，然后访问http://abcde.vaiwan.com/index.html测试一下。

### 准备开发环境
搭建JAVA环境，建议使用jdk8
下载eclipse或者intelliJ，Tomcat等搭建本地java项目环境，不一一赘述。
### DEMO部署
下载示例代码
```
git clone https://github.com/open-dingtalk/isv-demo-java.git
```
请开发者使用jdk1.6或以上版本。

__导入数据库文件__

提示：需要提前新建ding\_isv\_access数据库，详见“准备工作”第4步。

```
mysql -u root -p ding_isv_access < db_sql.sql
```

此步骤可能会遇到如下报错：

> COLLATION 'utf8\_general\_ci' is not valid for CHARACTER SET 'utf8mb4'

请将db\_sql.sql以文本形式打开，并将文件中所有“utf8mb4”字符串全部替换为“uft8”。

### 创建测试套件并配置代码
登录钉钉开发者平台，创建一个测试套件，如下图所示：

![image.png | center | 752x348](https://gw.alipayobjects.com/zos/skylark/46b7f7e8-3aae-48dc-9552-195c4cb4b74f/2018/png/01e80151-3d5a-4f22-a92f-a1d51b755503.png "")

选择条件类型为“测试套件”，注意：套件类型一旦选定不能进行修改，测试套件主要是用来做开发调试，不能发布商品、不能生成线下部署二维码。
![image.png | center | 752x625](https://gw.alipayobjects.com/zos/skylark/4d0c1fb9-e905-4033-aaa0-18dbed2b0d64/2018/png/9ae8512f-5577-4eca-b598-46c10f1219a4.png "")
在创建套件第二步需要我们验证回调地址的有效性，重点来了。

![image.png | center | 752x508](https://gw.alipayobjects.com/zos/skylark/4c8916c5-8638-485a-8090-c4ad996b140e/2018/png/64733494-f067-4d7e-b731-aefebd07b369.png "")
> 通过上面的创建我们能拿到如下参数：
> Token: 随意填写任意字符串
> suiteKey：套件key
> suiteSecret：套件秘钥
> AESKey：数据加密密钥，点击自动生成
在MySQL数据库中插入下面信息，注意替换“套件名称、suiteKey、suiteSecret、EncodingAESKey、Token”中的值：
```plain
insert into isv_suite(id, gmt_create, gmt_modified, suite_name, suite_key,
suite_secret, encoding_aes_key, token, event_receive_url)
values(1, NOW(), NOW(), '套件名称', 'suiteKey', 'suiteSecret','EncodingAESKey', 'Token', '');
```

我们用IDE打开示例DEMO代码，配置好如上参数。
1.ding-isv-access/web/src/main/webapp/WEB-INF/config.properties 里面配置好system.env、oapi.environment、corp.suite.callback、jdbc.url、db.username、db.password、suite.suiteKey，如下所示：

```plain
oapi.environment=https://oapi.dingtalk.com
corp.suite.callback=http://xxxxx.vaiwan.com/ding-isv-access/suite/corp_callback/
#jdbc.url=jdbc:mysql://127.0.0.1:3306/ding_isv_access?useUnicode=true&amp;characterEncoding=utf8
jdbc.url=
db.username=
db.password=
suite.suiteKey=
suite.microappAppId=
```
| 配置项 | 配置说明 |
| --- | --- |
| oapi.environment | 钉钉服务端接口地址 |
| corp.suite.callback | 套件通讯录回调地址，启动内网穿透后，将xxxxx.vaiwan.com修改为自己的subdomain地址。 |
| jdbc.url | 数据库连接池地址 |
| db.username | 数据库用户名 |
| db.password | 数据库密码 |
| suite.suiteKey | 创建套件拿到的suitekey |
| suite.microappAppId | 创建的微应用id，在创建好微应用后填写 |

2.用IDE打开ding-isv-access/web/src/main/webapp/WEB-INF/log4j.xml文件，配置好LOG\_PATH，也就是日志文件地址。
```plain
<property name="LOG_PATH" value="/usr/local/logs/ding-isv-access" />
```
3.完成上面的配置后，请将代码部署到本地Tomcat下面，如果配置无误，打开浏览器输入地址：http://abcde.vaiwan.com/ding-isv-access/checkpreload.htm，会看到浏览器中显示success，说明项目已经部署成功。
> 注意：我们提供的内网穿透服务可能会有延迟，如果页面刷新不出来，请稍等3~5分钟后再试。

### 验证回调
登录开发者后台，点击“创建套件”，点击“下一步”，在回调URL地址上填写通过远程代理的域名服务器地址，例如我的地址是：http://abcde.vaiwan.com/ding-isv-access/suite/callback/{suitekey}。

> 注意：{suitekey}替换为自己套件的真实suitekey

点击“验证有效性”验证成功，如下图所示：

![image.png | center | 752x658](https://gw.alipayobjects.com/zos/skylark/1cdcbd07-4c32-4069-9677-570588128698/2018/png/fbac1eb2-af90-4cd9-860c-663d2ef95628.png "")

### 开发微应用
点击“验证完成”，开始开发应用”进入到套件详情页面，点击右侧的“创建微应用”：

![image.png | center | 752x347](https://gw.alipayobjects.com/zos/skylark/32065ce5-82c9-4510-a7e0-00e0c07ccadd/2018/png/5c11435d-682a-4f63-a869-e58f28e33c6d.png "")
填写、名称、logo图标、应用描述、主页地址：请填写主页地址填为：
> 
> http://abcde.vaiwan.com/ding-isv-access/microapp.html?corpId=$CORPID$
> 注意为http://abcde.vaiwan.com自己申请的远程域名服务

![image.png | center | 752x449](https://cdn.yuque.com/lark/2018/png/b004e606-ba99-4ad1-bb79-90f722809bcc.png "")

点击确定，保存微应用信息。
### 配置微应用
用IDE打开ding-isv-access/web/src/main/webapp/WEB-INF/config.properties，配置好正确的suitekey和microappAppId。
```plain
suite.suiteKey=suitexxxxxxx
suite.microappAppId=APPID
```
### 授权微应用
1.在页面下方点击“重新推送”，向自己的服务器端推送一个suite\_ticket。
2.选择一个企业并授权一个微应用。
![image.png | center | 752x277](https://gw.alipayobjects.com/zos/skylark/3a256ec4-d25b-4cea-ba18-2d0c3d5a5adb/2018/png/a9a68cec-a258-4dae-a567-3ca2f1d09df9.png "")
### 预览微应用
打开自己的手机版钉钉，切换到刚才授权的测试企业下面，打开自己刚才创建的微应用，即可看到免登成功并拿到userid。
![IMG_0743.jpeg | center | 720x1280](https://gw.alipayobjects.com/zos/skylark/e33b7b48-bd5d-4e2e-a1d0-e23ea58e3ae1/2018/jpeg/4162d201-ad8c-4cc9-b973-7371ca0a0415.jpeg "")