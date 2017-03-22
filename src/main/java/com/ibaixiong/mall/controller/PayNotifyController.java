/**
 * ibaixiong.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.ibaixiong.mall.controller;

import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alipay.util.AlipayNotify;
import com.ibaixiong.constant.Constant;
import com.ibaixiong.entity.MerchantBondMoneyRecord;
import com.ibaixiong.entity.MerchantCouponMoneyRecord;
import com.ibaixiong.entity.MerchantFirstGoodsMoneyRecord;
import com.ibaixiong.entity.MerchantRebateMoneyRecord;
import com.ibaixiong.entity.MerchantSaleMoneyRecord;
import com.ibaixiong.entity.SsssCityMerchant;
import com.ibaixiong.entity.User;
import com.ibaixiong.entity.util.BalanceChangeStatusEnum;
import com.ibaixiong.mall.service.PayNotifyService;
import com.ibaixiong.mall.service.SsssCityMerchantService;
import com.papabear.order.api.OrderService;
import com.papabear.order.entity.MallOrder;
import com.papabear.pay.entity.PayChannel;
import com.weixinpay.model.Notify;

/**
 * @description
 * @author zhaolei
 * @create 2015年11月19日-上午11:25:42
 */
@Controller
@RequestMapping("/pay/notify")
class PayNotifyController extends AbstractController{
	
	Logger logger= LoggerFactory.getLogger(getClass());
	@Resource
	PayNotifyService payAfterService;
	@Resource
	SsssCityMerchantService ssssCityMerchantService;
	@Resource
    OrderService orderService;
	
	@RequestMapping("/ali/app")
	void aliApp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		aliPayNotify(request, response, PayChannel.ALIPAY_APP);
	}

	@RequestMapping("/ali/pc")
	void aliPc(HttpServletRequest request, HttpServletResponse response) throws Exception {
		aliPayNotify(request, response, PayChannel.ALIPYAY_PC);
	}

	/**
	 * 支付宝回调通知处理
	 * 
	 * @param request
	 * @param response
	 * @param payType
	 * @throws Exception
	 */
	private void aliPayNotify(HttpServletRequest request, HttpServletResponse response, PayChannel payChannel) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}

		String msg = "";
		if (AlipayNotify.verify(params)) {
			String trade_status = params.get("trade_status");
			if ("TRADE_FINISHED".equals(trade_status) || "TRADE_SUCCESS".equals(trade_status)) {
				payAfterService.payNotifyAlipay(params, payChannel, request.getRemoteAddr());
				createRecord(Float.valueOf(params.get("total_fee")),params.get("out_trade_no"));
			}
			msg = "success";
		} else {
			msg = "fail";
		}
		PrintWriter writer = null;
		writer = response.getWriter();
		writer.write(msg);
		writer.close();
	}

	@RequestMapping("/wx/app")
	void wxApp(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		wxPayNotify(request, response, PayChannel.WXPAY_APP);
	}

	@RequestMapping("/wx/pc")
	void wxPc(HttpServletRequest request, HttpServletResponse response) throws Exception {
		wxPayNotify(request, response, PayChannel.WXPAY_PC);
	}

	private void wxPayNotify(HttpServletRequest request, HttpServletResponse response, PayChannel payChannel) throws Exception {
		String inputLine = null;
		String notityXml = ""; // "<xml><appid><![CDATA[wxa6aa5833e4953106]]></appid><bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[1269303801]]></mch_id><nonce_str><![CDATA[1817547416]]></nonce_str><openid><![CDATA[o98rqvoBRh9n9uQA9gnrAmrg453E]]></openid><out_trade_no><![CDATA[1442303706]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[E79D80F5B0B5549CCA4D52603D3ABEDE]]></sign><time_end><![CDATA[20150916181832]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[NATIVE]]></trade_type><transaction_id><![CDATA[1003230407201509160898807762]]></transaction_id></xml>";
		String resXml = "";
		try {
			while ((inputLine = request.getReader().readLine()) != null) {
				notityXml += inputLine;
			}
			request.getReader().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("微信支付成功后返回的数据={}",notityXml);
		Map<String, Object> map = Notify.parseXmlToList2(notityXml);
		/*
		 * WxPayResult wpr = new WxPayResult();
		 * wpr.appid(m.get("appid").toString());
		 * wpr.bankType(m.get("bank_type").toString());
		 * wpr.cashFee(m.get("cash_fee").toString());
		 * wpr.feeType(m.get("fee_type").toString());
		 * wpr.isSubscribe(m.get("is_subscribe").toString());
		 * wpr.mchId(m.get("mch_id").toString());
		 * wpr.nonceStr(m.get("nonce_str").toString());
		 * wpr.openid(m.get("openid").toString());
		 * wpr.setOutTradeNo(m.get("out_trade_no"));
		 * wpr.setResultCode(m.get("result_code"));
		 * wpr.returnCode(m.get("return_code").toString());
		 * wpr.sign(m.get("sign").toString());
		 * wpr.timeEnd(m.get("time_end").toString());
		 * wpr.totalFee(m.get("total_fee").toString());
		 * wpr.tradeType(m.get("trade_type").toString());
		 * wpr.setTransactionId(m.get("transaction_id"));
		 */

		if ("SUCCESS".equals(map.get("return_code").toString())) {
			resXml = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
			float totalFee = Float.valueOf(map.get("total_fee").toString()) / 100;//微信支付以分为单位，需要转换成元
			payAfterService.payNotifyWxPay(map.get("out_trade_no").toString(), map.get("transaction_id").toString(), request.getRemoteAddr(), totalFee,
					payChannel, map);
			createRecord(totalFee,map.get("out_trade_no").toString());
		} else {
			resXml = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[报文为空]]></return_msg></xml>";
		}
		BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
		out.write(resXml.getBytes());
		out.flush();
		out.close();
	}
	//支付宝或者微信支付成功，金额变更记录保存
	public void createRecord(float totalPrice,String orderNumber){
		DecimalFormat df = new DecimalFormat("#.##");
		totalPrice = Float.parseFloat(df.format(totalPrice));
		MallOrder order = orderService.getMallOrder(orderNumber);
		SsssCityMerchant city = ssssCityMerchantService.getByUserId(order.getUserId());
		city.setUpdateTime(new Date());
		//保证金记录增加
		MerchantBondMoneyRecord bondRecord = new MerchantBondMoneyRecord();
		bondRecord.setMerchantId(city.getId());
		bondRecord.setMerchantLevel(city.getLevel());
		bondRecord.setBeforeBondMoney(city.getBondMoneyBalance());
		bondRecord.setTotalPrice(totalPrice);
		Float reBond = Float.parseFloat(df.format(totalPrice*Constant.BondFactor.RETURNBOND.getStatus()));
		bondRecord.setRemark("【支付宝或者微信支付】返还");
		bondRecord.setProportion(Constant.BondFactor.RETURNBOND.getStatus());
		bondRecord.setStatus(BalanceChangeStatusEnum.THIRDBALANCE.getCode());
		bondRecord.setCreateDateTime(new Date());
		bondRecord.setOrderNumber(orderNumber);
		//首批提货款记录
		MerchantFirstGoodsMoneyRecord firstRecord = new MerchantFirstGoodsMoneyRecord();
		firstRecord.setCreateDateTime(new Date());
		firstRecord.setRemark("【支付宝或者微信支付】保证金返还");
		firstRecord.setMerchantId(city.getId());
		firstRecord.setOrderNumber(orderNumber);
		firstRecord.setFirstGoodsMoneyBalanceBefore(city.getFirstGoodsMoneyBalance());
		//说明该代理商返还金额完毕
		if(reBond>=city.getBondMoneyBalance()){
			bondRecord.setMoney(-city.getBondMoneyBalance());
			bondRecord.setAfterBondMoney(bondRecord.getBeforeBondMoney()-city.getBondMoneyBalance());
			bondRecord.setReTotalPrice(Float.parseFloat(df.format(city.getBondMoneyBalance()/Constant.BondFactor.RETURNBOND.getStatus())));
			if(city.getBondMoneyBalance()!=0){
				bondRecordService.insert(bondRecord);
			}
			
			firstRecord.setMoney(city.getBondMoneyBalance());
			firstRecord.setFirstGoodsMoneyBalanceAfter(city.getFirstGoodsMoneyBalance()+city.getBondMoneyBalance());
			firstRecord.setMerchantLevel(city.getLevel());
			firstRecord.setStatus(BalanceChangeStatusEnum.THIRDBALANCE.getCode());
			firstRecord.setType(BalanceChangeStatusEnum.THIRDBALANCE.getCode());
			if(city.getBondMoneyBalance()!=0){
				firstGoodsRecordService.insertSelective(firstRecord);
			}
			
			city.setFirstGoodsMoney(city.getFirstGoodsMoney()+city.getBondMoneyBalance());
			city.setFirstGoodsMoneyBalance(city.getFirstGoodsMoneyBalance()+city.getBondMoneyBalance());
			//用于上级代理返还保证金的金额
			Float totalBalance = totalPrice-Float.parseFloat(df.format(city.getBondMoneyBalance()/Constant.BondFactor.RETURNBOND.getStatus()));
			SsssCityMerchant parentCity = ssssCityMerchantService.getById(city.getParentCityMerchantId());
			city.setBondMoneyBalance(0f);
			Float bondMoney = totalBalance*Constant.BondFactor.RETURNBOND.getStatus();
			//首批提货款记录
			MerchantFirstGoodsMoneyRecord fRecord = new MerchantFirstGoodsMoneyRecord();
			fRecord.setCreateDateTime(new Date());
			fRecord.setRemark("【支付宝或者微信支付】保证金返还");
			fRecord.setMerchantId(parentCity.getId());
			fRecord.setOrderNumber(orderNumber);
			fRecord.setFirstGoodsMoneyBalanceBefore(parentCity.getFirstGoodsMoneyBalance());
			fRecord.setMerchantLevel(parentCity.getLevel());
			fRecord.setStatus(BalanceChangeStatusEnum.THIRDBALANCE.getCode());
			fRecord.setType(BalanceChangeStatusEnum.THIRDBALANCE.getCode());
			
			MerchantBondMoneyRecord record = new MerchantBondMoneyRecord();
			record.setTotalPrice(totalBalance);
			record.setReTotalPrice(totalBalance);
			record.setOrderNumber(orderNumber);
			record.setRemark("下级代理多余金额返利");
			record.setProportion(Constant.BondFactor.RETURNBOND.getStatus());
			record.setMerchantId(parentCity.getId());
			record.setMerchantLevel(parentCity.getLevel());
			record.setBeforeBondMoney(parentCity.getBondMoneyBalance());
			record.setOrderNumber(orderNumber);
			record.setCreateDateTime(new Date());
			record.setBeforeBondMoney(parentCity.getBondMoneyBalance());
			//上级代理保证金大于本次返还的保证金
			if(parentCity.getBondMoneyBalance()>bondMoney){
				record.setReTotalPrice(totalBalance);
				record.setMoney(-bondMoney);
				record.setAfterBondMoney(parentCity.getBondMoneyBalance()-bondMoney);
				fRecord.setMoney(bondMoney);
				fRecord.setFirstGoodsMoneyBalanceAfter(parentCity.getFirstGoodsMoneyBalance()+bondMoney);
				parentCity.setFirstGoodsMoney(parentCity.getFirstGoodsMoney()+bondMoney);
				parentCity.setFirstGoodsMoneyBalance(parentCity.getFirstGoodsMoneyBalance()+bondMoney);
				parentCity.setBondMoneyBalance(parentCity.getBondMoneyBalance()-bondMoney);
				firstGoodsRecordService.insertSelective(fRecord);
				bondRecordService.insert(record);
			}else{
				record.setReTotalPrice(Float.parseFloat(df.format(parentCity.getBondMoneyBalance()/Constant.BondFactor.RETURNBOND.getStatus())));
				record.setMoney(-parentCity.getBondMoneyBalance());
				record.setAfterBondMoney(0f);
				fRecord.setMoney(parentCity.getBondMoneyBalance());
				fRecord.setFirstGoodsMoneyBalanceAfter(parentCity.getFirstGoodsMoneyBalance()+parentCity.getBondMoneyBalance());
				parentCity.setFirstGoodsMoney(parentCity.getFirstGoodsMoney()+parentCity.getBondMoneyBalance());
				parentCity.setFirstGoodsMoneyBalance(parentCity.getFirstGoodsMoneyBalance()+parentCity.getBondMoneyBalance());
				if(parentCity.getBondMoneyBalance()!=0){
					firstGoodsRecordService.insertSelective(fRecord);
					bondRecordService.insert(record);
				}
				parentCity.setBondMoneyBalance(0f);
			}
			ssssCityMerchantService.update(parentCity);
		}else{
			bondRecord.setAfterBondMoney(bondRecord.getBeforeBondMoney()-reBond);
			bondRecord.setReTotalPrice(totalPrice);
			bondRecord.setMoney(-reBond);
			if(city.getBondMoneyBalance()!=0){
				bondRecordService.insert(bondRecord);
			}
			firstRecord.setMoney(reBond);
			firstRecord.setFirstGoodsMoneyBalanceAfter(city.getFirstGoodsMoneyBalance()+reBond);
			firstRecord.setMerchantLevel(city.getLevel());
			firstRecord.setStatus(BalanceChangeStatusEnum.THIRDBALANCE.getCode());
			firstRecord.setType(BalanceChangeStatusEnum.THIRDBALANCE.getCode());
			firstGoodsRecordService.insertSelective(firstRecord);
			
			city.setBondMoneyBalance(city.getBondMoneyBalance()-reBond);
			city.setFirstGoodsMoney(city.getFirstGoodsMoney()+reBond);
			city.setFirstGoodsMoneyBalance(city.getFirstGoodsMoneyBalance()+reBond);
		}
		//优惠券记录增加
		MerchantCouponMoneyRecord couponRecord = new MerchantCouponMoneyRecord();
		couponRecord.setMerchantId(city.getId());
		couponRecord.setMerchantLevel(city.getLevel());
		couponRecord.setBeforeUnfreezeCouponMoney(city.getUnfreezeCoupon());
		Float unMoney = Float.parseFloat(df.format(totalPrice*Constant.CouponFactor.UNFREEZE.getStatus()));
		//代理商剩余冻结金额大于本次订单解冻的优惠金额时
		if(city.getFreezeCoupon()>=unMoney){
			couponRecord.setAddFreezeCouponMoney(-unMoney);
			couponRecord.setAddUnfreezeCouponMoney(unMoney);
			float unfreezeMoney = city.getUnfreezeCoupon()+unMoney;
			couponRecord.setAfterUnfreezeCouponMoney(unfreezeMoney);
			couponRecord.setBeforeFreezeCouponMoney(city.getFreezeCoupon());
			float freezeMoney = city.getFreezeCoupon()-unMoney;
			couponRecord.setAfterFreezeCouponMoney(freezeMoney);
			city.setFreezeCoupon(freezeMoney);
			city.setUnfreezeCoupon(unfreezeMoney);
		}else{
			//代理商剩余冻结金额小于本次订单解冻的优惠金额时
			couponRecord.setAddUnfreezeCouponMoney(city.getFreezeCoupon());
			couponRecord.setBeforeFreezeCouponMoney(city.getFreezeCoupon());
			couponRecord.setAfterFreezeCouponMoney(0f);
			couponRecord.setAfterUnfreezeCouponMoney(city.getUnfreezeCoupon()+city.getFreezeCoupon());
			city.setFreezeCoupon(0f);
			city.setUnfreezeCoupon(city.getUnfreezeCoupon()+city.getFreezeCoupon());
		}
		couponRecord.setRemark("【支付宝或者微信支付】返还");
		couponRecord.setCreateDateTime(new Date());
		couponRecord.setOrderNumber(orderNumber);
		couponRecord.setStatus(BalanceChangeStatusEnum.THIRDBALANCE.getCode());
		couponRecordService.insert(couponRecord);
		
		//销售额记录添加
		MerchantSaleMoneyRecord saleRecord = new MerchantSaleMoneyRecord();
		saleRecord.setMerchantId(city.getId());
		saleRecord.setMerchantLevel(city.getLevel());
		saleRecord.setCreateDateTime(new Date());
		saleRecord.setOrderNumber(orderNumber);
		saleRecord.setMoney(totalPrice);
		saleRecord.setRemark("【支付宝或者微信支付】增加");
		saleRecord.setSaleTotalMoney((city.getSaleTotalMoney()==null?0:city.getSaleTotalMoney())+totalPrice);
		saleRecord.setStatus(BalanceChangeStatusEnum.THIRDBALANCE.getCode());
		saleRecordService.insertSelective(saleRecord);
		city.setSaleTotalMoney((city.getSaleTotalMoney()==null?0:city.getSaleTotalMoney())+totalPrice);
		//返利操作
		Byte level = city.getLevel();
		MerchantRebateMoneyRecord rebateRecord = new MerchantRebateMoneyRecord();
		rebateRecord.setCreateDateTime(new Date());
		rebateRecord.setOrderNumber(orderNumber);
		rebateRecord.setStatus(BalanceChangeStatusEnum.THIRDBALANCE.getCode());
		if(level == 1){
			float money = Float.parseFloat(df.format(totalPrice*Constant.rebateFactor.ONEPROPORTION.getStatus()));
			rebateRecord.setMoney(money);
			rebateRecord.setMerchantId(city.getId());
			rebateRecord.setMerchantLevel(city.getLevel());
			rebateRecord.setTotalPrice(totalPrice);
			rebateRecord.setProportion(Constant.rebateFactor.ONEPROPORTION.getStatus());
			rebateRecord.setRemark("代理商自己下单返利");
			rebateRecord.setRebateMoney((city.getRebateMoney()==null?0:city.getRebateMoney())+money);
			rebateRecordService.insertSelective(rebateRecord);
			city.setRebateMoney((city.getRebateMoney()==null?0:city.getRebateMoney())+money);
			ssssCityMerchantService.update(city);
		}
		if(level == 2){
			float twoMoney = Float.parseFloat(df.format(totalPrice*Constant.rebateFactor.TWOPROPORTION.getStatus()));
			float threeMoney = Float.parseFloat(df.format(totalPrice*Constant.rebateFactor.THREEPROPORTION.getStatus()));
			rebateRecord.setMoney(twoMoney);
			rebateRecord.setMerchantId(city.getId());
			rebateRecord.setMerchantLevel(city.getLevel());
			rebateRecord.setProportion(Constant.rebateFactor.TWOPROPORTION.getStatus());
			rebateRecord.setTotalPrice(totalPrice);
			rebateRecord.setRemark("代理商自己下单返利");
			rebateRecord.setRebateMoney((city.getRebateMoney()==null?0:city.getRebateMoney())+twoMoney);
			rebateRecordService.insertSelective(rebateRecord);
			city.setRebateMoney((city.getRebateMoney()==null?0:city.getRebateMoney())+twoMoney);
			ssssCityMerchantService.update(city);
			if(city.getParentCityMerchantId()!=null && city.getParentCityMerchantId()!=0){
				SsssCityMerchant parentCity = ssssCityMerchantService.getById(city.getParentCityMerchantId());
				MerchantRebateMoneyRecord parentRebateRecord = new MerchantRebateMoneyRecord();
				parentRebateRecord.setCreateDateTime(new Date());
				parentRebateRecord.setOrderNumber(orderNumber);
				parentRebateRecord.setStatus(BalanceChangeStatusEnum.THIRDBALANCE.getCode());
				parentRebateRecord.setMoney(threeMoney);
				parentRebateRecord.setMerchantId(city.getParentCityMerchantId());
				parentRebateRecord.setMerchantLevel(parentCity.getLevel());
				parentRebateRecord.setProportion(Constant.rebateFactor.THREEPROPORTION.getStatus());
				parentRebateRecord.setDownMerchantId(city.getId());
				parentRebateRecord.setDownMerchantLevel(city.getLevel());
				parentRebateRecord.setTotalPrice(totalPrice);
				parentRebateRecord.setRemark("下级代理下单返利");
				parentRebateRecord.setRebateMoney((parentCity.getRebateMoney()==null?0:parentCity.getRebateMoney())+threeMoney);
				parentCity.setRebateMoney((parentCity.getRebateMoney()==null?0:parentCity.getRebateMoney())+threeMoney);
				rebateRecordService.insertSelective(parentRebateRecord);
				ssssCityMerchantService.update(parentCity);
			}
		}
		if(level == 3){
			float twoMoney = Float.parseFloat(df.format(totalPrice*Constant.rebateFactor.TWOPROPORTION.getStatus()));
			float threeMoney = Float.parseFloat(df.format(totalPrice*Constant.rebateFactor.THREEPROPORTION.getStatus()));
			rebateRecord.setMoney(threeMoney);
			rebateRecord.setMerchantId(city.getId());
			rebateRecord.setMerchantLevel(city.getLevel());
			rebateRecord.setTotalPrice(totalPrice);
			rebateRecord.setProportion(Constant.rebateFactor.THREEPROPORTION.getStatus());
			rebateRecord.setRemark("代理商自己下单返利");
			rebateRecord.setRebateMoney((city.getRebateMoney()==null?0:city.getRebateMoney())+threeMoney);
			rebateRecordService.insertSelective(rebateRecord);
			city.setRebateMoney((city.getRebateMoney()==null?0:city.getRebateMoney())+threeMoney);
			ssssCityMerchantService.update(city);
			if(city.getParentCityMerchantId()!=null && city.getParentCityMerchantId()!=0){
				SsssCityMerchant parentCity = ssssCityMerchantService.getById(city.getParentCityMerchantId());
				if(parentCity.getLevel()==2){
					MerchantRebateMoneyRecord parentRebateRecord = new MerchantRebateMoneyRecord();
					parentRebateRecord.setCreateDateTime(new Date());
					parentRebateRecord.setOrderNumber(orderNumber);
					parentRebateRecord.setStatus(BalanceChangeStatusEnum.THIRDBALANCE.getCode());
					parentRebateRecord.setMoney(threeMoney);
					parentRebateRecord.setProportion(Constant.rebateFactor.THREEPROPORTION.getStatus());
					parentRebateRecord.setMerchantId(city.getParentCityMerchantId());
					parentRebateRecord.setMerchantLevel(parentCity.getLevel());
					parentRebateRecord.setDownMerchantId(city.getId());
					parentRebateRecord.setDownMerchantLevel(city.getLevel());
					parentRebateRecord.setTotalPrice(totalPrice);
					parentRebateRecord.setRemark("下级代理下单返利");
					parentRebateRecord.setRebateMoney((parentCity.getRebateMoney()==null?0:parentCity.getRebateMoney())+threeMoney);
					parentCity.setRebateMoney((parentCity.getRebateMoney()==null?0:parentCity.getRebateMoney())+threeMoney);
					rebateRecordService.insertSelective(parentRebateRecord);
					ssssCityMerchantService.update(parentCity);
					if(parentCity.getParentCityMerchantId()!=null && parentCity.getParentCityMerchantId()!=0){
						SsssCityMerchant pCity = ssssCityMerchantService.getById(parentCity.getParentCityMerchantId());
						MerchantRebateMoneyRecord pRecord = new MerchantRebateMoneyRecord();
						pRecord.setCreateDateTime(new Date());
						pRecord.setOrderNumber(orderNumber);
						pRecord.setStatus(BalanceChangeStatusEnum.THIRDBALANCE.getCode());
						pRecord.setMoney(threeMoney);
						pRecord.setProportion(Constant.rebateFactor.THREEPROPORTION.getStatus());
						pRecord.setMerchantId(parentCity.getParentCityMerchantId());
						pRecord.setMerchantLevel(pCity.getLevel());
						pRecord.setDownMerchantId(city.getId());
						pRecord.setDownMerchantLevel(city.getLevel());
						pRecord.setTotalPrice(totalPrice);
						pRecord.setRemark("下下级代理下单返利");
						pRecord.setRebateMoney((pCity.getRebateMoney()==null?0:pCity.getRebateMoney())+threeMoney);
						pCity.setRebateMoney((pCity.getRebateMoney()==null?0:pCity.getRebateMoney())+threeMoney);
						rebateRecordService.insertSelective(pRecord);
						ssssCityMerchantService.update(pCity);
					}
				}
				if(parentCity.getLevel()==1){
					MerchantRebateMoneyRecord parentRebateRecord = new MerchantRebateMoneyRecord();
					parentRebateRecord.setCreateDateTime(new Date());
					parentRebateRecord.setOrderNumber(orderNumber);
					parentRebateRecord.setStatus(BalanceChangeStatusEnum.THIRDBALANCE.getCode());
					parentRebateRecord.setMoney(twoMoney);
					parentRebateRecord.setProportion(Constant.rebateFactor.TWOPROPORTION.getStatus());
					parentRebateRecord.setMerchantId(city.getParentCityMerchantId());
					parentRebateRecord.setMerchantLevel(parentCity.getLevel());
					parentRebateRecord.setDownMerchantId(city.getId());
					parentRebateRecord.setDownMerchantLevel(city.getLevel());
					parentRebateRecord.setTotalPrice(totalPrice);
					parentRebateRecord.setRemark("下级代理下单返利");
					parentRebateRecord.setRebateMoney((parentCity.getRebateMoney()==null?0:parentCity.getRebateMoney())+twoMoney);
					parentCity.setRebateMoney((parentCity.getRebateMoney()==null?0:parentCity.getRebateMoney())+twoMoney);
					rebateRecordService.insertSelective(parentRebateRecord);
					ssssCityMerchantService.update(parentCity);
				}
			}
		}
		if(level == 4){
			float oneMoney = Float.parseFloat(df.format(totalPrice*Constant.rebateFactor.ONEPROPORTION.getStatus()));
			float twoMoney = Float.parseFloat(df.format(totalPrice*Constant.rebateFactor.TWOPROPORTION.getStatus()));
			float threeMoney = Float.parseFloat(df.format(totalPrice*Constant.rebateFactor.THREEPROPORTION.getStatus()));
			ssssCityMerchantService.update(city);
			if(city.getParentCityMerchantId()!=null && city.getParentCityMerchantId()!=0){
				SsssCityMerchant c = ssssCityMerchantService.getById(city.getParentCityMerchantId());
				if(c.getLevel()==3){
					rebateRecord.setMoney(threeMoney);
					rebateRecord.setMerchantId(city.getParentCityMerchantId());
					rebateRecord.setMerchantLevel(c.getLevel());
					rebateRecord.setDownMerchantId(city.getId());
					rebateRecord.setDownMerchantLevel(city.getLevel());
					rebateRecord.setTotalPrice(totalPrice);
					rebateRecord.setProportion(Constant.rebateFactor.THREEPROPORTION.getStatus());
					rebateRecord.setRemark("个体店下单返利");
					rebateRecord.setRebateMoney((c.getRebateMoney()==null?0:c.getRebateMoney())+threeMoney);
					rebateRecordService.insertSelective(rebateRecord);
					c.setRebateMoney((c.getRebateMoney()==null?0:c.getRebateMoney())+threeMoney);
					ssssCityMerchantService.update(c);
					if(c.getParentCityMerchantId()!=null && c.getParentCityMerchantId()!=0){
						SsssCityMerchant parentCity = ssssCityMerchantService.getById(c.getParentCityMerchantId());
						if(parentCity.getLevel()==2){
							MerchantRebateMoneyRecord parentRebateRecord = new MerchantRebateMoneyRecord();
							parentRebateRecord.setCreateDateTime(new Date());
							parentRebateRecord.setOrderNumber(orderNumber);
							parentRebateRecord.setStatus(BalanceChangeStatusEnum.THIRDBALANCE.getCode());
							parentRebateRecord.setMoney(threeMoney);
							parentRebateRecord.setProportion(Constant.rebateFactor.THREEPROPORTION.getStatus());
							parentRebateRecord.setMerchantId(c.getParentCityMerchantId());
							parentRebateRecord.setMerchantLevel(parentCity.getLevel());
							parentRebateRecord.setDownMerchantId(city.getId());
							parentRebateRecord.setDownMerchantLevel(city.getLevel());
							parentRebateRecord.setTotalPrice(totalPrice);
							parentRebateRecord.setRemark("个体店下单返利");
							parentRebateRecord.setRebateMoney((parentCity.getRebateMoney()==null?0:parentCity.getRebateMoney())+threeMoney);
							parentCity.setRebateMoney((parentCity.getRebateMoney()==null?0:parentCity.getRebateMoney())+threeMoney);
							rebateRecordService.insertSelective(parentRebateRecord);
							ssssCityMerchantService.update(parentCity);
							if(parentCity.getParentCityMerchantId()!=null && parentCity.getParentCityMerchantId()!=0){
								SsssCityMerchant pCity = ssssCityMerchantService.getById(parentCity.getParentCityMerchantId());
								MerchantRebateMoneyRecord pRecord = new MerchantRebateMoneyRecord();
								pRecord.setCreateDateTime(new Date());
								pRecord.setOrderNumber(orderNumber);
								pRecord.setStatus(BalanceChangeStatusEnum.THIRDBALANCE.getCode());
								pRecord.setMoney(threeMoney);
								pRecord.setProportion(Constant.rebateFactor.THREEPROPORTION.getStatus());
								pRecord.setMerchantId(parentCity.getParentCityMerchantId());
								pRecord.setMerchantLevel(pCity.getLevel());
								pRecord.setDownMerchantId(city.getId());
								pRecord.setDownMerchantLevel(city.getLevel());
								pRecord.setTotalPrice(totalPrice);
								pRecord.setRemark("个体店下单返利");
								pRecord.setRebateMoney((pCity.getRebateMoney()==null?0:pCity.getRebateMoney())+threeMoney);
								pCity.setRebateMoney((pCity.getRebateMoney()==null?0:pCity.getRebateMoney())+threeMoney);
								rebateRecordService.insertSelective(pRecord);
								ssssCityMerchantService.update(pCity);
							}
						}
						if(parentCity.getLevel()==1){
							MerchantRebateMoneyRecord parentRebateRecord = new MerchantRebateMoneyRecord();
							parentRebateRecord.setCreateDateTime(new Date());
							parentRebateRecord.setOrderNumber(orderNumber);
							parentRebateRecord.setStatus(BalanceChangeStatusEnum.THIRDBALANCE.getCode());
							parentRebateRecord.setMoney(twoMoney);
							parentRebateRecord.setProportion(Constant.rebateFactor.TWOPROPORTION.getStatus());
							parentRebateRecord.setMerchantId(c.getParentCityMerchantId());
							parentRebateRecord.setMerchantLevel(parentCity.getLevel());
							parentRebateRecord.setDownMerchantId(city.getId());
							parentRebateRecord.setDownMerchantLevel(city.getLevel());
							parentRebateRecord.setTotalPrice(totalPrice);
							parentRebateRecord.setRemark("个体店下单返利");
							parentRebateRecord.setRebateMoney((parentCity.getRebateMoney()==null?0:parentCity.getRebateMoney())+twoMoney);
							parentCity.setRebateMoney((parentCity.getRebateMoney()==null?0:parentCity.getRebateMoney())+twoMoney);
							rebateRecordService.insertSelective(parentRebateRecord);
							ssssCityMerchantService.update(parentCity);
						}
					}
				}
				if(c.getLevel()==2){
					rebateRecord.setMoney(twoMoney);
					rebateRecord.setMerchantId(city.getParentCityMerchantId());
					rebateRecord.setMerchantLevel(c.getLevel());
					rebateRecord.setDownMerchantId(city.getId());
					rebateRecord.setDownMerchantLevel(city.getLevel());
					rebateRecord.setTotalPrice(totalPrice);
					rebateRecord.setProportion(Constant.rebateFactor.TWOPROPORTION.getStatus());
					rebateRecord.setRemark("个体店下单返利");
					rebateRecord.setRebateMoney((c.getRebateMoney()==null?0:c.getRebateMoney())+twoMoney);
					rebateRecordService.insertSelective(rebateRecord);
					c.setRebateMoney((c.getRebateMoney()==null?0:c.getRebateMoney())+twoMoney);
					ssssCityMerchantService.update(c);
					if(c.getParentCityMerchantId()!=null && c.getParentCityMerchantId()!=0){
						SsssCityMerchant pCity = ssssCityMerchantService.getById(c.getParentCityMerchantId());
						MerchantRebateMoneyRecord pRecord = new MerchantRebateMoneyRecord();
						pRecord.setCreateDateTime(new Date());
						pRecord.setOrderNumber(orderNumber);
						pRecord.setStatus(BalanceChangeStatusEnum.THIRDBALANCE.getCode());
						pRecord.setMoney(threeMoney);
						pRecord.setProportion(Constant.rebateFactor.THREEPROPORTION.getStatus());
						pRecord.setMerchantId(c.getParentCityMerchantId());
						pRecord.setMerchantLevel(pCity.getLevel());
						pRecord.setDownMerchantId(city.getId());
						pRecord.setDownMerchantLevel(city.getLevel());
						pRecord.setTotalPrice(totalPrice);
						pRecord.setRemark("个体店下单返利");
						pRecord.setRebateMoney((pCity.getRebateMoney()==null?0:pCity.getRebateMoney())+threeMoney);
						pCity.setRebateMoney((pCity.getRebateMoney()==null?0:pCity.getRebateMoney())+threeMoney);
						rebateRecordService.insertSelective(pRecord);
						ssssCityMerchantService.update(pCity);
					}
				}
				if(c.getLevel()==1){
					rebateRecord.setMoney(oneMoney);
					rebateRecord.setMerchantId(city.getParentCityMerchantId());
					rebateRecord.setMerchantLevel(c.getLevel());
					rebateRecord.setDownMerchantId(city.getId());
					rebateRecord.setDownMerchantLevel(city.getLevel());
					rebateRecord.setTotalPrice(totalPrice);
					rebateRecord.setProportion(Constant.rebateFactor.ONEPROPORTION.getStatus());
					rebateRecord.setRemark("个体店下单返利");
					rebateRecord.setRebateMoney((c.getRebateMoney()==null?0:c.getRebateMoney())+oneMoney);
					rebateRecordService.insertSelective(rebateRecord);
					c.setRebateMoney((c.getRebateMoney()==null?0:c.getRebateMoney())+oneMoney);
					ssssCityMerchantService.update(c);
				}
			}
		}
	}
}
