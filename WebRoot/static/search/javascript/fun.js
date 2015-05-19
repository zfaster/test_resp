/* 表单相关函数*/

/*
 *对获取元素的封装
 *
 */
function $E(id){
	return document.getElementById(id);
}

/*
 *给指定objId对应的元素设置HTML值
 */
function setHTMLValue(objId,value){
	var obj = $E(objId);
	if(obj){
		obj.innerHTML=value;
	}
}

/*
 *给指定objId对应的元素设置Value值
 */
function setValue(objId,value){
	var obj = $E(objId);
	if(obj){
		obj.value=value;
	}
}

/*
 *给指定objId对应的元素设置样式
 */
function setHTMLValue(objId,className){
	var obj = $E(objId);
	if(obj&&className){
		$E(objId).class=className;
	}
}

/*
 *点击主题处理事件
 *事件源:e
 *目标tid
 */
function subjectClick(e,tid){
	alert("111111");
	var target = $E(tid);
	if(target){
		setHTMLValue(tid,e.innerHTML);
		setHTMLValue("subject",e.innerHTML);
	}
	alert("innerHTML "+$E("innerHTML").innerHTML);
	alert("subject "+$E("subject").value);
}

