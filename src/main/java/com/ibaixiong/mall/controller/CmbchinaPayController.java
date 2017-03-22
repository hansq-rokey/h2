/**
 * ibaixiong.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.ibaixiong.mall.controller;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cmb.MerchantCode;
import cmb.netpayment.Security;

import com.ibaixiong.constant.Constant;
import com.ibaixiong.core.utils.DateUtil;
import com.ibaixiong.core.utils.IpUtil;
import com.ibaixiong.entity.MerchantBondMoneyRecord;
import com.ibaixiong.entity.MerchantCouponMoneyRecord;
import com.ibaixiong.entity.MerchantFirstGoodsMoneyRecord;
import com.ibaixiong.entity.MerchantRebateMoneyRecord;
import com.ibaixiong.entity.MerchantSaleMoneyRecord;
import com.ibaixiong.entity.SsssCityMerchant;
import com.ibaixiong.entity.User;
import com.ibaixiong.entity.util.BalanceChangeStatusEnum;
import com.ibaixiong.entity.util.OrderStatusEnum;
import com.ibaixiong.mall.cmbchinapay.util.CmbChina;
import com.ibaixiong.mall.exception.PayException;
import com.ibaixiong.mall.service.PayNotifyService;
import com.ibaixiong.mall.service.SsssCityMerchantService;
import com.papabear.order.api.OrderService;
import com.papabear.order.entity.MallOrder;
import com.papabear.pay.entity.OrderPayCmbChina;

/**
 * @description 招行网银支付
 * @author chenzehe
 * @email hljuczh@163.com
 * @create 2015年12月7日-上午11:56:40
 */
@Controller
public class CmbchinaPayController extends AbstractController {

	@Autowired
	CmbChina cmbChina;
	@Resource
	PayNotifyService payNotifyService;
	@Resource
	SsssCityMerchantService ssssCityMerchantService;
	@Resource
    OrderService orderService;

	@RequestMapping("/u/pay/cmbchina/getpaydata")
	@ResponseBody
	public String getCmbchinaPayData(ModelMap modelMap, HttpServletRequest request) {
		User user = getUser();
		String orderNumber = request.getParameter("orderNumber");

		// 1、根据orderNumber获取订单金额
		MallOrder order = mallOrderService.queryOrderByOrderNumber(user, orderNumber);
		if (order == null || order.getStatus() != OrderStatusEnum.OBLIGATION.getCode()) {
			return "{\"code\":-1}";
		}
		String fee = request.getParameter("fee");
//		Float amount = order.getDiscountPrice();

		// 2、生成校验码
		String strDate = DateUtil.getDateNow("yyyyMMdd");
		String strMerchantPara = "payType=" + cmbChina.getPayType() + "|orderNumber=" + orderNumber;
		String strPayerID = "";// 付方用户
		String strPayeeID = "";// 收放用户
		String strClientIP = IpUtil.getIpAddr(request);// 付方IP
		String strReserved = "";
		String thirdBankNo = request.getParameter("thirdBankNo");
		String strMerchantCode = "";
		String billno = createBillNo(order.getId().toString());
//		if (!StringUtils.isBlank(thirdBankNo)) {
			strReserved = "<CardBank>" + thirdBankNo + "</CardBank>";
			strMerchantCode = MerchantCode.genMerchantCode(cmbChina.getKey(), strDate, cmbChina.getBranchId(), cmbChina.getCoNo(), billno, fee,
					strMerchantPara, cmbChina.getMerchantUrl(), strPayerID, strPayeeID, strClientIP, cmbChina.getGoodsType(), strReserved);
//		}

		return "{\"branchid\":\"" + cmbChina.getBranchId() + "\",\"cono\":\"" + cmbChina.getCoNo() + "\",\"date\":\"" + strDate + "\",\"billno\":\"" + billno
				+ "\",\"amount\":\"" + fee + "\",\"merchantPara\":\"" + strMerchantPara + "\",\"merchantURL\":\"" + cmbChina.getMerchantUrl()
				+ "\",\"merchantRetURL\":\"" + cmbChina.getMerchantRetUrl() + "\",\"merchantCode\":\"" + strMerchantCode + "\",\"payUrl\":\""
				+ cmbChina.getPayUrl() + "\"}";
	}

	@RequestMapping("/pay/cmbchina/notify")
	@ResponseBody
	public String payNotify(ModelMap modelMap, HttpServletRequest request) {
		try {
			String sucess = request.getParameter("Succeed");
			if ("Y".equals(sucess)) {
				payAfter(request);
			}
		} catch (Exception e) {

		}

		return "";
	}

	@RequestMapping("/pay/cmbchina/show")
	public String show(ModelMap modelMap, HttpServletRequest request) {
		boolean result = false;
		String msg = "";
		try {
			String sucess = request.getParameter("Succeed");
			if ("Y".equals(sucess)) {
				result = payAfter(request);
			}
		} catch (PayException e) {
			msg = e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}

		modelMap.addAttribute("amount", request.getParameter("Amount"));
		modelMap.addAttribute("payType", "5");
		if (result) {
			modelMap.addAttribute("payResultImg", "http://fe.ibaixiong.com/shop/image/cmbchinapay-sucess.png");
		} else {
			modelMap.addAttribute("payResultImg", "http://fe.ibaixiong.com/shop/image/pay-error.png");
			modelMap.addAttribute("msg", msg);
		}

		return "pay/paySuccess";
	}

	private boolean payAfter(HttpServletRequest request) throws Exception {
		boolean result = false;
		// 对签名进行验证

		String cmbChinaPubKey = this.getClass().getClassLoader().getResource("").getPath() + "cmbchinapay/public.key";
		Security security = new Security(cmbChinaPubKey);
		if (!security.checkInfoFromBank(request.getQueryString().getBytes("GB2312"))) {
			return false;
		}

		String sucess = request.getParameter("Succeed");
		String coNo = request.getParameter("CoNo");
		String billNo = request.getParameter("BillNo");
		String amount = request.getParameter("Amount");
		String date = request.getParameter("Date");
		String msg = request.getParameter("Msg");
		String merchantPara = request.getParameter("MerchantPara");
		String signature = request.getParameter("Signature");

		OrderPayCmbChina payCmbChina = new OrderPayCmbChina();
		payCmbChina.setBankPayNumber(msg);
		payCmbChina.setBranchId(msg.substring(0, 4));
		payCmbChina.setCono(msg.substring(4, 10));
		payCmbChina.setDate8(msg.substring(10, 18));
		payCmbChina.setBankPayNumber(msg.substring(18, 38));
		payCmbChina.setCreateDateTime(new Date());
		payCmbChina.setInvalid(false);
		payCmbChina.setBillno(billNo);
		Map<String, String> merchantPara2Map = merchantPara2Map(merchantPara);
		String orderNumber = merchantPara2Map.get("orderNumber");
		payCmbChina.setOrderNumber(orderNumber);
		payCmbChina.setPara(merchantPara);
		payCmbChina.setSignature(signature);
		payCmbChina.setNotifyQueryString(request.getQueryString());
		payCmbChina.setAmount(Float.valueOf(amount));

		result = payNotifyService.payNotifyCmbChina(orderNumber, IpUtil.getIpAddr(request), payCmbChina);

		DecimalFormat df = new DecimalFormat("#.##");
		//根据银行卡支付的金额给代理商一定的优惠券和解冻一定的优惠券
		if(result){
			MallOrder order = orderService.getMallOrder(orderNumber);
			SsssCityMerchant city = ssssCityMerchantService.getByUserId(order.getUserId());
			city.setUpdateTime(new Date());
			//保证金记录增加
			MerchantBondMoneyRecord bondRecord = new MerchantBondMoneyRecord();
			bondRecord.setMerchantId(city.getId());
			bondRecord.setMerchantLevel(city.getLevel());
			bondRecord.setBeforeBondMoney(city.getBondMoneyBalance());
			bondRecord.setTotalPrice(Float.parseFloat(amount));
			Float reBond = Float.parseFloat(df.format(Float.parseFloat(amount)*Constant.BondFactor.RETURNBOND.getStatus()));
			bondRecord.setRemark("【银行卡支付】返还");
			bondRecord.setProportion(Constant.BondFactor.RETURNBOND.getStatus());
			bondRecord.setStatus(BalanceChangeStatusEnum.THIRDBALANCE.getCode());
			bondRecord.setCreateDateTime(new Date());
			bondRecord.setOrderNumber(orderNumber);
			//首批提货款记录
			MerchantFirstGoodsMoneyRecord firstRecord = new MerchantFirstGoodsMoneyRecord();
			firstRecord.setCreateDateTime(new Date());
			firstRecord.setRemark("【银行卡支付】保证金返还");
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
				Float totalBalance = Float.parseFloat(amount)-Float.parseFloat(df.format(city.getBondMoneyBalance()/Constant.BondFactor.RETURNBOND.getStatus()));
				SsssCityMerchant parentCity = ssssCityMerchantService.getById(city.getParentCityMerchantId());
				city.setBondMoneyBalance(0f);
				Float bondMoney = totalBalance*Constant.BondFactor.RETURNBOND.getStatus();
				//首批提货款记录
				MerchantFirstGoodsMoneyRecord fRecord = new MerchantFirstGoodsMoneyRecord();
				fRecord.setCreateDateTime(new Date());
				fRecord.setRemark("【银行卡支付】保证金返还");
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
				bondRecord.setReTotalPrice(Float.parseFloat(amount));
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
			Float unMoney = Float.parseFloat(df.format(Float.parseFloat(amount)*Constant.CouponFactor.UNFREEZE.getStatus()));
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
			couponRecord.setRemark("【银行卡支付】返还");
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
			saleRecord.setMoney(Float.parseFloat(amount));
			saleRecord.setRemark("【提货款支付】增加");
			saleRecord.setSaleTotalMoney((city.getSaleTotalMoney()==null?0:city.getSaleTotalMoney())+Float.parseFloat(amount));
			saleRecord.setStatus(BalanceChangeStatusEnum.THIRDBALANCE.getCode());
			saleRecordService.insertSelective(saleRecord);
			city.setSaleTotalMoney((city.getSaleTotalMoney()==null?0:city.getSaleTotalMoney())+Float.parseFloat(amount));
			//返利操作
			Byte level = city.getLevel();
			MerchantRebateMoneyRecord rebateRecord = new MerchantRebateMoneyRecord();
			rebateRecord.setCreateDateTime(new Date());
			rebateRecord.setOrderNumber(orderNumber);
			rebateRecord.setStatus(BalanceChangeStatusEnum.THIRDBALANCE.getCode());
			if(level == 1){
				float money = Float.parseFloat(df.format(Float.parseFloat(amount)*Constant.rebateFactor.ONEPROPORTION.getStatus()));
				rebateRecord.setMoney(money);
				rebateRecord.setMerchantId(city.getId());
				rebateRecord.setMerchantLevel(city.getLevel());
				rebateRecord.setTotalPrice(Float.parseFloat(amount));
				rebateRecord.setProportion(Constant.rebateFactor.ONEPROPORTION.getStatus());
				rebateRecord.setRemark("代理商自己下单返利");
				rebateRecord.setRebateMoney((city.getRebateMoney()==null?0:city.getRebateMoney())+money);
				rebateRecordService.insertSelective(rebateRecord);
				city.setRebateMoney((city.getRebateMoney()==null?0:city.getRebateMoney())+money);
				ssssCityMerchantService.update(city);
			}
			if(level == 2){
				float twoMoney = Float.parseFloat(df.format(Float.parseFloat(amount)*Constant.rebateFactor.TWOPROPORTION.getStatus()));
				float threeMoney = Float.parseFloat(df.format(Float.parseFloat(amount)*Constant.rebateFactor.THREEPROPORTION.getStatus()));
				rebateRecord.setMoney(twoMoney);
				rebateRecord.setMerchantId(city.getId());
				rebateRecord.setMerchantLevel(city.getLevel());
				rebateRecord.setProportion(Constant.rebateFactor.TWOPROPORTION.getStatus());
				rebateRecord.setTotalPrice(Float.parseFloat(amount));
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
					parentRebateRecord.setTotalPrice(Float.parseFloat(amount));
					parentRebateRecord.setRemark("下级代理下单返利");
					parentRebateRecord.setRebateMoney((parentCity.getRebateMoney()==null?0:parentCity.getRebateMoney())+threeMoney);
					parentCity.setRebateMoney((parentCity.getRebateMoney()==null?0:parentCity.getRebateMoney())+threeMoney);
					rebateRecordService.insertSelective(parentRebateRecord);
					ssssCityMerchantService.update(parentCity);
				}
			}
			if(level == 3){
				float twoMoney = Float.parseFloat(df.format(Float.parseFloat(amount)*Constant.rebateFactor.TWOPROPORTION.getStatus()));
				float threeMoney = Float.parseFloat(df.format(Float.parseFloat(amount)*Constant.rebateFactor.THREEPROPORTION.getStatus()));
				rebateRecord.setMoney(threeMoney);
				rebateRecord.setMerchantId(city.getId());
				rebateRecord.setMerchantLevel(city.getLevel());
				rebateRecord.setTotalPrice(Float.parseFloat(amount));
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
						parentRebateRecord.setTotalPrice(Float.parseFloat(amount));
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
							pRecord.setTotalPrice(Float.parseFloat(amount));
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
						parentRebateRecord.setTotalPrice(Float.parseFloat(amount));
						parentRebateRecord.setRemark("下级代理下单返利");
						parentRebateRecord.setRebateMoney((parentCity.getRebateMoney()==null?0:parentCity.getRebateMoney())+twoMoney);
						parentCity.setRebateMoney((parentCity.getRebateMoney()==null?0:parentCity.getRebateMoney())+twoMoney);
						rebateRecordService.insertSelective(parentRebateRecord);
						ssssCityMerchantService.update(parentCity);
					}
				}
			}
			if(level == 4){
				float oneMoney = Float.parseFloat(df.format(Float.parseFloat(amount)*Constant.rebateFactor.ONEPROPORTION.getStatus()));
				float twoMoney = Float.parseFloat(df.format(Float.parseFloat(amount)*Constant.rebateFactor.TWOPROPORTION.getStatus()));
				float threeMoney = Float.parseFloat(df.format(Float.parseFloat(amount)*Constant.rebateFactor.THREEPROPORTION.getStatus()));
				ssssCityMerchantService.update(city);
				if(city.getParentCityMerchantId()!=null && city.getParentCityMerchantId()!=0){
					SsssCityMerchant c = ssssCityMerchantService.getById(city.getParentCityMerchantId());
					if(c.getLevel()==3){
						rebateRecord.setMoney(threeMoney);
						rebateRecord.setMerchantId(city.getParentCityMerchantId());
						rebateRecord.setMerchantLevel(c.getLevel());
						rebateRecord.setDownMerchantId(city.getId());
						rebateRecord.setDownMerchantLevel(city.getLevel());
						rebateRecord.setTotalPrice(Float.parseFloat(amount));
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
								parentRebateRecord.setTotalPrice(Float.parseFloat(amount));
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
									pRecord.setTotalPrice(Float.parseFloat(amount));
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
								parentRebateRecord.setTotalPrice(Float.parseFloat(amount));
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
						rebateRecord.setTotalPrice(Float.parseFloat(amount));
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
							pRecord.setTotalPrice(Float.parseFloat(amount));
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
						rebateRecord.setTotalPrice(Float.parseFloat(amount));
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
		return result;
	}

	/**
	 * 返回10位的billNo
	 */
	private String createBillNo(String orderId) {
		int billNoLength = 10;
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < billNoLength - orderId.length(); i++) {
			str.append("0");
		}
		return str.toString() + orderId;
	}

	private Map<String, String> merchantPara2Map(String merchantPara) {
		String[] allMerchantParas = merchantPara.split("\\|");
		Map<String, String> merchantParaMap = new HashMap<String, String>();
		for (int i = 0; i < allMerchantParas.length; i++) {
			String[] oneMerchantParas = allMerchantParas[i].split("=");
			merchantParaMap.put(oneMerchantParas[0], oneMerchantParas[1]);
		}
		return merchantParaMap;
	}
}
