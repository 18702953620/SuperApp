# 超级APP

#### 介绍

20220610
分为学生、老师两个入口，根据用户类型提供不同的功能服务。采用混合开发模式，底层原生＋应用HTML5的开发模式。
主要功能：上网认证缴费、校园门禁、查询课表、查询成绩、请假离校报备、在线选课、学生签到、缴纳学费、考试报名、基本信息、消息提醒、新闻发布等。

#### 项目采用阿里Arouter实现组件化的架构，将各个业务模块拆分开来，整体架构采用MVP架构，其中M-Model 即实体类，V-View即视图层，P-Presnter即业务层，负责控制业务逻辑；
#### 开发工具：Android Studio
#### 技术点：
* MVP框架的封装及生命周期的处理；
* 使用retrofit2+okhttp网络框架，自定义解析器、公共参数的处理、拦截器的使用及异常统一处理；
* 自定义View的使用和封装；
* Rxjava和RxAndoird的使用；
* glide的使用和个性定制、缓存的处理；
* SmartRefreshLayout刷新控件的封装和自定义；
* 共享元素动画的封装和使用；
* Android 7.0 、8.0 、9.0、10.0 版本适配；

#### 软件架构
* `app_teacher` 老师端入口
* `module_base` 基础库，包含基类、网络请求/token刷新、常用组件、常用工具类等
* `module_common`公共组件、公共功能、服务
* `module_filepicker`图片/文件选择器
* `module_admission` 招生系统





#### 使用说明



#### 参与贡献

1.  Fork 本仓库
2.  新建 分支
3.  提交代码
4.  新建 Pull Request

#### 项目中用到的第三方开源库

名称|作者|描述|链接
-|-|-|-
BaseRecyclerViewAdapterHelper|CymChad|万能适配器|[链接](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)
statusbarutil|Jaeger|状态栏工具|[链接](https://github.com/laobie/StatusBarUtil)
retrofit2|Square|http封装|[链接](https://github.com/square/retrofit)
RxJava|ReactiveX|响应式编程|[链接](https://github.com/ReactiveX/RxJava)
RxAndroid|ReactiveX|响应式编程在Android上的应用|[链接](https://github.com/ReactiveX/RxAndroid)
SmartRefreshLayout|scwang90|刷新组件|[链接](https://github.com/scwang90/SmartRefreshLayout)
fastjson|alibaba|序列化|[链接](https://github.com/alibaba/fastjson)
PhotoView|Baseflow|支持双击或双指缩放的 ImageView|[链接](https://github.com/Baseflow/PhotoView)
glide|google|图片加载框架|[链接](https://github.com/bumptech/glide)
arouter|alibaba|Android App 进行组件化改造的路由框架|[链接](https://github.com/alibaba/ARouter)
android-gif-drawable|Karol Wrótniak|gif加载|[链接](https://github.com/koral--/android-gif-drawable)
flexbox|google|弹性布局|[链接](https://github.com/google/flexbox-layout)
banner|youth5201314|banner|[链接](https://github.com/youth5201314/banner)
bga-qrcode|bingoogolapple|二维码扫描|[链接](https://github.com/bingoogolapple/BGAQRCode-Android)
EasySwipeMenuLayout|anzaizai|侧滑删除|[链接](https://github.com/anzaizai/EasySwipeMenuLayout)



