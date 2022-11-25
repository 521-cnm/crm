package com.crm.workbench.service.impl;

import com.crm.commons.constant.Constant;
import com.crm.commons.utils.DateUtils;
import com.crm.commons.utils.UUIDUtils;
import com.crm.settings.domain.User;
import com.crm.workbench.domain.Customer;
import com.crm.workbench.domain.FunnelVO;
import com.crm.workbench.domain.Tran;
import com.crm.workbench.domain.TranHistory;
import com.crm.workbench.mapper.CustomerMapper;
import com.crm.workbench.mapper.TranHistoryMapper;
import com.crm.workbench.mapper.TranMapper;
import com.crm.workbench.service.TranService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("TranService")
public class TranServiceImpl implements TranService {
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private TranMapper tranMapper;
    @Autowired
    private TranHistoryMapper tranHistoryMapper;
    @Override
    public void saveCreateTran(Map<String, Object> map) {
        String customerName=(String)map.get("customerName");
        User user= (User) map.get(Constant.SESSION_USER);
        Customer customer=customerMapper.selectCustomerByName(customerName);
        if (customer==null){
            customer=new Customer();
            customer.setOwner(user.getId());
            customer.setName(customerName);
            customer.setId(UUIDUtils.getUUid());
            customer.setCreateTime(DateUtils.formatDateTime(new Date()));
            customer.setCreateBy(user.getId());
            customerMapper.insertCustomer(customer);
        }
        Tran tran=new Tran();
        tran.setStage((String)map.get("stage"));
        tran.setOwner((String)map.get("owner"));
        tran.setNextContactTime((String)map.get("nextContactTime"));
        tran.setName((String)map.get("name"));
        tran.setMoney((String)map.get("money"));
        tran.setId(UUIDUtils.getUUid());
        tran.setExpectedDate((String)map.get("expectedDate"));
        tran.setCustomerId(user.getId());
        tran.setCreateTime(DateUtils.formatDateTime(new Date()));
        tran.setContactSummary((String)map.get("contactSummary"));
        tran.setContactsId((String)map.get("contactsId"));
        tran.setActivityId((String)map.get("activityId"));
        tran.setDescription((String)map.get("description"));
        tran.setSource((String)map.get("source"));
        tran.setType((String)map.get("type"));
        tranMapper.insertTran(tran);
        TranHistory tranHistory=new TranHistory();
        tranHistory.setCreateBy(user.getId());
        tranHistory.setCreateTime(DateUtils.formatDateTime(new Date()));
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setId(UUIDUtils.getUUid());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setStage(tran.getStage());
        tranHistory.setTranId(tran.getId());
        tranHistoryMapper.insertTranHistory(tranHistory);
    }

    @Override
    public Tran queryTranForDetailById(String id) {
        return tranMapper.selectTranForDetailById(id);
    }

    @Override
    public List<FunnelVO> queryCountOfTranGroupByStage() {
        return tranMapper.selectCountOfTranGroupByStage();
    }
}
