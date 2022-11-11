package com.atlantis.pojo;

// 用于教程的分类
public class Category {
    // 类别的索引
    private Integer index;
    // 类别的名字
    private String type;
    // 路由的路径
    private String route;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return "Category{" +
                "index=" + index +
                ", type='" + type + '\'' +
                ", route='" + route + '\'' +
                '}';
    }
}
