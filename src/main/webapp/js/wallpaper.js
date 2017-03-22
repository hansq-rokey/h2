/**
 * Created by Administrator on 2016/4/6.
 */
$(function(){
	Switch=0;
	//问题反馈
    $('.feedback').on('click',function(){
        $('.pop').show();
    })
    $('.closeicon').on('click',function(){
        $(this).parent().parent().parent().hide();
    });
    //弹窗
    $(document).on('click','.alertBtn,.closeicon',function(){
    	$('.alertpop').remove();
    });
    var height=$(window).height();
    $('.pages').css('height',height);
    var passing=false;
    var page=-1;
    var paging=0;
    on=false;
    $(document).on("mousewheel", function (event, delta) {
        //向下滚动
        //console.log(page+'aa'+paging+'b'+$('.container').is(":animated")+'12'+passing)
        if(!$('.scroll-wrapper').find('div').is(":animated")) {
            /*console.log(event,delta);*/
        	  scoll(event, delta);
        	console.log(page+'aaa'+paging);
            if(page!=5&&page!=-1){
                $('.icon-circle-thin').removeClass('icon-circle-thin');
                $('.icon-circle').eq(page).addClass('icon-circle-thin');
            }
        }
    });
    $('.icon-circle').on('click',function(){
        if(!$('.scroll-wrapper').find('div').is(":animated")) {
            var num = $(this).attr('data-num');
            $('.icon-circle-thin').removeClass('icon-circle-thin');
            $(this).addClass('icon-circle-thin');
            page = num - 1;
            $('.container').animate({top: -(num - 1) * height-70}, 2000);
            setTimeout(function () {
            if (page == 3) {
                $('.image41').animate({width: '738px'}, 2000);
                setTimeout(function () {
                    $('.image43').animate({width: '738px'}, 2000);
                }, 600);
                setTimeout(function () {
                    $('.image45').animate({width: '738px'}, 2000);
                    $('.image42,.image44,.image46').fadeIn(1200);
                }, 1200);
            }
            }, 2000);
        }
    });
    function scoll(event, delta){
            if (delta < 0) {
            	if(page==-1){
            		$('.container').animate({top: '-70px'},500);
            		$('.bullet').fadeIn(500);
            	}
                if (page == 0) {
                    if (paging == 0) {
                        on=true;
                       /* setTimeout(function () {
                            $('.image11').css('transform', 'rotate(25deg)');
                        }, 1500);
                        $('.bg2').fadeIn(1200);
                        setTimeout(function () {
                            $('.image13').animate({opacity:'1'},1000);
                        }, 600);
                        setTimeout(function () {
                            $('.content02').fadeIn(1200);
                        }, 2200);*/
                        $(".image11").css('transform','rotate(25deg)').fadeIn(150);
                        $(".bg2").fadeIn(120);
                        $('.image13').animate({opacity:'1'},1000).fadeIn(160);
                        $('.content02').animate({opacity:'1'},1200).fadeIn(180);
                    }
                    if (paging == 1) {
                    	 on=true;
                        $('.image13,.image14,.image15').fadeIn(600).css('transform', 'perspective(1400px) rotateY(45deg)');
                        $('.line-box').fadeIn(400);
                        $('.image14').css('marginLeft', '-746px');
                        $('.image15').css('marginLeft', '-826px');
                        $('.bg1,.content02').fadeOut(200);
                        $('.bg3').animate({opacity:'1'},2000).css({width:'105%',height:'105%'});
                        $('.content03').fadeIn(400);
                    }
                    if (paging == 2) { 
                    	 on=true;
                        $('.image11').animate({transform: 'rotate(180deg)'},2000);
                        $('.image13').animate({marginLeft: '-586px'},400).fadeOut(400);
                        $('.box1').animate({marginLeft: '-536px'},400).fadeOut(400);
                        $('.image14').animate({marginLeft: '-666px'},400);
                        $('.image15').animate({marginLeft: '-746px'},400);
                        $('.box2').animate({marginLeft: '-616px'},400);
                        setTimeout(function () {
                            $('.box1').removeClass('line-box-on');
                            $('.box2').addClass('line-box-on');
                        }, 400);
                        $('.box3').animate({marginLeft: '-696px'},400);
                        $('.content03').fadeOut(400);
                        $('.content04').fadeIn(400);
                    }
                    if (paging == 3) {
                        on=true;
                        $('.image14').animate({marginLeft: '-586px'},400).fadeOut(400);
                        $('.box2').animate({marginLeft: '-536px'},400).fadeOut(400);
                        $('.image15').animate({marginLeft: '-666px'},400);
                        $('.box3').animate({marginLeft: '-616px'},400);
                        setTimeout(function () {
                            $('.box2').removeClass('line-box-on');
                            $('.box3').addClass('line-box-on');
                        }, 400);
                        $('.content04').fadeOut(400);
                        $('.content05').fadeIn(1100);
                    }
                    if (paging == 4) {
                        on=true;
                        $('.image15').fadeOut(400).animate({marginLeft: '-586px'},400);
                        $('.box3').css('marginLeft', '-536px').fadeOut(400);
                        $('.content05,.bg3,.image11').fadeOut(1000);
                        $('.bg4,.content06,.whiteBg').fadeIn(800);
                        setTimeout(function () {
                            $('.bg4').animate({left: '-4%', top: '-4%'}, 2000);
                        }, 400);
                    }
                    if (paging == 5) {
                        passing = true;
                        $('.container').animate({top: -1 * height-70},1500);
                    }
                    paging += 1;
                    if (paging > 5) {
                        paging = 5;
                    }
                }
                if (page == 1) {
                    $('.container').animate({top: -2 * height-70},1500);
                }
                if (page == 2) {
                    $('.container').animate({top: -3* height-70},1500);
                    $('.image41').animate({width: '738px'}, 2000);
                    setTimeout(function () {
                        $('.image43').animate({width: '738px'}, 2000);
                    }, 600);
                    setTimeout(function () {
                        $('.image45').animate({width: '738px'}, 2000);
                        $('.image42,.image44,.image46').fadeIn(2000);
                    }, 1200);
                }
                if (page == 3) {
                    $('.container').animate({top: -4 * height-70},1500);
                }
                if (page == 4) {
                    $('.container').animate({top: -4 * height-70-352},1500);
            		$('.bullet').fadeOut(500);
                }
                if (page!=0) {
                    page = page + 1;
                }
                if (passing&&page==0) {
                    page = page + 1;
                }
                if (page > 5) {
                    page = 5;
                }
            }
            //向上滚动
            if (delta > 0) {
                if (page == 0) {
                	if(paging==0){
                		$('.container').animate({top: '0px'},500);
                		$('.bullet').fadeOut(500);
                        page = page - 1;
                	}
                    if (paging == 1) {
                       /* setTimeout(function () {
                            $('.content02,.bg2').fadeOut(800);
                        }, 1000);*/
                    	$('.content02,.bg2').fadeOut(800);
                        $('.image11').css('transform', 'rotate(0deg)');
                        $('.image13').animate({opacity:'0'},600);
                    }
                    if (paging == 2) {
                        $('.image13').css('transform', 'perspective(1400px) rotateY(0deg)');
                        $('.image14,.image15').fadeOut(600).css({
                            transform: 'perspective(1400px) rotateY(0deg)',
                            marginLeft: '-666px'
                        });
                        $('.line-box').fadeOut(400);
                        $('.bg1,.content02').fadeIn(400);
                        $('.bg3').animate({width:'100%',height:'100%',opacity:'0'},600);
                        $('.content03').fadeOut(200);
                    }
                    if (paging == 3) {
                        $('.image13').css('marginLeft', '-666px').fadeIn(400);
                        $('.box1').css('marginLeft', '-616px').fadeIn(400);
                       /* setTimeout(function () {*/
                            $('.box1').addClass('line-box-on');
                            $('.box2').removeClass('line-box-on');
                        /*}, 400);*/
                        $('.image14').css('marginLeft', '-746px');
                        $('.image15').css('marginLeft', '-826px');
                        $('.box2').css('marginLeft', '-696px');
                        $('.box3').css('marginLeft', '-776px');
                        $('.content03').fadeIn(400);
                        $('.content04').fadeOut(200);
                    }
                    if (paging == 4) {
                        $('.image14').css('marginLeft', '-666px').fadeIn(400);
                        $('.box2').css('marginLeft', '-616px').fadeIn(400);
                       /* setTimeout(function () {*/
                            $('.box2').addClass('line-box-on');
                            $('.box3').removeClass('line-box-on');
                       /* }, 400);*/
                        $('.image15').css('marginLeft', '-746px');
                        $('.box3').css('marginLeft', '-696px');
                        $('.content04').fadeIn(400);
                        $('.content05').fadeOut(100);
                    }
                    if (paging == 5) {
                        $('.bg4,.content06,.whiteBg').fadeOut(800);
                        $('.bg4').animate({left: '0%', top: '0%'}, 1000);
                        $('.image15').show().css('marginLeft', '-666px');
                        $('.box3').css('marginLeft', '-616px').fadeIn(400);
                        $('.content05,.bg3,.image11').show();
                        passing = false;
                    }
                    paging -= 1;
                    if (paging < 0) {
                        paging = 0;
                    }
                }
                if (page == 1) {
                    $('.container').animate({top: '-70px'},1500);
                    setTimeout(function () {
                        $('.bg4').animate({left: '0', top: '0'}, 8000);
                    }, 400);
                    //$('.bg4,.content06').fadeIn(600);
                }
                if (page == 2) {
                    $('.container').animate({top: -1 * height-70},1500);
                }
                if (page == 3) {
                    $('.container').animate({top: -2 * height-70},1500);
                }
                if (page == 4) {
                    $('.container').animate({top: -3 * height-70},1500);
                    $('.container').animate({top: -3* height-70},1500);
                    $('.image41').animate({width: '738px'}, 2000);
                    setTimeout(function () {
                        $('.image43').animate({width: '738px'}, 2000);
                    }, 600);
                    setTimeout(function () {
                        $('.image45').animate({width: '738px'}, 2000);
                        $('.image42,.image44,.image46').fadeIn(2000);
                    }, 1200);
                }
                if (page == 5) {
                    $('.container').animate({top: -4 * height-70},1500);
            		$('.bullet').fadeIn(500);
                }
                if(page!=0){
                    page = page - 1;
                }
                if(page==0&&paging==0){
                    //page = page - 1;
                }
                if (page < -1) {
                    page = -1;
                }
            }
    }
})