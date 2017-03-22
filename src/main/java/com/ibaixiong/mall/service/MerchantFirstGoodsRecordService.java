package com.ibaixiong.mall.service;

import java.util.List;

import com.ibaixiong.entity.MerchantFirstGoodsMoneyRecord;

public interface MerchantFirstGoodsRecordService {
	
	int insertSelective(MerchantFirstGoodsMoneyRecord record);
	
	List<MerchantFirstGoodsMoneyRecord> queryListByMerchantId(Long merchantId,Integer pageNo);
}
