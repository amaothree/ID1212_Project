# ID1212 Project Backend Part

Yuehao Sui  
Zidi Chen

## API
http://localhost:10086

### User

#### /login
POST  
参数  String username, String password

如果用户不存在返回 "UserNotExist"  
密码不对返回 "PasswordNotRight"  
一切正确返回用户信息(非JSON)

#### /register
POST  
参数 
String username,
String password,
String email,
String phone_number

返回新用户信息(JSON)

### Room (/room)
#### /add
POST  
String topic,
String password,
String type

返回新房间信息

#### /get
GET
long id

返回指定id的房间信息

#### /get_all
GET

返回所有房间信息

#### /delete
DELETE
long id

删除指定id的房间  
房间不存在返回 "Room not exist"  
删除成功返回 "Success"

### Chat (websocket + STOMP)

如果要搜索相关文章，请搜索关键词 “STOMP”

https://blog.csdn.net/qq_41603102/article/details/88351729

#### STOMP的API

前端使用STOMP的API时，前端的每一个session应该先和websocket server建立起长连接。
咱们的websocket server链接地址是`https://localhost:10086/websocket`

STOMP的API有两类：
1. 一种需要订阅的，在我们这里订阅的API是用来接收信息的，包括每个房间以及广播消息。
2. 第二种是不需要订阅的，这里的体现是发送消息。

消息的形式应该是定义好的JSON，群发的消息JSON内只有三个键值对，sender， type以及content。房间内聊天多了一个room

#### 消息的发送与接受

首先要确保已经与 https://localhost:10086/websocket 建立了websocket长连接

群发只需要向`/msg/chatAll`发送JSON消息，那么后端就会将消息转发给`/all`，这样所有那时**正在订阅**`/all`这个endpoint的用户都会收到这个消息。

房间聊天类似，向`/msg/chat`发送消息，后端会先读取你JSON消息内的`room`这个键值对，假设这里`room`的值是`36`，那么剩下的三个键值将打包成JSON发给正在订阅`/room/36`这个endpoint的客户端

#### 怎么对STOMP测试

现在市面上绝大多数的http测试软件都不行，比如postman啥的。我仅找到一个软件可以测试STOMP。

https://apic.app/online

