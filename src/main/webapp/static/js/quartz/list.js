(function($){
	$(function(){
		var $date = $("#date");
		$date.val("");
//		$("#add").click(function(){
//			window.location.href = "add";
//		});
		
		function formatLabel(labelVal,isShow){
			return "<label><span class='red'>*</span>"+labelVal+"</label>";
		}
		
		var $gridTable = jQuery("#gridTable");
		$gridTable.jqGrid({
			url : "query",
			datatype: "json",
			mtype : 'post',
			rowNum : 20,
			pager: '#paper',
			pginput : false,
			pgbuttons : false,
			height : "100%",
			autowidth : true,
			altRows:true,//隔行变色
			altclass:'ui-widget-content-altclass',//隔行变色样式
			rownumbers : true,
			viewrecords : true,
			colNames : colNames,
			jsonReader : {
				root : "result",//数据内容
				repeatitems : false	//default is true
			},
			postData : {},
			editurl : "update",         
			ondblClickRow: editTr,
			colModel:[
			         {name:'triggerName',hidden:true},
	                 {name:'jobName',editrules:{required:true},
			        	 formoptions:{label:formatLabel(jobNameLable,true),elmprefix:commonColon},width:200,fixed:true},
	                 {name:'cronExpression',editable:true,editrules:{required:true},
			        		 formoptions:{label:formatLabel(ceLable,true),elmprefix:commonColon},width:100,fixed:true},
	                 {name:'nextFireTime',width:125,fixed:true},
	                 {name:'prevFireTime',width:125,fixed:true},
	                 {name:'triggerState',width:70,fixed:true},
	                 {name:'description',editable:true,editrules:{required:true},
	                	 formoptions:{label:formatLabel(descLable,true),elmprefix:commonColon}},
			         {name:"jobName",align:'center',width:150,fixed:true,formatter:function(cellvalue, options, rowObject ){
			        	  var operate = "";
			        	  if (canExecute) {
			        		  operate += "<a class='rollBack_"+cellvalue+"' href='javascript:void(0);'>"+execute+"</a> ";
			        	  }
		        		  if(rowObject.triggerState=="PAUSED"){
		        			  if (canPause) {
		        				  operate += "<a class='resume_"+cellvalue+"' href='javascript:void(0);'>"+resume+"</a> ";	 
					          }
		        		  }else{
		        			  if (canResume) {
		        				  operate += "<a class='pause_"+cellvalue+"' href='javascript:void(0);'>"+pause+"</a> ";  
		        			  }
		        		  }
		        		  if (canQueryHis) {
		        			  operate += "<a id='his_"+cellvalue+"' href='javascript:void(0);' >"+historyTitle+"</a> ";  
		        		  }
		        		  operate += "<a class='del_"+cellvalue+"' href='javascript:void(0);' >"+del+"</a>";  
		        		  return operate;
			          }},
			          {name:"jobName",align:'center',width:40,fixed:true,formatter:function(cellvalue, options, rowObject ){
			        	  	  var save = "";
			        	  	  if (canUpdate) {
			        	  		  save = "<a class='editBt_a tableIcon' href='javascript:void(0);' id='"+cellvalue+"' title='"+save+"'></a>";
			        	  	  }
			        		  return save;
			          }}
			         ],
					gridComplete : function(){
						//start lotus 合并最后两列的表头
						var $lastth = $(".ui-jqgrid-htable tr th:last");
						var lastthWd = $lastth.outerWidth();
						var plastthWd = $lastth.prev("th").outerWidth();
						if($lastth.attr("colspan")==2){
							return;
						}else{
							$lastth.prev("th").attr("colspan","2");
							$lastth.prev("th").css("width",lastthWd+plastthWd);
							$lastth.remove();
						}
						//end

						$("#gridTable").setGridWidth($(".gridDivWrap").innerWidth());
					}
		});
		var navParams = {
				edit:false,
				add:canAdd,
				del:false,
				view:false,
				search:false,
				refreshtext:refresh,
				addfunc : function(){
					var nnn = $gridTable.getGridParam("colModel");
					nnn[2].editable = true;
					var addParams = {
							url : "add",
							afterSubmit : function(response, postdata)
							{
								//in my case response text form server is "{sc:true,msg:''}" create js object from server reponse
								var json=eval("("+response.responseText+")"); 
								if(json.respFlag==false){
									return [false,json.errorMsg];
								}else{
									return [true,succ];
								}
							},
							onClose : function(){
								window.location.reload();
							},
							closeAfterEdit : true,
							beforeShowForm: function (formid) {
					        	var $td = $("table tbody tr td.CaptionTD",formid);
					            $td.css("text-align", "right"); //文本右对齐
					        }
					};
					$gridTable.jqGrid("editGridRow","new",addParams);
				},
				editfunc : function(rowId){
					var idStr = "#"+rowId;
					var $currRow = $gridTable.find(idStr);
					var triggerName = $currRow.find("td:eq(1)").text();
					var jobName = $currRow.find("td:eq(2)").text();
					var editParams = {
							editData : {
								triggerName : triggerName,
								jobName : jobName
							},
							afterSubmit : function(response, postdata)
							{
								//in my case response text form server is "{sc:true,msg:''}" create js object from server reponse
								var json=eval("("+response.responseText+")"); 
								if(json.respFlag==false){
									return [false,json.errorMsg];
								}else{
									return [true,succ];
								}
							},
							onClose : function(){
								window.location.reload();
							},
							closeAfterEdit : true
					};
					$gridTable.jqGrid("editGridRow",rowId,editParams);
					
					//隐藏上下选择不同行编辑的按钮(通过按钮选择后没有动态的改变rowId,保存到后台有问题)
					$("#Act_Buttons td:eq(0) a").each(function(){
						$(this).hide();
					});
				}
		};
		
		jQuery("#gridTable").jqGrid('navGrid',"#paper",navParams);
		
		function editTr(rowId) {//行编辑
			$gridTable.jqGrid('editRow', rowId);
			$("#" + rowId, $gridTable).find("td:last a").attr("class","submitBt_a tableIcon");//.attr("title",saveTitle).text(saveTitle);
		}
		
		var $dateDialog = $("#selDateDialog");
		$("input[name='confirm']",$dateDialog).click(function(){
			var $extDate = $("#extDate");
			if(!$extDate.validationEngine("validate")) return;//触发验证,失败则停止操作
			else{
				executeAgain($extDate);
			}
		});
		var mBoxy = null;
		var jobName = null;
		
		function executeAgain(extDate){
			$.ajax({
				url : "execute",
				dataType:"json",
				data: {
					date : extDate.val(),
					jobName : jobName
				},
				success:function(data){
					mBoxy.hide();
					Boxy.alert(running, null, {title:prompt,unloadOnhide:true});
				}
			});
		}
		
		$("input[name='cancel']",$dateDialog).click(function(){
			Boxy.get(this).hide(); 
		});
		
		$gridTable.delegate("a[class^='rollBack_']","click",function(){
			jobName = $(this).attr("class").split("_")[1];
			mBoxy = new Boxy($dateDialog,{title:selDate , modal:true});
		});
		
		$gridTable.delegate("a[class^='pause_']","click",function(){
			var jobName = $(this).attr("class").split("_")[1];
			$.ajax({
				url : "pause",
				dataType:"json",
				data: {
					jobName : jobName
				},
				success:function(data){
					alert(succ);
					window.location.reload();
				}
			});
		});
		$gridTable.delegate("a[class^='resume_']","click",function(){
			var jobName = $(this).attr("class").split("_")[1];
			$.ajax({
				url : "resume",
				dataType:"json",
				data: {
					jobName : jobName
				},
				success:function(data){
					alert(succ);
					window.location.reload();
				}
			});
		});
		$gridTable.delegate("a[class^='del_']","click",function(){
			if(!confirm('确定要删除？')){
				return ;
			}
			var jobName = $(this).attr("class").split("_")[1];
			$.ajax({
				url : "del",
				dataType:"json",
				data: {
					jobName : jobName
				},
				success:function(data){
					alert(succ);
					window.location.reload();
				}
			});
		});
		$gridTable.delegate("a[class^='edit']","click",function(){
			var $ctr = $(this).parent().parent();
			editTr($ctr.attr("id"));
		});
		$gridTable.delegate("a[class^='submit']","click",function(){
			var $this = $(this).attr("class", "editBt_a tableIcon");//.attr("title",saveTitle).text(saveTitle);
			var $saveTr = $this.parent().parent();
			var triggerName = $saveTr.find("td:eq(1)").text();
			var jobName = $saveTr.find("td:eq(2)").text();
			$gridTable.jqGrid('saveRow',$saveTr.attr('id'),{
				extraparam:{
					jobName:jobName,
					triggerName:triggerName
				},
				successfunc: function(response) {
					if(response.getResponseHeader("hasException")=="true"){
						var dataObj=jQuery.parseJSON(response.responseText);
						Boxy.alert(dataObj.errorMsg,null,{title:alertErrorTitle,modal:false},"error");
						return false;
					}else{
						return true;
					}
			    }
			});
			
		});
		$gridTable.delegate("a[id^='his_']","click",function(){
			var jobName = $(this).attr("id").split("_")[1];
			//这里跳转历史记录页面,考虑是否使用弹出框
			var url=ctx+"/system/quartz/history?jobName="+jobName;
			Boxy.loadIframe(url,{title:jobName,width:800,height:450,maxable:false});
		});
		
		$(window).resize(function(){
			$("#gridTable").setGridWidth($(".gridDivWrap").innerWidth());
		});
		
	});
})(jQuery);
