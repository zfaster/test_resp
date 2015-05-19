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
	<script src="${ctx }/static/plugin/plupload/moxie.min.js"></script>
	<script src="${ctx }/static/plugin/plupload/plupload.min.js"></script>
	<script src="${ctx }/static/plugin/plupload/zh_CN.js"></script>
	<script src="${ctx }/static/js/extend.js"></script>
	
	<script type='text/javascript' src='${ctx}/static/js/chat/room_common.js'> </script>
	<script type='text/javascript' src='${ctx}/static/js/chat/room_client.js'> </script>
	 <link href="${ctx }/static/css/chat/chat.css" rel="stylesheet" type="text/css"/>
  </head>
  
  <script type="text/javascript">
  
  $(function(){
		  $.post("${ctx}/chat/hasOnlineService.do",function(data){
			  if(data){
				  $.get("${ctx}/chat/waittingServer.do",null,function(data){
					  if(data){
						  var content = $(getMsgContent("系统提示","正在等待接入请稍后。。。",new Date()));
						  appendContent(content);
						  getMessages();
					  }
				  });
			  }else{
				  var content = $(getMsgContent("系统提示","抱歉当前无在线客服为您服务，您可选择<a href='javascript:void(0)' style='cursor:pointer'>离线留言</a> 或者稍后再试。",new Date()));
				  appendContent(content);
			  }
		  })
		  setSendBtn("bb");
		  $('#pinjia').tooltip({
			  position: 'top',
			  content: $("#pinjiaTip").html(),
			  showEvent:'mousedown',
				hideEvent:'',
			  onShow: function(){       
				  $(this).tooltip('tip').css({
					  backgroundColor: '#0000',
					  borderColor: '#0000'        
					  });    
				  }
			  });
		  setUploadBtn("container","pickfiles");
	  //$('#tip').tooltip("show");
  });
  

  username = '${username}';
  userId ='${userId}';
  
 
  </script>
  <body class="easyui-layout" data-options="fit:true">
  	<div id="pinjiaTip" style="display: none;" 	>
  				<div data-options="region:'center',border:false" >
  					<input type="radio" name="pinjia" value="1" id="p1" >很满意&nbsp;
			  		<input type="radio" name="pinjia" value="2" id="p2" >满意&nbsp;
			  		<input type="radio" name="pinjia" value="3" id="p3" >一般&nbsp;
			  		<input type="radio" name="pinjia" value="4" id="p4" >不满意&nbsp;
			  		<input type="radio" name="pinjia" value="5" id="p5" >差&nbsp;
  				</div>   
	  				<div data-options="region:'south',border:false" style="text-align: right;" >
	  					<!-- <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true">关闭</a>   -->
	  					<a href="javascript:pingjia()" class="easyui-linkbutton" >评价</a>
	  					<a href="javascript:closeTip()" class="easyui-linkbutton" >关闭</a>
  					  
  					</div>
  				</div>
  	</div>   
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
    <div data-options="region:'west'" style="width:300px;">
    </div>
    <div id="stroeData" style="display: none;"></div>
    <div data-options="region:'north',border:false" class="chat_header" >
    	<table width="80%" border="0" cellspacing="0" cellpadding="0" align="left">
            <tr>
                <td width="25%"><a href="javascript:void(0)"><img src="${ctx}/static/images/chat2/commute_logo.png"/></a></td>
                <td width="55%">
                	<h1>&nbsp;</h1>
                </td>
                <td style="vertical-align: bottom;"><a href="javascript:void(0)" class="chat_wyly_on">我要咨询</a></td>
                <td style="vertical-align: bottom;"><a href="javascript:void(0)" class="chat_wyly">我要留言</a></td>
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
    				<div data-options="region:'north',border:false"  id="container" style="height:auto">
    					<%-- <table border="0" cellspacing="5" cellpadding="0" >
			                <tr>
			                    <td width="30"><a href="javascript:void(0)"><img src="${ctx}/static/images/chat2/commute_save.png"/></a></td>
			                    <td  width="30"><a id="pickfiles" href="javascript:void(0)"><img  src="${ctx}/static/images/chat2/commute_file.png"/> </a></td>
			                    <td width="30"><a href="javascript:void(0)"><img src="${ctx}/static/images/chat2/commute_ts.png"/></a></td>
			                    <td width="30"><a id="pinjia" href="javascript:void(0)"><img src="${ctx}/static/images/chat2/commute_p.png"/></a></td>
			                </tr>
			            </table> --%>
	    				 <div id="toolbar" class="dialog-toolbar">
						   <a href="javascript:void(0)" class="easyui-linkbutton" style="cursor: default;" data-options="plain:true" ><img src="${ctx}/static/images/chat2/commute_save.png"/></a>  
						   <a href="javascript:void(0)" id="pickfiles" style="cursor: default;" class="easyui-linkbutton" data-options="plain:true"><img src="${ctx}/static/images/chat2/commute_file.png"/></a>  
						   <a href="javascript:void(0)" class="easyui-linkbutton" style="cursor: default;" data-options="plain:true"><img src="${ctx}/static/images/chat2/commute_ts.png"/></a>  
						   <a href="javascript:void(0)" id="pinjia" style="cursor: default;" class="easyui-linkbutton" data-options="plain:true,size:'large'"><img src="${ctx}/static/images/chat2/commute_p.png"/></a>  
						</div>
    				</div>   
    				<div id="bb" data-options="region:'center',border:false" >
    					<input id="msg_content" class="easyui-textbox" data-options="multiline:true,prompt:'说点什么。。。'" style="width:100%;height: 100%;"> 
    				</div>   
    				<div data-options="region:'south',border:false" >
    					<div id="msg_button" class="dialog-button">
    					<a href="javascript:closeWin()" class="easyui-linkbutton" style="cursor: default;" data-options="plain:true">关闭</a>
    					<a href="javascript:sendMsg()" id="sendBtn" class="easyui-splitbutton" style="cursor: default;" data-options="plain:true,menu:'#mm',disabled:true">发送</a>
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
