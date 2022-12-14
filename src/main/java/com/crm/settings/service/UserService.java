package com.crm.settings.service;

import com.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User queryUserByLoginActPwd(Map<String,Object> map);
    List<User> queryAllUsers();
}
