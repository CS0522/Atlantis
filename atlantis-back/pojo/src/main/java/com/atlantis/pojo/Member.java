package com.atlantis.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Member {
    private Integer id;
    private String realname;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date joinDate;
    private Integer status;
    private String position;

    private Integer hasPhoto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getHasPhoto() {
        return hasPhoto;
    }

    public void setHasPhoto(Integer hasPhoto) {
        this.hasPhoto = hasPhoto;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", realname='" + realname + '\'' +
                ", description='" + description + '\'' +
                ", joinDate=" + joinDate +
                ", status=" + status +
                ", position='" + position + '\'' +
                ", hasPhoto=" + hasPhoto +
                '}';
    }
}
