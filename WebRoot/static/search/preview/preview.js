//视频预览 配置             
// Collect query parameters in an object that we can
// forward to SWFObject:

//var pqs = new ParsedQueryString();
//var parameterNames = pqs.params(false);
var parameters = {
    src: "http://localhost:8080/1LightBoxTest/preview/media/20100809201008091621420081_WM9_256Kbps_download_PAL_ConstrainedVBR.flv",
    autoPlay: "true",
    verbose: true,
    controlBarAutoHide: "false",
    controlBarPosition: "bottom",
    poster: "preview/media/poster.png"
};

//for (var i = 0; i < parameterNames.length; i++) {
//    var parameterName = parameterNames[i];
//    parameters[parameterName] = pqs.param(parameterName) ||
//   parameters[parameterName];
//}

// Embed the player SWF:



/* Uncomment this code to be notified of playback errors in JavaScript:

 function onMediaPlaybackError(playerId, code, message, detail)            
 {
 	alert(playerId + "\n\n" + code + "\n" + message + "\n" + detail);            
 }
 */
 
 function changeVideoUrl(src){
				parameters.src = src;
				swfobject.embedSWF
				(
				"preview/media/StrobeMediaPlayback.swf", 
				"StrobeMediaPlayback", 
				"640", 
				"480", 
				"9.0.124", 
				"preview/media/expressInstall.swf", 
				parameters, 
				{
	                allowFullScreen: "true"
	            }, 
				{
	                name: "StrobeMediaPlayback"
	            });
			}            
            
        
  //文档预览 配置       
		var i18n = {
         file:
         {
            title: "文档预览"
         },
 		label:
 		{
 			actualSize: "实际尺寸",
          	fitPage: "适合页面",
         	fitWidth: "适合宽度",
         	fitHeight: "适合高度",
         	fullscreen: "全屏显示",
         	fullwindow: "最大化",
         	fullwindow_escape: "请按ESC退出最大化窗口",
         	page: "页码",
         	pageOf: "/"
 		},
 		errors: {
         	"error": "未知的错误，内容无法显示。",
           	"content": "错误的文件格式。仅支持 png、jpg、gif、swf格式。",
           	"io": "无法从服务器加载预览文件，请检查预览地址是否正确！"
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
var url = "preview/media/0000000000000094.swf?noCache=" + (new Date().getTime());
var flashvars =
{
	fileName: i18n.file.title,
         paging: true,  // If false only 1 page is displayed at a time
         url: url,
         jsCallback: "onPreviewEvent", // Optional but needed if Maximize button shall work
         jsLogger: "myPreviewLogger", // Optional
         show_fullscreen_button: true, // Set to false to hide "Fullscreen" button
         show_fullwindow_button: false,	// Set to false to hide "Maximize" button
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

			function changeDocumentUrl(url){
				flashvars.url = url;
				var width = document.body.clientWidth*0.6;
				var height = document.body.clientHeight*0.8;
				swfobject.embedSWF("/res_root/fjmh/search/preview/WebPreviewer/WebPreviewer.swf", "previewer", width, height, "9.0.124", false, flashvars, params, attributes);
			}
			


//
jQuery(function(){
	//图片预览js
	jQuery("a[ref=viewimage]").fancybox({
				'titlePosition'		: 'float',
				'overlayColor'		: '#000',
				'overlayOpacity': 0.7,
				'overlayShow'	: true, 
				'transitionIn'	: 'elastic',
				'transitionOut'	: 'elastic'
	});
	//视频预览js
	jQuery("a[ref=viewvideo]").each(function(index){
		var videoUrl = jQuery(this).attr("url");
		jQuery(this).fancybox({
			'titlePosition'		: 'float',
			'overlayColor'		: '#000',
			'overlayOpacity': 0.7,
			'overlayShow'	: true, 
			'transitionIn'		: 'none',
			'transitionOut'		: 'none',
			
			onStart		:	function() {
				if(videoUrl)
					changeVideoUrl(videoUrl);
			},
			onCancel	:	function() {
				//alert('Canceled!');
			},
			onComplete	:	function() {
	            jQuery("#rj_video").parent().css("overflow","");
			},
			onCleanup	:	function() {
	            //return window.confirm('Close?');
			},
			onClosed	:	function() {
	            //alert('Closed!');
			}
		});
	});
	//文档预览js
	/*
	jQuery("#rj_document").mouseover(function (){
		document.body.onmousewheel = function(){alert("helllll")};
	});
	*/
	jQuery("a[ref=viewdocument]").each(function(index){
		var documentUrl = jQuery(this).attr("url");
		jQuery(this).fancybox({
				'titlePosition'		: 'float',
				'overlayColor'		: '#000',
				'overlayOpacity': 0.7,
				'overlayShow'	: true, 
				'transitionIn'		: 'none',
				'transitionOut'		: 'none',
				onStart		:	function() {
					var stylestr = "width:"+(document.body.clientWidth*0.6)+";height:"+(document.body.clientHeight*0.8)+";*width:"+(document.body.clientWidth*0.6+20)+";";
					jQuery("#rj_document").attr("style",stylestr);
					if(documentUrl)
						changeDocumentUrl(documentUrl+"?noCache=" + (new Date().getTime()));
				},
				onCancel	:	function() {
					//alert('Canceled!');
				},
				onComplete	:	function() {
					//jQuery("#rj_document").parent().css("overflow","");
				},
				onCleanup	:	function() {
		            //return window.confirm('Close?');
				},
				onClosed	:	function() {
		            //alert('Closed!');
				}
			});
	});
	
	
	
});






