<template>
    <div>
        <div id="title-box">
            <div>车队成员</div>
        </div>
        <hr class="hr"/>
        <div id="content-box">
            <div>
                <div class="search-box">
                    <!-- 单纯对齐用 -->
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
                    <!-- 分页 -->
                    <el-pagination style="zoom: 220%; margin-bottom: 20px"
                                    @size-change="handleSizeChange" 
                                    @current-change="handleCurrentChange" 
                                    :current-page.sync="currentPage"
                                    :page-size="pageSize" 
                                    layout="prev, pager, next, jumper" 
                                    :total="totalNumber">
                    </el-pagination>

                    <div v-if="isShowFlag">
                        <img src="@/../public/imgs/commons/ae86.png" height="150px"
                            style="filter: drop-shadow(0px 5px 15px #eb1d2a);"/>
                        <p style="color: rgb(200, 200, 200)">这里什么也没有~</p>
                    </div>

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

            // 当前页
            currentPage: 1,
            // 每页条数
            pageSize: 6,
            // 数据总数
            totalNumber: 0,

            // 需要通过Id获取图像
            imgDownloadUrl: this.$store.state.memberImgBaseUrl + "download/",

            // 根据路由的不同，后端传入数据
            items: [],

            isShowFlag: false,
        }
    },
    methods: {
        // load
        load() {
            this.clearSearch();
            // 分页查询 getAll
            this.setItems();
        },
        setIsShowFlag(val) {
            this.isShowFlag = val;
        },

        clearSearch() {
            this.searchInput = '';
            this.isSearching = false;
        },
        async setItems() {
            try {
                let res = await request.get("/members/" + this.currentPage + "/" + this.pageSize);
                if (res.code === code.GET_OK) {
                    this.items = res.data.list;
                    this.totalNumber = res.data.total;
                    this.setIsShowFlag(false);
                }
                else if (res.code === code.GET_ERR) {
                    this.items = [];
                    this.totalNumber = 0;
                    this.setIsShowFlag(true);
                }
            }
            catch (err) {
                this.setIsShowFlag(true);
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET
                })
            }
        },

        async doSearch() {
            // 若输入为空，刷新、退出
            if (this.searchInput.trim() === '')
            {
                this.load();
                return;
            }
            this.isSearching = true;
            try {
                let res = await request.get("/members/" + this.searchInput.trim() +
                    "/" + this.currentPage + "/" + this.pageSize);
                if (res.code === code.GET_OK) {
                    this.items = res.data.list;
                    this.totalNumber = res.data.total;
                    this.setIsShowFlag(false);
                    this.$notify.success({
                        title: message.FIND_OK + "，共 " + this.totalNumber + " 条",
                        offset: code.OFFSET
                    })
                }
                else if (res.code === code.GET_ERR) {
                    this.items = [];
                    this.totalNumber = 0;
                    this.setIsShowFlag(true);
                    this.$notify.error({
                        title: message.FIND_ERR,
                        offset: code.OFFSET
                    })
                }
            }
            catch (err) {
                this.setIsShowFlag(true);
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET
                })
            }
        },

        handleSizeChange(val) {
            // console.log(`每页 ${val} 条`);
        },
        async handleCurrentChange(val) {
            try {
                let res;
                if (!this.isSearching) {
                    res = await request.get("/members/" + this.currentPage + "/" + this.pageSize);
                }
                else {
                    res = await request.get("/members/" + this.searchInput.trim() +
                        "/" + this.currentPage + "/" + this.pageSize);
                }
                if (res.code === code.GET_OK) {
                    this.items = res.data.list;
                    this.totalNumber = res.data.total;
                    this.setIsShowFlag(false);
                }
                else if (res.code === code.GET_ERR) {
                    this.items = [];
                    this.totalNumber = 0;
                    this.setIsShowFlag(true);
                }
            } catch (err) {
                this.setIsShowFlag(true);
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET
                })
            }
        },
    },
    watch: {
        // 含输入的记得掐空格
        async searchInput(val) {
            this.currentPage = 1;

            if (val.trim() === '')
            {
                this.load();
                return;
            }
            this.isSearching = true;
            // 延迟 0.2s 进行实时显示
            setTimeout(async () => {
                try {
                    let res = await request.get("/members/" + val.trim() +
                        "/" + this.currentPage + "/" + this.pageSize)
                    if (res.code === code.GET_OK) {
                        this.items = res.data.list;
                        this.totalNumber = res.data.total;
                        this.setIsShowFlag(false);
                    }
                    else if (res.code === code.GET_ERR) {
                        this.items = [];
                        this.totalNumber = 0;
                        this.setIsShowFlag(true);
                    }
                } catch (err) {
                    this.setIsShowFlag(true);
                    this.$notify.error({
                        title: message.REQUEST_ERR,
                        offset: code.OFFSET
                    })
                }
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