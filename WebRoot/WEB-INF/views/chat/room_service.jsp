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
	
	<script src="${ctx }/static/plugin/plupload/moxie.min.js"></script>
	<script src="${ctx }/static/plugin/plupload/plupload.min.js"></script>
	<script src="${ctx }/static/plugin/plupload/zh_CN.js"></script>
	<script type='text/javascript' src='${ctx}/static/js/chat/room_common.js'> </script>
	<script type='text/javascript' src='${ctx}/static/js/chat/room_service.js'> </script>
	 <link href="${ctx }/static/css/chat/chat.css" rel="stylesheet" type="text/css"/>
  </head>
  
  <script type="text/javascript">
  $(function(){
	 setSendBtn("bb");
	 setUploadBtn("container","pickfiles");
  });
  username = '${username}';
  userId ='${userId}';
  var timer = null;
function breakTimer(){
	if(timer){
		clearTimeout(timer)
	}
	$("#takeNext").linkbutton('enable');
	  $("#breakServer").linkbutton('disable');
	  var content = $(getMsgContent("系统提示","取消接入成功",new Date()));
		appendContent(content);
}
  function takeNextUser(){
	  if(timer){
			clearTimeout(timer)
		}
	  $("#takeNext").linkbutton('disable');
	  $("#breakServer").linkbutton('disable');
	  $.get("${ctx}/chat/popWaitingUser.do",null,function(user){
		  if(user){
				var content = $(getMsgContent("系统提示","成功接入客户："+user.username+"!",new Date()));
				reciverId = user.userId;
				appendContent(content);
				$("#breakServer").linkbutton('enable');
				$("#sendBtn").linkbutton('enable');
				getMessages();
				//$("#mm").linkbutton('enable');
		  }else{
			  timer = setTimeout(takeNextUser,60000);
			  var content = $(getMsgContent("系统提示","尚无需要接入的客户，60秒后重试<a href='javascript:breakTimer()'>取消</a>&nbsp;&nbsp;<a href='javascript:takeNextUser()'>立刻重试</a>",new Date()));
			  appendContent(content);
			  $("#takeNext").linkbutton('enable');
		  }
	  });
  }
  function endServer(){
	  $.get("${ctx}/chat/endServer.do",null,function(data){
		  if(data){
				var content = $(getMsgContent("系统提示","结束服务成功，可继续接待其他客户",new Date()));
				reciverId = 0;
				 appendContent(content);
				 $('#takeNext').linkbutton('enable');
				 $("#breakServer").linkbutton('disable');
				 $('#sendBtn').linkbutton('disable');
				//$("#mm").linkbutton('enable');
		  }
	  });
  }
  </script>
  <body class="easyui-layout" data-options="fit:true">   
    <div data-options="region:'east'" class="chat_right" style="width:400px;">
    	<%-- <ul id="userList" class="easyui-tree" data-options="url:'${ctx }/chat/userList.do',onDblClick:openUserTab" />  --%>
    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <th colspan="2"><img src="${ctx}/static/images/chat2/commute_gsmp.png"/>&nbsp;&nbsp;公司名片</th>
            </tr>
            <tr>
                <td>公司名称：</td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>成立时间：</td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>注册资金：</td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>企业规模：</td>
                <td>&nbsp;</td>
            </tr>
        </table>
    </div>   
    <div data-options="region:'west'" style="width:300px;" >
    	<table width="100%">   
		    <thead>   
		        <tr>   
		            <th >用户名</th>   
		            <th>咨询类型</th>   
		            <th>操作</th>   
		        </tr>
		        </thead> 
		      <tbody id="waitList">
		     </tbody>
		      
		</table>  

    </div>
    <div id="stroeData" style="display: none;"></div>
    <div data-options="region:'north',border:false" class="chat_header" >
    	<table width="80%" border="0" cellspacing="0" cellpadding="0" align="left">
            <tr>
                <td width="25%"><a href="#"><img src="${ctx}/static/images/chat2/commute_logo.png"/></a></td>
                <td width="55%">
                	<h1>&nbsp;</h1>
                </td>
                <td style="vertical-align: bottom;"><a href="#" class="chat_wyly_on">我要咨询</a></td>
                <td style="vertical-align: bottom;"><a href="#" class="chat_wyly">我要留言</a></td>
            </tr>
        </table>
    </div>
    <div data-options="region:'center',border:false" >
    	<div class="easyui-layout" style="width:600px;height:400px;" data-options="fit:true,border:false">
    		<!-- <div id="msg_main" data-options="region:'center'" style="padding:5px;background:#eee;">
    		
	    		<div id="jp-container" class="talk talk_record jp-container">
				
				</div>
    		</div> -->
    		<div id="msg_main" data-options="region:'center',border:false" style="background: #eee">
				        <div class="talk talk_record jp-container" >
						</div>  
    		</div>
    		<div data-options="region:'south',border:false" >
    		
    			<div class="easyui-layout" style="width:600px;height:180px;" data-options="fit:true,border:false">
    				<div data-options="region:'north',border:false" id="container"  style="height:auto">
    					<div id="toolbar" class="dialog-toolbar" >
						   <a href="javascript:void(0)" style="cursor: default;" class="easyui-linkbutton" data-options="plain:true" ><img src="${ctx}/static/images/chat2/commute_save.png"/></a>  
						   <a href="javascript:void(0)" style="cursor: default;" id="pickfiles" class="easyui-linkbutton" data-options="plain:true"><img src="${ctx}/static/images/chat2/commute_file.png"/></a>  
						   <a href="javascript:void(0)" style="cursor: default;" class="easyui-linkbutton" data-options="plain:true"><img src="${ctx}/static/images/chat2/commute_ts.png"/></a>  
						
						<a id="takeNext" href="javascript:takeNextUser()" style="cursor: default;"  class="easyui-linkbutton" data-options="iconCls:'icon-tip',plain:true,group:'serverOper'">接待下一个客户</a>  
						<a id="breakServer" href="javascript:endServer()" style="cursor: default;"  class="easyui-linkbutton" data-options="iconCls:'icon-no',plain:true,group:'serverOper'">结束服务</a>  

						</div>
    				</div>   
    				<div id="bb" data-options="region:'center',border:false" >
    					<input id="msg_content" class="easyui-textbox" data-options="multiline:true,prompt:'说点什么。。。'" style="width:100%;height: 100%"> 
    				</div>   
    				<div data-options="region:'south',border:false" >
    					<div id="msg_button" class="dialog-button">
    					<a href="javascript:closeWin()" style="cursor: default;" class="easyui-linkbutton" data-options="plain:true">关闭</a>
    					<a href="javascript:sendMsg()" style="cursor: default;" id="sendBtn" class="easyui-splitbutton" style="cursor: default;" data-options="plain:true,menu:'#mm'">发送</a>
    					<div id="mm" style="width:auto;display: none;" data-options="onClick:changeSendWay">   
						    <div id="enter" >按Enter键发送消息</div>   
						    <div id="center" data-options="iconCls:'icon-ok'">按Ctrl+Enter键发送消息</div>   
						</div>  
    					  
    					</div>
    				</div>
    			</div>
    		</div>   
		</div>  
    </div>   
</body>  
  </body>
</html>
