<template>
    <div>
        <div id="title-box">
            <div>车队管理</div>
        </div>
        <hr class="hr" />
        <div id="content-box">
            <!-- 修改 -->
            <div>
                <div class="add-and-search-box">
                    <div class="add-button-box">
                        <button class="add-button" @click="doOperation('insert', -1)">添加成员</button>
                    </div>
                    <div class="search-bar">
                        <!-- 实现查询实时显示 -->
                        <el-input placeholder="搜索车队成员名或队内职位" 
                            v-model="searchInput" class="search-input"
                            width="100%">
                            <!-- 点击进行搜索 -->
                            <el-button slot="append" icon="el-icon-search" @click="doSearch()"></el-button>
                        </el-input>
                    </div>
                    <div class="refresh-box">
                        <img id="refresh-icon" src="@/../public/imgs/icons/refresh.png" height=70px 
                            @click="load()" />
                    </div>
                </div>
                <div>
                    <!-- pagination -->
                    <el-pagination style="zoom: 220%;
                                margin-bottom: 20px"  
                                @size-change="handleSizeChange"
                                @current-change="handleCurrentChange" 
                                :current-page.sync="currentPage" 
                                :page-size="pageSize"
                                layout="prev, pager, next, jumper" 
                                :total="totalNumber">
                    </el-pagination>
                    <ul v-for="item in items" :key="item.id" style="list-style-type: none">
                        <div class="item-box">
                            <li>
                                <!-- 修改 -->                     
                                <div class="item-detail">
                                    <div class="item-photo">
                                        <el-upload 
                                            class = "avatar-uploader" 
                                            :action = "imgUploadUrl + item.id"
                                            :show-file-list = "false"
                                            :on-success = "handleAvatarSuccess" 
                                            :before-upload = "beforeAvatarUpload">
                                            <img v-if = "item.status" 
                                                    :src = "imgDownloadUrl + item.id" 
                                                    class = "avatar">
                                            <i v-else class = "el-icon-plus avatar-uploader-icon"></i>
                                        </el-upload>
                                    </div>
                                    <div class="item-detail-realname">
                                        {{ item.realname }}
                                    </div>
                                    <div class="item-detail-operation">
                                        <button 
                                            class="item-detail-operation-button" 
                                            style="background-color: #8beeff"
                                            @click="doOperation('update', item.id)">更新</button>
                                        <button 
                                            class="item-detail-operation-button" 
                                            style="background-color: #f64530"
                                            @click="doOperation('delete', item.id)">删除</button>
                                    </div>
                                </div>
                            </li>
                        </div>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import code from '@/utils/code';
import request from '@/utils/request';
import message from '@/utils/message';

export default {
    name: "dashabout",
    inject: ['reload'],
    data() {
        return {
            // for search
            searchInput: '',
            isSearching: false,

            items: [],

            // for pagination
            currentPage: 1,
            pageSize: 6,
            totalNumber: 0,

            // 用户头像。需要通过userId获取图像
            imgDownloadUrl: this.$store.state.memberImgBaseUrl + "download/",
            imgUploadUrl: this.$store.state.memberImgBaseUrl + "upload/",
        }
    },
    methods: {
        load() {
            this.searchInput = '';
            this.isSearching = false;
            // getAll请求，获取所有
            // 分页查询
            request.get("/members/" + this.currentPage + "/" + this.pageSize).then(res => {
                if (res.code === code.GET_OK) {
                    this.items = res.data.list;
                    this.totalNumber = res.data.total;

                    console.log("total number: " + this.totalNumber);
                }
            }).catch(err => {
                console.log(err)
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: 70
                });
            })
        },

        doOperation(operation, objId) {
            // 修改vuex中的state数据
            this.$store.commit('setOperation', operation);
            this.$store.commit('setObjId', objId);
            // 向父路由emit一个show-dialog事件
            this.$emit('show-dialog', 'about')
        },

        doSearch() {
            // 若输入为空，刷新、退出
            if (this.searchInput.trim() === '')
            {
                this.load();
                return;
            }
            request.get("/members/" + this.searchInput.trim() + 
                        "/" + this.currentPage + "/" + this.pageSize).then(res => {
                        if (res.code === code.GET_OK && res.data.total)
                        {
                            this.items = res.data.list;
                            this.totalNumber = res.data.total;
                            this.$notify.success({
                                title: message.FIND_OK + "，共 " + this.totalNumber + " 条",
                                offset: 70
                            })
                        }
                        else 
                        {
                            this.items = [];
                            this.totalNumber = 0;
                            this.$notify.error({
                                title: message.FIND_ERR,
                                offset: 70
                            })
                        }
                    }).catch(err => {
                        this.$notify.error({
                            title: message.REQUEST_ERR,
                            offset: 70
                        })
                    })
        },

        handleSizeChange(val) {
            console.log("val: " + val);
        },
        async handleCurrentChange(val) {
            console.log("val: " + val);
            let res;
            if (!isSearching)
            {
                res = await request.get("/members/" + this.currentPage + "/" + this.pageSize);
            }
            else 
            {
                res = await request.get("/members/" + this.searchInput.trim() + 
                                    "/" + this.currentPage + "/" + this.pageSize);
            }
            if (res.code === code.GET_OK) {
                this.items = res.data.list;
                this.totalNumber = res.data.total;
                console.log("total number: " + this.totalNumber);
            }
            else
            {
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: 70
                })
            }
            // console.log(`当前页: ${val}`);
        },

        // 成员头像上传
        handleAvatarSuccess(res, file) {
            // 通过item.status改变状态
            this.reload();
            this.$notify.success({
                title: '头像更新成功',
                offset: 70
            })
        },
        beforeAvatarUpload(file) {
            if (file) {
                console.log("uploading...actionUrl: " + this.actionUrl);
                const postfix = file.name.split('.')[1]
                const isSizeOk = file.size < (2 * 1024 * 1024);
                if (['png', 'jpeg', 'jpg'].indexOf(postfix) < 0) {
                    this.$notify.error({
                        title: '头像仅支持 .png, .jpg, .jpeg 格式',
                        offset: 70
                    })
                    this.$refs.upload.clearFiles()
                    return false
                }
                if (!isSizeOk) {
                    this.$notify.error({
                        title: '上传头像大小不能超过 2MB',
                        offset: 70
                    })
                    return false
                }
                return file
            }
        }
    },
    created() {
        this.load();
    },
    
    watch: {
        // 含输入的记得掐空格！！！
        searchInput(val) {
            this.currentPage = 1;

            if (val.trim() === '')
            {
                this.load();
                return;
            }
            this.isSearching = true;
            // 延迟 0.2s 进行实时显示
            setTimeout(() => {
                request.get("/members/" + val.trim() + 
                        "/" + this.currentPage + "/" + this.pageSize).then(res => {
                        if (res.code === code.GET_OK && res.data.total)
                        {
                            this.items = res.data.list;
                            this.totalNumber = res.data.total;
                        }
                        else 
                        {
                            this.items = [];
                            this.totalNumber = 0;
                            // this.$notify.error({
                            //     title: message.FIND_ERR,
                            //     offset: 70
                            // })
                        }
                    }).catch(err => {
                        this.$notify.error({
                            title: message.REQUEST_ERR,
                            offset: 70
                        })
                    })
            }, 200);
        }
    },
}
</script>

<style scoped src="@/../public/css/dashabout-style.css">
/* img
{
    object-fit: cover;
} */
</style>