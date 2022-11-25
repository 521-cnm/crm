package com.crm.workbench.service.impl;

import com.crm.workbench.domain.ClueActivityRelation;
import com.crm.workbench.mapper.ClueActivityRelationMapper;
import com.crm.workbench.service.ClueActivityRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ClueActivityRelationService")
public class ClueActivityRelationServiceImpl implements ClueActivityRelationService {
    @Autowired
    ClueActivityRelationMapper clueActivityRelationMapper;
    @Override
    public int saveCreateClueActivityRelationByList(List<ClueActivityRelation> list) {
        return clueActivityRelationMapper.insertClueActivityRelationByList(list);
    }

    @Override
    public int deleteClueActivityRelationByClueIdActivityId(ClueActivityRelation relation) {
        return clueActivityRelationMapper.deleteClueActivityRelationByClueIdActivityId(relation);
    }
}
