package com.atlantis.pojo;

// 论坛的话题
public class TopicJoinForumArticle {
    private Integer index;

    private String type;

    private Integer count;

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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "index=" + index +
                ", type='" + type + '\'' +
                ", count='" + count + '\'' +
                '}';
    }
}
