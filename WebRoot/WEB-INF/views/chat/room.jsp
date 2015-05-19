<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/common_jsp.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Chat Room</title>
    
	<link id='easyuiTheme' rel="stylesheet" type="text/css" href="${ctx }/static/plugin/easyui/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="${ctx }/static/plugin/easyui/themes/icon.css" />
	<link rel="stylesheet" type="text/css" href="${ctx }/static/css/chat/jscrollpane.css" />
	<script src="${ctx }/static/plugin/easyui/jquery.min.js"></script>
	<script src="${ctx }/static/plugin/easyui/jquery.easyui.min.js"></script>
	<script src="${ctx }/static/js/extend.js"></script>
	
	<script type='text/javascript' src='${ctx}/dwr/engine.js'> </script>
	<script type='text/javascript' src='${ctx}/dwr/interface/JavascriptChat.js'> </script>
	<script type='text/javascript' src='${ctx}/dwr/util.js'> </script>
	<script type='text/javascript' src='${ctx}/static/js/chat/room.js'> </script>
	<style type="text/css">
		.msg-background{
			background-color: #eee
		}
	</style>
  </head>
  
  <script type="text/javascript">
  $(function(){
	  dwr.engine.setActiveReverseAjax(true);
	  dwr.engine.setNotifyServerOnPageUnload(true);
	  //$('#tip').tooltip("show");
	  $("#bb").keydown(function(event){
		  if(event.keyCode == 13 && !event.ctrlKey){
			  event.preventDefault();
			  sendMsg();
		  }else if(event.keyCode == 13 && event.ctrlKey){
			  var text = $("#msg_content").textbox("getText");
			  $("#msg_content").textbox("setText",text+"\n");
		  }
		});
  });
  var username = '${username}';
  var userId ='${userId}';
  
 
  </script>
  <body class="easyui-layout" data-options="fit:true">   
    <div data-options="region:'east',title:'人员列表',split:true" style="width:200px;">
    
    	<ul id="userList" class="easyui-tree" data-options="url:'${ctx }/chat/userList.do',onDblClick:openUserTab" /> 
    	
    </div>   
    <div id="stroeData" style="display: none;"></div>
    <div data-options="region:'center',border:false" >
    	<div class="easyui-layout" style="width:600px;height:400px;" data-options="fit:true,border:false">
    		<!-- <div id="msg_main" data-options="region:'center'" style="padding:5px;background:#eee;">
    		
	    		<div id="jp-container" class="talk talk_record jp-container">
				
				</div>
    		</div> -->
    		<div data-options="region:'center'" >
    			<div id="msg_main" class="easyui-tabs"  data-options="fit:true,border:false" >
		    		<div id="all" title="全体成员" style="background: #eee">   
				        <div class="talk talk_record jp-container" >
						</div>  
				    </div>
	    		</div>
    		</div>
    		<div data-options="region:'south',split:true,border:false" >
    		
    			<div class="easyui-layout" style="width:600px;height:300px;" data-options="fit:true,border:false">
    				<div data-options="region:'north',border:false" style="height:auto">
	    				<div id="toolbar" class="dialog-toolbar">
						   <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">表情</a>  
						   <a href="javascript:void(0)"  class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">消息记录</a>  
						</div>
    				</div>   
    				<div id="bb" data-options="region:'center',border:false" >
    					<input id="msg_content" class="easyui-textbox" data-options="multiline:true,prompt:'说点什么。。。'" style="width:100%;height: 100%"> 
    				</div>   
    				<div data-options="region:'south',border:false" >
    					<div id="msg_button" class="dialog-button">
    					<!-- <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true">关闭</a>   -->
    					<a href="javascript:sendMsg()" class="easyui-linkbutton" data-options="plain:true">发送</a>  
    					</div>
    				</div>
    			</div>
    		</div>   
		</div>  
    </div>   
</body>  
  </body>
</html>
