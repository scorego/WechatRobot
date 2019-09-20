## 微信机器人Java版

个人微信(非公众号)微信机器人，根据指令自动回复好友消息、群聊陪聊、查天气、查垃圾分类，基于[ChatApi-WeChat](https://github.com/xuxiaoxiao-xxx/ChatApi-WeChat)构建。       
<div align=center>
      <img src="/doc/doc1.jpg" width = "500" height = "1500" />
</div> 

### 设计理念

主要是想写一个群助手，作为在群里的工具使用。所以这个机器人响应的信息主要是以指令前缀开头的。考虑到国内手机输入法的习惯，默认指令前缀是两个问号，因为拼音9宫格的布局问号在快捷栏里，方便输入。  
对于具体指令，希望汉字优先，缩写为主。

---

## 最近动态

- `fastjson`1.2.58爆出重大漏洞，升级为1.2.60
- 每日一句使用线程池调度，修复了之前使用`Timer`导致的调度稳定性差的bug
- 新增查看每日新闻命令、查看知乎热榜命令。新功能必须开启redis才能使用。  
- 垃圾分类接入AToolBox接口。AToolBox的数据库更全一些，还有近似词提示，但是接口有点慢，而且必须开启Redis。如果没开启redis缓存，可以换回LAJIFENLEIAPP。   
- 新增Redis缓存，可将天气查询结果、垃圾分类查询结果缓存在Redis。如果自己没有Redis，可在配置文件中关闭缓存。关闭缓存不影响现有功能，但可能后续会更新一些依赖redis实现的功能。  
---

## 配置及使用

需求环境：jdk 1.8+、Maven  

全局配置文件是[`resource/config.properties`](/src/main/resources/config.properties)。    
缓存配置文件是[`resource/redis.properties`](/src/main/resources/redis.properties)。     
程序入口：[`WechatBot.java`](/src/main/java/WechatBot.java)   
启动程序后打开控制台输出的二维码链接，并使用微信扫描。     
提示：任何非官方途径登陆网页微信都有可能导致封停账号登陆网页微信的权限(不影响其他端的使用)。建议使用小号。   

## 激活指令

默认的指令前缀是两个问号：`??`，中英文皆可。指令前缀＋具体指令组成一条完整的指令。如`北京天气`是一条天气指令，`??北京天气`是一条完整的指令，当具有天气模式权限的群里有群成员发送`??北京天气`时，此机器人会自动回复当日北京天气信息。    
指令前缀可在配置文件中自定义。  

### 指令举例

```
A. 获取详情

？？

B. 查天气

？？？
？？天气
？？北京天气
？？海淀天气
？？上海天气
？？深圳天气

C. 查垃圾分类

？？？电池
？？？无汞电池
？？？塑料袋

D. 当日新闻

？？新闻

E. 知乎热榜

？？知乎
？？知乎 1
？？知乎2
```

### 1. 查询天气

程序监听相应群聊内容，当监听到以`天气`开始的语句便查询相应城市天气并自动发送到群聊。比如：`北京天气`、`北京市天气`。只支持国内(大部分)市、区、县查询，不支持省。小部分地区由于接口数据丢失的原因不支持。       
如果监听到`?`、`天气`，会按发送人微信名片上的地址发送今日天气。  
```
完整指令举例：

？？？
？？天气
？？北京天气
？？上海天气
？？海淀天气
```    

### 2. 自动回复好友 

将配置文件`autoReplyFriend`设为`true`，便自动回复好友消息。不会回复黑名单中好友。  

### 3. AI陪聊

此功能默认只对白名单的群或好友开放。机器人会回复任何白名单的发送者的消息。  
提示：免费的机器人都是人工智障，所以此功能建议作为测试、娱乐使用。  

### 4. 查询垃圾分类

当一条指令(去除前缀后)以问号`?`/`？`开头时，此指令为查询垃圾分类指令。输入具体垃圾查询垃圾分类。如`？？？电池`、`？？？无汞电池`。      

```
完整指令举例：

？？？无汞电池
？？？电池
？？？塑料袋
？？？卫生纸
```  

### 5. 每日一句

在配置中启用每日一句，可在指定时间向指定群发送当日天气和名言名句。当日天气使用的是`api.WeatherApi`，每日一句使用`api.EveryDayHelloApi`。  
如果当日配置的时间已经过了，则会从次日开始正常执行。  
向好友发送消息尚未启用。  

### 6. 查看实时新闻

```
??新闻
```

### 7. 查看知乎热榜

```
??知乎
？？知乎 1
？？知乎 3
```
## 数据来源

### 1. 青云客

智能机器人API：https://www.sojson.com/api/semantic.html  
青云客天气API：https://www.sojson.com/api/weather.html

友情提示：人工智障在线陪聊，冷场利器、分手大师。  

### 2. RollToolsApi

RollToolsApi：https://github.com/MZCretin/RollToolsApi  

### 3. 每日一句

金山词霸： http://open.iciba.com/dsapi/

### 4. 垃圾分类

LAJIFENLEIAPP: http://lajifenleiapp.com/

AToolBox: http://www.atoolbox.net/Tool.php?Id=804

### 5. 知乎热榜 

知乎日报：https://news-at.zhihu.com/api/6/news/hot

## Acknowledgements

本项目离不开以下项目的灵感，在此对开发者表示感谢：

- [EverydayWechat](https://github.com/sfyc23/EverydayWechat)    
微信助手：1.每日定时给好友发送定制消息。2.自动回复好友。 (Python)  

- [xuxiaoxiao-xxx/ChatApi-WeChat](https://github.com/xuxiaoxiao-xxx/ChatApi-WeChat)     
Java版本微信聊天接口，使用网页微信API，让你能够开发自己的微信聊天机器人   


## License

[Apache License 2.0](https://github.com/scorego/WechatRobot/blob/master/LICENSE.md)