package com.crm.workbench.service.impl;

import com.crm.workbench.domain.ActivityRemark;
import com.crm.workbench.mapper.ActivityRemarkMapper;
import com.crm.workbench.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("ActivityRemarkService")
public class ActivityRemarkServiceImpl implements ActivityRemarkService {
    @Autowired
    ActivityRemarkMapper activityRemarkMapper;
    @Override
    public List<ActivityRemark> queryActivityRemarkForDetailByActivityId(String activityId) {
        return activityRemarkMapper.selectActivityForDetailById(activityId);
    }

    @Override
    public int saveCreateActivityRemark(ActivityRemark remark) {
        return activityRemarkMapper.insertActivityRemark(remark);
    }

    @Override
    public int deleteActivityRemarkById(String id) {
        return activityRemarkMapper.deleteActivityRemarkById(id);
    }

    @Override
    public int saveEditActivityRemark(ActivityRemark remark) {
        return activityRemarkMapper.updateActivityRemark(remark);
    }
}
