<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>${product.title}_熊爸爸</title>
		<link href="http://fe.ibaixiong.com/common/plug/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css">
		<link href="http://fe.ibaixiong.com/shop/css/common.css" rel="stylesheet" type="text/css">
		<link href="http://fe.ibaixiong.com/shop/css/pay.css" rel="stylesheet" type="text/css">
		<link href="/css/specification_bak.css" rel="stylesheet" type="text/css">
		<script src="http://fe.ibaixiong.com/common/plug/jQuery/jquery-1.9.1.min.js" type="text/javascript" ></script>
		<script src="http://fe.ibaixiong.com/common/plug/jQuery/jquery.rotate.min.js" type="text/javascript" ></script>
		<script src="http://fe.ibaixiong.com/common/plug/bootstrap/bootstrap.min.js" type="text/javascript" ></script>
    	<script src="/js/data_tool.js"></script>
	</head>
	<body>
		<div class="container clearfix">
		<div class="content">
		<jsp:include page="../include/top.jsp" />
		<div class="article">
			<div class="product">
				<span class="name" data-num="${bottomPriceFormat.stock }">${product.title}</span>
				<span class="price"  data-value="${bottomPriceFormat.productPurchasePrice*merchant.fixateProfit}">
					<fmt:formatNumber value="${bottomPriceFormat.productPurchasePrice * merchant.fixateProfit}" maxFractionDigits="2" minFractionDigits="2" />
				</span>
				<input type="hidden" value="${ bottomPriceFormat.propertyValueCombine}" id="flag" />
				<input type="hidden" name="picId" id="picId" />
				<input type="hidden" name="mallCustomPicId" id="mallCustomPicId" />
			</div>
			<div class="left">
				<c:forEach items="${productPics}" var="pic" varStatus="status">
					<c:if test="${status.first}">
						<img src="${pic.url}"  class="bigphone"/>
						<img src="" class="customPic">
					</c:if>
				</c:forEach>
			</div>
			<div class="right " unselectable="on" style="-moz-user-select:none;" onselectstart="return false;">
				<c:forEach items="${properties }" var="item">
					<p class="fomcat-name">${item.propertyName }<c:if test="${item.isCustomMade==1 }"><span class="notes">选择私人订制可以上传图案</span></c:if></p>
					<c:if test="${item.displayFormat==1 }">
						<div class="row">
							<c:forEach items="${item.propertiesValues }" var="valueItem">
								<div class="col-md-6"><div class="box valueItem" data-id="${valueItem.id }">${valueItem.value }</div></div>
							</c:forEach>
						</div>
					</c:if>
					<c:if test="${item.displayFormat==2 }">
						<div class="row private-order">
							<c:forEach items="${item.propertiesValues }" var="valueItem">
								<div class="col-md-3 imgbox">
									<img src="${valueItem.imageUrl }" class="pattern valueItem <c:if test="${fn:indexOf(valueItem.value,'订制')>0 }">designed</c:if>"  data-id="${valueItem.id }"/>
									<p class="bottomtext">${valueItem.value }</p>
								</div>
							</c:forEach>
						</div>
					</c:if>
				</c:forEach>
				<div class="delimiter" id="delimiter">
					<div class="delimiter_tag">房屋尺寸 <i>(单位：㎡)</i></div>
					<div class="delimiter_conetent">
						 <div class="delimiter_list"> <label>房屋面积：</label><input id="delim_arae" class="delim_arae" name="num" type="text"></div>
						 <div class="delimiter_list"> <label>发热墙纸面积：</label><input id="paper_arae" class="paper_arae" name="num" type="text"></div>
					</div>
				</div>
			</div>
			<div class="down">
			  <div class="down_Inventory">
			  	<h3 class="name tex_center">物料清单</h3>
				    <div class="down_box">
				  	<div class="down_tag down_bottom">
				  		<span class="down_taglist">物料名称</span>
				  		<span class="down_taglist">规格</span>
				  		<span class="down_taglist">编号</span>
				  		<span class="down_taglist">数量</span>
				  	</div> 	
				  	<div id="down_content"></div>
				  	
				  </div>
			  </div> 
			  <div class="down_total">总价:<span class="down_t">0.00</span>元</div>
			</div>
		</div>
		<input type="hidden" id="productId" name="productId"  value="${product.id }"/>
		<input type="hidden" name="format" id="format" value="" />
		<input type="hidden" name="isCustomMade" value="0" id="isCustomMade">
		<input type="hidden" name="isExt" id="thisExt" value="0">
		<input type="hidden" name="formatId" id="formatId" value="0">
	   </div>
	 </div>
  </body>
</html>
