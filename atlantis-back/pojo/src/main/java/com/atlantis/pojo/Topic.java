package com.atlantis.pojo;

// 论坛的话题
public class Topic {
    private Integer index;

    private String type;

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
        return "Topic{" +
                "index=" + index +
                ", type='" + type + '\'' +
                ", route='" + route + '\'' +
                '}';
    }
}
