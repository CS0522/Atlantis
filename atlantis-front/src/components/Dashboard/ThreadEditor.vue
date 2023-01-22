<template>
    <div>
        <div id="title-box">
            <div>帖子编辑</div>
        </div>
        <hr class="hr" />
        <div id="content-box">
            <!-- 开头，结尾各放一个 operation-box -->
            <div class="operation-box">
                <table class="table-operation" cellpadding=10px cellspacing=15px>
                    <tr align="center">
                        <td>
                            <button class="operation" style="background-color: #eb1d2a" 
                                @click="doEdit()">提交</button>
                        </td>
                        <td>
                            <button class="operation" style="background-color: #f64530"
                                @click="reset()">重置</button>
                        </td>
                    </tr>
                </table>
            </div>

            <div class="edit-box">
                <!-- 分类box -->
                <div class="edit-index-box">
                    <span style="color: red">* </span>选择话题：<br/>
                    <div class="item-box">                   
                        <select name="selectType" class="selectType"
                            v-model="form.index">
                            <option 
                                v-for="topicItem in topicItems" 
                                :key="topicItem.index"
                                :value="topicItem.index">
                                {{ topicItem.type }}
                            </option>
                        </select>
                    </div>
                </div>
                <!-- <div class="edit-thumbnail-box">
                    文章缩略图：
                </div> -->
                <!-- 标题box -->
                <div class="edit-title-box">
                    <span style="color: red">* </span>帖子标题：
                    <div class="item-box">
                        <input type="text" name="title"
                            class="form-input" v-model="form.title"/>
                    </div>
                </div>
                <!-- 文章box -->
                <div class="edit-content-box">
                    <span style="color: red">* </span>帖子内容：
                    <div class="item-box" style="border: solid #eb1d2a; overflow: hidden;">
                        <!-- 富文本编辑器 -->
                        <mavon-editor class="editor" v-model="form.content"
                                    ref = md
                                    @imgAdd = "$imgAdd"
                                    placeholder = "有什么想分享的，欢迎发帖~"
                                    :toolbars = "toolbars"/>
                    </div>
                </div>
            </div>

            <div class="operation-box">
                <table class="table-operation" cellpadding=10px cellspacing=15px>
                    <tr align="center">
                        <td>
                            <button class="operation" style="background-color: #eb1d2a" 
                                @click="doEdit()">提交</button>
                        </td>
                        <td>
                            <button class="operation" style="background-color: #f64530"
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
import metoolbar from '@/utils/mavon-editor-toolbars';
import 'github-markdown-css/github-markdown-light.css';

export default {
    name: "threadeditor",
    data() {
        return {
            topicItems: [],

            // accountInfo
            // accountInfo: this.$storage.get("accountInfo"),

            id: this.$route.query.id,

            // 获取
            form: {},

            // 对象属性，传给mavon-editor
            toolbars: metoolbar.toolbars,
        }
    },
    methods: {
        load() {
            this.topicItems = this.$storage.get('forumTopicItems');
            this.setForm();
            this.clearForm();
        },
        setForm() {
            request.get("/forumArticles/" + this.id).then(res => {
                if (res.code === code.GET_OK)
                {
                    this.form = res.data;
                }
            }).catch(err => {
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET
                })
            })
        },
        clearForm() {
            this.form = {};
        },

        reset() {
            this.load();
            this.$notify.info({
                title: "已重置",
                offset: code.OFFSET,
            })
        },

        async doEdit() {
            // 发布前检查
            if (!this.form.index || !this.form.title || !this.form.content)
            {
                this.$notify.info({
                    title: "请填入必要信息",
                    offset: code.OFFSET
                })
                return;
            }
            // update
            let res = await request.put("/forumArticles", this.form);
            if (res.code === code.UPDATE_OK)
            {
                this.$notify.success({
                    title: message.UPDATE_OK,
                    offset: code.OFFSET,
                })
                // back to dashboard
                // setTimeout(() => {
                //     this.$router.push("/dashboard/forum/thread");
                // }, 1000);
                setTimeout(() => {
                    this.load();
                }, 1000);
            }
            else if (res.code === code.UPDATE_ERR)
            {
                this.$notify.error({
                    title: message.UPDATE_ERR + "，请检查帖子是否重复",
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
}
</script>

<style scoped src="@/../public/css/editor-style.css">
</style>