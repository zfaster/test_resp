/****************接收消息，断开连接相关*****************/

function reciveService(service){
	 var content = $(getMsgContent("系统提示","您好，客服："+service.username+"为您服务!",new Date()));
	 reciverId = service.userId;
	 reciverName = service.username;
	 appendContent(content);
	 $('#sendBtn').linkbutton('enable');
}
/**
 * 客服断开连接处理
 * @param service
 */
function serverBreak(service){
	 var content = $(getMsgContent("系统提示","抱歉，客服："+service.username+"断开连接，请刷新页面后重试!",new Date()));
	 breakConnect();
	 appendContent(content);
	 $('#sendBtn').linkbutton('disable');
}
function endSever(service){
	var content = $(getMsgContent("系统提示","本次服务到此结束，您可对客服："+service.username+"做出评价，谢谢！",new Date()));
	 appendContent(content);
	 $('#sendBtn').linkbutton('disable');
}
/**
 * 断开后清理过期值
 */
function breakConnect(){
	 reciverId = 0;
	 reciverName = null;
}
/**
 * 评价
 */
function pingjia(){
	 if(reciverId){
		 $.post(ctx+"/chat/pingjia.do",
				 {scoreUserId:reciverId,scoreById:userId},
				 function (data){
					 if(data){
						 var content = $(getMsgContent("系统提示","您对客服："+reciverName+",评价成功!此次服务结束~",new Date()));
						 appendContent(content);
						 breakConnect();
						 closeTip();
					 }
				 });
	 }else{
		 var content = $(getMsgContent("系统提示","尚无客服人员接入，请稍后评价",new Date()));
		 appendContent(content);
	 }
}
function closeTip(){
	  $('#pinjia').tooltip('hide');
}