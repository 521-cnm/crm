package com.crm.workbench.web.controller;

import com.crm.commons.constant.Constant;
import com.crm.commons.domain.ReturnObject;
import com.crm.settings.domain.DicValue;
import com.crm.settings.domain.User;
import com.crm.settings.service.DicValueService;
import com.crm.settings.service.UserService;
import com.crm.workbench.domain.FunnelVO;
import com.crm.workbench.domain.Tran;
import com.crm.workbench.domain.TranHistory;
import com.crm.workbench.domain.TranRemark;
import com.crm.workbench.service.CustomerService;
import com.crm.workbench.service.TranHistoryService;
import com.crm.workbench.service.TranRemarkService;
import com.crm.workbench.service.TranService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

@Controller
public class TranController {
    @Autowired
    private DicValueService dicValueService;
    @Autowired
    private UserService userService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private TranService tranService;
    @Autowired
    private TranRemarkService tranRemarkService;
    @Autowired
    private TranHistoryService tranHistoryService;
    @RequestMapping("/workbench/transaction/index")
    public String index(HttpServletRequest request){
        List<DicValue> stageList=dicValueService.queryDicValueByTypeCode("stage");
        List<DicValue> transactionTypeList=dicValueService.queryDicValueByTypeCode("transactionType");
        List<DicValue> sourceList=dicValueService.queryDicValueByTypeCode("source");
        request.setAttribute("transactionTypeList",transactionTypeList);
        request.setAttribute("sourceList",sourceList);
        request.setAttribute("stageList",stageList);
        return "workbench/transaction/index";
    }
    @RequestMapping("/workbench/transaction/toSave")
    public String toSave(HttpServletRequest request){
        List<User> userList=userService.queryAllUsers();
        List<DicValue> stageList=dicValueService.queryDicValueByTypeCode("stage");
        List<DicValue> transactionTypeList=dicValueService.queryDicValueByTypeCode("transactionType");
        List<DicValue> sourceList=dicValueService.queryDicValueByTypeCode("source");
        request.setAttribute("transactionTypeList",transactionTypeList);
        request.setAttribute("sourceList",sourceList);
        request.setAttribute("stageList",stageList);
        request.setAttribute("userList",userList);
        return "workbench/transaction/save";
    }
    @RequestMapping("/workbench/transaction/getPossibilityByStage")
    public @ResponseBody Object getPossibilityByStage(String stageValue){
        ResourceBundle bundle=ResourceBundle.getBundle("possibility");
        String possibility=bundle.getString(stageValue);
        return possibility;
    }
    @RequestMapping("/workbench/transaction/queryCustomerNameByName")
    public @ResponseBody Object queryCustomerNameByName(String customerName){
        List<String> customerNameList=customerService.queryCustomerNameByName(customerName);
        return customerNameList;
    }
    @RequestMapping("/workbench/transaction/saveCreateTran")
    public @ResponseBody Object saveCreateTran(@RequestParam Map<String,Object> map, HttpSession session){
        map.put(Constant.SESSION_USER,session.getAttribute(Constant.SESSION_USER));
        ReturnObject returnObject=new ReturnObject();
        try {
            tranService.saveCreateTran(map);
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试");
        }
        return returnObject;
    }
    @RequestMapping("/workbench/chart/transaction/detailTran")
    public String detailTran(String id,HttpServletRequest request){
        Tran tran=tranService.queryTranForDetailById(id);
        List<TranRemark> remarkList=tranRemarkService.queryTranRemarkForDetailByTranId(id);
        List<TranHistory> tranHistoryList=tranHistoryService.queryTranHistoryForDetailByTranId(id);
        ResourceBundle bundle=ResourceBundle.getBundle("possibility");
        String possibility=bundle.getString(tran.getStage());
        tran.setPossibility(possibility);
        request.setAttribute("tran",tran);
        request.setAttribute("remarkList",remarkList);
        request.setAttribute("tranHistoryList",tranHistoryList);
        List<DicValue> stageList=dicValueService.queryDicValueByTypeCode("stage");
        request.setAttribute("stageList",stageList);
        return "workbench/transaction/detail";
    }
    @RequestMapping("/workbench/chart/transaction/queryCountOfTranGroupByStage")
    public @ResponseBody Object queryCountOfTranGroupByStage(){
        List<FunnelVO> funnelVOList=tranService.queryCountOfTranGroupByStage();
        return funnelVOList;
    }
}
