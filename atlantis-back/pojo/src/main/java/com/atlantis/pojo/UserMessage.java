package com.atlantis.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class UserMessage {
    private Integer index;
    private Integer source;
    private Integer destination;
    private String title;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date sendDate;
    private Integer status;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getDestination() {
        return destination;
    }

    public void setDestination(Integer destination) {
        this.destination = destination;
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

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserMessage{" +
                "index=" + index +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", sendDate=" + sendDate +
                ", status=" + status +
                '}';
    }
}
