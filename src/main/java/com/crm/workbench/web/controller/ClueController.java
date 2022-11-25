package com.crm.workbench.web.controller;

import com.crm.commons.constant.Constant;
import com.crm.commons.domain.ReturnObject;
import com.crm.commons.utils.DateUtils;
import com.crm.commons.utils.UUIDUtils;
import com.crm.settings.domain.DicValue;
import com.crm.settings.domain.User;
import com.crm.settings.service.DicValueService;
import com.crm.settings.service.UserService;
import com.crm.workbench.domain.Activity;
import com.crm.workbench.domain.Clue;
import com.crm.workbench.domain.ClueActivityRelation;
import com.crm.workbench.domain.ClueRemark;
import com.crm.workbench.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class ClueController {
    @Autowired
    private UserService userService;
    @Autowired
    private DicValueService dicValueService;
    @Autowired
    private ClueService clueService;
    @Autowired
    private ClueRemarkService clueRemarkService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private ClueActivityRelationService clueActivityRelationService;
    @RequestMapping("/workbench/clue/index")
    public String index(HttpServletRequest request){
        List<User> userList=userService.queryAllUsers();
        List<DicValue> appellationList=dicValueService.queryDicValueByTypeCode("appellation");
        List<DicValue> clueStateList=dicValueService.queryDicValueByTypeCode("clueState");
        List<DicValue> sourceList = dicValueService.queryDicValueByTypeCode("source");
        request.setAttribute("userList",userList);
        request.setAttribute("appellationList",appellationList);
        request.setAttribute("clueStateList",clueStateList);
        request.setAttribute("sourceList",sourceList);
        return "workbench/clue/index";
    }
    @RequestMapping("/workbench/clue/saveCreateClue")
    public @ResponseBody Object saveCreateClue(Clue clue, HttpSession session){
        User user=(User) session.getAttribute(Constant.SESSION_USER);
        clue.setId(UUIDUtils.getUUid());
        clue.setCreateBy(user.getId());
        clue.setCreateTime(DateUtils.formatDateTime(new Date()));
        ReturnObject returnObject = new ReturnObject();
        int res = clueService.saveCreateClue(clue);
        try {
            if (res>0){
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            }else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试");
        }
        return returnObject;
    }
    @RequestMapping("/workbench/clue/detailClue")
    public String detailClue(String id,HttpServletRequest request){
        Clue clue =clueService.queryClueForDetailById(id);
        List<ClueRemark> clueRemarkList=clueRemarkService.queryClueRemarkForDetailByClueId(id);
        List<Activity> activityList=activityService.queryActivityForDetailByClueId(id);
        request.setAttribute("clue",clue);
        request.setAttribute("clueRemarkList",clueRemarkList);
        request.setAttribute("activityList",activityList);
        return "workbench/clue/detail";
    }
    @RequestMapping("/workbench/clue/queryActivityForDetailByNameClueId")
    public @ResponseBody Object queryActivityForDetailByNameClueId(String activityName,String clueId){
        Map<String,Object> map=new HashMap<>();
        map.put("activityName",activityName);
        map.put("clueId",clueId);
        List<Activity> activityList=activityService.queryActivityForDetailByNameClueId(map);
        return activityList;
    }
    @RequestMapping("/workbench/clue/saveBund")
    public @ResponseBody Object saveBund(String[] activityId,String clueId){
        ClueActivityRelation car=null;
        List<ClueActivityRelation> relationList=new ArrayList<>();
        for (String ai:activityId){
            car=new ClueActivityRelation();
            car.setActivityId(ai);
            car.setClueId(clueId);
            car.setId(UUIDUtils.getUUid());
            relationList.add(car);

        }
        ReturnObject returnObject = new ReturnObject();
        int res = clueActivityRelationService.saveCreateClueActivityRelationByList(relationList);
        try {
            if (res>0){
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
                List<Activity> activityList=activityService.queryActivityForDetailByIds(activityId);
                returnObject.setRetData(activityList);
            }else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试");
        }
        return returnObject;
    }
    @RequestMapping("/workbench/clue/saveUnbund")
    public @ResponseBody Object saveUnbund(ClueActivityRelation relation){
        ReturnObject returnObject = new ReturnObject();
        int res=clueActivityRelationService.deleteClueActivityRelationByClueIdActivityId(relation);
        try {
            if (res>0){
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            }else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试");
        }
        return returnObject;
    }
    @RequestMapping("/workbench/clue/toConvert")
    public String toConvert(String id,HttpServletRequest request){
        Clue clue = clueService.queryClueForDetailById(id);
        List<DicValue> stageList=dicValueService.queryDicValueByTypeCode("stage");
        request.setAttribute("clue",clue);
        request.setAttribute("stageList",stageList);
        return "workbench/clue/convert";
    }
    @RequestMapping("/workbench/clue/queryActivityForConvertByNameClueId")
    public @ResponseBody Object queryActivityForConvertByNameClueId(String activityName,String clueId){
        Map<String,Object> map=new HashMap<>();
        map.put("activityName",activityName);
        map.put("clueId",clueId);
        List<Activity> activityList=activityService.queryActivityForConvertByNameClueId(map);
        return activityList;
    }
    @RequestMapping("/workbench/clue/convertClue")
    public @ResponseBody Object convertClue(String clueId, String money, String name, String expectedDate, String stage, String activityId, String isCreateTran,HttpSession session){
        Map<String,Object> map=new HashMap<>();
        map.put("clueId",clueId);
        map.put("money",money);
        map.put("name",name);
        map.put("expectedDate",expectedDate);
        map.put("stage",stage);
        map.put("activityId",activityId);
        map.put("isCreateTran",isCreateTran);
        map.put(Constant.SESSION_USER,session.getAttribute(Constant.SESSION_USER));
        ReturnObject returnObject = new ReturnObject();
        try {
            clueService.saveConvertClue(map);
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试");
        }
        return returnObject;
    }
}
