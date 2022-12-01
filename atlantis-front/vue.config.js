// const { defineConfig } = require('@vue/cli-service')
// module.exports = defineConfig({
//   transpileDependencies: true,
//   lintOnSave: false,
// },
// )

module.exports = {
    transpileDependencies: true,
    lintOnSave: false,
    chainWebpack:(config)=>{
        config.performance
            //set第一个参数：设置的别名，第二个参数：设置的路径
            .hints('warning')
            .maxEntrypointSize(50000000)
            .maxAssetSize(30000000)
            .assetFilter(function (assetFilename) {
                          return assetFilename.endsWith('.js')
                        })
        
    }
    // configureWebPack: {
    //     performance: {
    //         hints: 'warning',
    //         // 入口起点的最大体积
    //         maxEntrypointSize: 50000000,
    //         // 生成文件的最大体积
    //         maxAssetSize: 30000000,
    //         // 只给出 js 文件的性能提示
    //         assetFilter: function (assetFilename) {
    //           return assetFilename.endsWith('.js')
    //         }
    //       }
    // }
}
