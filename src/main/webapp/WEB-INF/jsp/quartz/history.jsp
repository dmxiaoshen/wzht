<%@ page pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="/WEB-INF/jsp/common/html-jqGrid.jsp"%>
<script type="text/javascript">
var colNames=["描述",
              "Cron表达式",
              "开始时间",
              "结束时间",
              "下次执行时间",
              "状态",
              "备注"];
var jobName="${jobName}";
</script>
<script type="text/javascript" src="${ctx}/static/js/quartz/history.js"></script>

</head>
<body>
<div style="width: 760px">
	<div class="color_title">
		<%-- <label>${jobName }</label> --%>
	</div>
	<table id="gridTable" class="jqGridTable"></table>
	<div id="paper"></div>
</div>
</body>
</html>
