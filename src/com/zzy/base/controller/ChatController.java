package com.zzy.base.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zzy.base.common.manager.OnlineUserManager;
import com.zzy.base.entity.chat.ChatFile;
import com.zzy.base.entity.chat.Message;
import com.zzy.base.entity.chat.Score;
import com.zzy.base.entity.chat.User;
import com.zzy.base.service.chat.ChatFileService;
import com.zzy.base.service.chat.MessageService;
import com.zzy.base.service.chat.ScoreService;
import com.zzy.base.service.chat.UserService;

@Controller
@RequestMapping(value = { "/chat" })
public class ChatController {

	@Resource
	UserService userService;
	@Resource
	MessageService messageService;
	@Resource
	ChatFileService chatFileService;
	@Resource
	ScoreService scoreService;
	@Resource
	OnlineUserManager onlineUserManager;
	@RequestMapping({ "room" })
	public String room(HttpServletRequest request, Model model) {
		User user = (User)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("username", user.getUsername());
			model.addAttribute("userId", user.getUserId());
			model.addAttribute("userType", user.getType());
			if(user.getType() == 2){
				return "chat/room_service";
			}
		}else{
			model.addAttribute("username", "游客");
			model.addAttribute("userId", -1);
			model.addAttribute("userType", 1);
		}
		return "chat/room_client";
	}
	@RequestMapping({ "roomService" })
	public String roomService(HttpServletRequest request, Model model) {
		User user = (User)request.getSession().getAttribute("user");
		onlineUserManager.online(user);
		if(user!=null){
			model.addAttribute("username", user.getUsername());
			model.addAttribute("userId", user.getUserId());
			model.addAttribute("userType", user.getType());
			if(user.getType() == 2){
				return "chat/room_service";
			}
		}
		return "chat/room_client";
	}
	@RequestMapping(value = "popWaitingUser", method = RequestMethod.GET)
	@ResponseBody
	public User popWaitingUser(HttpServletRequest request){
		User user = onlineUserManager.popWaitingUser();
		User currentUser = (User)request.getSession().getAttribute("user");
		if(user!=null){
			messageService.buildUserServerRelation(user, currentUser);
		}
		return user;
	}
	@RequestMapping(value = "endServer", method = RequestMethod.GET)
	@ResponseBody
	public boolean endServer(HttpServletRequest request){
		User currentUser = (User)request.getSession().getAttribute("user");
		messageService.destoryServerRelation(currentUser);
		return true;
	}
	@RequestMapping(value = "userIcon/{id}", method = RequestMethod.GET, produces = "application/octet-stream")
	public void userIcon(@PathVariable("id") String id,HttpServletRequest request, 
			HttpServletResponse response) throws IOException {
		// response.setContentType("application/octet-stream");
		InputStream is = null;
		BufferedInputStream bis = null;
		OutputStream out = null;
		try {
			if("0".equals(id)){
				is = request.getServletContext().getResourceAsStream("static/images/chat/thumbs/11.jpg");
			}else{
				is = request.getServletContext().getResourceAsStream("static/images/chat/thumbs/15.jpg");
			}
			bis = new BufferedInputStream(is);
			
			out = response.getOutputStream();
			byte[] reBytes = new byte[1024];
			while(bis.read(reBytes)!=-1){
				out.write(reBytes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(out!=null){
				out.close();
			}
			if(bis!=null){
				bis.close();
			}
			if(is!=null){
				is.close();	
			}
			
		}
		
	}
	@RequestMapping({ "login" })
	@ResponseBody
	public boolean login(String username,String password,Short type,HttpServletRequest request){
		User user = userService.login(username, password,type);
		if(user!=null){
			request.getSession().setAttribute("user", user);
		}else{
			return false;
		}
		return true;
	}
	@ResponseBody
	@RequestMapping({ "userList" })
	public List<Map<String,Object>> userList(){
		
		List<User> list = userService.findList();
		List<Map<String,Object>> userList = new ArrayList<>();
		if(list!=null){
			for(User user : list){
				Map<String,Object> uMap = new HashMap<String, Object>();
				uMap.put("text", user.getUsername());
				uMap.put("id", user.getUserId());
				if(onlineUserManager.isOnline(user.getUserId())){
					uMap.put("iconCls", "icon-online");
				}else{
					uMap.put("iconCls", "icon-offline");
				}
				userList.add(uMap);
			}
		}
		return userList;
	}
	@RequestMapping({ "uploadFile" })
	@ResponseBody
	public String uploadFile(MultipartFile file,String fileSize,Integer reciverId,String fileName,String fileId,HttpServletRequest request) throws IOException{
		User user = (User)request.getSession().getAttribute("user");
		
		File dir = new File(ChatFile.BASEPATH+"/"+user.getUserId());
		if(!dir.exists()){
			dir.mkdirs();
		}
		File destFile = new File(dir,fileId);
		FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
		ChatFile cf = new ChatFile();
		cf.setCreaterId(user.getUserId());
		cf.setCreateTime(new Date());
		cf.setFileId(fileId);
		cf.setName(fileName);
		cf.setRelativePath(user.getUserId()+"/"+fileId);
		cf.setReciverId(reciverId);
		cf.setContentType(file.getContentType());
		chatFileService.save(cf);
		return "success";
	}
	/**
	 * 进入等待状态
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping (value = "waittingServer")
	@ResponseBody
	public boolean waittingServer(HttpServletRequest request,HttpServletResponse response){
		User currentUser = (User) request.getSession().getAttribute("user");
		onlineUserManager.online(currentUser);
		return true;
	}
	@RequestMapping (value = "sendMsg")
	@ResponseBody
	public boolean sendMsg(Message message,HttpServletRequest request,HttpServletResponse response)throws Exception{
		User currentUser = (User) request.getSession().getAttribute("user");
		message.setSenderId(currentUser.getUserId());
		message.setSendTime(new Date());
		message.setType((short)1);
		message.setSenderName(currentUser.getUsername());
		return messageService.sendMessage(message)>0;
	}
	@RequestMapping (value = "getMsg/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public String getMsg(@PathVariable("userId")Integer userId,HttpServletRequest request,HttpServletResponse response)throws Exception{
		return messageService.getReciveMessage(userId);
	}
	
	@RequestMapping (value = "download/{id}", method = RequestMethod.GET)
	public void download(@PathVariable("id")String id,HttpServletResponse response)throws Exception{
		if(id==null){
			return;
		}
		ChatFile file = chatFileService.findById(id);
			
			File f = new File(ChatFile.BASEPATH+file.getRelativePath());
			downLoadFile(response,file.getName(),f,file.getContentType());
	}
	private void downLoadFile(HttpServletResponse response,String filename,File file,String contentType) throws Exception{
		if(StringUtils.isEmpty(contentType)){
			response.setContentType("application/x-msdownload;");
		}else{
			response.setContentType(contentType);
		}
		response.setHeader("Content-disposition", "attachment;filename="+new String(filename.getBytes("gbk"),"iso8859-1"));
		response.setHeader("Content-Length",String.valueOf(file.length()));
		FileUtils.copyFile(file, response.getOutputStream());
	} 
	@ResponseBody
	@RequestMapping({ "pingjia" })
	public boolean pingjia(Integer scoreUserId,Integer scoreById){
		scoreService.save(new Score(scoreUserId,scoreById));
		return true;
	}
	@ResponseBody
	@RequestMapping({ "msgHistory" })
	public List<Message> msgHistory(Integer senderId,Integer reciverId,Short flag){
		return messageService.getMessageByCondition(senderId, reciverId, flag);
	}
	@ResponseBody
	@RequestMapping({ "read" })
	public void read(Integer senderId,Integer reciverId){
		messageService.changeMsgFlag(senderId,reciverId);
	}
	@ResponseBody
	@RequestMapping({ "hasOnlineService" })
	public boolean hasOnlineService(HttpServletRequest request){
		
		return onlineUserManager.getOnlineServerNum()>0;
	}
	@ResponseBody
	@RequestMapping({ "getOnlineService" })
	public User getOnlineService(HttpServletRequest request){
		return userService.getOnlineServiceByRandom();
	}
	
}
