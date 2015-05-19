<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/common_jsp.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>四级页面</title>
    <link href="${ctx }/static/css/chat/chat.css" rel="stylesheet" type="text/css"/>
    <script src="${ctx }/static/plugin/easyui/jquery.min.js"></script>
</head>

<body>
<script >
var username = "";
var userId = "";
function sendMsg(){
	  var text = $("#msg_content").val();
	  if(!$.trim(text)){
		  return;
	  }
	  var content = $(getMsgContent(username,text,new Date(),1));
	  
	  appendContent(content);
}
function getMsgContent(username,text,date,type){
	var className = '';
	var box = 1;
	if(type == 1){
		className = 'chat_imgright';
		var box = 2;
	}else{
		className = 'chat_imgleft';
	}
	var content = '<div class="box'+box+'" >'+
    '<div class="'+className+'"><img src="${ctx}/static/images/chat2/chat_1.png"/></div>'+
    '<div class="jt"></div>'+
    text+
	'</div>';
	return content;
}
function appendContent(content){
	$("#chat_main").append(content);
		content.fadeIn("slow");
	  $("#msg_content").val("");
	  var scrollTop = $("#chat_main").scrollHeight;
	  $("#chat_main").scrollTop(scrollTop);
}

</script>
<div class="content">
    <div id="chat_header_absolute" class="chat_header">
        <table width="80%" border="0" cellspacing="0" cellpadding="0" align="left">
            <tr>
                <td width="25%"><a href="#"><img src="${ctx}/static/images/chat2/commute_logo.png"/></a></td>
                <td width="55%"><h1>在线客服001</h1></td>
                <td style="vertical-align: bottom;"><a href="#" class="chat_wyzx">我要咨询</a></td>
                <td style="vertical-align: bottom;"><a href="#" class="chat_wyly">我要留言</a></td>
            </tr>
        </table>
        <div class="chat_btn"><a href="#">X</a></div>
    </div>
    <div class="chat_left">&nbsp;</div>
    <div class="chat_right">
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
    <div id="chat_center_resizeable" class="chat_center">
        <ul class="chat_service">
            <li><a href="#">小容<img src="${ctx}/static/images/chat2/commute_zx.png"/></a></li>
            <li><a href="#">小容<img src="${ctx}/static/images/chat2/commute_zx.png"/></a></li>
            <li><a href="#">小容<img src="${ctx}/static/images/chat2/commute_lx.png"/></a></li>
        </ul>
        <div class="clear"></div>
        <div id="chat_main" class="chat_main" style="overflow: auto;">
            <div class="box1">
                <div class="chat_imgleft"><img src="${ctx}/static/images/chat2/chat_2.png"/></div>
                <div class="jt"></div>
                33
            </div>
            <div class="box2">
                <div class="chat_imgright"><img src="${ctx}/static/images/chat2/chat_1.png"/></div>
                <div class="jt"></div>
                4545454545
            </div>
        
        
        </div>
        <div class="chat_bottom">
        <div class="chat_menu">
            <table border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td width="30"><a href="#"><img src="${ctx}/static/images/chat2/commute_save.png"/></a></td>
                    <td width="30"><a href="#"><img src="${ctx}/static/images/chat2/commute_file.png"/></a></td>
                    <td width="30"><a href="#"><img src="${ctx}/static/images/chat2/commute_ts.png"/></a></td>
                    <td width="30"><a href="#"><img src="${ctx}/static/images/chat2/commute_p.png"/></a></td>
                </tr>
            </table>
        </div>
        <div class="chat_input">
            <textarea id="msg_content" name="" cols="" rows="" class="chat_box"></textarea>
            <div class="chat_btn2">
                <a href="#" class="chat_close">关闭</a>
                <a href="javascript:sendMsg()" class="chat_send">发送<img src="${ctx}/static/images/chat2/arrow.png" id="chat_send_arrow"/></a>
                <ul class="chat_sendmenu" id="chat_sendmenu_ul" style="display: none;">
                    <li><a href="#">按enter键发送消息</a></li>
                    <li><a href="#">按ctrl+enter键发送消息</a></li>
                </ul>
            </div>
        </div>
    </div>
    </div>
</div>
<script type="text/javascript">
    (function () {
        function addEvent(elm, type, handler) {
            if(elm.addEventListener){
                elm.addEventListener(type, handler, false);
            }else if(elm.attachEvent){
                elm.attachEvent('on' + type, handler);
            }else{
                elm['on' + type] = handler;
            }
        }
        /*自适应高度*/
        var timeID;
        var autoSize = function () {
            clearTimeout(timeID);
            timeID = setTimeout(function () {
                        var center = document.getElementById('chat_center_resizeable'),
                            header = document.getElementById('chat_header_absolute'),
                            inHeight = window.innerHeight || document.documentElement.clientHeight;
                        center.style.height = (inHeight - header.clientHeight) + 'px';
                     }, 100);
        };
        autoSize();
        addEvent(window, 'resize', autoSize);
        /*发送箭头 弹出窗*/
        var arraw = document.getElementById('chat_send_arrow'),
            sendMenu = document.getElementById('chat_sendmenu_ul');
        if(arraw && arraw.nodeType === 1){
            addEvent(document.getElementById('chat_center_resizeable'), 'click', function (e) {
                var event = e || window.event,
                    target = event.srcElement || event.target,
                    display = (target === arraw)?'block':'none';
                if(sendMenu.style.display !== display)  sendMenu.style.display = display;
            });
        }
    })();
</script>
</body>
</html>
