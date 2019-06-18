## 微信机器人

自动回复好友消息、群聊陪聊、查询天气，基于[wechat-api](https://github.com/biezhi/wechat-api)构建。  

---

## TODO

- 每日一句
- AT_ME消息的识别

---

### 配置及使用
  
配置文件是[`resource/config.properties`](https://github.com/scorego/WechatRobot/blob/master/src/main/resources/config.properties)。  
程序入口：`HelloBot.java`  
启动程序后使用微信扫描控制台输出的二维码。有时候需要多扫几次才能成功登陆。停止程序短时间内可免扫码直接登陆。   
提示：任何非官方途径登陆网页微信都有可能导致封停账号登陆网页微信的权限。建议使用小号。   

#### 1. 查询天气

程序监听相应群聊内容，当监听到以`天气`开始或结尾的语句便查询相应城市天气并自动发送到群聊。比如：`北京天气`、`北京市天气`、`天气北京`。只支持国内部分(大部分)市、区、县查询，不支持省。由于一些区和市同名，所以查询区天气时需要全称，如：`海淀区`。查询市及县可以不带`市`或`县`字。  
  

#### 2. 自动回复好友

将配置文件`autoReplyFriend`设为`true`，便自动回复好友消息。

#### 3. 聊天

由于免费的机器人都是人工智障，所以此功能建议作为测试、娱乐使用，也可以自己扩展接口。此功能默认只对白名单的群或好友开放。  

### 数据来源

#### 1. 青云客
智能机器人API：https://www.sojson.com/api/semantic.html  
青云客天气API：https://www.sojson.com/api/weather.html

友情提示：人工智障在线陪聊，冷场利器、分手大师。  

#### 2. RollToolsApi

RollToolsApi：https://github.com/MZCretin/RollToolsApi  

### LICENSE

[MIT](https://github.com/scorego/WechatRobot/blob/master/LICENSE.md)