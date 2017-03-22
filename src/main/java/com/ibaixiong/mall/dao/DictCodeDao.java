package com.ibaixiong.mall.dao;

import java.util.List;
import java.util.Map;

import com.ibaixiong.mall.model.DictCode;


public interface DictCodeDao {
    int deleteByPrimaryKey(Long id);

    int insert(DictCode record);

    int insertSelective(DictCode record);

    DictCode selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DictCode record);

    int updateByPrimaryKey(DictCode record);
    
    List<DictCode> queryDictCodeList(Map<String, Object> map);
}