package com.crm.workbench.mapper;

import com.crm.workbench.domain.Contacts;
import com.crm.workbench.domain.Customer;

import java.util.List;

public interface CustomerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer
     *
     * @mbg.generated Fri Aug 05 09:06:54 GMT+08:00 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer
     *
     * @mbg.generated Fri Aug 05 09:06:54 GMT+08:00 2022
     */
    int insert(Customer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer
     *
     * @mbg.generated Fri Aug 05 09:06:54 GMT+08:00 2022
     */
    int insertSelective(Customer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer
     *
     * @mbg.generated Fri Aug 05 09:06:54 GMT+08:00 2022
     */
    Customer selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer
     *
     * @mbg.generated Fri Aug 05 09:06:54 GMT+08:00 2022
     */
    int updateByPrimaryKeySelective(Customer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer
     *
     * @mbg.generated Fri Aug 05 09:06:54 GMT+08:00 2022
     */
    int updateByPrimaryKey(Customer record);
    int insertCustomer(Customer customer);
    List<String> selectCustomerNameByName(String name);
    Customer selectCustomerByName(String name);
}