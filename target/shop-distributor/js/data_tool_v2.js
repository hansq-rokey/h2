	$(function() {
			function calculatePrice(){
				var num=$('.number').val();
				if(!num)
					return false;
				var thisExt=$("#thisExt").val();
				 if(thisExt==='1'){
					var wn=$(".length").val();
					var hn=$(".width").val();
					var ln=$(".height").val();
					var ground=$("#Ground_arae").val();//地面面积
					var wall=$("#Save_arae").val();//墙面面积
					var formatId=$("#formatId").val();
					if(!parseInt(wn)||!parseInt(hn)||!parseInt(ln)){
						var html='<div class="alertpop" style="display: block;position: fixed;width: 100%;height:100%;top:0;left:0;z-index: 9999;"><div class="popbg" style="    width: 100%;height:100%;position: absolute;top:0;left:0;z-index: 989;background: #000;opacity: 0.5;"></div><div class="alertLayel" style="width:400px;height:200px;padding-top:10px;background:#fff;position:absolute;margin:auto;top:0;left:0;right:0;bottom:0;z-index:9999;"><h3 style="height:30px;position:relative;"><i class="closeicon" style="margin-right:12px;margin-top:0;"></i> </h3><p class="alertContent" style="line-height:30px;font-size:14px;text-align:center;height:100px;">请输入长宽高再进行计算</p><div class="row tc"><input type="submit" value="确定" class="submitSure" style="height:30px;border:none;width:80px;background:#ff6200;color:#fff;margin-right:20px;"></div></div></div>';
						$("body").append(html);
						return false;
					}
					$.ajax({
						type: "POST",
						url:"/u/product/tool/calculate.html",
						data: {formatId:formatId,groundArea:ground,area:wall},
						cache: false,
						async:false,
						dataType:"json",
						success: function(data) {
							$(".buyprice").text('￥'+parseFloat(data.result.price).toFixed(2));
						},
						error: function () {
							var errhtml='<div class="alertpop" style="display: block;position: fixed;width: 100%;height:100%;top:0;left:0;z-index: 9999;"><div class="popbg" style="    width: 100%;height:100%;position: absolute;top:0;left:0;z-index: 989;background: #000;opacity: 0.5;"></div><div class="alertLayel" style="width:400px;height:200px;padding-top:10px;background:#fff;position:absolute;margin:auto;top:0;left:0;right:0;bottom:0;z-index:9999;"><h3 style="height:30px;position:relative;"><i class="closeicon" style="margin-right:12px;margin-top:0;"></i> </h3><p class="alertContent" style="line-height:30px;font-size:14px;text-align:center;height:100px;">计算失败</p><div class="row tc"><input type="submit" value="确定" class="submitSure" style="height:30px;border:none;width:80px;background:#ff6200;color:#fff;margin-right:20px;"></div></div></div>';
							$("body").append(errhtml);
						},
					});
				 }else{
					 price();				 
				 }
			}
		$('.number').bind('input propertychange', function() {//propertychange 文本框改变事件文本 
			var num=$(this).val();
			if(!num)
				return false;
			$('.numberText').text(num);
			$("#Save_arae").val(num);
			var thisExt=$("#thisExt").val();
			var sava_arae=$("#Save_arae").val();
			if(num>total){
				$('.fomcat-remark').show();
				$('.submitLink').addClass('submitLinkLayel');
				$('.buycar').addClass('buycarLayel');
			}else{
				$('.fomcat-remark').hide();
				$('.submitLink').removeClass('submitLinkLayel');
				$('.buycar').removeClass('buycarLayel');	
			}
			calculatePrice();
		});
		
		/*// 文本框只能输入数字
		var $numTxtBox = $('input[name="num"]');// 锁键状态
		$numTxtBox.keydown(function(e){// 绑定事件
			var oEvent = e || window.event;// 兼容处理
			if(!(oEvent.keyCode==46)&&!(oEvent.keyCode==8)&&!(oEvent.keyCode==9)&&!(oEvent.keyCode==37)&&!(oEvent.keyCode==39))// 键值
			if(!((oEvent.keyCode>=48&&oEvent.keyCode<=57)||(oEvent.keyCode>=96&&oEvent.keyCode<=105))) // 键值
				return false;
			//oEvent.returnValue = false; // firefox 兼容问题
		});*/
		
		//////////////
		var flag=$('#flag').val();
		data=flag.split('-');
		for(i=0;i<data.length;i++){
			$('.valueItem').each(function(){
				var data_id=$(this).attr('data-id');
				if(data_id==data[i]){
					$(this).addClass('on');
					return;
				}
			})
		}
		function valueSelected(flag){
			$.ajax({
				type: "POST",
				url: "/u/product/format/price.html?flag="+flag,
				cache: false,
				dataType:"json",
				success: function(data) {
					/*imghref=data.result.pics.join();*/
					perprice=data.result.discountPrice;
					total=data.result.stock;
					/*console.log(data.result.pics);*/
					var num=parseInt($('.number').val());
					prices=perprice*num;
					/*$(".left").html('<img src="'+imghref+'" >');//图片插入
*/					$("#thisExt").val(data.result.isExt);
					if(parseInt(data.result.isExt)==0){
						$('input[name="num"]').attr("disabled","disabled");
						$('input[name="num"]').val('');
			        	$('.down_Inventory').fadeOut(200);
					}else{
						$('input[name="num"]').removeAttr("disabled");
			        	$('.down_Inventory').fadeIn(200);
			        	$('input[name="num"]').val('');
					}
					$("#formatId").val(data.result.id);
					if(total<num){
						$('.submitLink').addClass('submitLinkLayel');
						$('.fomcat-remark').show();
					}else{
						$('.submitLinkLayel').removeClass('submitLinkLayel');
						$('.fomcat-remark').hide();
					}
				}
			});
		}
		format();
		var isCustomMade='';    //是否私人订制
		function format(code){
			var obj=[];
			var text='';
			$('.on').each(function(){
				var id=$(this).attr('data-id');
				var word=$(this).text();
		    	$('#isCustomMade').val('0');
				if($(this).hasClass('pattern')){
					word=$(this).next('p').text();
				}
				if($(this).hasClass('designed')){
					var code=1;
					$('.buycar').hide();
				}
				text+=word+' ';
				obj.push(id);
			})
			obj= obj.join(",");
			valueSelected(obj);
			$('.inforText').text(text);
			$('#format').val(obj);
			if(code==1){
				$('.upload').attr('data-value',obj);
			}
		}
		//选择规格
		$(".valueItem").click(function() {
			$(this).parent().parent().find('.on').removeClass('on');
			$(this).addClass('on');
			$('.buycar').show();
			if($(this).hasClass('designed')){
				var code=1;
				$('.buycar').hide();
				format(code);
			}
			format();
		
		});
	});
	//提交前验证壁纸面积
	var checked=false;
	function check(){
		if(!checked){
			if($('.recom-area')){
				var m=parseInt($('.recom-area').text());
				var m1=parseInt($('.number').val());
				if(m1<m){
					var html='<div class="alertpop" style="display: block;position: fixed;width: 100%;height:100%;top:0;left:0;z-index: 9999;"><div class="popbg" style="    width: 100%;height:100%;position: absolute;top:0;left:0;z-index: 989;background: #000;opacity: 0.5;"></div><div class="alertLayel" style="width:400px;height:200px;padding-top:10px;background:#fff;position:absolute;margin:auto;top:0;left:0;right:0;bottom:0;z-index:9999;"><h3 style="height:30px;position:relative;"><i class="closeicon" style="margin-right:12px;margin-top:0;"></i> </h3><p class="alertContent" style="line-height:30px;font-size:14px;text-align:center;height:100px;">您购买的面积小于所需最小面积，是否确认购买?</p><div class="row tc"><input type="submit" value="确定" class="submitSure" style="height:30px;border:none;width:80px;background:#ff6200;color:#fff;margin-right:20px;"><input type="button" value="取消" class="alertBtn" data-id="1" style="height:30px;border:none;width:80px;background:none;color:#999;"></div></div></div>';
					$("#formBuy").append(html);
					checked=true;
					return false;
				}
			}
		}
	}
//弹窗打开
function alertLayel(e){
	var html='<div class="alertpop" style="display: block;position: fixed;width: 100%;height:100%;top:0;left:0;z-index: 9999;"><div class="popbg" style="    width: 100%;height:100%;position: absolute;top:0;left:0;z-index: 989;background: #000;opacity: 0.5;"></div><div class="alertLayel" style="width:400px;height:200px;padding-top:10px;background:#fff;position:absolute;margin:auto;top:0;left:0;right:0;bottom:0;z-index:9999;"><h3 style="height:30px;position:relative;"><i class="closeicon" style="margin-right:12px;margin-top:0;"></i> </h3><p class="alertContent" style="line-height:30px;font-size:14px;text-align:center;height:100px;">'+e+'</p><div class="row tc"><input type="button" value="确定" class="alertBtn" style="height:30px;border:none;width:80px;background:#ff6200;color:#fff;"></div></div></div>';
	$("body").append(html);
}
//关闭弹窗
    $(document).on('click','.alertBtn',function(){
    	$(this).parent().parent().parent('.alertpop').remove();
    	$('.modal-backdrop').remove();
    });
/* $(".number").change(function(event) {
	var plusContent=$(this).val();
	console.log(plusContent);
}); */
 //关闭计算失败 弹窗
    $(document).on('click', '.tc>input,.closeicon', function() {
        $(".alertpop").fadeOut(200);
      }); 
    $(document).on('click', '.apply_for', function() {
    	var cc=$(this).siblings('.deposit_money').children('.c_content').text();
    	$("#aa_content").text(cc)
    	$(".deposit_layer").fadeIn(400);

    });

    $(".number").change(function() {
    	var plusContent=$(this).val();
    	console.log(plusContent);
    });  
/*新面积 计算*/
    
    $(document).on('change', '#delim_arae,#paper_arae,#paper_h', function(event) {
    	var thiselement=$(this);
        var delimvalue=parseFloat($("#delim_arae").val());//房屋面积
        var papervalue=parseFloat($("#paper_arae").val());//长
        var hw=parseFloat($("#paper_h").val());//高
        var total=papervalue * hw;
        var formatId=$("#formatId").val();
        var isExt=$("#thisExt").val();
        if(delimvalue==''||delimvalue==null){
        	$("#delim_arae").css('border','1px solid #f00');
        	$("#delim_arae").focus();
        	return false;
        }else{
        	$("#delim_arae").css('border','1px solid #dedede');
        }
        if(papervalue==null||papervalue==''){
        	$("#paper_arae").css('border','1px solid #f00');
        	$("#paper_arae").focus();
        	return false;
        }else{
        	$("#paper_arae").css('border','1px solid #dedede');
        }
        if(hw =='' || hw == null){
        	$("#paper_h").css('border','1px solid #f00');
        	$("#paper_h").focus();
        	return false;
        }else{
        	$("#paper_h").css('border','1px solid #dedede');
        }
        if(isExt == 1){
	        if (delimvalue != null && papervalue != null && delimvalue!='' && papervalue!='' && hw != '' && hw !=null) {
	        	//$("#paper_arae").css('border','1px solid #dedede');
	        	$.ajax({
					type: "POST",
					url:"/u/product/tool/calculate.html",
					data: {formatId:formatId,groundArea:delimvalue,length:papervalue,height:hw},
					cache: false,
					async:false,
					dataType:"json",
					success: function(data) {
						$(".down_t").text('￥'+parseFloat(data.result.price).toFixed(2));
						var value=data.result.materials;
						var constent='';
							for(var x in value){
								constent+='<div class="down_content down_bottom">';
								constent+='<span class="down_taglist  _width2">'+value[x].materialName+'</span>';
							    constent+='<span class="down_taglist _width2">'+value[x].materialModel+'</span>';
							    constent+='<span class="down_taglist _width2">'+value[x].serialNumber+'</span>';
							    constent+='<span class="down_taglist _width2">'+(value[x].num).toFixed(2)+'</span>';
							    constent+="</div>";
						}
							$("#down_content").html(constent);
					},
					error: function () {
						var errhtml='<div class="alertpop" style="display: block;position: fixed;width: 100%;height:100%;top:0;left:0;z-index: 9999;"><div class="popbg" style="    width: 100%;height:100%;position: absolute;top:0;left:0;z-index: 989;background: #000;opacity: 0.5;"></div><div class="alertLayel" style="width:400px;height:200px;padding-top:10px;background:#fff;position:absolute;margin:auto;top:0;left:0;right:0;bottom:0;z-index:9999;"><h3 style="height:30px;position:relative;"><i class="closeicon" style="margin-right:12px;margin-top:0;"></i> </h3><p class="alertContent" style="line-height:30px;font-size:14px;text-align:center;height:100px;">计算失败</p><div class="row tc"><input type="submit" value="确定" class="submitSure" style="height:30px;border:none;width:80px;background:#ff6200;color:#fff;margin-right:20px;"></div></div></div>';
						$("body").append(errhtml);
					},
				});
	        }
        }
    });

function keyup(obj,that){
	if(!(/^\d+\.?\d$|^\d+$/).test(obj)){
		that.val(Math.round(obj*10)/10);
		if(that.val()=='NaN' || parseFloat(that.val())<=0){
			that.val(1);
		}
		alertLayel("请正确输入数量，小数点后只能为1位");
	}
}