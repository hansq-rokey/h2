$(document).on('click','.buy',function(){
	$('.addpop').show();
});
$(function() {
		$(".plus").click(function() {//商品增加
			var stock = $(".name").attr("data-num");//商品库存
			var num=parseFloat($('.number').val());
			if(num < stock){
				num+=1;
				$('.number').val(num.toFixed(1));//商品数量
				$('.numberText').text(num);//左下角商品数量
				$("#Save_arae").val(num);//存储值
			}else{
	        	$(this).parent().find('.fomcat-remark').show();
	        	$(".buycar").hide();
			}
			calculatePrice();
		});
        var perprice='';   //单价
		var total="";     //库存
		$(".minus").click(function() {//商品减少
			var num=parseFloat($('.number').val());
			if(num>1){
				num-=1;
				$('.number').val(num.toFixed(1));
				$('.numberText').text(num.toFixed(1));
				$("#Save_arae").val(num); 
			}
			if(num<total+1){
	        	$(this).parent().find('.fomcat-remark').hide();
	        	$('.submitLinkLayel').removeClass('submitLinkLayel');
				$('.buycar').removeClass('buycarLayel');
			}
//			price();//本地计算价格
			calculatePrice();
		});
	    function numeric(vlength,vwidth){	/*实时计算地面面积*/
	      this.Vwidth=vlength;
	      this.Vlength=vwidth;
	      function Ground_arae(){
	    	  $("#Ground_arae").val(vlength*vwidth);
	      }
	      return Ground_arae();
	    }
	    
	    $(document).on('blur', '.sizes', function(event) {//发热墙纸 计算价格
	    	if($(this).hasClass("length")){
	    		 var thislength=parseFloat($(this).val());
	    	     var thiswidth=parseFloat($(".width").val());
	    	     numeric(Math.abs(thislength),Math.abs(thiswidth));
	    	} else if($(this).hasClass("width")){
	    		  var thislength=parseFloat($(this).val());
	    	      var thiswidth=parseFloat($(".length ").val());
	    	      numeric(Math.abs(thislength),Math.abs(thiswidth));
	    	      //calculatePrice();
	    	}else if($(this).hasClass("height")){
	    		calculatePrice();
	    	}
	    });
	    $(document).on('click', '#paper-close', function(event) {
			  paperClose();
			  calculatePrice();  
		  });
		   function price(){//本地价格计算
				var num=parseFloat($('.number').val());
				prices=perprice*num;
				//$('.price').text('￥'+prices);
				$('.buyprice').text('￥'+prices.toFixed(2));
			}
			function calculatePrice(){//墙面 面积计算
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
					wall = parseFloat(wall).toFixed(1);
					var formatId=$("#formatId").val();
					if(!parseFloat(wn)||!parseFloat(hn)||!parseFloat(ln)){
						alertLayel("请输入长宽高再进行计算");
						$(".height").blur();
						return false;
					}
					$.ajax({
						type: "POST",
						url:"/u/product/calculate.html",
						data: {formatId:formatId,groundArea:ground,area:wall},
						cache: false,
						async:false,
						dataType:"json",
						success: function(data) {
							//console.log(data.result);
							$(".buyprice").text('￥'+parseFloat(data.result).toFixed(2));
						},
						error: function () {
							alertLayel("计算失败");
						},
					});
				 }else{
					 price();				 
				 }
			}
		$('.number').bind('input propertychange', function() {//propertychange 文本框改变事件文本 手动输入商品件数
			var num=$(this).val();
			var tvalue=num.split(".").length;
			if(tvalue <= 2){
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
			}else{
				alertLayel("数量有误请重新输入！");
				$(this).val(" ");
				$(this).blur();
			}
		});
		$('.number').on('blur', function(event) {// 默认商品件数为1
			var num=$(this).val();
			if(num == ''){
				$('.number').val("1");
			}
		});
		// 文本框只能输入数字
		var $numTxtBox = $('input[name="num"]');// 锁键状态
		$numTxtBox.keydown(function(e){// 绑定事件
			var oEvent = e || window.event;// 兼容处理
			if(!(oEvent.keyCode==8)&&!(oEvent.keyCode==9)&&!(oEvent.keyCode==37)&&!(oEvent.keyCode==39) && !(oEvent.keyCode==110) && !(oEvent.keyCode==190))// 键值
			if(!((oEvent.keyCode>=48&&oEvent.keyCode<=57)||(oEvent.keyCode>=96&&oEvent.keyCode<=105))) // 键值
				return false;
			//oEvent.returnValue = false; // firefox 兼容问题
		});
		
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
					var num=parseFloat($('.number').val());
					prices=perprice*num;
					/*$(".left").html('<img src="'+imghref+'" >');//图片插入
*/					$("#thisExt").val(data.result.isExt);
					$("#formatId").val(data.result.id);
					if(total<num){
						$('.submitLink').addClass('submitLinkLayel');
						$('.fomcat-remark').show();
					}else{
						$('.submitLinkLayel').removeClass('submitLinkLayel');
						$('.fomcat-remark').hide();
					}
					if(data.result.isExt==1){
						var arr=new Array();
						arr.push('<div class="extPro">');
						arr.push('<p class="fomcat-name isext">房屋尺寸<span>(单位:米  备注：请按照实际尺寸输入，错误的尺寸会导致物料不准确。)</span></p>');
						arr.push('<div class="change isext">');
						$.each(data.result.exts,function(i,item){
							if(item.type==1){
								arr.push('<span>'+item.propertyName+'</span>');
								arr.push('<input type="text" name="'+item.identify +'" data-txt="'+item.identify +'" class="'+item.identify +' sizes" onchange="keyup(this.value,$(this))" value="0"/>');
							}else if(item.type==4){
								arr.push('<span class="icon_house">面积计算</span>');
							}
						});
						arr.push('</div></div>');
						$('.extPro').each(function(){
							$(this).remove();
						});
						$('#delimiter').after(arr.join(''));
						$('#numExplain').html(data.result.explain+'<span>（推荐不低于<i class="recom-area">0</i>'+data.result.unit +'）</span>');
						$('.unit').html(data.result.unit);
						$('.icon_house').on('click',function(){houseClick();});
					}else{
						$('#numExplain').html('数量<span>（单位：'+(data.result.unit==null?"":data.result.unit) +'）</span>');
						$('.unit').html(data.result.unit);
						$('.extPro').each(function(){
							$(this).remove();
						});
					}
					$('#picWidth').val(data.result.width);
					$('#picLength').val(data.result.length);
					$.each(data.result.pics,function(i,item){
						//console.log('picurl='+item);
					});
					$('.bigphone,.previewBg').attr('src',data.result.pics[0]);//产品预览图
					$('.price').text('￥'+perprice.toFixed(2));//商品单价
					$('.buyprice').text('￥'+prices.toFixed(2));//商品总价格
					$('.name').attr('data-num',data.result.stock);//存储商品库存
					if(data.result.stock == 0){
						$('.submitLink').addClass('submitLinkLayel');
						$(".number").prop("readonly","readonly");
						$(".buy").css("display","none");
						$(".submitLink").css("backgroundColor","#999");
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
			var flag=true;
			var thisvalue=$(this).attr("data-id");//获取当前存储Id
			$(this).parent().parent().find('.on').removeClass('on');
			$(this).addClass('on');//给当前选中 添加样式
			$('.buycar').show();//显示加入购物车
			if($(this).hasClass('designed')){
				var code=1;
				$('.buycar').hide();
				format(code);
			}
			$(".number").val('1');
			format();
			price();
			var combin=$(".inforText").text().trim();
			var arr_hidden = [];
			//获取不显示的规格Id组合
			var hidenId = $("input[name='f_name']");
			$.each(hidenId,function(item){
				arr_hidden.push(hidenId[item].value);
			});
			$("body").find(".selector_format").removeClass("selector_format");
			$.each(arr_hidden,function(item,i){
				if(arr_hidden[item]==combin){
					flag=false;
				}
			});
			if(!flag){
				$(this).addClass("selector_format");
				$('.submitLink').addClass('submitLinkLayel');
				$(".number").prop("readonly","readonly");
				$(".buy").css("display","none");
				$(".submitLink").css("backgroundColor","#999");
			}else{
				$(".number").prop("readonly",false);
				$(".buy").css("display","block");
				$(".submitLink").css("backgroundColor","#ff6200");
			}
		});

		$(document).on("scroll", function () {
		 	if ($(window).scrollTop() > 195) {
              $(".left").addClass('dong');
     		}
		 	else{
		 		$(".left").removeClass('dong');
		 	}
		});
		//关闭弹窗
		$('.closeicon,.greyword').on('click',function(){
			if($(this).hasClass(' uploadclose')){
				isChange=false;
				$('.file').val('');
				isChange=true;
			}
			$(this).parent().parent().parent().hide();
		});
		//私人订制弹窗
		$(document).on('click','.designed',function(){
	    	$('.uploadpop').show();
	    	$('#isCustomMade').val('1');
   		 });
		//重新上传
		$('.re-upload').on('click',function(){
			$('.successpop').hide();
			$('.modal-backdrop').remove();
			$('.uploadpop').show();
			isChange=false;
			$('.file').val('');
			isChange=true;
		})
		//图片上传
		var uploadPicId;
		var isChange=true;
		$(document).on('change','.file',function(){
			if(isChange!=true){
				return false;
			}
			var typebtn=$(this);
			var imgPath = $(this).val();
			var formatId=$(this).attr('data-value');
			//判断是否有选择上传文件
			if (imgPath == "") {
				alertLayel("请选择上传图片！");
				return;
			}
			//判断上传文件的后缀名
			var strExtension = imgPath.substr(imgPath.lastIndexOf('.') + 1);
			if (strExtension != 'jpg' && strExtension != 'gif'
					&& strExtension != 'png' && strExtension != 'bmp'&& strExtension != 'JPG'&& strExtension != 'PNG') {
				alertLayel("请选择图片文件");
				return;
			}
			//创建FormData对象
            var data = new FormData();
            //为FormData对象添加数据
            //
            $.each($(this)[0].files, function(i, file) {
                   	data.append('file', file);
                  });
			var bar = $('.bar');
			var percent = $('.percent');
			//var showimg = $('#showimg');
			var progress = $(".progress");
			$('.uploading-animate').show();
			$('.modal-open').css('paddingRight','0');
			$('.uploadclose').hide();
			$.ajax({
				type: "POST",
				url: "/u/order/upload.html?format="+formatId,
				data: data,
				//data: {data:data,format:formatId},
				cache: false,
				contentType: false,    //不可缺
				processData: false,    //不可缺
				dataType:"json",
				success: function(data) {
					if(!data.success){
						$('.uploading-animate').hide();
						$('.uploadclose').show();
						alertLayel(data.message);
					}else{
						$('.uploading-animate').hide();
						$('.uploadclose').show();
						$('.modal-dialog').show();
					      $('.modal-content').show();
						$('.successpop').show();
						$('.uploadpop').hide();
						uploadPicId=data.result.id;
						$('#picId').val(uploadPicId);
					    var w=$('#picWidth').val(),
					    	h=$('#picLength').val(),
					    	pw=$('.previewBg').width();
					    	hw=$('.previewBg').height();
					    $('.previewBg').css('height',pw*h/w);/*245*/
					    $('.customPic').css({width:$('.bigphone').width()-2,height:$('.bigphone').height()-2});
				        $('.avatar-preview').css({'width':'240px','height':'500px'});    
					}
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					if(XMLHttpRequest.status==0){
						$('.uploading-animate').hide();
						$('.uploadclose').show();
						alertLayel("请登录后重试");
						isChange=false;
						$('.file').val('');
						isChange=true;
					}else{
						$('.uploading-animate').hide();
						$('.uploadclose').show();
						isChange=false;
						$('.file').val('');
						isChange=true;
						alertLayel("上传失败，请检查网络后重试");
					}
				}
			});
		});
		$('.close').on('click',function(){
			$('.successpop').hide();
		});
		$(document).on('click','.alertBtn',function(){
			isChange=false;
			$('.file').val('');
			isChange=true;
		})
		//提交图片
		$('.upload-sure').on('click',function(){
			$('.uploading-animate2').show();
			isChange=false;
			var x=$('#x').val();
			var y=$('#y').val();
			var width=$('#width').val();
			var height=$('#height').val();
			var degree=$('#degree').val();
			$.ajax({
				type: "POST",
				url: "/u/order/cut.html?id="+uploadPicId+"&x="+x+"&y="+y+"&width="+width+"&height="+height+"&degree="+degree,
				data: data,
				cache: false,
				contentType: false,    //不可缺
				processData: false,    //不可缺
				dataType:"json",
				success: function(data) {
					if(data.success){
						$('.uploading-animate2').hide();
						$('.customPic').attr('src',data.result.url).show();
						$('.modal-backdrop').remove();
						$('.file').val('');
						isChange=true;
					    $('.successpop').hide();
					    $("#mallCustomPicId").val(data.result.id);
					}else{
						alertLayel(data.message);
						$('.uploading-animate2').hide();
					}
					$('body').removeClass('modal-open');
					
				}
			});
		})
		//提交数据
		$('.submitLink').click(function(){
			if(!$(this).hasClass('submitLinkLayel')){
			$("#formBuy").submit();
			}
		});
		//确认私人订制
		$('.sureUpload').on('click',function(){
			$('.uploadpop').hide();
			$('#picId').val($('#picId_').val());
		})      
		//加入购物车
		$(".buycar").on('click',function(){
			if(!$(this).hasClass('buycarLayel')){
				var num=$(".number").val();
				var formatId=$("#format").val();
				var productId=$("#productId").val();
				var tag=$("#area").val();
				var length,
					width,
					height;
				if($('.length')){
					length=$('.length').val();
					width=$('.width').val();
					height=$('.height').val();
				}
				//response.setHeader("Access-Control-Allow-Origin", "*")
				$.ajax({
			        url: "/u/car/add.html",
			        type: 'post', 
			        data:{ "format": formatId,"num":num,"product.id":productId,"length":length,"width":width,"height":height,"tag":tag},
			        dataType: 'json',//here
			        success: function (data) {
			        	alertLayel(data.message);
			        },
					error: function(XMLHttpRequest) {
						if(XMLHttpRequest.readyState==0&&XMLHttpRequest.responseText==""){
							alertLayel("请登录后重试");
			        	}
					}
			    });	
			}
		});
		//壁纸
		$('.pic-left,.pic-right').mousemove(function(){
			if($(this).hasClass('pic-left')){
				$('.pic-house-left,.area-left').show();
			}
			if($(this).hasClass('pic-right')){
				$('.pic-house-right,.area-right').show();
			}
		}).mouseleave(function(){
			if($(this).hasClass('pic-left')&&!$(this).next('img').hasClass('showimg')){
				$('.pic-house-left,.area-left').hide();
			}
			if($(this).hasClass('pic-right')&&!$(this).next('img').hasClass('showimg')){
				$('.pic-house-right,.area-right').hide();
			}
		}).on('click',function(){
			$('.showimg').removeClass('showimg').hide().next('span').hide();
			$(this).next('img').addClass('showimg');
		})
		//壁纸尺寸修改
		$('.sizes').bind('input propertychange', function() {
			var length=parseFloat($('.length').val());
			var weigth=parseFloat($('.width').val());
			var height=parseFloat($('.height').val());
			var m1=length*height;
			var m2=weigth*height;
			var m=(m1<m2)?m1:m2;
			$('.recom-area').text(m);
			
		});
		//关闭壁纸弹窗
		$('.paper-close').on('click',function(){
			var picLeft=$(".pic-house-left").hasClass('showimg');
			var picRight=$(".pic-house-right").hasClass('showimg');
			if(picLeft || picRight){//判断墙面是否选中  未选中不关闭弹窗
				$('.paperpop').hide();
				var wnum=parseInt($(".wide-num").text());
				var lnum=parseInt($(".long-num").text());
				var aral=wnum*lnum;//地面面积
				if($('.showimg')){
					var m=$('.showimg').next('span').find('i').text();
					$('.recom-area').text(m);
					$(".number").val(m);
					$("#Save_arae").val(m);
					$("#Ground_arae").val(aral);
					calculatePrice();
				}
			}else{
				//alert("墙面未选中");
			}
		
		})
		//打开壁纸弹窗
		$('.icon_house').on('click',function(){
			houseClick();
		})
		
	});
function houseClick(){
    var length=parseFloat($('.length').val());//长
	var weigth=parseFloat($('.width').val());//宽
	var height=parseFloat($('.height').val());//高
	var bool=$("#wallbox > div").hasClass("showon");
	if(!length||length==0||!weigth||weigth==0||!height||height==0){
		alertLayel('长宽高不能为空或0');
		$(".height").blur();
		return false;
	}
		var m1=length*height;
	    var m2=weigth*height;
	$('.wallback').attr('data-value',m1);
	$('.walltop').attr('data-value',m1);
	$('.wallleft').attr('data-value',m2);
	$('.wallright').attr('data-value',m2);
	$('.wallfront ').attr('data-value',m1);
	$('.wallbox_width').text(length+'M');
	$('.wallbox_long').text(weigth+'M');
	$('.wallbox_height').text(height+'M');
	$('.paperpop').show();
	if(!bool){
		$(".paper-close").attr('disabled',true);
		$(".paper-close").css("background",'#ccc');
	}else{
		$(".paper-close").attr('disabled',false);
	}
} 
	//提交前验证壁纸面积
	var checked=false;
	function check(){
		if(!checked){
			if($('.recom-area')){
				var m=parseInt($('.recom-area').text());
				var m1=parseInt($('.number').val());
				if(m1<m){
					alertLayel("您购买的面积小于所需最小面积，是否确认购买?");
					checked=true;
					return false;
				}
			}
		}
	}

function alertLayel(e){//弹窗打开
	var html='<div class="alertpop" style="display: block;position: fixed;width: 100%;height:100%;top:0;left:0;z-index: 9999;"><div class="popbg" style="    width: 100%;height:100%;position: absolute;top:0;left:0;z-index: 989;background: #000;opacity: 0.5;"></div><div class="alertLayel" style="width:400px;height:200px;padding-top:10px;background:#fff;position:absolute;margin:auto;top:0;left:0;right:0;bottom:0;z-index:9999;"><h3 style="height:30px;position:relative;"><i class="closeicon" style="margin-right:12px;margin-top:0;"></i> </h3><p class="alertContent" style="line-height:30px;font-size:14px;text-align:center;height:100px;">'+e+'</p><div class="row tc"><input type="button" value="确定" class="alertBtn" style="height:30px;border:none;width:80px;background:#ff6200;color:#fff;"></div></div></div>';
	$("body").append(html);
}
    $(document).on('click','.alertBtn',function(){//关闭弹窗
    	$(this).parent().parent().parent('.alertpop').remove();
    	$('.modal-backdrop').remove();
    });
    $(document).on('click', '.tc>input,.closeicon', function() { //关闭计算失败 弹窗
        $(".alertpop").fadeOut(200);
      }); 
    $(document).on('change', '.length,.width,.height', function() {
    	   var thiscontent=$(this).val();
    	   if(thiscontent="1") {
    	     $(this).css('border', '1px solid #dcdcdc');
    	   }
    	 });
    $(document).on('click', '.list_top>.d_box', function() {  //选择当前场景
    		var col=$(this).children('a').data("i");
    	    $(this).children('a').children('i').removeClass('icon-yuanshixin').addClass('icon-duihao');
    		$(this).children('a').children('span,i,p').css('color', '#ff6200'); 
    		$(this).siblings().children('a').children('i').removeClass('icon-duihao').addClass('icon-yuanshixin');
    		$(this).siblings().children('a').children('span,i,p').css('color', '#9EACBB');
    		$("#area").val(col);
    	});	

    
    $(document).on('click', '.subundo', function() {//关闭弹窗
    	$(".deposit_layer").fadeOut(400);
    });
    $(document).on('click', '.apply_for', function() {
    	var cc=$(this).siblings('.deposit_money').children('.c_content').text();
    	$("#aa_content").text(cc)
    	$(".deposit_layer").fadeIn(400);
    });
	function paperClose(){//关闭壁纸修改
		$('.paperpop').hide();
		var length=parseFloat($('.length').val());
		var weigth=parseFloat($('.width').val());
		var height=parseFloat($('.height').val());
		var m1=length*height;
		var m2=weigth*height;
		var m=(m1<m2)?m1:m2;
		m= m+'';
		var len=m.split('.')[1];
		if(len==null || len == ""||len == undefined){
			$('.recom-area').text(m);
		}
		if(len>=5){
			$('.recom-area').text(parseFloat(m).toFixed(1));
		}
		if(len<5){
			$('.recom-area').text(parseFloat(m).toFixed(1)+0.1);
		}
		$('.wallback').attr('data-value','');
		$('.walltop').attr('data-value','');
		$('.wallleft').attr('data-value','');
		$('.wallright').attr('data-value','');
		$('.wallfront ').attr('data-value','');
		$(".wallnumber ").css("display","none");
		$(".wallbox").children('div').removeClass("showon");
	}
$(document).on('click', '.walltop,.wallback,.wallright,.wallleft', function(event) { /*发热墙纸墙面选择*/
  	var $this=$(this);
  	//console.log($this.data('value'));
  	var  total=0;
  	var aa=$(this);
  	if($this.hasClass('walltop')){
    		$(".wallfront").toggleClass('showon')
  		}else{
  			$(this).toggleClass('showon');
  		}
  	var _showonArr=$(".showon");
  	if(_showonArr.length==0){
  		$(".wallnumber2").show();
	    	$("#total_money").text(0);
	    	$(".paper-close").attr('disabled',true);
	    	$(".paper-close").css("background",'#ccc');
  	}else{
  	    $(".showon").each(function(index,item){
  	    	total+=parseFloat($(this).attr('data-value'));
  	    });
  	    $(".wallnumber2").show();                                                                                   
  	    $("#total_money").text(total.toFixed(2));
  	    $("#Save_arae").val(total);//存储墙面面积
  	    $(".number").val(total.toFixed(1));
  	    $(".paper-close").attr('disabled',false);
    	$(".paper-close").css("background",'#ff6200');
  	}
  });
function keyup(obj,that){//验证只能输入一位小数点
	if(!(/^\d+\.?\d$|^\d+$/).test(obj)){
		that.val(Math.round(obj*10)/10);
		if(that.val()=='NaN' || parseFloat(that.val())<=0){
			that.val(1);
		}
		alertLayel("请正确输入数量，小数点后只能为1位");
	}
}