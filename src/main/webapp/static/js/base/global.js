// JavaScript Document
/*
*	edit by lotus
*	2014.12.27
*/
(function($) {
	$(function() {
		//右边宽度
		var windowWidth = $(".scrollDiv").width();
		var bodyRgtWd = windowWidth - 242;
		
		//左边高度全屏
		var windowHeight = $(window).height();
		var scrollHeight = windowHeight - 80;
		$(".scrollDiv").css("height",scrollHeight);
		$(".navlist").css("height",scrollHeight - 22);
		$(".body_rgt").css("height",scrollHeight);
		
		//表格的宽度
		//var bodyrgtWd = $(".bodyrgtDiv").width();
		//$(".jqGridTable").setGridWidth(bodyrgtWd - 24);
		
		//左侧展开二级菜单
		$(".li1 ul").hide();
		/*$(".li1 .a1").click(function(){
			var ifshow = $(this).next("ul").css("display");
			if(ifshow == "none"){
				$(this).next("ul").show();
			}else{
				$(this).next("ul").hide();
			}
			
		});*/
		$('.li1').bind('click',function(){
			$('#navlist_ulbox ul').hide();
			$(this).find('ul').show();
		});
		
		$(".li1 .ul2 li.current").parent("ul").show();
		
		//tabPanel切换
		$(".tabPanelTitle ul li").removeClass("current");
		$(".tabPanelCon .con").hide();
		$(".tabPanelTitle ul li").eq(0).addClass("current");
		$(".tabPanelCon .con").eq(0).show();
		$(".tabPanelTitle ul li").click(function(){
			var $this = $(this);
			var index = $this.index();
			$(".tabPanelTitle ul li").removeClass("current");
			$(".tabPanelCon .con").hide();
			$(".tabPanelTitle ul li").eq(index).addClass("current");
			$(".tabPanelCon .con").eq(index).show();
		});
		
		//窗口大小改变
		$(window).resize(function(){
			//右边宽度
			var windowWidth = $(".scrollDiv").width();
			var bodyRgtWd = windowWidth - 242;
			
			//左边高度全屏
			var windowHeight = $(window).height();
			var scrollHeight = windowHeight - 80;
			$(".scrollDiv").css("height",scrollHeight);
			$(".navlist").css("height",scrollHeight - 22);
			$(".body_rgt").css("height",scrollHeight);
			
			//表格的宽度
			var bodyrgtWd = $(".bodyrgtDiv").width();
			jQuery(".jqGridTable").setGridWidth(bodyrgtWd - 24);
		});
	});
})(jQuery);
	