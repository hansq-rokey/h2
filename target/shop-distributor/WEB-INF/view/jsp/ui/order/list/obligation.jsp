<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<jsp:include page="../../include/orderHead.jsp"></jsp:include>
<body>
	<div class="container clearfix">
		<div class="content">
			<jsp:include page="../../include/top.jsp"></jsp:include>
			<div class="clearfix">
				<div class="content-left">
					<jsp:include page="../../include/left.jsp"><jsp:param value="order" name="checkLefr" /></jsp:include>
				</div>
				<div class="content-right">
					<jsp:include page="../../include/orderTag.jsp"></jsp:include>
				<c:if test="${fn:length(orderList)>0}">
					<c:forEach items="${orderList }" var="item" varStatus="status">
						<div class="user-orderlist-box">
							<div class="clearfix user-orderlist-title">
								<span class="left ml10"><fmt:formatDate value="${item.createDateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
								<em class="left">订单号：${item.orderNumber }  ${item.information.reveiveUserName }</em>
								<span class="fr order-close"></span>
							</div>
							<table class="user-orderlist-table">
								<tbody>
									<c:forEach items="${item.orderItems }" varStatus="statusItem" var="orderItem">
										<tr>
											<td ${statusItem.index==0?"class=\"line-bottom\"":""} style="width:358px;">
												<c:forEach items="${orderItem.pics }" var="pic" varStatus="statusPic">
													<c:if test="${statusPic.index==0 }"><img  class="pic-show"  src="${pic.url }" /></c:if>
												</c:forEach>
												<p style="padding-left: 150px;margin-top:36px;">${orderItem.productTitle }</p>
												<p style="padding-left: 150px">${orderItem.productModelFormatName }</p>
											</td>
											<td class="sale ${statusItem.index==0?"line-bottom":""}">
												<fmt:formatNumber value="${orderItem.discountUnitPrice}" type="currency" maxFractionDigits="2" minFractionDigits="2"/>
											</td>
											<td class="num ${statusItem.index==0?"line-bottom":""}">
												${orderItem.num } (${orderItem.unit })
											</td>
											<c:if test="${statusItem.index==0}">
												<td rowspan="${fn:length(item.orderItems)}" class="line-left total">
													<c:if test="${item.enableCouponMoney>0 }">
														<p style="text-decoration: line-through;color:#9c9c9c;"><fmt:formatNumber value="${item.totalPrice }" type="currency" maxFractionDigits="2" minFractionDigits="2"/></p>
														<p style="color:#3c3c3c;"><fmt:formatNumber value="${item.shouldPayMoney }" type="currency" maxFractionDigits="2" minFractionDigits="2"/></p>
													</c:if>
													<c:if test="${item.enableCouponMoney==0 }">
														<p><fmt:formatNumber value="${item.shouldPayMoney }" type="currency" maxFractionDigits="2" minFractionDigits="2"/></p>
													</c:if>
												</td>
												<td rowspan="${fn:length(item.orderItems)}" class="line-left pay">
													<c:choose>
														<c:when test="${item.status==10 }">待付款</c:when>
														<c:when test="${item.status==20 }">已付款</c:when>
														<c:when test="${item.status==22 }">定制确认</c:when>
														<c:when test="${item.status==24 }">定制中</c:when>
														<c:when test="${item.status==26 }">组装中</c:when>
														<c:when test="${item.status==28 }">已入库</c:when>
														<c:when test="${item.status==30 }">配货中</c:when>
														<c:when test="${item.status==40 }">已发货</c:when>
														<c:when test="${item.status==50 }">已完成</c:when>
														<c:otherwise>已关闭</c:otherwise>
													</c:choose>
												</td>
												<td rowspan="${fn:length(item.orderItems)}" class="line-left operate">
													<c:choose>
														<c:when test="${item.status==10 }">
															<p><a href="/u/order/detail.html?number=${item.orderNumber }" class="detail">订单详情</a></p>
															<p><a class="new-orange-btn" href="/u/order/repeat/pay.html?number=${item.orderNumber }">立即支付</a></p>
														</c:when>
														<c:when test="${item.status==20 }">
															<p><a href="/u/order/detail.html?number=${item.orderNumber }" class="detail">订单详情</a></p>
														</c:when>
														<c:when test="${item.status==30 }">
															<p><a href="/u/order/detail.html?number=${item.orderNumber }" class="detail">订单详情</a></p>
														</c:when>
														<c:when test="${item.status==40 }">
															<p><a href="/u/order/detail.html?number=${item.orderNumber }" class="detail">订单详情</a></p>
														</c:when>
														<c:when test="${item.status==50 }">
															<p><a href="/u/order/detail.html?number=${item.orderNumber }" class="detail">订单详情</a></p>
															<p><a href="user-order.html" class="detail">申请售后</a></p>
														</c:when>
														<c:otherwise>
															<p><a href="user-order.html" class="detail">重新下单</a></p>
														</c:otherwise>
													</c:choose>
												</td>
											</c:if>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</c:forEach>
					<div class="list-page">
						<c:if test="${pager.hasPreviousPage }">
							<a class="prev" href="${url }${pager.pageNumber-1}"></a>
						</c:if>
						<c:if test="${pager.start-1>0 }">
							<a href="${url }1">1</a>
						</c:if>
						<c:if test="${pager.hasBeforPoint }">
							...
						</c:if>
						<div class="list-page-num">
							<c:forEach items="${pager.navigatePageNumbers }" var="item">
								<a class="${item==pager.pageNumber?'current':'' }" href="${url }${item }">${item }</a>
							
							</c:forEach>
						</div>
						<c:if test="${pager.hasAfterPoint }">
							...
						</c:if>
						<c:if test="${ pager.hasNextPage}">
							<a class="next" href="${url }${pager.pageNumber+1}"></a>						
						</c:if>
					</div>
				</c:if>
				<c:if test="${fn:length(orderList)==0}">
					<div class="none-container">
						<div class="tac none-container-pic">
							<i></i>
						</div>
						<p class="text">您当前没有未付款的订单哦~~</p>
					</div>
				</c:if>

				</div>
				
			</div>
		</div>
	</div>
	<script type="text/javascript" src="http://fe.ibaixiong.com/shop/js/common.js"></script>
	<script type="text/javascript">
		var JsonData = [// 模拟数据
				{
					"content": "由【深圳福田西丽大学城】发往 【广东深圳中转部】3",
					"time": "2015-05-16 10:22:08",
					"sign": "1"// 当前所以位置 1为在
				},
				{
					"content": "由【深圳福田西丽大学城】发往 【广东深圳中转部】2",
					"time": "2015-05-17 10:22:08",
					"sign": "0"
				},
				{
					"content": "由【深圳福田西丽大学城】发往 【广东深圳中转部】1",
					"time": "2015-05-18 10:22:08",
					"sign": "0"
				}
			];
		// 获取物流接口
		var settings = {
			getLogistics: function(number, fn){
				fn(JsonData, number);// 模拟动态请求后的回调
				/*$.ajax({
                    type: "POST",//请求方式
                    url: "",//请求地址
                    data: d,//参数
                    dataType: "json",// 接收数据的格式
                    success: function (data) {
                        fn(data);// 数据回调
                    },//成功后启动的回调函数
                    error: function () {
                        flg = 3;//错误，提示信息！
                    },
                    cache: false,//缓存
                    async: false//锁定用户操作
                });*/

			}
		};
	</script>
<jsp:include page="../../include/footer.jsp"></jsp:include>
		<script>
		var html='';
		function dateTime(){
			$('.order-close').each(function(){
				var startDate=$(this).parent().find('.ml10').text().toString().replace(/-/g,"/");
				var newDate=new Date();
				//newDate=newDate++;
				startDate=new Date(Date.parse(startDate));
				startDate++;
				var time=43200000-(newDate-startDate);
				var seconds = time/1000;
		        var minutes = Math.floor(seconds/60);
		        var hours = Math.floor(minutes/60);
		        var CHour= hours % 24;
		        var CMinute= minutes % 60;
		        var CSecond= seconds % 60;
		        html=''+CHour+'时'+CMinute+'分'+parseFloat(CSecond).toFixed(0)+'秒后订单关闭';
		        $(this).text(html);
				
			})
		}
		window.onload=function(){
	        setInterval("dateTime()",1000);
	    }
	</script>
</body>
</html>
