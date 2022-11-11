package com.atlantis.pojo;

// 记录user同时是admin的情况
public class UserAdmin {
    private Integer userId;
    private Integer adminId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    @Override
    public String toString() {
        return "UserAdmin{" +
                "userId=" + userId +
                ", adminId=" + adminId +
                '}';
    }
}
