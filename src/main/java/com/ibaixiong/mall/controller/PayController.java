/**
 * ibaixiong.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.ibaixiong.mall.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ibaixiong.constant.Constant;
import com.ibaixiong.core.utils.Md5Util;
import com.ibaixiong.entity.MerchantBondMoneyRecord;
import com.ibaixiong.entity.MerchantCouponMoneyRecord;
import com.ibaixiong.entity.MerchantFirstGoodsMoneyRecord;
import com.ibaixiong.entity.MerchantRebateMoneyRecord;
import com.ibaixiong.entity.MerchantSaleMoneyRecord;
import com.ibaixiong.entity.SsssCityMerchant;
import com.ibaixiong.entity.User;
import com.ibaixiong.entity.util.BalanceChangeStatusEnum;
import com.ibaixiong.entity.util.OrderStatusEnum;
import com.ibaixiong.mall.service.MerchantFirstGoodsRecordService;
import com.ibaixiong.mall.service.PayNotifyService;
import com.ibaixiong.mall.service.SsssCityMerchantService;
import com.ibaixiong.mall.service.UserService;
import com.ibaixiong.mall.util.Response;
import com.papabear.order.api.OrderService;
import com.papabear.order.entity.MallOrder;
import com.papabear.pay.api.PayAccountService;
import com.papabear.pay.entity.PayAccount;
import com.papabear.pay.entity.PayChannel;


@Controller
@RequestMapping("/pay")
class PayController extends AbstractController{
	@Resource
	PayAccountService payAccountService;
	@Resource
	UserService userService;
	@Resource
    OrderService orderService;
	@Resource
	PayNotifyService payNotifyService;
	@Resource
	SsssCityMerchantService ssssCityMerchantService;
	@Resource
	MerchantFirstGoodsRecordService recordService;
	
	@ResponseBody
	@RequestMapping("/checkPwd")
	public String getBalance(
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "payMoney", required = false) Float payMoney,
			@RequestParam(value = "balance", required = false) Float balance,
			@RequestParam(value = "goodsBalance", required = false) Float goodsBalance,
			HttpServletRequest request){
		Response response = new Response();
		Map<String, Object> map=new HashMap<String, Object>();
		int code = 0;
		BigDecimal big_balance = new BigDecimal(balance);
		BigDecimal big_goodsBalance = new BigDecimal(goodsBalance);
		Float sum = big_balance.add(big_goodsBalance).floatValue();
		if(payMoney > sum){
			response.setMessage("余额不足,请选择其他支付方式");
			response.setSuccess(false);
			code = 1;
			map.put("code", code);
			response.setResult(map);
			return JSON.toJSONString(response);
		}
		User user = userService.get(getUser().getId());
		String md5Pwd = Md5Util.encode(Md5Util.encode(password+user.getSalt()));
		if(user.getUserPwd().equals(md5Pwd)){
			response.setMessage("密码正确");
			response.setSuccess(true);
			map.put("code", code);
			response.setResult(map);
		}else{
			response.setMessage("密码错误");
			code = 2;
			map.put("code", code);
			response.setResult(map);
			response.setSuccess(false);
		}
		return JSON.toJSONString(response);
	}
	
	@RequestMapping("/pay")
	public String payPrice(
			@RequestParam(value = "balance", required = false) Float balance,
			@RequestParam(value = "shouldPayMoney", required = false) Float payMoney,
			@RequestParam(value = "goodsBalance", required = false) Float goodsBalance,
			ModelMap model,HttpServletRequest request){
		String orderNumber = request.getParameter("orderNumber");
		MallOrder order = orderService.getMallOrder(orderNumber);
		if(order == null){
			return "/pay/payError";
		}
		if(order.getStatus().byteValue()==OrderStatusEnum.PAID.getCode()){//订单状态为已支付
			model.addAttribute("payMoney", payMoney);
			model.addAttribute("payType", PayChannel.BALANCEPAY_PC.getType());
			return "/pay/payBalanceSuccess";
		}
		DecimalFormat df = new DecimalFormat("#.##");
		goodsBalance = Float.parseFloat(df.format(goodsBalance));
		payMoney = Float.parseFloat(df.format(payMoney));
		balance = Float.parseFloat(df.format(balance));
		payNotifyService.payNotifyBalancepay(order, payMoney,PayChannel.BALANCEPAY_PC, request.getRemoteAddr());
		SsssCityMerchant city = ssssCityMerchantService.getByUserId(order.getUserId());
		MerchantFirstGoodsMoneyRecord record = new MerchantFirstGoodsMoneyRecord();
		record.setMerchantId(city.getId());
		record.setCreateDateTime(new Date());
		record.setAdminId(null);
		record.setRemark("【提货款支付】记录");
		record.setMerchantLevel(city.getLevel());
		record.setOrderNumber(orderNumber);
		record.setFirstGoodsMoneyBalanceBefore(city.getFirstGoodsMoneyBalance());
		record.setStatus(BalanceChangeStatusEnum.FIRSTBALANCE.getCode());
		record.setType(BalanceChangeStatusEnum.FIRSTBALANCE.getCode());
		if(goodsBalance >= payMoney){
			record.setMoney(-payMoney);
			Float firstGoodsMoneyBalance = goodsBalance-payMoney;
			city.setFirstGoodsMoneyBalance(firstGoodsMoneyBalance);
			record.setFirstGoodsMoneyBalanceAfter(firstGoodsMoneyBalance);
			int count = recordService.insertSelective(record);
			if(count>0){
				ssssCityMerchantService.update(city);
			}
		}else{
			record.setMoney(-city.getFirstGoodsMoneyBalance());
			record.setFirstGoodsMoneyBalanceAfter(0f);
			city.setFirstGoodsMoneyBalance(0f);
			Float accountBalance = balance-(payMoney-goodsBalance);
			city.setMoney(accountBalance);
			int count = recordService.insertSelective(record);
			record.setRemark("【余额支付】记录");
			record.setMoney(-(payMoney-goodsBalance));
			record.setFirstGoodsMoneyBalanceBefore(balance);
			record.setFirstGoodsMoneyBalanceAfter(accountBalance);
			record.setStatus(BalanceChangeStatusEnum.PAYBALANCE.getCode());
			record.setType(BalanceChangeStatusEnum.PAYBALANCE.getCode());
			int cou = recordService.insertSelective(record);
			if(count>0 && cou>0){
				ssssCityMerchantService.update(city);
			}
		}
		//保证金记录增加
		MerchantBondMoneyRecord bondRecord = new MerchantBondMoneyRecord();
		bondRecord.setMerchantId(city.getId());
		bondRecord.setMerchantLevel(city.getLevel());
		bondRecord.setBeforeBondMoney(city.getBondMoneyBalance());
		bondRecord.setTotalPrice(payMoney);
		//四舍五入保留2位小数
		Float reBond = Float.parseFloat(df.format(payMoney*Constant.BondFactor.RETURNBOND.getStatus()));
		bondRecord.setRemark("【提货款支付】返还");
		bondRecord.setOrderNumber(orderNumber);
		bondRecord.setProportion(Constant.BondFactor.RETURNBOND.getStatus());
		bondRecord.setStatus(BalanceChangeStatusEnum.FIRSTBALANCE.getCode());
		bondRecord.setCreateDateTime(new Date());
		//余额记录
		MerchantFirstGoodsMoneyRecord firstRecord = new MerchantFirstGoodsMoneyRecord();
		firstRecord.setCreateDateTime(new Date());
		firstRecord.setRemark("【提货款支付】保证金返还");
		firstRecord.setMerchantId(city.getId());
		firstRecord.setMerchantLevel(city.getLevel());
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
			//首批提货款记录
			firstRecord.setMoney(city.getBondMoneyBalance());
			firstRecord.setFirstGoodsMoneyBalanceAfter(city.getFirstGoodsMoneyBalance()+city.getBondMoneyBalance());
			firstRecord.setStatus(BalanceChangeStatusEnum.FIRSTBALANCE.getCode());
			firstRecord.setType(BalanceChangeStatusEnum.FIRSTBALANCE.getCode());
			if(city.getBondMoneyBalance()!=0){
				firstGoodsRecordService.insertSelective(firstRecord);
			}
			city.setFirstGoodsMoney(city.getFirstGoodsMoney()+city.getBondMoneyBalance());
			city.setFirstGoodsMoneyBalance(city.getFirstGoodsMoneyBalance()+city.getBondMoneyBalance());
			//用于上级代理返还保证金的金额
			Float totalBalance = payMoney-Float.parseFloat(df.format(city.getBondMoneyBalance()/Constant.BondFactor.RETURNBOND.getStatus()));
			city.setBondMoneyBalance(0f);
			SsssCityMerchant parentCity = ssssCityMerchantService.getById(city.getParentCityMerchantId());
			Float bondMoney = Float.parseFloat(df.format(totalBalance*Constant.BondFactor.RETURNBOND.getStatus()));
			//首批提货款记录
			MerchantFirstGoodsMoneyRecord fRecord = new MerchantFirstGoodsMoneyRecord();
			fRecord.setCreateDateTime(new Date());
			fRecord.setRemark("【提货款支付】保证金返还");
			fRecord.setMerchantId(parentCity.getId());
			fRecord.setOrderNumber(orderNumber);
			fRecord.setFirstGoodsMoneyBalanceBefore(parentCity.getFirstGoodsMoneyBalance());
			fRecord.setMerchantLevel(parentCity.getLevel());
			fRecord.setStatus(BalanceChangeStatusEnum.FIRSTBALANCE.getCode());
			fRecord.setType(BalanceChangeStatusEnum.FIRSTBALANCE.getCode());
			
			MerchantBondMoneyRecord bRecord = new MerchantBondMoneyRecord();
			bRecord.setTotalPrice(totalBalance);
			bRecord.setOrderNumber(orderNumber);
			bRecord.setRemark("下级代理多余金额返利");
			bRecord.setProportion(Constant.BondFactor.RETURNBOND.getStatus());
			bRecord.setMerchantId(parentCity.getId());
			bRecord.setMerchantLevel(parentCity.getLevel());
			bRecord.setBeforeBondMoney(parentCity.getBondMoneyBalance());
			bRecord.setStatus(BalanceChangeStatusEnum.FIRSTBALANCE.getCode());
			bRecord.setCreateDateTime(new Date());
			bRecord.setOrderNumber(orderNumber);
			bRecord.setBeforeBondMoney(parentCity.getBondMoneyBalance());
			if(parentCity.getBondMoneyBalance()>bondMoney){
				bRecord.setReTotalPrice(totalBalance);
				bRecord.setMoney(-bondMoney);
				bRecord.setAfterBondMoney(parentCity.getBondMoneyBalance()-bondMoney);
				fRecord.setMoney(bondMoney);
				fRecord.setFirstGoodsMoneyBalanceAfter(parentCity.getFirstGoodsMoneyBalance()+bondMoney);
				parentCity.setFirstGoodsMoney(parentCity.getFirstGoodsMoney()+bondMoney);
				parentCity.setFirstGoodsMoneyBalance(parentCity.getFirstGoodsMoneyBalance()+bondMoney);
				parentCity.setBondMoneyBalance(parentCity.getBondMoneyBalance()-bondMoney);
				firstGoodsRecordService.insertSelective(fRecord);
				bondRecordService.insert(bRecord);
			}else{
				bRecord.setReTotalPrice(Float.parseFloat(df.format(parentCity.getBondMoneyBalance()/Constant.BondFactor.RETURNBOND.getStatus())));
				bRecord.setMoney(-parentCity.getBondMoneyBalance());
				bRecord.setAfterBondMoney(0f);
				fRecord.setMoney(parentCity.getBondMoneyBalance());
				fRecord.setFirstGoodsMoneyBalanceAfter(parentCity.getFirstGoodsMoneyBalance()+parentCity.getBondMoneyBalance());
				parentCity.setFirstGoodsMoney(parentCity.getFirstGoodsMoney()+parentCity.getBondMoneyBalance());
				parentCity.setFirstGoodsMoneyBalance(parentCity.getFirstGoodsMoneyBalance()+parentCity.getBondMoneyBalance());
				if(parentCity.getBondMoneyBalance()!=0){
					firstGoodsRecordService.insertSelective(fRecord);
					bondRecordService.insert(bRecord);
				}
				parentCity.setBondMoneyBalance(0f);
			}
			ssssCityMerchantService.update(parentCity);
		}else{
			bondRecord.setAfterBondMoney(bondRecord.getBeforeBondMoney()-reBond);
			bondRecord.setReTotalPrice(payMoney);
			bondRecord.setMoney(-reBond);
			if(city.getBondMoneyBalance()!=0){
				bondRecordService.insert(bondRecord);
			}
			//首批提货款记录
			firstRecord.setMoney(reBond);
			firstRecord.setFirstGoodsMoneyBalanceAfter(city.getFirstGoodsMoneyBalance()+reBond);
			firstRecord.setStatus(BalanceChangeStatusEnum.FIRSTBALANCE.getCode());
			firstRecord.setType(BalanceChangeStatusEnum.FIRSTBALANCE.getCode());
			firstGoodsRecordService.insertSelective(firstRecord);
			
			city.setBondMoneyBalance(city.getBondMoneyBalance()-reBond);
			city.setFirstGoodsMoney(city.getFirstGoodsMoney()+reBond);
			city.setFirstGoodsMoneyBalance(city.getFirstGoodsMoneyBalance()+reBond);
		}
		//销售额记录添加
		MerchantSaleMoneyRecord saleRecord = new MerchantSaleMoneyRecord();
		saleRecord.setMerchantId(city.getId());
		saleRecord.setMerchantLevel(city.getLevel());
		saleRecord.setCreateDateTime(new Date());
		saleRecord.setOrderNumber(orderNumber);
		saleRecord.setMoney(payMoney);
		saleRecord.setRemark("【提货款支付】增加");
		saleRecord.setSaleTotalMoney((city.getSaleTotalMoney()==null?0:city.getSaleTotalMoney())+payMoney);
		saleRecord.setStatus(BalanceChangeStatusEnum.FIRSTBALANCE.getCode());
		saleRecordService.insertSelective(saleRecord);
		city.setSaleTotalMoney((city.getSaleTotalMoney()==null?0:city.getSaleTotalMoney())+payMoney);
		//返利操作
		Byte level = city.getLevel();
		MerchantRebateMoneyRecord rebateRecord = new MerchantRebateMoneyRecord();
		rebateRecord.setCreateDateTime(new Date());
		rebateRecord.setOrderNumber(orderNumber);
		rebateRecord.setStatus(BalanceChangeStatusEnum.FIRSTBALANCE.getCode());
		if(level == 1){
			float money = Float.parseFloat(df.format(payMoney*Constant.rebateFactor.ONEPROPORTION.getStatus()));
			rebateRecord.setMoney(money);
			rebateRecord.setMerchantId(city.getId());
			rebateRecord.setMerchantLevel(city.getLevel());
			rebateRecord.setTotalPrice(payMoney);
			rebateRecord.setProportion(Constant.rebateFactor.ONEPROPORTION.getStatus());
			rebateRecord.setRemark("代理商自己下单返利");
			rebateRecord.setRebateMoney((city.getRebateMoney()==null?0:city.getRebateMoney())+money);
			rebateRecordService.insertSelective(rebateRecord);
			city.setRebateMoney((city.getRebateMoney()==null?0:city.getRebateMoney())+money);
			ssssCityMerchantService.update(city);
		}
		if(level == 2){
			float twoMoney = Float.parseFloat(df.format(payMoney*Constant.rebateFactor.TWOPROPORTION.getStatus()));
			float threeMoney = Float.parseFloat(df.format(payMoney*Constant.rebateFactor.THREEPROPORTION.getStatus()));
			rebateRecord.setMoney(twoMoney);
			rebateRecord.setMerchantId(city.getId());
			rebateRecord.setMerchantLevel(city.getLevel());
			rebateRecord.setProportion(Constant.rebateFactor.TWOPROPORTION.getStatus());
			rebateRecord.setTotalPrice(payMoney);
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
				parentRebateRecord.setStatus(BalanceChangeStatusEnum.FIRSTBALANCE.getCode());
				parentRebateRecord.setMoney(threeMoney);
				parentRebateRecord.setMerchantId(city.getParentCityMerchantId());
				parentRebateRecord.setMerchantLevel(parentCity.getLevel());
				parentRebateRecord.setProportion(Constant.rebateFactor.THREEPROPORTION.getStatus());
				parentRebateRecord.setDownMerchantId(city.getId());
				parentRebateRecord.setDownMerchantLevel(city.getLevel());
				parentRebateRecord.setTotalPrice(payMoney);
				parentRebateRecord.setRemark("下级代理下单返利");
				parentRebateRecord.setRebateMoney((parentCity.getRebateMoney()==null?0:parentCity.getRebateMoney())+threeMoney);
				parentCity.setRebateMoney((parentCity.getRebateMoney()==null?0:parentCity.getRebateMoney())+threeMoney);
				rebateRecordService.insertSelective(parentRebateRecord);
				ssssCityMerchantService.update(parentCity);
			}
		}
		if(level == 3){
			float twoMoney = Float.parseFloat(df.format(payMoney*Constant.rebateFactor.TWOPROPORTION.getStatus()));
			float threeMoney = Float.parseFloat(df.format(payMoney*Constant.rebateFactor.THREEPROPORTION.getStatus()));
			rebateRecord.setMoney(threeMoney);
			rebateRecord.setMerchantId(city.getId());
			rebateRecord.setMerchantLevel(city.getLevel());
			rebateRecord.setTotalPrice(payMoney);
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
					parentRebateRecord.setStatus(BalanceChangeStatusEnum.FIRSTBALANCE.getCode());
					parentRebateRecord.setMoney(threeMoney);
					parentRebateRecord.setProportion(Constant.rebateFactor.THREEPROPORTION.getStatus());
					parentRebateRecord.setMerchantId(city.getParentCityMerchantId());
					parentRebateRecord.setMerchantLevel(parentCity.getLevel());
					parentRebateRecord.setDownMerchantId(city.getId());
					parentRebateRecord.setDownMerchantLevel(city.getLevel());
					parentRebateRecord.setTotalPrice(payMoney);
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
						pRecord.setStatus(BalanceChangeStatusEnum.FIRSTBALANCE.getCode());
						pRecord.setMoney(threeMoney);
						pRecord.setProportion(Constant.rebateFactor.THREEPROPORTION.getStatus());
						pRecord.setMerchantId(parentCity.getParentCityMerchantId());
						pRecord.setMerchantLevel(pCity.getLevel());
						pRecord.setDownMerchantId(city.getId());
						pRecord.setDownMerchantLevel(city.getLevel());
						pRecord.setTotalPrice(payMoney);
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
					parentRebateRecord.setStatus(BalanceChangeStatusEnum.FIRSTBALANCE.getCode());
					parentRebateRecord.setMoney(twoMoney);
					parentRebateRecord.setProportion(Constant.rebateFactor.TWOPROPORTION.getStatus());
					parentRebateRecord.setMerchantId(city.getParentCityMerchantId());
					parentRebateRecord.setMerchantLevel(parentCity.getLevel());
					parentRebateRecord.setDownMerchantId(city.getId());
					parentRebateRecord.setDownMerchantLevel(city.getLevel());
					parentRebateRecord.setTotalPrice(payMoney);
					parentRebateRecord.setRemark("下级代理下单返利");
					parentRebateRecord.setRebateMoney((parentCity.getRebateMoney()==null?0:parentCity.getRebateMoney())+twoMoney);
					parentCity.setRebateMoney((parentCity.getRebateMoney()==null?0:parentCity.getRebateMoney())+twoMoney);
					rebateRecordService.insertSelective(parentRebateRecord);
					ssssCityMerchantService.update(parentCity);
				}
			}
		}
		if(level == 4){
			float oneMoney = Float.parseFloat(df.format(payMoney*Constant.rebateFactor.ONEPROPORTION.getStatus()));
			float twoMoney = Float.parseFloat(df.format(payMoney*Constant.rebateFactor.TWOPROPORTION.getStatus()));
			float threeMoney = Float.parseFloat(df.format(payMoney*Constant.rebateFactor.THREEPROPORTION.getStatus()));
			ssssCityMerchantService.update(city);
			if(city.getParentCityMerchantId()!=null && city.getParentCityMerchantId()!=0){
				SsssCityMerchant c = ssssCityMerchantService.getById(city.getParentCityMerchantId());
				if(c.getLevel()==3){
					rebateRecord.setMoney(threeMoney);
					rebateRecord.setMerchantId(city.getParentCityMerchantId());
					rebateRecord.setMerchantLevel(c.getLevel());
					rebateRecord.setDownMerchantId(city.getId());
					rebateRecord.setDownMerchantLevel(city.getLevel());
					rebateRecord.setTotalPrice(payMoney);
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
							parentRebateRecord.setStatus(BalanceChangeStatusEnum.FIRSTBALANCE.getCode());
							parentRebateRecord.setMoney(threeMoney);
							parentRebateRecord.setProportion(Constant.rebateFactor.THREEPROPORTION.getStatus());
							parentRebateRecord.setMerchantId(c.getParentCityMerchantId());
							parentRebateRecord.setMerchantLevel(parentCity.getLevel());
							parentRebateRecord.setDownMerchantId(city.getId());
							parentRebateRecord.setDownMerchantLevel(city.getLevel());
							parentRebateRecord.setTotalPrice(payMoney);
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
								pRecord.setStatus(BalanceChangeStatusEnum.FIRSTBALANCE.getCode());
								pRecord.setMoney(threeMoney);
								pRecord.setProportion(Constant.rebateFactor.THREEPROPORTION.getStatus());
								pRecord.setMerchantId(parentCity.getParentCityMerchantId());
								pRecord.setMerchantLevel(pCity.getLevel());
								pRecord.setDownMerchantId(city.getId());
								pRecord.setDownMerchantLevel(city.getLevel());
								pRecord.setTotalPrice(payMoney);
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
							parentRebateRecord.setStatus(BalanceChangeStatusEnum.FIRSTBALANCE.getCode());
							parentRebateRecord.setMoney(twoMoney);
							parentRebateRecord.setProportion(Constant.rebateFactor.TWOPROPORTION.getStatus());
							parentRebateRecord.setMerchantId(c.getParentCityMerchantId());
							parentRebateRecord.setMerchantLevel(parentCity.getLevel());
							parentRebateRecord.setDownMerchantId(city.getId());
							parentRebateRecord.setDownMerchantLevel(city.getLevel());
							parentRebateRecord.setTotalPrice(payMoney);
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
					rebateRecord.setTotalPrice(payMoney);
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
						pRecord.setStatus(BalanceChangeStatusEnum.FIRSTBALANCE.getCode());
						pRecord.setMoney(threeMoney);
						pRecord.setProportion(Constant.rebateFactor.THREEPROPORTION.getStatus());
						pRecord.setMerchantId(c.getParentCityMerchantId());
						pRecord.setMerchantLevel(pCity.getLevel());
						pRecord.setDownMerchantId(city.getId());
						pRecord.setDownMerchantLevel(city.getLevel());
						pRecord.setTotalPrice(payMoney);
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
					rebateRecord.setTotalPrice(payMoney);
					rebateRecord.setProportion(Constant.rebateFactor.ONEPROPORTION.getStatus());
					rebateRecord.setRemark("个体店下单返利");
					rebateRecord.setRebateMoney((c.getRebateMoney()==null?0:c.getRebateMoney())+oneMoney);
					rebateRecordService.insertSelective(rebateRecord);
					c.setRebateMoney((c.getRebateMoney()==null?0:c.getRebateMoney())+oneMoney);
					ssssCityMerchantService.update(c);
				}
			}
		}
//		//优惠券记录增加
//		MerchantCouponMoneyRecord couponRecord = new MerchantCouponMoneyRecord();
//		couponRecord.setMerchantId(city.getId());
//		couponRecord.setMerchantLevel(city.getLevel());
//		//代理商剩余冻结金额大于本次订单解冻的优惠金额时
//		if(city.getFreezeCoupon()>=payMoney*Constant.CouponFactor.UNFREEZE.getStatus()){
//			couponRecord.setAddFreezeCouponMoney(-payMoney*Constant.CouponFactor.UNFREEZE.getStatus());
//			couponRecord.setAddUnfreezeCouponMoney(payMoney*Constant.CouponFactor.UNFREEZE.getStatus());
//			float unfreezeMoney = city.getUnfreezeCoupon()+(payMoney*Constant.CouponFactor.UNFREEZE.getStatus());
//			couponRecord.setAfterUnfreezeCouponMoney(unfreezeMoney);
//			couponRecord.setBeforeFreezeCouponMoney(city.getFreezeCoupon());
//			float freezeMoney = city.getFreezeCoupon()-payMoney*Constant.CouponFactor.UNFREEZE.getStatus();
//			couponRecord.setAfterFreezeCouponMoney(freezeMoney);
//			city.setFreezeCoupon(freezeMoney);
//			city.setUnfreezeCoupon(unfreezeMoney);
//		}else{
//			//代理商剩余冻结金额大于本次订单解冻的优惠金额时
//			couponRecord.setAddUnfreezeCouponMoney(city.getFreezeCoupon());
//			couponRecord.setBeforeFreezeCouponMoney(city.getFreezeCoupon());
//			couponRecord.setAfterFreezeCouponMoney(0f);
//			couponRecord.setAfterUnfreezeCouponMoney(city.getUnfreezeCoupon()+city.getFreezeCoupon());
//			city.setFreezeCoupon(0f);
//			city.setUnfreezeCoupon(city.getUnfreezeCoupon()+city.getFreezeCoupon());
//		}
//		couponRecord.setRemark("【提货款支付】返还");
//		couponRecord.setCreateDateTime(new Date());
//		couponRecord.setOrderNumber(orderNumber);
//		couponRecord.setBeforeUnfreezeCouponMoney(city.getUnfreezeCoupon());
//		couponRecord.setStatus(BalanceChangeStatusEnum.FIRSTBALANCE.getCode());
//		couponRecordService.insert(couponRecord);
		model.addAttribute("payMoney", payMoney);
		return "/pay/payBalanceSuccess";
	}
	
}
