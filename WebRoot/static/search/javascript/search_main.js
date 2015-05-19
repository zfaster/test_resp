function o_tab(){
	var tab_menu,tab_main;
	var on,off;
	var type;
	function setListener(){
		for( var i = 0; i < tab_menu.length; i++ ){
			tab_menu[i].cur_no = i;
			if( type == "" ){
				tab_menu[i].onclick = setOnAndOff;
			} else {
				tab_menu[i].onmouseover = setOnAndOff;
				tab_menu[i].onmouseout = setOnAndOff;
			}
		}
	}
	
	function setOnAndOff(){
		for( var i=0; i<tab_menu.length; i++ ){
			document.getElementById(tab_main+(i+1)).style.display = ( i==this.cur_no )?"block":"none";
			tab_menu[i].className = ( i==this.cur_no )?on:off;
		}
		return false;
	}
	
	this.init = function( tab_menu1,tagName,tab_main1,on1,off1,no1,type1 ){
		tab_menu = document.getElementById(tab_menu1).getElementsByTagName( tagName );
		tab_main = tab_main1;
		on = on1;
		off = off1;
		type = type1;
		tab_menu[no1-1].className = on;
		document.getElementById(tab_main+no1).style.display = "block";
		setListener();
	}
}

var flag=false;
function drawimage(imgd,iwidth,iheight){
	//参数(图片,允许的宽度,允许的高度)
	var image=new Image();
	image.src=imgd.src;
	if(image.width>0 && image.height>0){
		flag=true;
		if(image.width / image.height >= iwidth/iheight){
			if(image.width>iwidth){
				imgd.width=iwidth;
				imgd.height=(image.height*iwidth)/image.width;
			}else{
				imgd.width=image.width;
				imgd.height=image.height;
			}
		}else{
			if(image.height>iheight){
				imgd.height=iheight;
				imgd.width=(image.width*iheight)/image.height;
			}else{
				imgd.width=image.width;
				imgd.height=image.height;
			}
		}
	}
	imgd.style.marginTop =(iheight - imgd.height)+"px";
}
