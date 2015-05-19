 var oneform = document.searchForm;
function init()
{
	//对默认表单进行负值
	oneform = document.searchForm;
}


function submitornot(){
if (trim(oneform.q.value).length==0) { 
   return false; 
 } else {
	var url = "/search.do";
	var pars = "q="+encodeURIComponent(encodeURIComponent(trim(oneform.q.value)));
	for(i=0;i<oneform.p.length;i++){
		if(oneform.p[i].checked){
			pars += "&p="+oneform.p[i].value;
		}
	}
	for(i=0;i<oneform.l.length;i++){
		if(oneform.l[i].checked){
			pars += "&l="+oneform.l[i].value;
		}
	}
	if(oneform.ls.value!=""){
		pars += "&ls="+oneform.ls.value;
	}
	if(oneform.le.value!=""){
		pars += "&le="+oneform.le.value;
	}
	for(i=0;i<oneform.s.length;i++){
		if(oneform.s[i].checked){
			pars += "&s="+oneform.s[i].value;
		}
	}
	
	for(i=0;i<oneform.op.length;i++){
		if(oneform.op[i].checked){
			pars += "&op="+oneform.op[i].value;
		}
		
	}
	for(i=0;i<oneform.r.length;i++){
		if(oneform.r[i].checked){
		pars += "&r="+oneform.r[i].value;	
		}
	}
	window.location.href=url+"?"+pars; 
	return false;
  }
}

function submitEncoding(Encoding){
	if (trim(oneform.q.value).length==0) { 
   return false; 
 } else {
	var url = "/search.do";
	var pars = "q="+encodeURIComponent(encodeURIComponent(trim(document.searchForm.q.value)));
	window.location.href=url+"?"+pars+"&e="+Encoding;  
	return false;
  }
}
function searchSubmit(){
if (trim(oneform.q.value).length==0) { 
   return false; 
 } else {
 	if(oneform.st[0].checked){
 		var url = "./wArSearch.sp";
 	}else{
 		var url = "/fileSearch.do";
 	}
	var pars = "act=index&"+"q="+encodeURIComponent(encodeURIComponent(trim(oneform.q.value)));
	window.location.href=url+"?"+pars;
	return false;
  }
}
function submitornotIndex(Encoding){
	oneform = document.searchForm;
if (trim(oneform.q.value).length==0) { 
   return false; 
 } else {
	 var url ;
 	if(oneform.st.value == "ar"){
 		url = "/search.do";
 	}else{
 		url = "/fileSearch.do";
 	}
	
	var pars = "q="+encodeURIComponent(encodeURIComponent(trim(oneform.q.value)));
	var links =  url+"?"+pars+"&e="+Encoding;
	//window.location.href= links;
	window.open(links,"_blank");
	return false;
  }
}
function submitornot2(){
	if (trim(oneform.q.value).length==0) { 
      return false; 
    } else {
	oneform.q.value=encodeURIComponent(encodeURIComponent(oneform.q.value));
	return true;
  }  
}



function changeDate(){
document.getElementById("betweenDate").checked="true";
}
function changeDateClear(){

document.getElementById("bis").value="";
document.getElementById("bie").value="";
}

function trim(str){  //删除左右两端的空格    
 return str.replace(/(^\s*)|(\s*$)/g, "");    
}    
function ltrim(str){  //删除左边的空格    
 return str.replace(/(^\s*)/g,"");    
}    
function rtrim(str){  //删除右边的空格    
 return str.replace(/(\s*$)/g,"");    
}  