/***cookie操作**/
function get_cookie(Name) {
	var search = Name + "="
	var returnvalue = "";
	if (document.cookie.length > 0) {
	offset = document.cookie.indexOf(search)
	if (offset != -1) { // if cookie exists
	offset += search.length
	// set index of beginning of value
	end = document.cookie.indexOf(";", offset);
	// set index of end of cookie value
	if (end == -1)
	end = document.cookie.length;
	returnvalue=unescape(document.cookie.substring(offset, end))
	}
	}
	return returnvalue;
}
function addCookie(objName,objValue,objHours){//添加cookie
	var str = objName + "=" + escape(objValue);
	if(objHours > 0){//为0时不设定过期时间，浏览器关闭时cookie自动消失
	var date = new Date();
	var ms = objHours*3600*1000;
	//var ms = objHours*5*1000;
	date.setTime(date.getTime() + ms);
	str += "; expires=" + date.toGMTString();
	}
	document.cookie = str;
	//alert("添加cookie成功");
}


//防止mouseover mouseout被多次觸發
	function contains(parentNode, childNode) {    
	if (parentNode.contains) {
	        return parentNode != childNode && parentNode.contains(childNode);    
	} else {
	        return !!(parentNode.compareDocumentPosition(childNode) & 16);
	 }
	}
	function checkHover(e,target){
	    if (getEvent(e).type=="mouseover")  {
	        return !contains(target,getEvent(e).relatedTarget||getEvent(e).fromElement) && !((getEvent(e).relatedTarget||getEvent(e).fromElement)===target);
	    } 
	    else {
		    return !contains(target,getEvent(e).relatedTarget||getEvent(e).toElement) && !((getEvent(e).relatedTarget||getEvent(e).toElement)===target);
		}
	}
	function getEvent(e){
	    return e||window.event;
	}
//----------------------------------
