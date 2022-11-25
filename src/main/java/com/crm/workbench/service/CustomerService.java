package com.crm.workbench.service;


import com.crm.workbench.domain.Customer;
import jdk.nashorn.internal.runtime.OptimisticBuiltins;

import java.util.List;
import java.util.Map;

public interface CustomerService {
    List<String> queryCustomerNameByName(String customerName);
}
