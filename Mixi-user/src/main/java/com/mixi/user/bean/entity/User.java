package com.mixi.user.bean.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

}
