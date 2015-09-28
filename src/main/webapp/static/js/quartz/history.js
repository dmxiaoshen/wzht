/**
 * added by tong
 */
(function($) {
	$(function() {
		jQuery("#gridTable").jqGrid({
			url : ctx+'/system/quartz/hisQuery',
			datatype: "json",
			mtype : 'post',
			rowNum:10,
			pager: '#paper',
			height : "100%",
			sortname: 'startTime',
			sortorder : 'desc',
			autowidth : true,
			altRows:true,//隔行变色
			altclass:'ui-widget-content-altclass',//隔行变色样式
			rownumbers : true,
			viewrecords : true,
			jsonReader : {	//default
				root : "result",	//表格需要的数据从哪里开始
				page : "page",	//当前第几页
				total : "total",	//总页数
				records : "record",	//查询出的记录数
				repeatitems : false	//default is true
			},
			postData : {
				"jobName" : jobName,
			},
			colNames : colNames,
			colModel:[{name:'description',index:'description'}, 
			          {name:'cronExpression',index:'cronExpression'}, 
			          {name:'startTime',index:'startTime'}, 
			          {name:'endTime',index:'endTime'}, 
			          {name:'nextTime',index:'nextTime'}, 
			          {name:'status.name',index:'status'}, 
			          {name:'jobComment',index:'jobComment'}]
		});
	});//document ready end
})(jQuery);
