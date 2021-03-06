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
		<link href="http://fe.ibaixiong.com/common/plug/cropper.css" rel="stylesheet" type="text/css">
		<link href="http://fe.ibaixiong.com/shop/css/common.css" rel="stylesheet" type="text/css">
		<link href="http://fe.ibaixiong.com/shop/css/pay.css" rel="stylesheet" type="text/css">
		<link href="/css/specification.css" rel="stylesheet" type="text/css">
		<script src="http://fe.ibaixiong.com/common/plug/jQuery/jquery-1.9.1.min.js" type="text/javascript" ></script>
		<script src="http://fe.ibaixiong.com/common/plug/jQuery/jquery.rotate.min.js" type="text/javascript" ></script>
		<script src="http://fe.ibaixiong.com/common/plug/bootstrap/bootstrap.min.js" type="text/javascript" ></script>
		<script src="/plug/cropper.js" type="text/javascript" ></script>
		<script src="/plug/main.js" type="text/javascript" ></script>
    	<script src="/js/data.js"></script>
	</head>
	<body>
		<div class="container clearfix">
		<div class="content">
		<jsp:include page="../include/top.jsp" />
		<form action="/u/order/buy.html" method="post" id="formBuy" onsubmit="return check()">
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
				<div id="delimiter"></div>
				<!-- 墙纸面积 -->
				<c:if test="${bottomPriceFormat.isExtProperties==1 }">
				<div class="extPro">
					<p class="fomcat-name isext">房屋尺寸<span>（单位:米）</span></p>
					<div class="change isext">
						<c:forEach items="${exts }" var="item">
							<c:if test="${item.type==1 }">
								<span>${item.propertyName }</span>
								<input type="text" name="${item.identify }" data-txt="${item.identify }" class="${item.identify } sizes" value="0"/>
							</c:if>
							<c:if test="${item.type==4 }">
								<span class="icon_house">计算面积</span>
							</c:if>
						</c:forEach>
					</div>
				</div>
				</c:if>
				<!-- 数量 -->
				<p class="fomcat-name" id="numExplain">
					<c:if test="${ bottomPriceFormat.explain==null}">
					数量
					</c:if>
					<c:if test="${ bottomPriceFormat.explain!=null}">
					${bottomPriceFormat.explain }<span>（推荐不低于<i class="recom-area">0</i>${bottomPriceFormat.unit }）</span>
					</c:if>
				</p>
				<div class="change">
					<span class="minus" data-btn=""></span>
					<input type="text" name="num" data-txt="num" data-type="1" class="number" value="1"/>
					<span class="unit">${bottomPriceFormat.unit }</span>
					<span class="plus" data-btn=""></span>
				<p class="fomcat-remark" style="display:none;width:100px;float:left;line-height:40px;">(库存不足)</p>
				</div>
			</div>
		</div>
		<input type="hidden" id="productId" name="productId"  value="${product.id }"/>
		<input type="hidden" name="format" id="format" value="" />
		<input type="hidden" name="isCustomMade" value="0" id="isCustomMade">
		<input type="hidden" name="tag" id="area" value="1">
		<input type="hidden" name="isExt" id="thisExt" value="0">
		<input type="hidden" name="formatId" id="formatId" value="0">
		<input type="hidden" name="Save_arae" id="Save_arae" value="1"><!-- 墙纸面积-->
	    <input type="hidden" name="Ground" id="Ground_arae" value="1"><!-- 111地面面积-->
	    
	    
		</form>
		</div>
		<div class="buy">
			<div class="buybox">
			<div class="buy-infor">
				<p class="productname">${product.title}</p>
				<p class="productinfor"><span class="inforText">飘窗款  1200W  土豪金  私人订制</span><span>x<span class="numberText">1</span></span></p>
			</div>
			<div class="addbuy">
				<span class="buyprice"></span>
				<a href="javascript:void(0)" class="buycar">加入购物车</a>	
				<a href="javascript:void(0)" class="submitLink">立即购买</a>
			</div>
			</div>
		</div>
		</div>
		<div class="uploadpop" style="display: none;z-index:995">
			<div class="layel">
				<h3 class="poptitle"><span class="greyword">选择产品规格></span>私人定制<i class="closeicon uploadclose"></i> </h3>
				<div class="leftLayel">
					<div class="prodBox"></div>
				</div>
				<div class="rightLayel">
					<p class="text" style="margin-top:100px;">您可以选择一张本地照片做产品正面图案印刷</p>
					<p class="text">为了保证印刷精细，请上传高度大于<span class="numberword">4000</span>像素，宽度大于<span class="numberword">2000</span>像素的照片</p>
					<p class="text blueword"><a href="http://bbs.ibaixiong.com/detail/368.html" target="_blank">如何查看像素点?</a></p>
					<div class="row tc uploadbox">
						<input type="button" value="上传本地图片" class="upload">
						<input type="file" value="上传本地图片" class="upload file">
						<span class="uploading-animate"></span>
					</div>
					<p class="text">私人定制图片<span class="blueword">请勿参照</span>上图,下单后,客服会第一时间与您联系,我们将为您打造私人完美!</p>
					<!-- <div class="row tc" style="position:relative;margin-top:100px;">
						<input type="button" value="确认" class="sureUpload">
						<div class=""></div>					<input type="button" value="重新上传图片" class="upload reUpload">
						<input type="file" value="重新上传图片" class="upload file reUpload">
					</div> -->
				</div>
			</div>
		</div>
 		<div class="successpop" style="display: none;">
 		<div class="layel">
		  <div class="container" id="crop-avatar" style="background:none;">
		    <!-- Current avatar -->
		    <div class="avatar-view" title="Change the avatar" style="opacity:0;">
		      <img src="#" alt="Avatar">
		    </div>
		
		    <!-- Cropping modal -->
		    <div class="modal fade" id="avatar-modal" aria-hidden="true" aria-labelledby="avatar-modal-label" role="dialog" tabindex="-1" style="width:1200px;margin:auto;">
		      <div class="modal-dialog modal-lg" style="width:1200px;">
		        <div class="modal-content">
		          <form class="avatar-form" enctype="multipart/form-data" method="post">
		            <div class="modal-header">
		              <button class="close closeicon  uploadclose" type="button"></button>
		              <h4 class="modal-title" id="avatar-modal-label">私人定制</h4>
		            </div>
		            <div class="modal-body">
		              <div class="avatar-body">
		                <!-- Crop and preview -->
		                <div class="row">
		                  <div class="col-md-9">
		                    <div class="avatar-wrapper"></div>
		                    <div class="promptword">图片选取确定后客服会与您取得联系并对图片做相应修改与美化，<span style="color:#ff6200;">实际定制图以双方确认后为准！</span></div>
		                    <div class="btn-list" style="text-align:center;margin-top:20px;position:relative;">
		                    	<div class="uploading-animate2"></div>
			                    <div class="btn-group">
			                      <input type="button" class="btn btn-left avatar-btns" data-method="rotate" data-option="-90" type="button" title="Rotate -90 degrees">
			                    </div>
			                    <div class="btn-group">
			                      <input type="button" class="btn btn-right avatar-btns" data-method="rotate" data-option="90" type="button" title="Rotate -90 degrees">
			                    </div><br>
			                    <div class="btn-group" style="margin-top: 10px;">
			                    	<input type="button" value="确定" class="upload-sure">
			                    </div>
			                    <div class="btn-group" style="margin-top: 10px;">
			                    	<input type="button" value="重新上传" class="re-upload">
			                    </div>
		                    </div>
		                    
		                  </div>
		                  <div class="col-md-3" style="position:relative;">
		                     <div class="perview_box">
		                    	<img src="http://fe.ibaixiong.com/shop/images/previewBg.png" class="previewBg">
		                        <div class="avatar-preview preview-lg"></div>
		                     </div>	   
		                  </div>
		                </div>
		              </div>
		            </div>
		          </form>
		        </div>
		      </div>
		    </div><!-- /.modal -->
		
		    <!-- Loading state -->
		    <!-- <div class="loading" aria-label="Loading" role="img" tabindex="-1"></div> -->
		  </div>
		</div>
		</div>
		<div class="upfailedpop" style="display: none;">
			<div class="popbg"></div>
			<div class="layel2">
				<h3 class="poptitle" style="margin-bottom:30px;">上传出错 </h3>
				<p class="text">对不起，上传的图片尺寸不满足要求，为保证印刷的质量，请重新上传高度大于500像素，宽度大于300像素的照片。</p>
				<p class="text blueword"><a href="#">如何查看像素点?</a></p>
				<div class="row tc">
					<input type="button" value="重新上传" class="re-upload">
				</div>
			</div>
		</div>
		<!-- 发热壁纸弹窗 -->
		<div class="paperpop">
			<div class="popbg"></div>
		    <div class="layel">
				 <div class="red d_wrap">
				  <div class="d_left"><span>注意事项:</span></div>
				  <div class="d_right">
				    <p>1.发热墙纸需整面墙张贴；</p>
				    <p>2.发热墙纸前<b>不能被衣柜等大型家具遮挡，</b>否则将严重影响取暖效果；</p>
				  </div>
				 </div>
				 <div class="d_mian">
				   <div class="d_list d_list1">
				      <div class="d_list_mian">
				        <div class="pic-house">
						<div class="pic-left"></div>
						<img src="/images/pic_house_left.png" class="pic-house-left">
						<span class="area-left">共需发热墙纸<i>0</i>㎡</span>
						<div class="pic-right"></div>
						<img src="/images/pic_house_right.png" class="pic-house-right">
						<span class="area-right">共需发热墙纸<i>0</i>㎡</span>
						<span class="wide-num">11</span>
						<span class="high-num">11</span>
						<span class="long-num">11</span>
				      </div>
				     </div>
				   </div>
				   <div class="d_list d_list2">
				    <div class="row list_top">
				      <div class="d_box" >
				         <a href="###"   data-i="1">
					         <span class="icon iconfont">&#xe66c;</span>
					         <i class="icon iconfont icon-duihao d_size"></i>
					         <p>客厅</p>
				         </a>
				      </div>
				      <div class="d_box">
				          <a href="###"   data-i="2" >
					          <span class="icon iconfont">&#xe623;</span>
					          <i class="icon iconfont icon-yuanshixin d_size"></i>
					          <p>主卧</p>
				          </a>
				      </div>
				      <div class="d_box" >
				         <a href="###" data-i="3">
					         <span class="icon iconfont">&#xe615;</span>
					         <i class="icon iconfont icon-yuanshixin d_size"></i>
					         <p>次卧</p>
                         </a>				         
				      </div>
				      <div class="d_box d_height">
				         <a href="###"  data-i="4">
					         <span class="icon iconfont">&#xe617;</span>
					         <i class="icon iconfont icon-yuanshixin d_size"></i>
					         <p>书房</p>
				         </a>
				      </div>
				      <div class="d_box d_height">
				          <a href="###"  data-i="5">
					          <span class="icon iconfont">&#xe607;</span>
					          <i class="icon iconfont icon-yuanshixin d_size"></i>
					          <p>走廊</p>
				          </a>
				      </div>
				      <div class="d_box d_height">
				          <a href="###"  data-i="6">
					          <span class="icon iconfont">&#xe8d2;</span>
					          <i class="icon iconfont icon-yuanshixin d_size"></i>
					          <p>餐厅</p>
				          </a>
				      </div>
				      <div class="d_box d_height">
				          <a href="###"  data-i="7">
					          <span class="icon iconfont">&#xe620;</span>
					          <i class="icon iconfont icon-yuanshixin d_size"></i>
					          <p>其他</p>
				          </a>
				      </div>
				    </div>
				   </div>
				 </div>
				 <input type="button" class="paper-close" value="下一步">
				 <!-- pic-title-->
				<!--<div class="pic-house">
					<div class="pic-left"></div>
					<img src="../images/pic_house_left.png" class="pic-house-left">
					<span class="area-left">共需发热墙纸<i>0</i>㎡</span>
					<div class="pic-right"></div>
					<img src="../images/pic_house_right.png" class="pic-house-right">
					<span class="area-right">共需发热墙纸<i>0</i>㎡</span>
					<span class="wide-num">11</span>
					<span class="high-num">11</span>
					<span class="long-num">11</span>
				</div>
				<input type="button" class="paper-close" value="关闭"> -->
			</div>
		</div>
		<input type="hidden" id="picWidth">
		<input type="hidden" id="picLength">
		<input type="hidden" id="picId_"/>
		<input type="hidden" id="x">
		<input type="hidden" id="y">
		<input type="hidden" id="width">
		<input type="hidden" id="height">
		<input type="hidden" id="degree" value="0">
	</body>
</html>
