<template>
    <div>
        <div id="title-box">
            <!-- 根据动态路由传递参数判断title并渲染 -->
            <div>{{ title }}</div>
        </div>
        <hr class="hr"/>
        <div id="content-box">
            <!-- pagination -->
            <el-pagination style="zoom: 220%; margin-bottom: 20px"
                            @size-change="handleSizeChange" 
                            @current-change="handleCurrentChange" 
                            :current-page.sync="currentPage"
                            :page-size="pageSize" 
                            layout="prev, pager, next, jumper" 
                            :total="totalNumber">
            </el-pagination>
            <div>
                <ul v-for="item in items" :key="item.id" style="list-style-type: none">
                    <div class="item-box">
                        <li>
                            <!-- 修改 -->
                            <div class="item-id">{{item.id}}</div>
                            <div class="item-detail">{{item.detail}}</div>
                        </li>
                    </div>
                </ul>
            </div>
        </div>
    </div>
</template>

<script>
import code from '@/utils/code';
import message from '@/utils/message';
import request from '@/utils/request';

export default {
    name: "subcontent",
    data() {
        return {
            title: '',

            // for pagination
            // 当前页
            currentPage: 1,
            // 每页条数
            pageSize: 10,
            // 数据总数
            totalNumber: 0,

            // 后端传入数据，根据路由的不同
            items: [
                {
                    id: "1",
                    detail: "testExample"
                },
                {
                    id: "2",
                    detail: "testExample"
                },
                {
                    id: "3",
                    detail: "testExample"
                }
            ]
        }
    },
    created() {
        this.load();
    },

    methods: {
        // 用于设置标题
        load() {
            let categoryItems = this.$storage.get('tutorialCategoryItems');
            for (let i = 0; i < categoryItems.length; i++)
            {
                if (categoryItems[i].route === this.type)
                {
                    this.title = categoryItems[i].type;
                    return;
                }
            }
        },

        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
        },
        handleCurrentChange(val) {
            console.log("val: " + val);
            request.get("/" + this.typeStr + "/" + this.currentPage + "/" + this.pageSize).then(res => {
                if (res.code === code.GET_OK) {
                    this.items = res.data.list;
                    this.totalNumber = res.data.total;

                    console.log("currentpage after: " + this.currentPage);

                    console.log("total number: " + this.totalNumber);
                }
            }).catch(err => {
                console.log(err)
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: 150
                });
            })
            // console.log(`当前页: ${val}`);
        },
    },
    props: ['type']
}
</script>

<style scoped src="@/../public/css/subcontent-style.css">
</style>