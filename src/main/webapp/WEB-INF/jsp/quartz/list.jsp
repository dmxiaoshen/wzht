<%@ page pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="decorator" content="admin" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="/WEB-INF/jsp/common/html-jqGrid.jsp"%>
<title>定时任务列表</title>
<style type="text/css">
.selDateDialog {height: 120px; width:300px;}
</style>

<script type="text/javascript">
	var colNames = [
	                '触发器名字',
	                '任务名',
	                'cron表达式',
	                '下一个触发时间',
	                '上一个触发时间',
	                '触发状态',
	                '描述',
	                '操作',
	                ''
	                ];
	var jobNameLable = '任务名';
	var ceLable = 'cron表述式';
	var descLable = '描述';
	var commonColon = "：";
	
	var saveTitle = '保存';
	var editTitle = '编辑';
	var execute = '重新执行';
	var pause = '暂停';
	var resume = '恢复';
	var del = '删除';
	
	var succ='操作成功';
	var fail='操作失败';
	
	var refresh = '刷新';
	var selDate = '选择日期';
	var running = '重新执行中...';
	var prompt = '提示';
	var historyTitle = '历史';
	
	//权限设置
	var canAdd =  true ;
	var canUpdate = true ;
	var canExecute = true ;
	var canPause =  true ;
	var canResume =  true ;
	var canQueryHis =  true ;
</script>
<script type="text/javascript" src="${ctx}/static/js/quartz/list.js"></script>
</head>
<body>

	<div class="gridTableWrapDiv" style="margin-top:50px;">
	    <table id="gridTable" class="jqGridTable"></table>
	    <div id="paper"></div>
	     <div id="selDateDialog" class="selDateDialog" style="display: none;">
	    	<input type="text" name="extDate" id="extDate" class="validate[required] Wdate" onfocus="WdatePicker({lang:'zh_cn'})"/><br/>
		    	<form class="answers">
		    		<input class="boxy-btn1" name="confirm" type="button" value="确定" />
		    		<input class="boxy-btn2" name="cancel" type="button" value="取消" />
		    	</form>
	    </div> 
    </div>
   
</body>
</html>