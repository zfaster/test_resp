<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/common/common_jsp.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>咨询登入</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link id='easyuiTheme' rel="stylesheet" type="text/css" href="${ctx }/static/plugin/easyui/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="${ctx }/static/plugin/easyui/themes/icon.css" />
	<script src="${ctx }/static/plugin/easyui/jquery.min.js"></script>
	<script src="${ctx }/static/plugin/easyui/jquery.easyui.min.js"></script>
	<style type="text/css">
	body{ text-align:center} 
.div{ margin:0 auto; width:400px; height:100px; margin-top: 10%} 
	</style>
	<script type="text/javascript">
	$(function(){
		$("#login").keydown(function(event){
				  if(event.keyCode == 13 ){
					  event.preventDefault();
					  login();
				  }
			});
	})
		function login(){
			var username = $("#username").textbox("getValue");
			var password = $("#password").textbox("getValue");
			var type = $("input[name='type']:checked").val();
			$.post("${ctx}/chat/login.do",{username:username,password:password,type:type},function(data){
				if(data){
					$("#tip").html(getTip("登入成功，正在跳转请稍后",data));
					if(type==1){
						document.location = "${ctx}/chat/room.do";
					}else{
						document.location = "${ctx}/chat/roomService.do";
					}
				}else{
					$("#tip").html(getTip("登入失败",data));
				}
			});
		}
		function getTip(content,success){
			if(success){
				return "<span style='color:green'>"+content+"</span>";
			}else{
				return "<span style='color:red'>"+content+"</span>";
			}
		}
	</script>
  </head>
  <body>
  <div class="div">
	<div id="login" class="easyui-panel" title="咨询系统登入" style="width:400px;padding:30px 70px 20px 70px;">
		<div style="margin-bottom:10px">
			<input class="easyui-textbox" id="username" style="width:100%;height:40px;padding:12px" data-options="prompt:'用户名',iconCls:'icon-man',iconWidth:38,required:true,missingMessage:'用户名必填'">
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-textbox" id="password" type="password" style="width:100%;height:40px;padding:12px" data-options="prompt:'密码',iconCls:'icon-lock',iconWidth:38,required:true,missingMessage:'密码必填'">
		</div>
		<div style="margin-bottom:15px">
			<input id="type1" type="radio" name="type" checked="checked" value="1" ><label for="type1">企业咨询客户</label> 
			<input id="type2" type="radio" name="type" value="2"><label for="type2">在线服务客服</label> 
			<!-- <select id="type" class="easyui-combobox" name="type" data-options="panelMaxHeight:50">   
			    <option value="1"></option>   
			    <option value="2"></option>   
			</select>   -->
		</div>
		<div id="tip" style="margin-bottom:5px">
		</div>
		<div>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok',onClick:login" style="padding:5px 0px;width:100%;">
				<span style="font-size:14px;">登&nbsp;&nbsp;入</span>
			</a>
		</div>
	</div>
  </div>
  </body>
  
  </html>
  	
  	