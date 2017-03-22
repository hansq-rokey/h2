package com.ibaixiong.mall.service;

import java.util.List;

import com.ibaixiong.entity.MerchantCouponMoneyRecord;

public interface MerchantCouponMoneyRecordService {
	
	int insert(MerchantCouponMoneyRecord record);
	
	List<MerchantCouponMoneyRecord> queryListByMerchantId(Long merchantId,Integer pageNo);
}
