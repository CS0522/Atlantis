<template>
    <div>
        <div id="title-box">
            <div>车队成员</div>
        </div>
        <hr class="hr"/>
        <div id="content-box">
            <div>
                <div class="search-box">
                    <!-- 纯粹对齐用 -->
                    <div class="add-button-box">
                        <button style="cursor:auto; opacity: 0"></button>
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
                    <el-pagination style="zoom: 220%; margin-bottom: 20px"
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
                                <div class="item-detail">
                                    <div class="item-photo">
                                        <img :src="imgDownloadUrl + item.id" 
                                            class="avatar">
                                    </div>
                                    <div class="item-detail-realname">
                                        {{ item.realname }}
                                    </div>
                                    <table style="margin: auto;line-height: 40px;">
                                        <tr align="right">
                                            <td>
                                                队内职位：
                                            </td>
                                            <td align="center">
                                                <div class="item-detail-position">
                                                    {{ item.position }}
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                加入时间：
                                            </td>
                                            <td align="center">
                                                <div class="item-detail-joindate">
                                                    {{ item.joinDate }}
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
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
    name: "aboutmember",
    data() {
        return {

            searchInput: '',
            isSearching: false,

            // for pagination
            // 当前页
            currentPage: 1,
            // 每页条数
            pageSize: 6,
            // 数据总数
            totalNumber: 0,

            // 头像。需要通过Id获取图像
            imgDownloadUrl: this.$store.state.memberImgBaseUrl + "download/",

            // 后端传入数据，根据路由的不同
            items: [],
        }
    },
    methods: {
        // load
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

        doSearch() {
            // 若输入为空，刷新、退出
            if (this.searchInput.trim() === '')
            {
                this.load();
                return;
            }
            this.isSearching = true;
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
            console.log(`每页 ${val} 条`);
        },
        async handleCurrentChange(val) {
            console.log("val: " + val);
            let res;
            if (!this.isSearching)
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
            }
            else
            {
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: 70
                })
            }
        },
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
    created() {
        this.load();
    },

}
</script>

<style scoped src="@/../public/css/aboutmember-style.css">
</style>