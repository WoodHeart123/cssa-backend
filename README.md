# 目录结构说明
~~~
.
├── Dockerfile                      Dockerfile 文件
├── LICENSE                         LICENSE 文件
├── README.md                       README 文件
├── mvnw                            mvnw 文件，处理mevan版本兼容问题
├── mvnw.cmd                        mvnw.cmd 文件，处理mevan版本兼容问题
├── pom.xml                         pom.xml文件
├── settings.xml                    maven 配置文件
├── springboot-cloudbaserun.iml     项目配置文件
└── src                             源码目录
    └── main                        源码主目录
        ├── java                    业务逻辑目录
        └── resources               资源文件目录
~~~


# 接口说明

## 公共Header参数
### 所有接口都将接受到该参数
参数名 | 示例值 | 参数描述
---|---|---
x-wx-openid | --- | 微信的userID
## /主页/搜索课程
#### 接口状态
> 开发中

#### 接口URL
> /search?input

#### 请求方式
> GET

#### Content-Type
> form-data

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
input |  | Text | 是 | 用户填写信息

## /主页/获取专业列表
#### 接口状态
> 开发中

#### 接口URL
> /getDepartmentList

#### 请求方式
> GET

#### Content-Type
> form-data

## /主页/获取课程列表
#### 接口状态
> 开发中

#### 接口URL
> /getCourseList?departmentID

#### 请求方式
> GET

#### Content-Type
> form-data

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
departmentID |  | Text | 否 |
## /活动展示页面
```text
暂无描述
```
暂无参数
## /活动展示页面/获取活动列表
```text
暂无描述
```
#### 接口状态
> 开发中

#### 接口URL
> /activity/activityList?current=1661563571000

#### 请求方式
> GET

#### Content-Type
> form-data

#### 成功响应示例
```javascript
{
	"status": "100",
	"message": "成功",
	"data": {
		"actList": [
			{
				"actID": "1",
				"date": "08/20/2022",
				"location": "Humanities 1102",
				"price": "15",
				"capacity": "150",
				"userJoined": "120",
				"title": "元宵节活动",
				"imgs": "../../static/detail_sample/hua4.jpeg",
				"description": "赏花节，有时人们又称为看花节，是四川省阿坝州马尔康地区藏族人民的传统节日。每年农历六月举行，为期三、五天或十来天不等。 农历六月，雪山草地百花盛开，牧草如茵，自然风光十分迷人。各村寨根据自己的实际情况，或在月初，或在月中，或在月末过节。届时藏族人民带着食品、帐篷，骑着披红挂彩的骏马，成群结队地在野外游走对歌，欣赏大自然的美景。然后选择山花烂漫的山岗，或绿草如茵的草坪，搭起帐篷，熬上茶，盛满青稞酒。人们聚集一起赏花品酒，交流放牧经验。 晚上，草坪上燃起一堆堆篝火，人们且饮且舞，或放声歌唱。近年来，赏花节更为热闹，除赏花，品酒，唱歌，跳舞，欣赏大自然外，还增添了摔跤，赛马，文艺演出等文体内容。传统赏花节中的祈祷人口增殖、生殖繁盛已淹没在历史的波涛巨浪之中。",
				"additionalInfo":[],
			}
		]
	}
}
```
参数名 | 示例值 | 参数类型 | 参数描述
--- | --- | --- | ---
status | 100 | Text |
message | 成功 | Text |
data |  | Text | 返回数据
data.actList |  | Text |
data.actList.actID | 1 | Text |
data.actList.date | 08/20/2022 | Text |
data.actList.location | Humanities 1102 | Text |
data.actList.price | 15 | Text |
data.actList.capacity | 150 | Text |
data.actList.userJoined | 120 | Text |
data.actList.title | 元宵节活动 | Text |
data.actList.imgs | ../../static/detail_sample/hua4.jpeg | Text |
data.actList.description | 赏花节，有时人们又称为看花节，是四川省阿坝州马尔康地区藏族人民的传统节日。每年农历六月举行，为期三、五天或十来天不等。 农历六月，雪山草地百花盛开，牧草如茵，自然风光十分迷人。各村寨根据自己的实际情况，或在月初，或在月中，或在月末过节。届时藏族人民带着食品、帐篷，骑着披红挂彩的骏马，成群结队地在野外游走对歌，欣赏大自然的美景。然后选择山花烂漫的山岗，或绿草如茵的草坪，搭起帐篷，熬上茶，盛满青稞酒。人们聚集一起赏花品酒，交流放牧经验。 晚上，草坪上燃起一堆堆篝火，人们且饮且舞，或放声歌唱。近年来，赏花节更为热闹，除赏花，品酒，唱歌，跳舞，欣赏大自然外，还增添了摔跤，赛马，文艺演出等文体内容。传统赏花节中的祈祷人口增殖、生殖繁盛已淹没在历史的波涛巨浪之中。 | Text |
data.actList.additionalInfo | {} | Text |
## /活动展示页面/报名活动
```text
暂无描述
```
#### 接口状态
> 开发中

#### 接口URL
> /activity/register

#### 请求方式
> POST

#### Content-Type
> json

#### 请求Header参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
x-wx-openid | sdjenww | Text | 是 | token
#### 请求Body参数
```javascript
{
    "actID":"1",
    "response":["1","2"]
}
## /课程页面
```text
暂无描述
```
## /课程页面/举报
```text
举报评价
如果举报条数==10，暂时把commentTime设置为Timestamp(0) 
```
#### 接口状态
> 开发中

#### 接口URL
> /report

#### 请求方式
> POST

#### Content-Type
> form-data

#### 请求Body参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
commentID |  | Text | 是 |
reportList | | Array | 是 | 虽然为array类型，但array.length == 1
## /课程页面/赞
```text
添加commentID到user列表
like计数加一
```
#### 接口状态
> 开发中

#### 接口URL
> /zan?commentID&zan

#### 请求方式
> GET

#### Content-Type
> form-data

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
commentID |  | Text | 是 |

## /课程页面/获取课程信息
```text
获取当前课程的所有信息
```
#### 接口状态
> 开发中

#### 接口URL
> /getcourse

#### 请求方式
> GET

#### Content-Type
> form-data

#### 请求Header参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
courseID |  | Text | 是 |
## /课程页面/获取评论列表
```text
获取当前课程下的评论列表
1页20条信息
```
#### 接口状态
> 开发中

#### 接口URL
> /getcommentlist?courseID&pageNumber&sortType

#### 请求方式
> GET

#### Content-Type
> form-data

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
courseID |  | Text | 是 |
pageNumber |  | Text | 是 | 1页为20行信息
sortType |  | Text | 是 | 排序方式（SORT_BY_LIKE,SORT_BY_TIME)
## /课程页面/发布评论
```text
发布评论
```
#### 接口状态
> 开发中

#### 接口URL
> /postcomment

#### 请求方式
> POST

#### Content-Type
> form-data

#### 请求Body参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
courseID |  | Text | 是 |
professor |  | Text | 是 | 教授名字
courseTime |  | Text | 是 | 上课时间
difficulty |  | Text | 是 | 难度评分
prefer |  | Text | 是 | 喜爱程度
comment |  | Text | 是 | 评论

# License

[MIT](./LICENSE)