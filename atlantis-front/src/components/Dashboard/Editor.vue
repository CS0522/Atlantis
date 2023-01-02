<template>
    <div>
        <div id="title-box">
            <div>{{ title }}</div>
        </div>
        <hr class="hr" />
        <div id="content-box">
            <!-- 开头，结尾各放一个 operation-box -->
            <div class="operation-box">
                <table class="table-operation" cellpadding=10px cellspacing=15px>
                    <tr align="center">
                        <td>
                            <button class="operation" style="background-color: #8beeff" 
                                @click="doSubmit()">提交</button>
                        </td>
                        <td>
                            <button class="operation" 
                                @click="reset()">重置</button>
                        </td>
                    </tr>
                </table>
            </div>

            <div class="edit-box">
                <!-- 分类box -->
                <div class="edit-index-box">
                    <span style="color: red">* </span>选择分类：<br/>
                    <div class="item-box">
                        <!-- 资讯编辑 -->
                        <select name="selectType" class="selectType" v-show="type==='news'"
                            v-model="form.index">
                            <option value="1">游戏新闻</option>
                            <option value="2">游戏公告</option>
                        </select>
                        <!-- 教程编辑 -->
                        <select name="selectType" class="selectType" v-show="type==='tutorial'"
                            v-model="form.index">
                            <option 
                                v-for="categoryItem in categoryItems" 
                                :key="categoryItem.index"
                                :value="categoryItem.index">
                                {{ categoryItem.type }}
                            </option>
                        </select>
                    </div>
                </div>
                <!-- <div class="edit-thumbnail-box">
                    文章缩略图：
                </div> -->
                <!-- 标题box -->
                <div class="edit-title-box">
                    <span style="color: red">* </span>文章标题：
                    <div class="item-box">
                        <input type="text" name="title"
                            class="form-input" v-model="form.title"/>
                    </div>
                </div>
                <!-- 文章box -->
                <div class="edit-content-box">
                    <span style="color: red">* </span>文章内容：
                    <div class="item-box" style="border: solid #8beeff; overflow: hidden;">
                        <!-- 富文本编辑器 -->
                        <mavon-editor class="editor" v-model="form.content"
                                    ref = md
                                    @imgAdd = "$imgAdd"
                                    placeholder = "输入文章内容"
                                    :toolbars = "toolbars"/>
                    </div>
                </div>
            </div>

            <div class="operation-box">
                <table class="table-operation" cellpadding=10px cellspacing=15px>
                    <tr align="center">
                        <td>
                            <button class="operation" style="background-color: #8beeff" 
                                @click="doSubmit()">提交</button>
                        </td>
                        <td>
                            <button class="operation" 
                                @click="reset()">重置</button>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</template>

<script>
import code from '@/utils/code';
import message from '@/utils/message';
import request from '@/utils/request';
import metoolbar from '@/utils/mavon-editor-toolbars'

export default {
    name: "newseditor",
    data() {
        return {
            title: '',
            typeStr: '',

            categoryItems: [],

            // 读入数据库中
            item: {},
            // 写入
            form: {},

            // 对象属性，传给mavon-editor
            toolbars: metoolbar.toolbars,
        }
    },
    methods: {
        load() {
            this.setCategoryItems();
            this.clearForm();
            this.setTypeStr();
            this.setTitle();
            this.setContent();
        },
        async setCategoryItems() {
            // 获取有哪些分类
            let res = await request.get("/categories");
            if (res.code === code.GET_OK)
            {
                this.categoryItems = res.data;
            }
            else 
            {
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET
                })
            }
        },
        
        clearForm() {
            this.form = {};
        },
        setTypeStr() {
            if (this.type === 'news') {
                this.typeStr = 'news';
            }
            else if (this.type === 'tutorial') {
                this.typeStr = 'tutorial';
            }
        },
        setTitle() {
            if (this.type === 'news') {
                this.title = '资讯编辑';
            }
            else if (this.type === 'tutorial') {
                this.title = '教程编辑';
            }
        },
        // 获取对应id的文章
        async setContent() {
            if (this.id <= 0) {
                // 操作为新增，或者id不合法
                return;
            }
            let res = await request.get("/" + this.typeStr + "Articles/" + this.id);
            if (res.code === code.GET_OK) {
                this.item = res.data;
                this.form = this.item;
            }
            else {
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET
                })
            }
        },

        reset() {
            this.load();
            this.$notify.info({
                title: "已重置",
                offset: code.OFFSET,
            })
        },

        async doSubmit() {
            let res;
            // 新增
            if (this.id <= 0) {
                res = await request.post("/" + this.typeStr + "Articles", this.form);
            }
            // 更新
            else {
                // form 中应该包含了查询出来的id
                res = await request.put("/" + this.typeStr + "Articles", this.form);
            }
            // console.log(res);
            // form 为要提交的表单
            if (res.code === code.UPDATE_OK) {
                // 跳转回管理界面
                // 提示消息
                this.$notify.success({
                    title: message.UPDATE_OK,
                    offset: code.OFFSET,
                })
            }
            else if (res.code === code.INSERT_OK)
            {
                this.$notify.success({
                    title: message.INSERT_OK,
                    offset: code.OFFSET,
                })
            }
            else if (res.code === code.INSERT_ERR)
            {
                this.$notify.error({
                    title: "请检查标题是否重复",
                    offset: code.OFFSET,
                })
            }
            else 
            {
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET,
                })
            }
        },

        // 绑定@imgAdd event
        $imgAdd(pos, $file){
            // 执行上传前方法，检测图片的合格性
            if (!this.beforeUpload($file))
            {
                return;
            }
            // 第一步：将图片上传到服务器.
           var formdata = new FormData();
           formdata.append('file', $file);
           // request中的请求头是json格式，不方便传输图片
           // 采用 form-data
           this.$axios({
                url: this.$store.state.newsArticleImgBaseUrl + "upload",
                method: 'post',
                data: formdata,
                headers: { 'Content-Type': 'multipart/form-data' },
                timeout: 5000,
                withCredentials: true,
           }).then((res) => {
                // 第二步：将返回的url替换到文本原位置![...](0) -> ![...](url)
                let result = JSON.parse(JSON.stringify(res));
                // console.log("result: " + result);
                if (result.data.code === code.UPLOAD_OK)
                {
                    this.$refs.md.$img2Url(pos, 
                            this.$store.state.newsArticleImgBaseUrl + "download/" + result.data.data);
                }
           })
        },

        beforeUpload(file) {
            if (file) {
                console.log("uploading...actionUrl: " + this.actionUrl);
                const postfix = file.name.split('.')[1]
                const isSizeOk = file.size < (code.MAX_SIZE * 1024 * 1024);
                if (['png', 'jpeg', 'jpg'].indexOf(postfix) < 0) {
                    this.$notify.error({
                        title: '图片仅支持 png, jpg, jpeg 格式',
                        offset: code.OFFSET
                    })
                    this.$refs.upload.clearFiles()
                    return false
                }
                if (!isSizeOk) {
                    this.$notify.error({
                        title: '上传图片大小不能超过 ' + String(code.MAX_SIZE) + 'MB！请删除失败文件后重新上传',
                        offset: code.OFFSET
                    })
                    return false
                }
                return true
            }
        }
    },
    created() {
            this.load();
        },
    props: ['type', 'id'],
}
</script>

<style scoped src="@/../public/css/editor-style.css">
</style>