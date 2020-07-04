# DiscuzToFlarum（Discuz2Flarum）（简称：D2F）
---

## 什么是D2F？
+ 这是一个可以将Discuz系统的论坛内帖子数据以及用户基本数据移动到Flarum系统的小工具

# TPS：

**这个工具只适合小微论坛，不适合大型论坛，请勿直接上线使用，请在本地测试再使用！**

**这个工具只适合小微论坛，不适合大型论坛，请勿直接上线使用，请在本地测试再使用！**

**这个工具只适合小微论坛，不适合大型论坛，请勿直接上线使用，请在本地测试再使用！**

## 示例站点：
Discuz站点：<https://bbs.mcplugin.cn>

Flarum站点：<http://test.mcplugin.cn>

### 我该如何使用它？
+ 下载源码之后，配置好JDBC的Java驱动，最好是导入到一些开发软件（Eclipse或者IDEA都行）
+ 找到`sql.properties`，根据里面的提示自行配置好您的配置文件
+ 配置好flarum的discussion_id和discuz的板块ID即可（因为没做DZ和FL的板块对照表，所以帖子转换只能一次转换一个板块）
`ids`数组是flarum的分类id，可以多个，所以是数组
`fid`是discuz的板块id，只能是一个。

![image.png](https://pic.rmb.bdstatic.com/bjh/b0cf3a2461898912940221a28fe4f3eb.png)


+ 一切diy好了以后，导出您自定义好的Jar包，然后再cmd输入 `java -jar jar包名.jar` 即可运行

### 目前D2F实现的功能
+ 转移Discuz用户的邮箱、注册时间、回复内容、头像   （密码无法迁移怎么办？别着急，接着往下看）
+ 讲指定fid（Discuz论坛版块ID）中的帖子，转移到对应的discussion_id当中（Flarum的板块ID）
+ 帖子只有帖子内容、作者、阅览量、、回复的迁移（预计未来版本添加帖子附件的转化）
---

### 未来D2F添加的功能
+ 增加DZ论坛`FID`（板块ID）与Flarum论坛`Discussion_Id`的对照表文件，方便自动化转化论坛所有帖子
+ 论坛用户密码、注册IP的转化
+ 帖子附件的转化
+ 更多细节的开发尚待发现

---

### 常见问题：

#### 1.目前无法转换密码怎么办？
因为邮箱被导入了，所以可以使用Flarum自带的邮箱找回密码，让用户重新设置密码（唯一的解决方法）

![image.png](https://pic.rmb.bdstatic.com/bjh/3e0390c7a4d6d32ded191f3d514d8e90.png)

![image.png](https://pic.rmb.bdstatic.com/bjh/1b488dd2fce1d85c6d0651fad9afa137.png)

---

#### 作者的一些留言：
说一下开发原因吧，因为总被人吐槽用Discuz太土了，上个世纪的风格和上个世纪的产物了，
又被人推荐了Flarum，安装了一下试了试，感觉还不错，但是就是数据没法转移。
于是在半年前我尝试去转移数据，基础的数据可以转移了，但是还有很多数据没办法迁移，
后续等待其他大佬开发比较NB的转换程序，或者说慢慢等我划水写完把。
这个工具建议给有Java基础的人员来用，因为可以高度DIY，我把所有功能全封装了。
`FlarumSQLUtil`是专门控制Flarum数据库的，`DiscuzSQLUtil`是专门控制DZ数据库库的，所有你想获得的东西 ~~前提是我写了~~ 都可以通过`getXXXX()`方法获得到

~~什么？你说这太屑了，功能一点都不多？~~

啊这，我也没办法，没时间继续写下去，只能抽空改一改，增加一些新功能，可能这个东西也没人用吧。


---

#### 示例图片：

![image.png](https://pic.rmb.bdstatic.com/bjh/9053db2109338441b2852e13556badba.png)

![image.png](https://pic.rmb.bdstatic.com/bjh/cc8135047e0c1167cf28743efaca7221.png)

![image.png](https://pic.rmb.bdstatic.com/bjh/cf0145231f041815d2092877141b151a.png)

![image.png](https://pic.rmb.bdstatic.com/bjh/f7479c464e89443953fa2f53f44fecc8.png)
