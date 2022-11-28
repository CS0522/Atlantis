<!-- Created by Frank (Chen Shi) -->
<!-- Started on October 20 -->

#  `Atlantis Racer JMVT` 门户网站

### 简介
* `Atlantis Racer JMVT` 网站是由业余游戏爱好者组建的 `Atlantis Racer JMVT` 车队的门户网站。该网站项目由 `Frank (Chen Shi)`, `Izumi` 等成员建设，采用 `Vue` + `SpringBoot` 前后端分离技术，90% 的功能或组件自主实现，其他功能采用了第三方的方案，引用了 `Element-UI`, `github-markdown-css`, `mavon-editor`, `vuex` 等。由于建设者是大学生，第一次接触 `Vue` 和 `SpringBoot` 等技术，边学习、边练习、边实践，因此项目中代码有很多地方冗余、复杂或有更为简单的方案实现，仍能够继续优化；再者由于时间关系，初始版本中，部分功能并没有开发，将会在后续版本中进行开发和优化。网站达到要求后可能会部署上线。

### 功能
* 页面板块  
    * 网页内容分页显示、视频播放、详情浏览、定点跳转
    * `Markdown` 格式内容渲染
    * 分类模糊搜索资讯、教程，实时显示
    * 分类模糊搜索车队成员，实时显示
    * ~~全局搜索（开发中）~~
    * ~~小论坛（开发中）~~


* 用户板块
    * 用户账号登录、注册、登出
    * MD5加密处理
    * 记住我功能
    * 图形注册码获取、验证
    * 获取天气和问候
    * 主页上传、更新头像
    * 申请（撤销）成为管理员
    * 资料、密码的更新、重置（包含表单检验）
    * ~~消息中心获取个人消息（开发中）~~


* 管理板块
    * 管理员账号登录、登出
    * MD5加密处理
    * 获取天气和问候
    * 进入时提示是否未处理消息
    * 对话框操作
    * 管理员账号分页显示、CRUD（包含表单检验）
    * 管理员账号禁用检测，无法禁用最后一个启用的管理员
    * 管理员账号模糊搜索，实时显示
    * 用户账号分页显示、CRUD（包含表单检验）
    * 用户账号模糊搜索，实时显示
    * 消息中心显示管理员申请消息
    * 申请消息分页显示、同意或拒绝、批量拒绝
    * 申请消息模糊搜索，实时显示
    * 资讯、教程管理分页显示、自定义排序显示、CRUD
    * 资讯、教程模糊搜索，实时显示，隐藏选择条件
    * 使用 `mavon-editor` 编辑文章（ `Markdown` 格式）
    * 教程添加分类、删除分类
    * 成员管理分页显示、CRUD（包含表单检验）、头像上传、更新
    * 成员模糊搜索，实时显示


