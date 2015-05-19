/**
 * 聊天室
 */
var ctx = "/cms";
/**
 * 发送快捷方式
 * ctrl+enter center
 * enter enter
 */
var sendeWay = "center";
/**
 * 接收人id
 */
var reciverId = 0;
/**
 * 接收人姓名
 */
var reciverName = null;
/**
 * 当前用户名
 */
var username = '';
/**
 * 当前用户ID
 */
var userId ='';
/**
 * 发送消息
 */
function sendMsg(){
	  var text = $("#msg_content").textbox("getText");
	  if(!$.trim(text)){
		  return;
	  }
	  if(reciverId){
		  $.post(ctx+'/chat/sendMsg.do',{content:text,reciverId:reciverId}, function(result){
			  if(result){
				  var content = $(getMsgContent(username,text,new Date(),1));
				  appendContent(content);
			  }else{
				  var content = $(getMsgContent("系统提示","消息发送失败请重试",new Date()));
				  appendContent(content);
			  }
		  })
	  }else{
		  var content = $(getMsgContent("系统提示","无交流对象，无法发送消息",new Date()));
		  appendContent(content);
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
	  var uid = 0;
	  if(flag==1){//发送
		  calssName = "talk_recordboxme";
		  uid = userId;
	  }else{//接收
		  calssName = "talk_recordbox";
		  uid = reciverId;
	  }
	  if(typeof time != "object"){
		  time = new Date(time);
	  }
	 return '<div class="'+calssName+'" style="display:none">'+
			  '<div class="user"><img src="'+ctx+'/chat/userIcon/'+uid+'.do"/>'+senderName+'</div>'+
			  '<div class="talk_recordtextbg">&nbsp;</div>'+
			  '<div class="talk_recordtext">'+
			  '<font size="3px">'+content+'</font>'+
			  '<span class="talk_time">'+time.format("yyyy-MM-dd hh:mm:ss")+'</span>'+
			  '</div>'+
			  '</div>';
}
/**
 * 给窗口添加收到的消息
 * @param content 内容
 * @param panelId 需要添加消息的panel
 */
function appendContent(content){
	  var tab = $("#msg_main");
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
function getMessages(result){
		var url = ctx+'/chat/getMsg/'+userId+'.do';
		if(result){
				var messages = eval('('+result+')');
				if(messages.content){
					messages.content = unescape(messages.content.replace(/\\/g, "%"));
					messages.senderName = unescape(messages.senderName.replace(/\\/g, "%"));
					if(messages.type==1 || messages.type == 3){//聊天 //系统
						var content = $(getMsgContent(messages.senderName,messages.content,messages.sendTime));
						  appendContent(content);
		   	   		}else if(messages.type == 2){//接入
		   	   			reciveService(eval('('+messages.content+')'));
		   	   		}else if(messages.type == 4){//断开
		   	   			var target = eval('('+messages.content+')');
		   	   			if(target.type==1){
		   	   				userBreak(target);
		   	   			}else{
		   	   				serverBreak(target);
		   	   			}
		   	   		}else if(messages.type == 5){//结束服务
		   	   			endSever(eval('('+messages.content+')'));
		   	   		}
				}
		}
		$.ajax({
		  url: url,
		  cache: false,
		  success:getMessages
		});
		//$.post('control/chat_message_getMsg.action',null, getMsg)
	}
function errorHandler(errorString, exception){
	  $.messager.alert('错误','服务器异常！','error');
}
/****************发送按钮相关事件*****************/
/**
 * 修改发送方式
 * @param item
 */
function changeSendWay(item){
	 $('#mm').find('div').each(function(){
		 if(this.id){
			 $('#mm').menu('setIcon', {
					target: $('#'+this.id)[0],
					iconCls: 'icon-blank'
			});
		 }
		
	 })
	 sendeWay = item.id;
	 $('#mm').menu('setIcon', {
			target: $('#'+item.id)[0],
			iconCls: 'icon-ok'
	});
}
/**
 * 绑定发送按钮
 * @param btnId 按钮id
 */
function setSendBtn(btnId){
	  $("#"+btnId).keydown(function(event){
		  if(sendeWay == "center"){
			  if(event.keyCode == 13 && event.ctrlKey){
				  event.preventDefault();
				  sendMsg();
			  }
		  }else{
			  if(event.keyCode == 13 && !event.ctrlKey){
				  event.preventDefault();
				  sendMsg();
			  }else if(event.keyCode == 13 && event.ctrlKey){
				  var text = $("#msg_content").textbox("getText");
				  $("#msg_content").textbox("setText",text+"\n");
			  }
		  }
		});
}
/**
 * 设置发送文件按钮
 * @param container 按钮容器div
 * @param pickfiles 按钮id
 */
function setUploadBtn(container,pickfiles){
	 var uploader = new plupload.Uploader({
			runtimes : 'html5,flash,silverlight,html4',
			browse_button : pickfiles, // you can pass in id...
			container: document.getElementById(container), // ... or DOM Element itself
			url : ctx+'/chat/uploadFile.do',
			flash_swf_url : ctx+'/static/plugin/plupload/Moxie.swf',
			silverlight_xap_url : ctx+'/static/plugin/plupload/Moxie.xap',
			
			filters : {
				max_file_size : '100mb'
				/* ,mime_types: [
					{title : "Image files", extensions : "jpg,gif,png"},
					{title : "text files", extensions : "txt"},
					{title : "Zip files", extensions : "zip"}
				] */
			},
			preinit:{
				UploadFile:function (up,file){
					up.settings.multipart_params = {
							fileSize:plupload.formatSize(file),
							fileName:file.name,
							fileId:file.id,
							reciverId:reciverId
					}
				}
			},
			init: {
				PostInit: function() {
					/* document.getElementById('filelist').innerHTML = '';

					document.getElementById('uploadfiles').onclick = function() {
						uploader.start();
						return false;
					}; */
				},

				FilesAdded: function(up, files) {
					if(!reciverId){
						appendContent($(getMsgContent("系统提示","无接收人员无法发送文件",new Date())));
						return false;
					}
					plupload.each(files, function(file) {
						appendContent($(getMsgContent('系统提示','正在上传文件: ' + file.name+'<div id="'+file.id+'" style="width:auto;height: 20px"></div>',new Date(),1)));
					});
					uploader.start();//选择后直接上传
				},

				UploadProgress: function(up, file) {
					$('#'+file.id).progressbar({ 
										value: file.percent 
										});
				},
				FileUploaded:function(up,file,info){
					appendContent($(getMsgContent("系统提示","文件: " + file.name+"发送成功",new Date())));
					
					ConsultingChat.sendMessageToOne("来自："+username+" 发送的文件：<a href='"+ctx+"/chat/download/"+file.id+".do'>"+file.name+"</a>", reciverId);
					
				},
				Error: function(up, err) {
					appendContent($(getMsgContent("错误提示",err.code + ": " + err.message,new Date())));
				}
			}
		});

		uploader.init();
}

function closeWin(){
	self.opener=null;
	self.close();
}
