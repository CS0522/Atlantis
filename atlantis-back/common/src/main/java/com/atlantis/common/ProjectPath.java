package com.atlantis.common;

public class ProjectPath {
    // 统一配置项目路径
    public static String getPhotosPath() {
        return (System.getProperty("user.dir") + "/Data/photos/");
    }

    public static String getArticlePicturesPath() {
        return (System.getProperty("user.dir") + "/Data/article_pictures/");
    }
}
