/**
 * 聊天室
 */
var ctx = "/cms";
/**
 * 发送消息
 */
function sendMsg(){
	  var text = $("#msg_content").textbox("getText");
	  if(!$.trim(text)){
		  return;
	  }
	  var reciverId = $("#msg_main").tabs('getSelected').panel('options').id;
	  if(reciverId == "all"){
		  JavascriptChat.sendMessageToAll(text);
	  }else{
		  JavascriptChat.sendMessageToOne(text,reciverId);
	  }
	  var content = $(getMsgContent(username,text,new Date(),1));
	  appendContent(content);
}
/**
 * 给窗口添加收到的消息
 * @param content 内容
 * @param panelId 需要添加消息的panel
 */
function appendContent(content,panelId){
	  var tab = null;
	  if(!panelId){
		  tab = $("#msg_main").tabs('getSelected');
	  }else{
		  tab = $("#"+panelId);
	  }
	  tab.children().append(content);
	  content.fadeIn("slow");
	  $("#msg_content").textbox("setText","");
	  //$("#msg_main").animate({scrollTop:$("#last").offset().top},1);
	  var scrollTop = tab[0].scrollHeight;
	  tab.scrollTop(scrollTop);
}
/**
 * 接收消息
 * @param messages
 */
function receiveMessages(messages) {
	  
	  var content = $(getMsgContent(messages.senderName,messages.content,messages.sendTime));
	  if(!messages.reciverId){//全体成员
		  appendContent(content,"all");
	  	return;
	  }
	  if($("#msg_main").tabs('exists',messages.senderName)){//如果存在tab
		  var tab = $("#msg_main").tabs('getSelected');
		  if(tab.panel('options').id == messages.senderId){//是选中的tab
			  appendContent(content);
		  }else{
			  appendContent(content,messages.senderId);
		  }
	  }else{
		  var msg = messages.content;
		  if(msg.length>10){
			  msg = msg.substring(0,10)+"...";
		  }
		  var cache = $("#stroeData").data("store_"+messages.senderId);
		  if(!cache){
			  $("#stroeData").data("store_"+messages.senderId, [messages]);
		  }else{
			  cache[cache.length] = messages;
			  $("#stroeData").data("store_"+messages.senderId, cache);
		  }
		 
		 if($('#tipMsg_'+messages.senderId)[0]){
			 $('#tipMsg_'+messages.senderId).panel("destroy");
		 }
		  $.messager.show({
			  	id:'tipMsg_'+messages.senderId,
				title:'来自'+messages.senderName+'的消息',
				msg:msg+'<a href="javascript:showNewReciveMsg('+messages.senderId+',\''+messages.senderName+'\')">查看</a>',
				timeout:0,
				showType:'slide'
			});
	  }
	}
/**
 * 根据数据组拼消息字符串
 * @param senderName 发送者名称
 * @param content 内容
 * @param time 发送时间
 * @param flag 标志 1:发送消息 2:接收消息
 * @returns {String} 消息内容
 */
function getMsgContent(senderName,content,time,flag){
	  var calssName = "";
	  if(flag==1){//发送
		  calssName = "talk_recordboxme";
	  }else{//接收
		  calssName = "talk_recordbox";
	  }
	 return '<div class="'+calssName+'" style="display:none">'+
			  '<div class="user"><img src="'+ctx+'/chat/userIcon/1.do"/>'+senderName+'</div>'+
			  '<div class="talk_recordtextbg">&nbsp;</div>'+
			  '<div class="talk_recordtext">'+
			  '<font size="3px">'+content+'</font>'+
			  '<span class="talk_time">'+time.format("yyyy-MM-dd hh:mm:ss")+'</span>'+
			  '</div>'+
			  '</div>';
}
/**
 * 新消息时打开查看窗口
 * @param senderId
 * @param senderName
 */
function showNewReciveMsg(senderId,senderName){
	  if(!$("#"+senderId)[0]){
		  $("#msg_main").tabs('add',{
			  	id:senderId,
				title:senderName,
				closable:true,  
				bodyCls:"msg-background",
				content:'<div class="talk talk_record jp-container"></div>',
				selected: true
			});
		  var cache = $("#stroeData").data("store_"+senderId);//是否有存储在页面上的未读消息
		  var tab = $("#msg_main").tabs('getSelected').children();
		  $.post(ctx+"/chat/msgHistory.do",{senderId:senderId,
			  reciverId:userId,
			  flag:2},function (data){
				  $(data).each(function(i){
					  var messages = this;
					  var senderName = messages.senderName?messages.senderName:"游客";
					  var content = $(getMsgContent(senderName,messages.content,new Date(messages.sendTime)));
					  tab.append(content);
					  content.fadeIn("slow");
				  });
				  if(data.length>0){
					  $.post(ctx+"/chat/read.do",{senderId:senderId,
						  reciverId:userId})
				  }
				  var scrollTop = tab.parent()[0].scrollHeight;
				  tab.parent().scrollTop(scrollTop);
				  if(cache){
					  $(cache).each(function(){
						  var content = $(getMsgContent(senderName,this.content,this.sendTime));
						  tab.append(content);
						  content.fadeIn("slow");
					  });
					   var scrollTop = tab.parent()[0].scrollHeight;
						tab.parent().scrollTop(scrollTop);
						$("#stroeData").removeData("store_"+senderId);  
				  }
			  });
	  }
	  $("#tipMsg_"+senderId).panel("destroy");
}
/**
 * 双击用户树打开对话窗口
 * @param node 树节点
 */
function openUserTab(node){

	  var exists = $("#msg_main").tabs('exists',node.text);
	  if(exists){
		  $("#msg_main").tabs('select',node.text);
		  //var tab = $("#msg_main").tabs('getSelected').panel('options').id;
	  }else{
		  $("#msg_main").tabs('add',{
			  	id:node.id,
				title: node.text,
				closable:true,  
				bodyCls:"msg-background",
				content:'<div class="talk talk_record jp-container"></div>',
				selected: true
			});
	  }
}
/**
 * 用户上线触发
 * @param userId
 */
function userOnlineEvent(userId){
  var node = $("#userList").tree("find",userId);
  $('#userList').tree('update', {
		target: node.target,
		iconCls: 'icon-online'
	});
}
/**
 * 用户下线触发
 * @param userId
 */
function userOfflineEvent(userId){
	var node = $("#userList").tree("find",userId);
	  $('#userList').tree('update', {
			target: node.target,
			iconCls: 'icon-offline'
		});
}