package com.atlantis.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

// 留言类
public class CommentJoinForumArticle {
    private Integer index;

    private Integer id;

    private String name;

    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createDate;

    // 来自的帖子的 title
    private String title;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "CommentJoinForumArticle{" +
                "index=" + index +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", createDate=" + createDate +
                ", title='" + title + '\'' +
                '}';
    }
}
