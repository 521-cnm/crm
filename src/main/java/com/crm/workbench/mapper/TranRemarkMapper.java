package com.crm.workbench.mapper;

import com.crm.workbench.domain.TranRemark;

import java.util.List;

public interface TranRemarkMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_remark
     *
     * @mbg.generated Fri Aug 05 11:32:28 GMT+08:00 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_remark
     *
     * @mbg.generated Fri Aug 05 11:32:28 GMT+08:00 2022
     */
    int insert(TranRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_remark
     *
     * @mbg.generated Fri Aug 05 11:32:28 GMT+08:00 2022
     */
    int insertSelective(TranRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_remark
     *
     * @mbg.generated Fri Aug 05 11:32:28 GMT+08:00 2022
     */
    TranRemark selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_remark
     *
     * @mbg.generated Fri Aug 05 11:32:28 GMT+08:00 2022
     */
    int updateByPrimaryKeySelective(TranRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_remark
     *
     * @mbg.generated Fri Aug 05 11:32:28 GMT+08:00 2022
     */
    int updateByPrimaryKey(TranRemark record);
    int insertTranRemarkByList(List<TranRemark> list);
    List<TranRemark> selectTranRemarkForDetailByTranId(String id);
}