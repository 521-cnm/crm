package com.crm.workbench.mapper;

import com.crm.workbench.domain.TranHistory;

import java.util.List;

public interface TranHistoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_history
     *
     * @mbg.generated Sat Aug 06 08:38:46 GMT+08:00 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_history
     *
     * @mbg.generated Sat Aug 06 08:38:46 GMT+08:00 2022
     */
    int insert(TranHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_history
     *
     * @mbg.generated Sat Aug 06 08:38:46 GMT+08:00 2022
     */
    int insertSelective(TranHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_history
     *
     * @mbg.generated Sat Aug 06 08:38:46 GMT+08:00 2022
     */
    TranHistory selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_history
     *
     * @mbg.generated Sat Aug 06 08:38:46 GMT+08:00 2022
     */
    int updateByPrimaryKeySelective(TranHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_history
     *
     * @mbg.generated Sat Aug 06 08:38:46 GMT+08:00 2022
     */
    int updateByPrimaryKey(TranHistory record);
    int insertTranHistory(TranHistory tranHistory);
    List<TranHistory> selectTranHistoryForDetailByTranId(String tranId);
}
