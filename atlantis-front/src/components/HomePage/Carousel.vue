<template>
    <div class="carousel">
        <!-- 设置走马灯高度 -->
        <el-carousel :interval="5000"  type="card" arrow="always" height="1000px">
            <el-carousel-item v-for="item in imgList" :key="item.id">
                <!-- 注意这里要绑定属性！ -->
                <img :src="item.img" height="1000px" class="image"/>
            </el-carousel-item>
        </el-carousel>
    </div>
</template>

<script>
export default {
    name: 'carousel-img',
    data(){
        return {
            // 需要后端传递数据
            imgList: [
                {
                    id: 0, img: require('@/../public/imgs/carousels/doraemon_11.png')
                },
                {
                    id: 1, img: require('@/../public/imgs/carousels/doraemon_12.png')
                },
                {
                    id: 2, img: require('@/../public/imgs/carousels/doraemon_13.png')
                },
                {
                    id: 3, img: require('@/../public/imgs/carousels/doraemon_17.png')
                }
            ]
        }
    },
    methods:{

    }
}
</script>
  
<style>
.el-carousel__item {
    color: #475669;
    font-size: 18px;
    opacity: 1;
    line-height: 500px;
    margin: 0;
    /* 设置圆角 */
    border-radius: 15px;
}

.el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
}

.carousel {
    /* 左右边距 */
    margin-top: 5cm;
    margin-left: 0.5cm;
    margin-right: 0.5cm;
    height: auto;
}
</style>