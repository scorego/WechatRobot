## 微信机器人

根据指令自动回复好友消息、群聊陪聊、查天气，基于[ChatApi-WeChat](https://github.com/xuxiaoxiao-xxx/ChatApi-WeChat)构建。    
[老版README](/doc/OLD_README.md)

### 设计理念

主要是想写一个群助手，作为在群里的工具使用。所以将这个机器人监听的信息从@自己的改为指令开头的。这样做的好处是在群里人数比较多的时候@人太麻烦，需要找很久。以指令开头方便简单。另外，考虑到国内手机输入法的习惯，默认指令前缀是两个问号，因为拼音9宫格的布局问号在快捷栏里，方便输入。  
对于具体指令，希望汉字优先，缩写为主。

---

## TODO

- [x] 垃圾分类模式
- [ ] 投票模式
- [ ] 拼车模式
- [x] 每日一句
- [ ] 下载二维码并打印到控制台

## 最近动态

- 新增Redis缓存，可将天气查询结果、垃圾分类查询结果缓存在Redis。如果自己没有Redis，可在配置文件中关闭缓存。
- 增加垃圾分类查询功能，可在群聊中查询垃圾如何分类。
- 天气接口默认改回Qingyunke
---

### 配置及使用

需求环境：jdk 1.8+、Maven
  
全局配置文件是[`resource/config.properties`](/src/main/resources/config.properties)。    
缓存配置文件是[`resource/redis.properties`](/src/main/resources/redis.properties)。     
程序入口：[`main/WechatBot.java`](/src/main/java/main/WechatBot.java)   
启动程序后打开控制台输出的二维码链接，并使用微信扫描。     
提示：任何非官方途径登陆网页微信都有可能导致封停账号登陆网页微信的权限(不影响其他端的使用)。建议使用小号。   

#### 激活指令

默认的指令前缀是两个问号：`??`，中英文皆可。指令前缀＋具体指令组成一条完整的指令。如`北京天气`是一条天气指令，`??北京天气`是一条完整的指令，当具有天气模式权限的群里有群成员发送`??北京天气`时，此机器人会自动回复当日北京天气并@发送者。    
指令前缀可在配置文件中自定义。  

<div align=center>
      <img src="/doc/doc1.jpg" width = "500" height = "1500" />
</div> 


#### 1. 天气模式

程序监听相应群聊内容，当监听到以`天气`开始或结尾的语句便查询相应城市天气并自动发送到群聊。比如：`北京天气`、`北京市天气`、`天气北京`。只支持国内部分(大部分)市、区、县查询，不支持省。由于一些区和市同名，所以查询区天气时需要全称，如：`海淀区`。查询市及县可以不带`市`或`县`字。   
如果监听到`天气`或者`天气预报`，会按发送人微信名片上的地址发送今日天气。  

#### 2. 自动回复好友 

将配置文件`autoReplyFriend`设为`true`，便自动回复好友消息。不会回复黑名单中好友。  

#### 3. 聊天模式

此功能默认只对白名单的群或好友开放。机器人会回复任何以指令前缀开头的消息。  
提示：免费的机器人都是人工智障，所以此功能建议作为测试、娱乐使用。  

#### 4. 垃圾分类模式

当命令(去处前缀后)以问号`?`/`？`开头时，此时为查询垃圾分类模式。输入具体垃圾查询垃圾分类。如`？？？电池`、`？？？无汞电池`。      


#### 5. 每日一句

在配置中启用每日一句，可在指定时间向指定群发送当日天气和名言名句。当日天气使用的是`api.WeatherApi`，每日一句使用`api.EveryDayHelloApi`。  
如果当日配置的时间已经过了，则会从次日开始正常执行。  
向好友发送消息尚未启用。  



### 数据来源

#### 1. 青云客
智能机器人API：https://www.sojson.com/api/semantic.html  
青云客天气API：https://www.sojson.com/api/weather.html

友情提示：人工智障在线陪聊，冷场利器、分手大师。  

#### 2. RollToolsApi

RollToolsApi：https://github.com/MZCretin/RollToolsApi  

#### 3. 每日一句

金山词霸： http://open.iciba.com/dsapi/

#### 4. 垃圾分类

LAJIFENLEIAPP: http://lajifenleiapp.com/sk/

### LICENSE

[Apache License 2.0](https://github.com/scorego/WechatRobot/blob/master/LICENSE.md)