package com.crm.settings.mapper;

import com.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_user
     *
     * @mbg.generated Sat Jul 09 22:54:42 GMT+08:00 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_user
     *
     * @mbg.generated Sat Jul 09 22:54:42 GMT+08:00 2022
     */
    int insert(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_user
     *
     * @mbg.generated Sat Jul 09 22:54:42 GMT+08:00 2022
     */
    int insertSelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_user
     *
     * @mbg.generated Sat Jul 09 22:54:42 GMT+08:00 2022
     */
    User selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_user
     *
     * @mbg.generated Sat Jul 09 22:54:42 GMT+08:00 2022
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_user
     *
     * @mbg.generated Sat Jul 09 22:54:42 GMT+08:00 2022
     */
    int updateByPrimaryKey(User record);
    User selectUserByLoginActAndPwd(Map<String,Object> map);
    List<User> selectAllUsers();
}
