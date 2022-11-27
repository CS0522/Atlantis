package com.atlantis.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class TutorialArticle {
    // 新闻的唯一标识
    private Integer id;

    // 用于区分资讯的类别
    // 1 = 新闻，2 = 公告
    private Integer index;

    private String title;

    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createDate;

    private String thumbnail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "NewsArticle{" +
                "id=" + id +
                ", index=" + index +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createDate=" + createDate +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }
}
