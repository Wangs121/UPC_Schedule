## 2020.2.14		--基本功能已完成 0.9BETA版本

* 能显示各周课程信息在主页

* 小部件添加成功，正常工作

* 修改设置中的内容为：

  * 查看数据库全部课程数据
  * 更新课程数据
  * 退出登录

* 完成反馈页面

  * 使用qq联系作者
  * 在酷安评论
  * 在GitHub上`submit new issue`

* 完成简单的关于页面

* 优化课程颜色，避免与文字颜色相似

  

#### 2020.2.13

* 只保留了xml文件，数据处理部分全部移除
* 优化了课程信息保存内容，可以减少很多的计算，修改了：
  * INDEX : 周-天-节,ww-d-cc
* 优化了登录结构，调用的class都单独抽出来，减少与其他部分的联系
* 修复了每次登录都会闪退一下的bug (`AsyncTask` : `UI thread` and `background thread`  ) 

#### 2020.2.12

* home page完成，范围约束到1-18周，可显示每天的日期
* 点击课程出现课程详情
* 移植桌面插件
* 完善：若软件一直未关闭，则可自动更新主界面
* 实现小部件自动更新
* 删除记住密码功能
* 自动获取学期

在手机上登录之后就闪退。模拟器竟然和手机不一样。找不到原因，代码几乎注释完了还不行。前天以前的代码程序能正常执行，隐藏了两天的bug:sob::

#### 2020.2.11

* 成功显示到当前周到主页面
* 更换图标与名称

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

#### 2020.2.9

* 搬运MD课表中的桌面插件（未移植）[mnnyang](https://github.com/mnnyang)/**[ClassSchedule](https://github.com/mnnyang/ClassSchedule)**
* 侧面导航栏完成一半

#### 2020.2.8

* 解析至SQLite数据库保存格式（Gson）
* 抓取信息保存到本地(SQLite)
* 完成粗糙主界面 （[revolvingweekview](https://github.com/jlurena/revolvingweekview) ）

#### 2020.2.7

* :sweat:觉得使用安卓的登录框架（ViewModelProvider）限制了我，程序被设置的越来越复杂了
* 移植登录程序(AsyncTask)
* 保存cookies到本地(SharedPreference)
* 连接超时处理
* 爬虫移植(AsyncTask)

#### 2020.2.6

* 检查输入合法性(ViewModelProvider)
* 重新整理登录逻辑（程序逻辑框图.drawio）
* 实现登录过程模拟
* 登录状况的判断（SharedPreference)
* 增加退出登录的功能
* 添加保存课程信息的数据库类(SQLite)

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

#### 2020.2.4

* 记住用户名和密码(使用SharedPreferences)


#### 2020.2.3

* 实现使用Jsoup抓取目标（课表）信息
* 登录界面设计完成