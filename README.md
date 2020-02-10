# 项目介绍

通过抓取微信企业号中的课表信息 **实现在桌面添加课表widget**从而避免频繁查课表或将课表设为桌面壁纸

# 程序逻辑框图

UML一直没耐心学，自己随便画的:cry:

画图是为了说明/理清一些类的功能和各类之间的联系

![程序逻辑框图](AppLogicGraph.png)

# 项目进度

#### 2020.2.3

* 实现使用Jsoup抓取目标（课表）信息
* 登录界面设计完成

#### 2020.2.4

* 记住用户名和密码(使用SharedPreferences)

#### 2020.2.5

* 实现抓取数据并重构(Gson)
* 完成SQLite的密码保存Class
* 完成SQLite的课程信息保存Class
  * 课程储存于数据库的格式（尽量减少不需要的数据） : 
    * (STRING)INDEX: YYYY-MM-DD-TT(年-月-日-节)
    * (STRING)NAME:课程名称
    * (STRING)LOCATION:上课地点
    * (STRING)TEACHER:教师名称
    * (INT)TOTALLENGTH:课程长度
* 完成登录部分程序逻辑框图(draw.io)

#### 2020.2.6

* 检查输入合法性(ViewModelProvider)
* 重新整理登录逻辑（程序逻辑框图.drawio）
* 实现登录过程模拟
* 登录状况的判断（SharedPreference)
* 增加退出登录的功能
* 添加保存课程信息的数据库类(SQLite)

#### 2020.2.7

* :sweat:觉得使用安卓的登录框架（ViewModelProvider）限制了我，程序被设置的越来越复杂了
* 移植登录程序(AsyncTask)
* 保存cookies到本地(SharedPreference)
* 连接超时处理
* 爬虫移植(AsyncTask)

#### 2020.2.8

* 解析至SQLite数据库保存格式（Gson）
* 抓取信息保存到本地(SQLite)
* 完成粗糙主界面 （[revolvingweekview](https://github.com/jlurena/revolvingweekview) ）

#### 2020.2.9

* 搬运MD课表中的桌面插件（未移植）[mnnyang](https://github.com/mnnyang)/**[ClassSchedule](https://github.com/mnnyang/ClassSchedule)**
* 侧面导航栏完成一半

#### 2020.2.10

* 完成侧面导航栏，移植完毕

  * 主页
  * 设置
    * 当前学期
    * 刷新数据
    * 退出登录

  * 反馈>
  * 关于>

* 配置主页

# 待办

* 设置页面
* 反馈页面
* 关于页面
* 自动判断当前属于年份、学期
* 更新、反馈方法
* 美化
* 各类接口

## optional

* 使用SQLite记住用户名密码（安全性更高）
