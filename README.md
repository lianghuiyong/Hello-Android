## 1.说明

​		wanandroid api构建的一个demo项目，使用了jetpack相关组件（lifecycles、livedata、paging、room、viewmodel），使用了kotlin语言，使用kotlin协程（coroutine）构建任务，使用了ktx扩展库，使用了androidx sdk，实现了部分功能。

## 2.使用技术学习的途径

### （1）kotlin语言

​		书籍：《疯狂kotlin讲义》、《kotlin核心编程》

​		网站：[kotlin中文站](https://www.kotlincn.net/docs/reference/)、[lixiaojun kotlin教程](https://lixiaojun.xin/static/courses/kotlin/)、[掘金小册-kotlin实战](https://juejin.im/book/5af1c5ee6fb9a07a9f018368)

### （2）android-architecture-components组件库

​		①lifecycles生命周期感知组件：[官方使用指南说明](https://developer.android.google.cn/topic/libraries/architecture/lifecycle)

​		②LiveData响应式组件：[官方使用指南](https://developer.android.google.cn/topic/libraries/architecture/livedata)

​		③ViewModel绑定LiveData业务逻辑和View的中介：[官方使用指南](https://developer.android.google.cn/topic/libraries/architecture/viewmodel)

​		④Paging分页库：[官方使用指南](https://developer.android.google.cn/topic/libraries/architecture/paging)、[官方示例代码](https://github.com/googlesamples/android-architecture-components/tree/master/PagingWithNetworkSample)

​		⑤Room数据库操作：[官方使用指南](https://developer.android.google.cn/training/data-storage/room/index.html)

​		⑥WorkManager定时任务处理（暂未使用，后续可用于定时检测cookie是否过期）：[官方文档使用指南](https://developer.android.google.cn/topic/libraries/architecture/workmanager)

​		⑦Navigation导航库（Navigation本身使用起来还存在缺陷，fragment带来的缺陷，暂时放弃使用）：[官方文档使用指南](https://developer.android.google.cn/guide/navigation/)

​		⑧DataBinding：不想用

- 组件库所有示例代码都可以在[github](https://github.com/googlesamples/android-architecture-components)找到示例代码。
- 组件库最佳实现：[sunflower项目](https://github.com/googlesamples/android-sunflower)（该项目paging分页库没用到多页加载，只用了单个页面配合listadapter差异更新）

#### （3）material design相关（部分待学习）

​		①[toolbar使用博客](https://www.jianshu.com/p/05ef48b777cc)

​		②[TextInputLayout使用博客](https://www.jianshu.com/p/2b0cd9e9a4d9)

​		③[ConstraintLayout使用博客](https://www.jianshu.com/p/17ec9bd6ca8a)

​		④[ConstraintLayout动画博客](https://www.jianshu.com/p/c585180af02b)

​		⑤[ConstraintLayout动画官方示例代码](https://github.com/googlesamples/android-ConstraintLayoutExamples)

​		⑥[MotionLayout用法博客](https://www.jianshu.com/p/54a6e2568cdd)

​		⑦[CoordinatorLayout用法博客](https://www.jianshu.com/p/7f50faa65622)

​		⑧[Material.io](https://material.io/)

​		⑨[ViewPager2官方示例代码，现在绑定TabLayout还是alpha版本](https://github.com/googlesamples/android-viewpager2)