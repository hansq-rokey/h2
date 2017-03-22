/**
 * ibaixiong.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.ibaixiong.mall.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibaixiong.entity.ErpBom;
import com.ibaixiong.entity.ErpPurchaseMaterial;
import com.ibaixiong.mall.core.CalculateFactory;
import com.ibaixiong.mall.dao.ErpPurchaseMaterialDao;
import com.ibaixiong.mall.model.MaterialDisplayEnum;
import com.ibaixiong.mall.service.ErpPurchaseMaterialServce;
import com.papabear.commons.redis.RedisObjectUtils;

/**
 * 
 * @author yaoweiguo
 * @email 280435353@qq.com
 * @date 2016年7月20日
 * @since 1.0.0
 */
@Service
public class ErpPurchaseMaterialServiceImpl implements ErpPurchaseMaterialServce {

	@Resource
	ErpPurchaseMaterialDao purchaseMaterialDao;
	@Resource
	RedisObjectUtils redisObjectUtils;

	@Resource
	CalculateFactory calculateFactory;

	@Override
	public float calculatePrice(Long formatId, float wallArea,float groundArea) {

		float price=0;
		List<ErpPurchaseMaterial> list= redisObjectUtils.getMaterials(formatId.toString(),MaterialDisplayEnum.B.getDisplay());
		if(list==null||list.size()==0){
			list = purchaseMaterialDao.queryPurchaseMaterials(formatId);
			redisObjectUtils.saveOrUpdateMaterialS(formatId.toString(),MaterialDisplayEnum.B.getDisplay(), list);
		}

		for (ErpPurchaseMaterial pm : list) {
			price+=pm.getFactoryPrice()*calculateModel(pm.getCalculateModel().intValue(), wallArea, groundArea, pm.getCoefficient());
		}
		return price;
	}

	public float calculateModel(int model, float wallArea, float groundArea, float coefficient) {

		if (model == 1) {
			return calculateFactory.getWallPaperArea(wallArea, coefficient);
		} else if (model == 2) {
			return calculateFactory.getHotwallPaperArea(groundArea);
		} else if (model == 3) {
			return calculateFactory.getYellowWallpaperGlueArea(groundArea, coefficient);
		} else if (model == 4) {
			return calculateFactory.getWhiteWallpaperGlueArea(wallArea);
		} else if (model == 5) {
			return calculateFactory.getMicrocontrollersNumber(groundArea);
		} else if (model == 6) {
			return calculateFactory.getTJonLine(groundArea);
		}else if (model == 7) {
			return calculateFactory.getQuickConnector(groundArea);
		}else if (model == 8) {
			return calculateFactory.getOtherMountingsNumber();
		}
		return 1f;
	}
	
	public float calculateModel(int model, float length,float height, float groundArea, float coefficient) {

		if (model == 1) {
			return calculateFactory.getWallPaperAreaB(length , coefficient);
		} else if (model == 2) {
			return calculateFactory.getHotwallPaperArea(groundArea);
		} else if (model == 3) {
			return calculateFactory.getYellowWallpaperGlueArea(groundArea, coefficient);
		} else if (model == 4) {
			return calculateFactory.getWhiteWallpaperGlueArea(length*height);
		} else if (model == 5) {
			return calculateFactory.getMicrocontrollersNumber(groundArea);
		} else if (model == 6) {
			return calculateFactory.getTJonLine(groundArea);
		}else if (model == 7) {
			return calculateFactory.getQuickConnector(groundArea);
		}else if (model == 8) {
			return calculateFactory.getOtherMountingsNumber();
		}
		return 1f;
	}

	@Override
	public Map<String, Object> calculateMaterial(Long formatId, float wallArea, float groundArea) {
		Map<String, Object> map=new HashMap<String, Object>();
		float price=0;
		List<ErpPurchaseMaterial> list= redisObjectUtils.getMaterials(formatId.toString(),MaterialDisplayEnum.B.getDisplay());
		if(list==null||list.size()==0){
			list = purchaseMaterialDao.queryPurchaseMaterials(formatId);
			redisObjectUtils.saveOrUpdateMaterialS(formatId.toString(),MaterialDisplayEnum.B.getDisplay(), list);
		}
		List<ErpBom> boms=new ArrayList<ErpBom>(list.size());
		for (ErpPurchaseMaterial pm : list) {
			float num=calculateModel(pm.getCalculateModel().intValue(), wallArea, groundArea, pm.getCoefficient());
			price+=pm.getFactoryPrice()*num;
			ErpBom bom=new ErpBom();
			bom.setMaterialModel(pm.getModel());
			bom.setMaterialName(pm.getName());
			bom.setSerialNumber(pm.getSerialNumber());
			bom.setNum(num);
			boms.add(bom);
		}
		map.put("price", price);
		map.put("materials", boms);
		return map;
	}

	@Override
	public Map<String, Object> calculateMaterial(Long formatId, float length,float height, float groundArea) {
		Map<String, Object> map=new HashMap<String, Object>();
		float price=0;
		List<ErpPurchaseMaterial> list= redisObjectUtils.getMaterials(formatId.toString(),MaterialDisplayEnum.B.getDisplay());
		if(list==null||list.size()==0){
			list = purchaseMaterialDao.queryPurchaseMaterials(formatId);
			redisObjectUtils.saveOrUpdateMaterialS(formatId.toString(),MaterialDisplayEnum.B.getDisplay(), list);
		}
		List<ErpBom> boms=new ArrayList<ErpBom>(list.size());
		for (ErpPurchaseMaterial pm : list) {
			float num=calculateModel(pm.getCalculateModel().intValue(), length,height, groundArea, pm.getCoefficient());
			price+=pm.getFactoryPrice()*num;
			ErpBom bom=new ErpBom();
			bom.setMaterialModel(pm.getModel());
			bom.setMaterialName(pm.getName());
			bom.setSerialNumber(pm.getSerialNumber());
			bom.setNum(num);
			boms.add(bom);
		}
		map.put("price", price);
		map.put("materials", boms);
		return map;
	}
	
}
