
function addEvent( obj, type, fn ) {
    if ( obj.attachEvent ) {
        obj['e'+type+fn] = fn;
        obj[type+fn] = function(){obj['e'+type+fn]( window.event );}
        obj.attachEvent( 'on'+type, obj[type+fn] );
    } else
        obj.addEventListener( type, fn, false );
}
function removeEvent( obj, type, fn ) {
    if ( obj.detachEvent ) {
        obj.detachEvent( 'on'+type, obj[type+fn] );
        obj[type+fn] = null;
    } else
        obj.removeEventListener( type, fn, false );
}

function o_tab(){
	var tab_menu,tab_main;
	var on,off;
	var type;
	function setListener(){
		for( var i = 0; i < tab_menu.length; i++ ){
			tab_menu[i].cur_no = i;
			if( type == "" ){
				addEvent(tab_menu[i],"click",setOnAndOff);
			} else {
				addEvent(tab_menu[i],"mouseover",setOnAndOff);
				addEvent(tab_menu[i],"mouseout",setOnAndOff);
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