package com.crm.workbench.service.impl;

import com.crm.commons.constant.Constant;
import com.crm.commons.utils.DateUtils;
import com.crm.commons.utils.UUIDUtils;
import com.crm.settings.domain.User;
import com.crm.workbench.domain.*;
import com.crm.workbench.mapper.*;
import com.crm.workbench.service.ClueService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("ClueService")
public class ClueServiceImpl implements ClueService {
    @Autowired
    private ClueMapper clueMapper;

    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private ContactsMapper contactsMapper;
    @Autowired
    private ClueRemarkMapper clueRemarkMapper;
    @Autowired
    private CustomerRemarkMapper customerRemarkMapper;
    @Autowired
    private ContactsRemarkMapper contactsRemarkMapper;
    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;
    @Autowired
    private ContactsActivityRelationMapper contactsActivityRelationMapper;
    @Autowired
    private TranMapper tranMapper;
    @Autowired
    private TranRemarkMapper tranRemarkMapper;
    @Override
    public int saveCreateClue(Clue clue) {
        return clueMapper.insertClue(clue);
    }

    @Override
    public Clue queryClueForDetailById(String id) {
        return clueMapper.selectClueForDetailById(id);
    }

    @Override
    public void saveConvertClue(Map<String, Object> map) {
        String clueId=(String)map.get("clueId");
        User user=(User)map.get(Constant.SESSION_USER);
        String isCreateTran=(String)map.get("isCreateTran");
        //根据id查询线索的信息
        Clue clue=clueMapper.selectClueById(clueId);
        //把该线索中有关公司的信息转换到客户表中
        Customer c=new Customer();
        c.setAddress(clue.getAddress());
        c.setContactSummary(clue.getContactSummary());
        c.setCreateBy(user.getId());
        c.setCreateTime(DateUtils.formatDateTime(new Date()));
        c.setDescription(clue.getDescription());
        c.setId(UUIDUtils.getUUid());
        c.setName(clue.getCompany());
        c.setNextContactTime(clue.getNextContactTime());
        c.setOwner(user.getId());
        c.setPhone(clue.getPhone());
        c.setWebsite(clue.getWebsite());
        customerMapper.insertCustomer(c);
        //把该线索中有关个人的信息转换到联系人表中
        Contacts co=new Contacts();
        co.setAddress(clue.getAddress());
        co.setAppellation(clue.getAppellation());
        co.setContactSummary(clue.getContactSummary());
        co.setCreateBy(user.getId());
        co.setCreateTime(DateUtils.formatDateTime(new Date()));
        co.setCustomerId(c.getId());
        co.setDescription(clue.getDescription());
        co.setEmail(clue.getEmail());
        co.setFullname(clue.getFullname());
        co.setId(UUIDUtils.getUUid());
        co.setJob(clue.getJob());
        co.setMphone(clue.getMphone());
        contactsMapper.insertContacts(co);
        //根据clueId查询该线索下所有的备注
        List<ClueRemark> crList= clueRemarkMapper.selectClueRemarkByClueId(clueId);
        if (crList!=null&&crList.size()>0){
            CustomerRemark cur=null;
            ContactsRemark cor=null;
            List<CustomerRemark> curList=new ArrayList<>();
            List<ContactsRemark> corList=new ArrayList<>();
            for (ClueRemark cr:crList){
                cur=new CustomerRemark();
                cur.setCreateBy(cr.getCreateBy());
                cur.setCreateTime(cr.getCreateTime());
                cur.setCustomerId(c.getId());
                cur.setEditBy(cr.getEditBy());
                cur.setEditFlag(cr.getEditFlag());
                cur.setEditTime(cr.getEditTime());
                cur.setId(UUIDUtils.getUUid());
                cur.setNoteContent(cr.getNoteContent());
                curList.add(cur);

                cor=new ContactsRemark();
                cor.setContactsId(co.getId());
                cor.setCreateBy(cr.getCreateBy());
                cor.setCreateTime(cr.getCreateTime());
                cor.setEditBy(cr.getEditBy());
                cor.setEditFlag(cr.getEditFlag());
                cor.setEditTime(cr.getEditTime());
                cor.setId(UUIDUtils.getUUid());
                cor.setNoteContent(cr.getNoteContent());
                corList.add(cor);
            }
            customerRemarkMapper.insertCustomerRemarkByList(curList);
            contactsRemarkMapper.insertContactsRemarkByList(corList);
        }
        List<ClueActivityRelation> carList=clueActivityRelationMapper.selectClueActivityRelationByClueId(clueId);
        if (carList!=null&&carList.size()>0){
            ContactsActivityRelation coar=null;
            List<ContactsActivityRelation> coarList=new ArrayList<>();
            for (ClueActivityRelation car:carList){
                coar=new ContactsActivityRelation();
                coar.setActivityId(car.getActivityId());
                coar.setContactsId(co.getId());
                coar.setId(UUIDUtils.getUUid());
                coarList.add(coar);
            }
            contactsActivityRelationMapper.insertContactsActivityRelationByList(coarList);
        }
        if ("true".equals(isCreateTran)){
            Tran tran=new Tran();
            tran.setActivityId((String)map.get("activityId"));
            tran.setContactsId(co.getId());
            tran.setCreateBy(user.getId());
            tran.setCreateTime(DateUtils.formatDateTime(new Date()));
            tran.setCustomerId(c.getId());
            tran.setExpectedDate((String)map.get("expectedDate"));
            tran.setId(UUIDUtils.getUUid());
            tran.setMoney((String)map.get("money"));
            tran.setName((String)map.get("name"));
            tran.setOwner(user.getId());
            tran.setStage((String)map.get("stage"));
            tranMapper.insertTran(tran);
            if (crList!=null&&crList.size()>0){
                TranRemark tr=null;
                List<TranRemark> trList=new ArrayList<>();
                for (ClueRemark cr:crList){
                    tr=new TranRemark();
                    tr.setCreateBy(cr.getCreateBy());
                    tr.setCreateTime(cr.getCreateTime());
                    tr.setEditBy(cr.getEditBy());
                    tr.setEditFlag(cr.getEditFlag());
                    tr.setId(UUIDUtils.getUUid());
                    tr.setNoteContent(cr.getNoteContent());
                    tr.setTranId(tran.getId());
                    trList.add(tr);
                }
                tranRemarkMapper.insertTranRemarkByList(trList);
            }
        }
        clueRemarkMapper.deleteClueRemarkByClueId(clueId);
        clueActivityRelationMapper.deleteClueActivityRelationByClueId(clueId);
        clueMapper.deleteClueById(clueId);
    }
}
