 function reciveWaitingUser(user){
	 $('#waitList').append('<tr id="w_'+user.userId+'"><td>'+user.username+'</td><td>咨询</td><td><a href="javascript:takeUser('+user.userId+')">接入</a></td></tr>')
 }
 function userLeave(user){
	 $('#w_'+user.userId).remove();
 }
 function takeUser(userId){
	 ConsultingChat.takeUser(userId,function(user){
		 var content = "";
		 if(user){
			 content = $(getMsgContent("系统提示","成功接入客户："+user.username+"!",new Date()));
			 reciverId = user.userId;
		 }else{
			 content = $(getMsgContent("系统提示","用户已不再等待状态，请接入其他客户",new Date()));
			 reciverId = 0;
		 }
		 appendContent(content);
	 });
 }
 function userBreak(user){
	 var content = $(getMsgContent("系统提示","用户："+user.username+"断开连接，请继续接待新客户!",new Date()));
	 reciverId = 0;
	 appendContent(content);
	 $('#takeNext').linkbutton('enable');
	 $("#breakServer").linkbutton('disable');
 }