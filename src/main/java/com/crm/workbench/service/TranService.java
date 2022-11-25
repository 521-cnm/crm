package com.crm.workbench.service;

import com.crm.workbench.domain.Customer;
import com.crm.workbench.domain.FunnelVO;
import com.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;
public interface TranService {
    void saveCreateTran(Map<String,Object> map);
    Tran queryTranForDetailById(String id);
    List<FunnelVO> queryCountOfTranGroupByStage();
}
