package com.mixi.user.service;

import com.mixi.user.bean.entity.User;

public interface UserDaoService {

   boolean emailExist(String email);

   void register(User user);

   User getUserByFinger(String finger);
}
