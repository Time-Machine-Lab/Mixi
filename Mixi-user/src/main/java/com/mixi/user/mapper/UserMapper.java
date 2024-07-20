package com.mixi.user.mapper;

import com.mixi.user.bean.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author yuech
* @description 针对表【mixi_user】的数据库操作Mapper
* @createDate 2024-06-25 16:18:03
* @Entity com.mixi.user.domain.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




