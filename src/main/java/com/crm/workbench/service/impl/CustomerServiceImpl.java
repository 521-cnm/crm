package com.crm.workbench.service.impl;

import com.crm.workbench.mapper.CustomerMapper;
import com.crm.workbench.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("CustomerService")
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;
    @Override
    public List<String> queryCustomerNameByName(String customerName) {
        return customerMapper.selectCustomerNameByName(customerName);
    }
}
