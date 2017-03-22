/**
 * ibaixiong.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.ibaixiong.mall.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibaixiong.core.web.RequestUtils;
import com.ibaixiong.entity.MallBusinessJoin;
import com.ibaixiong.mall.service.MallBusinessJoinService;

/**
 * 
 * @author yaoweiguo
 * @email  280435353@qq.com
 * @date   2016年7月12日
 * @since  1.0.0
 */

@Controller
public class BusinessJoinController extends AbstractController{
	Logger logger=LoggerFactory.getLogger(getClass());
	
	@Resource
	private MallBusinessJoinService businessJoinService;
	/**
	 * 添加招商加盟功能
	 * @author yaoweiguo
	 * @date 2016年7月12日
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/join/add.html")
	public String addBusinessJoin(@ModelAttribute MallBusinessJoin join,HttpServletRequest request){
		join.setIp(RequestUtils.getIpAddr(request));
		try {
			businessJoinService.add(join);
			
		} catch (Exception e) {
			logger.error("提交招商数据报错,错误信息:{}",e.getMessage());
			return "error";
		}
		
		return "success";
	}
	
	@RequestMapping("/attract.html")
	public String index(){
		
		return "enterprise/attract";
	}
}
