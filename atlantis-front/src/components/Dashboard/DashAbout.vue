<template>
    <div>
        <div id="title-box">
            <div>车队管理</div>
        </div>
        <hr class="hr" />
        <div id="content-box">
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
                    <!-- 刷新按钮功能 -->
                    <div class="refresh-box">
                        <img id="refresh-icon" src="@/../public/imgs/icons/refresh.png" height=70px 
                            @click="load()" />
                    </div>
                </div>
                <div>
                    <!-- 分页 -->
                    <el-pagination style="zoom: 220%;
                                margin-bottom: 20px"  
                                @size-change="handleSizeChange"
                                @current-change="handleCurrentChange" 
                                :current-page.sync="currentPage" 
                                :page-size="pageSize"
                                layout="prev, pager, next, jumper" 
                                :total="totalNumber">
                    </el-pagination>
                    <!-- 内容列表 -->
                    <ul v-for="item in items" :key="item.id" style="list-style-type: none">
                        <div class="item-box">
                            <li>
                                <!-- 成员头像上传、更新、成员名字、操作 -->                     
                                <div class="item-detail">
                                    <div class="item-photo">
                                        <el-upload 
                                            class = "avatar-uploader" 
                                            :action = "imgUploadUrl + item.id"
                                            :show-file-list = "false"
                                            :on-success = "handleAvatarSuccess" 
                                            :before-upload = "beforeAvatarUpload">
                                            <img v-if = "item.hasPhoto" 
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
    // 从父页面注入 reload 依赖
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

            // 上传、下载用户头像
            // 上传通过form-data，element-ui已经封装好的
            // 下载通过后台的流写入
            imgDownloadUrl: this.$store.state.memberImgBaseUrl + "download/",
            imgUploadUrl: this.$store.state.memberImgBaseUrl + "upload/",
        }
    },
    methods: {
        load() {
            // 首先清空搜索
            this.clearSearch();
            // 分页查询，getAll
            this.setItems();
        },
        clearSearch() {
            this.searchInput = '';
            this.isSearching = false;
        },
        // 分页查询 getAll
        setItems() {
            request.get("/members/" + this.currentPage + "/" + this.pageSize).then(res => {
                if (res.code === code.GET_OK) {
                    // res.data 返回数据
                    // list 包含内容
                    // total 包含查到的总个数
                    this.items = res.data.list;
                    this.totalNumber = res.data.total;
                    // console.log("total number: " + this.totalNumber);
                }
            }).catch(err => {
                console.log(err)
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET
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
            // 输入不为空
            // 搜索状态设置为 true
            this.isSearching = true;
            request.get("/members/" + this.searchInput.trim() + 
                        "/" + this.currentPage + "/" + this.pageSize).then(res => {
                        // 获取成功 且 存在数据
                        if (res.code === code.GET_OK && res.data.total)
                        {
                            this.items = res.data.list;
                            this.totalNumber = res.data.total;
                            this.$notify.success({
                                title: message.FIND_OK + "，共 " + this.totalNumber + " 条",
                                offset: code.OFFSET
                            })
                        }
                        // 获取失败 或 没有数据
                        else 
                        {
                            this.items = [];
                            this.totalNumber = 0;
                            this.$notify.error({
                                title: message.FIND_ERR,
                                offset: code.OFFSET
                            })
                        }
                    // 请求失败
                    }).catch(err => {
                        this.$notify.error({
                            title: message.REQUEST_ERR,
                            offset: code.OFFSET
                        })
                    })
        },

        handleSizeChange(val) {
            // console.log("val: " + val);
        },
        async handleCurrentChange(val) {
            // console.log("val: " + val);
            let res;
            // 首先判断是否处在搜索状态
            // 不是，则请求 getAll
            if (!this.isSearching)
            {
                res = await request.get("/members/" + this.currentPage + "/" + this.pageSize);
            }
            // 是，则请求 find 且页面改变
            else 
            {
                res = await request.get("/members/" + this.searchInput.trim() + 
                                    "/" + this.currentPage + "/" + this.pageSize);
            }
            if (res.code === code.GET_OK) {
                this.items = res.data.list;
                this.totalNumber = res.data.total;
                // console.log("total number: " + this.totalNumber);
            }
            else
            {
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET
                })
            }
            // console.log(`当前页: ${val}`);
        },

        // 成员头像上传
        // element-ui 提供的 api
        handleAvatarSuccess(res, file) {
            this.reload();
            this.$notify.success({
                title: '头像更新成功',
                offset: code.OFFSET
            })
        },
        // element-ui 提供的 api
        // 上传前进行图像的检验
        beforeAvatarUpload(file) {
            if (file) {
                // console.log("uploading...actionUrl: " + this.actionUrl);
                const postfix = file.name.split('.')[1]
                const isSizeOk = file.size < (code.MAX_SIZE * 1024 * 1024);
                if (['png', 'jpeg', 'jpg'].indexOf(postfix) < 0) {
                    this.$notify.error({
                        title: '头像仅支持 png, jpg, jpeg 格式',
                        offset: code.OFFSET
                    })
                    this.$refs.upload.clearFiles()
                    return false
                }
                if (!isSizeOk) {
                    this.$notify.error({
                        title: '上传头像大小不能超过' + String(code.MAX_SIZE) + 'MB',
                        offset: code.OFFSET
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
        // 含输入的记得掐空格
        searchInput(val) {
            // 首先让当前页面回到 1
            this.currentPage = 1;

            // 如果 trim 后搜索内容为空，说明没有搜索，退出
            if (val.trim() === '')
            {
                // 同时 isSearching 也会归回 false
                this.load();
                return;
            }
            // 否则在搜索，将搜索状态设置为 true
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
                            //     offset: code.OFFSET
                            // })
                        }
                    }).catch(err => {
                        this.$notify.error({
                            title: message.REQUEST_ERR,
                            offset: code.OFFSET
                        })
                    })
            }, 200);
        }
    },
}
</script>

<style scoped src="@/../public/css/dashabout-style.css">
</style>