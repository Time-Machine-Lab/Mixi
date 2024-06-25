package com.mixi.user.domain.entity;

import java.io.Serializable;

/**
 * 
 * @TableName mixi_user
 */
public class User implements Serializable {
    /**
     * 主键
     */
    private String id;

    /**
     * 账号
     */
    private String username;

    /**
     * 头像
     */
    private String password;

    /**
     * 昵称
     */
    private String avatar;

    /**
     * 密码
     */
    private String nickname;

    /**
     * 性别
     */
    private String sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 自我介绍
     */
    private String resume;

    /**
     * 注销标记
     */
    private String delFlag;

    /**
     * //已激活、未激活=游客
     */
    private String status;

    /**
     * 若是游客，则机器码不为空且唯一
     */
    private String machinecode;

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 账号
     */
    public String getUsername() {
        return username;
    }

    /**
     * 账号
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 头像
     */
    public String getPassword() {
        return password;
    }

    /**
     * 头像
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 昵称
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 昵称
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * 密码
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 密码
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 性别
     */
    public String getSex() {
        return sex;
    }

    /**
     * 性别
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 自我介绍
     */
    public String getResume() {
        return resume;
    }

    /**
     * 自我介绍
     */
    public void setResume(String resume) {
        this.resume = resume;
    }

    /**
     * 注销标记
     */
    public String getDelFlag() {
        return delFlag;
    }

    /**
     * 注销标记
     */
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * //已激活、未激活=游客
     */
    public String getStatus() {
        return status;
    }

    /**
     * //已激活、未激活=游客
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 若是游客，则机器码不为空且唯一
     */
    public String getMachinecode() {
        return machinecode;
    }

    /**
     * 若是游客，则机器码不为空且唯一
     */
    public void setMachinecode(String machinecode) {
        this.machinecode = machinecode;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        User other = (User) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getAvatar() == null ? other.getAvatar() == null : this.getAvatar().equals(other.getAvatar()))
            && (this.getNickname() == null ? other.getNickname() == null : this.getNickname().equals(other.getNickname()))
            && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getResume() == null ? other.getResume() == null : this.getResume().equals(other.getResume()))
            && (this.getDelFlag() == null ? other.getDelFlag() == null : this.getDelFlag().equals(other.getDelFlag()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getMachinecode() == null ? other.getMachinecode() == null : this.getMachinecode().equals(other.getMachinecode()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getAvatar() == null) ? 0 : getAvatar().hashCode());
        result = prime * result + ((getNickname() == null) ? 0 : getNickname().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getResume() == null) ? 0 : getResume().hashCode());
        result = prime * result + ((getDelFlag() == null) ? 0 : getDelFlag().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getMachinecode() == null) ? 0 : getMachinecode().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", avatar=").append(avatar);
        sb.append(", nickname=").append(nickname);
        sb.append(", sex=").append(sex);
        sb.append(", email=").append(email);
        sb.append(", resume=").append(resume);
        sb.append(", delFlag=").append(delFlag);
        sb.append(", status=").append(status);
        sb.append(", machinecode=").append(machinecode);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}