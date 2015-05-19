<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/common/common_jsp.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type='text/javascript' src='${ctx}/dwr/engine.js'> </script>
	<script type='text/javascript' src='${ctx}/dwr/interface/JavascriptChat.js'> </script>
	<script type='text/javascript' src='${ctx}/dwr/util.js'> </script>
  </head>
  <script type="text/javascript">
  function init() {
	  dwr.engine.setActiveReverseAjax(true);
	}

	function sendMessage() {
	  var text = dwr.util.getValue("text");
	  dwr.util.setValue("text", "");
	  JavascriptChat.addMessage(text);
	}

	function receiveMessages(messages) {
	  var chatlog = "";
	  for (var data in messages) {
	    chatlog = "<div>" + dwr.util.escapeHtml(messages[data].text) + "</div>" + chatlog;
	  }
	  dwr.util.setValue("chatlog", chatlog, { escapeHtml:false });
	}

  </script>
  <body onload="dwr.engine.setActiveReverseAjax(true);">
<div id="page-title">[
  <a href="http://directwebremoting.org/dwr/">DWR Website</a> |
  <a href="..">Web Application Index</a>
]</div>


<div id="tabContents">

  <div id="demoDiv">
    <p>
      Your Message:
      <input id="text" onkeypress="dwr.util.onReturn(event, sendMessage)"/>
      <input type="button" value="Send" onclick="sendMessage()"/>
    </p>
    <hr/>

    <div id="chatlog"></div>
  </div>

</div>

</body>
</html>