<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<%
	String url = request.getParameter("url");

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>doc.jsp</title>
	
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
	<script type="text/javascript" src="swfobject.js"></script>
	<script type="text/javascript" src="extMouseWheel.js"></script>
	<script type="text/javascript" >
		 	var i18n = {
            file:
            {
               title: "Some document.pdf"
            },
	   		label:
	   		{
	   			actualSize: "Actual Size",
	            fitPage: "Fit Page",
            	fitWidth: "Fit Width",
            	fitHeight: "Fit Height",
            	fullscreen: "Fullscreen",
            	fullwindow: "Maximize",
            	fullwindow_escape: "Press Esc to exit full window mode",
            	page: "Page",
            	pageOf: "of"
	   		},
	   		errors: {
            	"error": "The content cannot be displayed due to an unknown error.",
              	"content": "The content cannot be displayed because it is not of type png, jpg, gif or swf.",
              	"io": "The preview could not be loaded from the server."
           	}
	   	};

		   var onPreviewEvent = function (event)
		   {
		   	if (event.event)
            {
               var wrapper;
	            if (event.event.type == "onFullWindowClick")
	            {
	               // Called when "Maximize" button was clicked
	               wrapper = document.getElementById("wrapper");
	               wrapper.style.width = "100%";
	               wrapper.style.height = "100%";
	               wrapper.style.position = "absolute";
	            }
	            else if (event.event.type == "onFullWindowEscape")
	            {
	               wrapper = document.getElementById("wrapper");
	               wrapper.style.width = "600px";
	               wrapper.style.height = "700px";
	               wrapper.style.position = "static";
	            }
	         }
	         else if (event.error)
	         {
	            // Inform the user about the failure
	            alert(i18n.errors[event.error.code] || "Error");
	         }
		   };

		   var myPreviewLogger = function (msg, level)
		   {
		   	if (console && console.log) {
		   		console.log("WebPreviewer [" + level + "]: " + msg);
		   	}
		   };

			// Url to the result of the pdf2swf conversion (make sure the -T flag was turned on during the transformation)
			var url = "<%=url%>?noCache=" + (new Date().getTime());
			var flashvars =
			{
				fileName: i18n.file.title,
            paging: true,  // If false only 1 page is displayed at a time
            url: url,
            jsCallback: "onPreviewEvent", // Optional but needed if Maximize button shall work
            jsLogger: "myPreviewLogger", // Optional
            show_fullscreen_button: true, // Set to false to hide "Fullscreen" button
            show_fullwindow_button: true,	// Set to false to hide "Maximize" button
            disable_i18n_input_fix: false, // Set to true to stop fix from being sed if it causes problem for some reason
            i18n_actualSize: i18n.label.actualSize,
            i18n_fitPage: i18n.label.fitPage,
            i18n_fitWidth: i18n.label.fitWidth,
            i18n_fitHeight: i18n.label.fitHeight,
            i18n_fullscreen: i18n.label.fullscreen,
            i18n_fullwindow: i18n.label.fullwindow,
            i18n_fullwindow_escape: i18n.label.fullwindow_escape,
            i18n_page: i18n.label.page,
            i18n_pageOf: i18n.label.pageOf
			};

			var params =
			{
				allowScriptAccess: "sameDomain", // Allow JavaScript & ActionScript to connect
            allowFullScreen: "true", // To allow the "Fullscreen" button to work
            wmode: "transparent" // Remove if you don't plan to display html elements on top of the previewer (will increase performace slightly)
         };

			var attributes =
			{
				id: "previewer",
				name: "previewer" // Must match id to make extMouseWheel.js work (scrolling on MacOSX)
			};
			swfobject.embedSWF("WebPreviewer.swf", "previewer", "100%", "100%", "9.0.124", false, flashvars, params, attributes);
			
	</script>
  </head>
  
  <body>
  <div style="width:780px;height:530px;">
    <div id="previewer" >
	   <!-- The entire "previewer" div will be replaced with the flash embed/object tag -->
	   请安装Flash插件，以便能够预览文档</br>
	  <!-- Please install Flash to be able to use the Preview the document.<br/> --> 
		<a href="http://www.adobe.com/go/getflashplayer">
			<img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash player" />
		</a>
	</div>
  </body>
</html>
