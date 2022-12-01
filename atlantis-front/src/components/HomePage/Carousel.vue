<template>
    <div class="carousel">
        <!-- 设置走马灯高度 -->
        <el-carousel :interval="5000"  type="card" arrow="always" height="1000px">
            <el-carousel-item v-for="item in imgList" :key="item.id">
                <!-- 这里要绑定v-bind -->
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
            // 存储在前端
            imgList: [
                {
                    id: 0, img: require('@/../public/imgs/carousels/carousel_01.png')
                },
                {
                    id: 1, img: require('@/../public/imgs/carousels/carousel_02.png')
                },
                {
                    id: 2, img: require('@/../public/imgs/carousels/carousel_03.png')
                },
                {
                    id: 3, img: require('@/../public/imgs/carousels/carousel_04.png')
                },
                {
                    id: 4, img: require('@/../public/imgs/carousels/carousel_05.png')
                },
                {
                    id: 5, img: require('@/../public/imgs/carousels/carousel_06.png')
                },
                {
                    id: 6, img: require('@/../public/imgs/carousels/carousel_07.png')
                },
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