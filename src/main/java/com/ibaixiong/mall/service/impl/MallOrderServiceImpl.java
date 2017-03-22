package com.ibaixiong.mall.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibaixiong.entity.MallAddress;
import com.ibaixiong.entity.MallCustomPic;
import com.ibaixiong.entity.MerchantCouponMoneyRecord;
import com.ibaixiong.entity.SsssCityMerchant;
import com.ibaixiong.entity.User;
import com.ibaixiong.entity.util.BalanceChangeStatusEnum;
import com.ibaixiong.mall.activemq.MallOrderProducer;
import com.ibaixiong.mall.dao.MallCustomPicDao;
import com.ibaixiong.mall.dao.MerchantCouponMoneyRecordDao;
import com.ibaixiong.mall.dao.SsssCityMerchantDao;
import com.ibaixiong.mall.exception.CreateOrderFailedException;
import com.ibaixiong.mall.exception.NotFindOrderException;
import com.ibaixiong.mall.model.MallOrderModel;
import com.ibaixiong.mall.model.OrderSubmitInfo;
import com.ibaixiong.mall.service.ErpPurchaseMaterialServce;
import com.ibaixiong.mall.service.MallOrderService;
import com.papabear.commons.entity.Pager;
import com.papabear.order.api.CarItemService;
import com.papabear.order.api.DepositService;
import com.papabear.order.api.OrderService;
import com.papabear.order.entity.MallCarItem;
import com.papabear.order.entity.MallOrder;
import com.papabear.order.entity.MallOrderItem;
import com.papabear.order.entity.MallOrderRevicerInformation;
import com.papabear.order.entity.enums.SourceEnum;
import com.papabear.order.model.OrderParamters;
import com.papabear.pay.api.PayService;
import com.papabear.product.api.CategoryQueryService;
import com.papabear.product.api.ProductQueryService;
import com.papabear.product.entity.MallBasicCategoryModelFormat;
import com.papabear.product.entity.MallFormatExt;
import com.papabear.product.entity.MallProductPic;

/**
 * Created by Administrator on 2015/8/5.
 */
@Service
class MallOrderServiceImpl extends AbstractServiceImpl implements MallOrderService {
	@Resource
	private MallCustomPicDao mallCustomPicDao;
	@Resource
	private MallOrderProducer mallOrderProducer;
	@Resource
	private MallCustomPicDao customPicDao;
	@Resource
	private CarItemService carItemService;
	@Resource
	private OrderService orderService;
	@Resource
	private ErpPurchaseMaterialServce erpPurchaseMaterialServce;

	@Resource
	private PayService payService;

	@Resource
	private ProductQueryService productQueryService;

	@Resource
	private CategoryQueryService categoryQueryService;
	@Resource
	private DepositService depositService;
	@Resource
	private SsssCityMerchantDao ssssCityMerchantDao;
	@Resource
	private MerchantCouponMoneyRecordDao couponRecordDao;
	@Override
	@Transactional
	public MallOrder commit(MallOrderModel orderModel,User user, Long addressId, String deliverTimeName, String deliverValue, String ip,List<MallCarItem> carItems,Float fixateProfit,Float couponMoney) {
		orderModel.setSource(SourceEnum.MALL_PC.getSource());
//		MallOrder order = orderService.createCartOrder((MallOrder) orderModel, addressId, deliverTimeName, deliverValue, ip);
		OrderParamters paramters=this.CreateOrderParamters(user, null, 0f, ip, orderModel.getRemark(), SourceEnum.MALL_PC.getSource(), 
				null, null, null, (byte)2, null, null, null, null, fixateProfit,couponMoney);
		MallOrder order=orderService.createMerchantCartCommonOrder(paramters, carItems);
//		MallOrder order =orderService.createCartCommonOrder(user.getId(), user.getUserName(), user.getBxNum(), orderModel.getRemark(), (byte)0, 
//				SourceEnum.MALL_PC.getSource(), (byte)0, (byte)0, 0f, (byte)0, ip, null);
		SsssCityMerchant city = ssssCityMerchantDao.getByUserId(user.getId());
		//优惠券使用记录
		MerchantCouponMoneyRecord couponRecord = new MerchantCouponMoneyRecord();
		couponRecord.setMerchantId(city.getId());
		couponRecord.setMerchantLevel(city.getLevel());
		couponRecord.setStatus(BalanceChangeStatusEnum.MINUSBALANCE.getCode());
		couponRecord.setCreateDateTime(new Date());
		couponRecord.setRemark("购物车下单使用");
		couponRecord.setUseCouponMoney(-couponMoney);
		couponRecord.setBeforeFreezeCouponMoney(city.getFreezeCoupon());
		couponRecord.setAfterFreezeCouponMoney(city.getFreezeCoupon());
		couponRecord.setBeforeUnfreezeCouponMoney(city.getUnfreezeCoupon());
		couponRecord.setAfterUnfreezeCouponMoney(city.getUnfreezeCoupon()-couponMoney);
		couponRecord.setOrderNumber(order.getOrderNumber());
		if(couponMoney>0){
			couponRecordDao.insertSelective(couponRecord);
		}
		//更新代理商优惠券金额
		city.setUnfreezeCoupon(city.getUnfreezeCoupon()-couponMoney);
		city.setUpdateTime(new Date());
		ssssCityMerchantDao.updateByPrimaryKeySelective(city);
		
		MallAddress address = addressDao.getMallAddress(addressId, order.getUserId());
		if (address == null) {
			throw new CreateOrderFailedException("收货人信息不能为空！");
		}
		// 快递信息保存
		MallOrderRevicerInformation revicer = new MallOrderRevicerInformation();
		revicer.setDeliverTimeName(deliverTimeName);
		revicer.setDeliverTimeValue(deliverValue);
		revicer.setCityCode(address.getCityCode());
		revicer.setCityName(address.getCityName());
		revicer.setCreateDateTime(new Date());
		revicer.setDetailAddress(address.getDetailAddress());
		revicer.setDistrictCode(address.getDistrictCode());
		revicer.setDistrictName(address.getDistrictName());

		revicer.setMobilePhone(address.getMobilePhone());
		revicer.setOrderId(order.getId());
		revicer.setOrderNumber(order.getOrderNumber());
		revicer.setProvinceCode(address.getProvinceCode());
		revicer.setProvinceName(address.getProvinceName());
		revicer.setReveiveUserName(address.getUserName());
		revicer.setTelPhone(address.getTelPhone());
		revicer.setUserId(order.getUserId());
		revicer.setZipcode(address.getZipcode());

		Long expressId = orderService.saveMallOrderRevicerInformation(revicer);
		orderService.updateOrderExpressId(expressId, order.getOrderNumber());
//		User user = (User) SecurityUtils.getSubject().getPrincipal();
//		this.saveSsssOrder(order, user, order.getOrderNumber(), orderModel.getCarItems());
		
		//发一条订单消息给服务器
		mallOrderProducer.send("queue.bom", order.getOrderNumber());
		//发送一条短信给代理商对应的业务员
		mallOrderProducer.send("queue.message", order.getOrderNumber());
		return order;
	}

	@Override
	public MallOrder queryOrderByOrderNumber(User user, String orderNumber) {

		MallOrder order = orderService.getOrderByOrderNumber(user.getId(), orderNumber);
		if (order == null) {
			throw new NotFindOrderException("没有找到订单信息");
		}
		return order;
	}
	/**
	 * 创建订单参数
	 * @param user			用户对象
	 * @param format		规格对象
	 * @param frontMoney	定金金额
	 * @param ip			ip地址
	 * @param remark		订单备注
	 * @param source		来源
	 * @param tagCode		发热墙布房间标识
	 * @param totalNum		购买总数量
	 * @param totalPrice	订单总价
	 * @param type			类型，1、个人订单  2：经销商订单
	 * @param length		发热墙布长
	 * @param width			发热墙布宽
	 * @param height		发热墙布高
	 * @param groundArea	地面面积
	 * @param fixateProfit	经销商进货价系数
	 * @return
	 */
private OrderParamters CreateOrderParamters(User user,MallBasicCategoryModelFormat format,Float frontMoney,String ip,
		String remark,Byte source,Integer tagCode,Float totalNum,Float totalPrice,Byte type,Float length,Float width,
		Float height,Float groundArea,Float fixateProfit,Float couponMoney){
	OrderParamters paramters=new OrderParamters();
	paramters.setCityId(ssssCityMerchantDao.getByUserId(user.getId()).getId());
	paramters.setBxNum(user.getBxNum());
	paramters.setUserId(user.getId());
	paramters.setUserName(user.getUserName());
	paramters.setFormat(format);
	paramters.setFrontMoney(frontMoney);
	paramters.setIp(ip);
	paramters.setRemark(remark);
	paramters.setSource(source);
	paramters.setTagCode(tagCode);
	paramters.setTotalNum(totalNum);
	paramters.setTotalPrice(totalPrice);
	paramters.setType(type);
	//发热墙布
	paramters.setLength(length);
	paramters.setWidth(width);
	paramters.setHeight(height);
	paramters.setGroundArea(groundArea);
	paramters.setFixateProfit(fixateProfit);
	paramters.setCouponMoney(couponMoney);
	
	paramters.setIsInstall((byte)0);
	paramters.setIsMakeBill((byte)0);
	paramters.setIsUseFrontMoney((byte)0);
	paramters.setIsUseInvitecode((byte)0);
	return paramters;
}
	@Override
	@Transactional
	public MallOrder createSingleOrder(User user,MallBasicCategoryModelFormat format,OrderSubmitInfo info,String ip,List<MallFormatExt> extList,Float totalPrice,String depositIds,Float length,Float width,Float height,Float groundArea,Float fixateProfit,Float couponMoney) {
		MallOrder order = null;
		if(format.getIsExtProperties()!=null&&format.getIsExtProperties().intValue()==1){
			//创建特殊商品订单
			OrderParamters paramters=this.CreateOrderParamters(user, format, 0f, ip, info.getRemark(), SourceEnum.MALL_PC.getSource(), 
					info.getTag(), info.getNum(), totalPrice, (byte)2, length, width, height, groundArea, fixateProfit,couponMoney);
			order=orderService.createMerchantSingleSpecialOrder(paramters);
			//发一条订单消息给服务器
			mallOrderProducer.send("queue.bom", order.getOrderNumber());
			//发送一条短信给代理商对应的业务员
			mallOrderProducer.send("queue.message", order.getOrderNumber());
//			order=orderService.createSingleSpecialOrder(user.getId(), user.getUserName(), user.getBxNum(), info.getRemark(), info.getNum(), format,
//					totalPrice,(byte)0, SourceEnum.MALL_PC.getSource(), (byte)0, (byte)0,  0f, (byte)0, ip,info.getTag(), extList);
		}else{
			//创建普通订单
			OrderParamters paramters=this.CreateOrderParamters(user, format, 0f, ip, info.getRemark(), SourceEnum.MALL_PC.getSource(), 
					info.getTag(), info.getNum(), totalPrice, (byte)2, null, null, null, 0f, fixateProfit,couponMoney);
			order=orderService.createMerchantSingleCommonOrder(paramters);
			//发送一条短信给代理商对应的业务员
			mallOrderProducer.send("queue.message", order.getOrderNumber());
//			order=orderService.createSingleCommonOrder(user.getId(), user.getUserName(), user.getBxNum(), info.getRemark(), info.getNum(), format,
//					(byte)0, SourceEnum.MALL_PC.getSource(), (byte)0, (byte)0,  0f, (byte)0, ip);
		}
		SsssCityMerchant city = ssssCityMerchantDao.getByUserId(user.getId());
		//优惠券使用记录
		MerchantCouponMoneyRecord couponRecord = new MerchantCouponMoneyRecord();
		couponRecord.setMerchantId(city.getId());
		couponRecord.setMerchantLevel(city.getLevel());
		couponRecord.setStatus((byte)2);
		couponRecord.setCreateDateTime(new Date());
		couponRecord.setRemark("立即下单使用");
		couponRecord.setUseCouponMoney(-couponMoney);
		couponRecord.setBeforeFreezeCouponMoney(city.getFreezeCoupon());
		couponRecord.setAfterFreezeCouponMoney(city.getFreezeCoupon());
		couponRecord.setBeforeUnfreezeCouponMoney(city.getUnfreezeCoupon());
		couponRecord.setAfterUnfreezeCouponMoney(city.getUnfreezeCoupon()-couponMoney);
		couponRecord.setOrderNumber(order.getOrderNumber());
		if(couponMoney>0){
			couponRecordDao.insertSelective(couponRecord);
		}
		//更新代理商优惠券金额
		city.setUnfreezeCoupon(city.getUnfreezeCoupon()-couponMoney);
		city.setUpdateTime(new Date());
		ssssCityMerchantDao.updateByPrimaryKeySelective(city);

		MallAddress address = addressDao.getMallAddress(info.getAddressId(), user.getId());
		if (address == null) {
			throw new CreateOrderFailedException("收货人信息不能为空！");
		}
		// 快递信息保存
		MallOrderRevicerInformation revicer = new MallOrderRevicerInformation();
		revicer.setDeliverTimeName(info.getDeliverTimeName());
		revicer.setDeliverTimeValue(info.getDeliverValue());
		revicer.setCityCode(address.getCityCode());
		revicer.setCityName(address.getCityName());
		revicer.setCreateDateTime(new Date());
		revicer.setDetailAddress(address.getDetailAddress());
		revicer.setDistrictCode(address.getDistrictCode());
		revicer.setDistrictName(address.getDistrictName());
		revicer.setMobilePhone(address.getMobilePhone());
		revicer.setOrderId(order.getId());
		revicer.setOrderNumber(order.getOrderNumber());
		revicer.setProvinceCode(address.getProvinceCode());
		revicer.setProvinceName(address.getProvinceName());
		revicer.setReveiveUserName(address.getUserName());
		revicer.setTelPhone(address.getTelPhone());
		revicer.setUserId(user.getId());
		revicer.setZipcode(address.getZipcode());

		Long expressId = orderService.saveMallOrderRevicerInformation(revicer);
		orderService.updateOrderExpressId(expressId, order.getOrderNumber());

		String orderNumber = order.getOrderNumber();
		// -------------------------私人定制功能------------------------------
		if (info.getPicId() != null && info.getPicId().longValue() > 0 && info.getIsCustomMade().intValue() == 1) {
			MallCustomPic mallCustomPic = mallCustomPicDao.selectByPrimaryKey(info.getPicId());
			if (mallCustomPic != null && mallCustomPic.getUserId().longValue() == user.getId().longValue()) {
				mallCustomPic.setOrderNumber(orderNumber);
				mallCustomPic.setUpdateTime(new Date());
				mallCustomPicDao.updateByPrimaryKey(mallCustomPic);
				MallCustomPic cutPic = mallCustomPicDao.getmalCustomPic(user.getId(), mallCustomPic.getPicName() + "-1");
				if (cutPic != null) {
					cutPic.setOrderNumber(orderNumber);
					cutPic.setUpdateTime(new Date());
					mallCustomPicDao.updateByPrimaryKey(cutPic);
				}
			}
		}
		
		//定金支付业务
		
		if(StringUtils.isNotBlank(depositIds)&&depositIds.length()>0){
			depositService.useDeposites(order.getOrderNumber(), user.getId(), depositIds);			
		}
		return order;
	}

	


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibaixiong.mall.service.MallOrderService#queryOrderByStatusArr(java
	 * .lang.Long, java.lang.Byte[], int)
	 */
	@Override
	public Pager<MallOrder> queryOrderByStatusArr(Long userId, Byte[] statusArray, int pageNo) {

		return this.queryOrderByStatusArr(userId, statusArray, pageNo, 10);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibaixiong.mall.service.MallOrderService#queryOrderByStatusArr(java
	 * .lang.Long, java.lang.Byte[], int, int)
	 */
	@Override
	public Pager<MallOrder> queryOrderByStatusArr(Long userId, Byte[] statusArray, int pageNo, int pageSize) {
		Pager<MallOrder> pager = orderService.queryOrdersByUserIdAndStatusArr(userId, statusArray, pageNo, pageSize);
//		List<MallOrderExt> orderExtList = new ArrayList<MallOrderExt>();
		for (MallOrder order : pager.getList()) {
//			MallOrderExt orderExt = new MallOrderExt();
//			orderExt.setOrder(order);
			order.setInformation(orderService.getRevicerByUserIdAndOrderNumber(userId, order.getOrderNumber()));
			List<MallOrderItem> orderItems = orderService.queryOrderItems(userId, order.getOrderNumber());
//			List<MallOrderItemExt> orderItemExtList = new ArrayList<MallOrderItemExt>();

			for (MallOrderItem orderItem : orderItems) {
//				MallOrderItemExt orderItemExt = new MallOrderItemExt();
//				orderItem.setOrderItem(orderItem);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("productId", orderItem.getProductId());
				map.put("formatId", orderItem.getProductModelFormatId());
				orderItem.setPics(productQueryService.queryProductPicByFormatId(map));
//				orderItemExtList.add(orderItemExt);
			}
			order.setOrderItems(orderItems);
//			orderExtList.add(orderExt);
		}

//		Pager<MallOrder> page = new Pager<MallOrderExt>(pager.getTotal(), pageNo, pageSize);
//		page.setList(orderExtList);
		return pager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibaixiong.mall.service.MallOrderService#queryOrderByStatus(java.lang
	 * .Long, java.lang.Byte, int)
	 */
	@Override
	public Pager<MallOrder> queryOrderByStatus(Long userId, Byte status, int pageNo) {

		return this.queryOrderByStatus(userId, status, pageNo, 10);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibaixiong.mall.service.MallOrderService#queryOrderByStatus(java.lang
	 * .Long, java.lang.Byte, int, int)
	 */
	@Override
	public Pager<MallOrder> queryOrderByStatus(Long userId, Byte status, int pageNo, int pageSize) {
		Pager<MallOrder> pager = orderService.queryOrdersByUserIdAndStatus(userId, status, pageNo, pageSize);
//		List<MallOrderExt> orderExtList = new ArrayList<MallOrderExt>();
		for (MallOrder order : pager.getList()) {
//			MallOrderExt orderExt = new MallOrderExt();
//			orderExt.setOrder(order);
			order.setInformation(orderService.getRevicerByUserIdAndOrderNumber(userId, order.getOrderNumber()));
			List<MallOrderItem> orderItems = orderService.queryOrderItems(userId, order.getOrderNumber());
//			List<MallOrderItemExt> orderItemExtList = new ArrayList<MallOrderItemExt>();

			for (MallOrderItem orderItem : orderItems) {
//				MallOrderItemExt orderItemExt = new MallOrderItemExt();
//				orderItemExt.setOrderItem(orderItem);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("productId", orderItem.getProductId());
				map.put("formatId", orderItem.getProductModelFormatId());
				if(order.getIsCustomMade()!=null&&order.getIsCustomMade().intValue()==1){
					
					List<MallCustomPic> customPics= customPicDao.queryByOrderNumber(order.getOrderNumber());
					List<MallProductPic> picList=new ArrayList<MallProductPic>();
					for(MallCustomPic pic:customPics){
						MallProductPic p=new MallProductPic();
						p.setUrl(pic.getUrl());
						picList.add(p);
					}
					customPics=null;
					orderItem.setPics(picList);
				}else{
					orderItem.setPics(productQueryService.queryProductPicByFormatId(map));					
				}
//				orderItemExtList.add(orderItemExt);
			}
			order.setOrderItems(orderItems);
//			orderExtList.add(orderExt);
		}

//		Pager<MallOrderExt> page = new Pager<MallOrderExt>(pager.getTotal(), pageNo, pageSize);
//		page.setList(orderExtList);
		return pager;
	}

	@Override
	public int updateOrderStatus(Byte status, String orderNumber) {
		return orderService.updateOrderStatus(status, orderNumber);
	}

}
