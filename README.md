# ID1212 Project Backend Part

Yuehao Sui  
Zidi Chen

##API
http://localhost:10086

###User

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

###Room (/room)
####/add
POST  
String topic,
String password,
String type

返回新房间信息

####/get
GET
long id

返回指定id的房间信息

####/get_all
GET

返回所有房间信息

####/delete
DELETE
long id

删除指定id的房间  
房间不存在返回 "Room not exist"  
删除成功返回 "Success"

### Chat (websocket)
https://blog.csdn.net/qq_41603102/article/details/88351729

####room内聊天 (/msg/chat)
前端需要发一个request给/msg/chat  
包含destination, sender, type, content

然后后端就会把sender, type, content 这三个转发给(response的形式)给destination

destination应该是每个房间的ID

然后一直访问/topic/<roomid>的浏览器就可以收到来自这个socket的信息了

####群发 (/msg/chatAll)

前端需要发一个request给/msg/chatAll  
包含sender, type, content

所有浏览器都可以从/all这里收到这里收到群发消息