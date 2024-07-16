package com.mixi.user.domain.entity;


import lombok.Builder;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mixi.user.domain.vo.InfoVo;
import com.mixi.user.utils.UuidUtils;

import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;

/**
 * 
 * @TableName mixi_user
 */
@Data
@TableName("mixi_user")
public class User implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 账号
     */
//    从2000000000开始自增
    //不可修改
    @Transient
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

    public static User baseBuild(String email,String password){
        User user = new User();
        user.setId(UuidUtils.creatUuid());
        user.setEmail(email);
        user.setAvatar("默认头像");
        user.setNickname("默认账号");
        user.setResume("这里什么都没有发送");
        user.setPassword(password);
        return user;
    }

    public static User InfoTo(InfoVo infoVo,String uid) {
        User user = new User();
        user.setId(uid);
        user.setAvatar(infoVo.getAvatar());
        user.setNickname(infoVo.getNickname());
        user.setSex(infoVo.getSex());
        user.setResume(infoVo.getResume());
        return user;
    }
}