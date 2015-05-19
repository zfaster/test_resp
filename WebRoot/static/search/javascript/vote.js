var isBt = false;
var Jq=jQuery;
function IsvoteBt(n){
	if(n&&!isVoted){
		document.getElementById("vote_bt").style.display = "block";
		document.getElementById("vote_over_bt").style.display = "none";
	}
	if(!n&&!isVoted){
		document.getElementById("vote_bt").style.display = "none";
		document.getElementById("vote_over_bt").style.display = "block";
	}
	if(isVoted){
		document.getElementById("vote_bt").style.display = "none";
		document.getElementById("vote_over_bt").style.display = "none";
		document.getElementById("vote_info").style.display = "none";
		document.getElementById("vote_voted_bt").style.display = "block";
	}
	if(isBt&&isVoted){
		showVoteSuccess();
	}
}
function clickVote(){
	isVoted = true;
	isBt = true;
	showdd();	
}
function showVoteSuccess(){
	Jq("#vote_pop_info").show(0,function(){
	Jq("#vote_pop_info")
		.animate({
			filter: "alpha(opacity=0)"
		}, 1000)
		.animate({ 
			filter: "alpha(opacity=1)"
		}, 1000);
	})
	.hide(0,function(){
	Jq("#vote_pop_info")
		.animate({
			filter: "alpha(opacity=0)"
		}, 1000);
	})
	isBt =false;
}

function changeInputStatue(){
	var inp = document.getElementById("vote").getElementsByTagName("input");
	for(var j=0; j<inp.length; j++){
		if(isVoted){
			if(inp[j].getAttribute("type").toLocaleLowerCase()=="checkbox"||inp[j].getAttribute("type").toLocaleLowerCase()=="radio"){
				inp[j].disabled = "disabled";
			}
		}
	}	
}
function showdd(){
	var dds = document.getElementById("vote").getElementsByTagName("dd");
	for(var i=0; i<dds.length; i++){
		if(!isVoted&&dds[i].style.display == "none"){
			dds[i].style.display = "block";							
		}else if(!isVoted&&dds[i].style.display == "block"){
			dds[i].style.display = "block";		
		}else {
			dds[i].style.display = "block";	
		}
	}
	changeInputStatue();
	//Jq(function(){
		for(var j=1; j<=5; j++ ){
			Jq.each(Jq(".li_s"+j),function(){
				var widthValue = Jq(this).css("width");
				//alert(widthValue);
				Jq(this).css("width","0px");
				Jq(this).animate({ 
					width: widthValue
				}, 1000 );
			});
		}
	//});
	IsvoteBt('');
}
function toggleClass(target){
	var inputs = target.getElementsByTagName("input");
	var isChecked = false; 
	for(var j=0; j<inputs.length; j++){
		//alert(inputs[j].checked);
		if(inputs[j].checked&&(inputs[j].getAttribute("type").toLocaleLowerCase()=="checkbox"||inputs[j].getAttribute("type").toLocaleLowerCase()=="radio")){
			target.className = "clearfix v_hover";
			//target.setAttribute("class","clearfix v_hover");
			isChecked=true;
			break;
		}else{
			target.className = "clearfix";
			//target.setAttribute("class","clearfix");
		}
	}
	for(var j=0; j<inputs.length; j++){
		if(inputs[j].getAttribute("type").toLocaleLowerCase()=="text"&&isChecked){
			inputs[j].style.display = "";
		}else if(inputs[j].getAttribute("type").toLocaleLowerCase()=="text"&&!isChecked){
			inputs[j].style.display = "none";	
		}	
	}
}
Jq(function(){
	var dl = document.getElementById("vote").getElementsByTagName("dl");
	var inp = document.getElementById("vote").getElementsByTagName("input");
	for(var i=0; i<dl.length; i++){
		dl[i].onmouseover = function(){
			this.className = "clearfix v_hover ";
		}
		dl[i].onmouseout = function(){
			toggleClass(this);
		}
		toggleClass(dl[i]);	
		
		var n = 0;
		for(var j=0; j<inp.length; j++){
			if(inp[j].checked){
				n = j+1;
				break;
			}
		}
		IsvoteBt(n);
		
		dl[i].onclick = function(){
			var inputs = this.getElementsByTagName("input");
			for(var j=0; j<inputs.length; j++){
				//alert(inputs[j].disable);
				if(!inputs[j].disabled){
					if(inputs[j].checked&&inputs[j].getAttribute("type").toLocaleLowerCase()=="checkbox"){
						inputs[j].checked=null;
						this.className = "clearfix";
					}else{
						inputs[j].checked="checked";
						this.className = "clearfix v_hover";
					}
					if(inputs[j].getAttribute("type").toLocaleLowerCase()=="text"){
						inputs[j].style.display = "block";
					}
				}
			}
			var dl2 = document.getElementById("vote").getElementsByTagName("dl");
			for(var k=0; k<dl2.length; k++){
				toggleClass(dl2[k]);
				//alert("1");
			}
			
			var inp = document.getElementById("vote").getElementsByTagName("input");
			for(var j=0; j<inp.length; j++){
				if(inp[j].checked){
					var n = 0;
					n = j+1;
					//alert(inp[j].checked.length);
					break;
				}
			}
			
			IsvoteBt(n);
			
		}
	}
	for(var i=0; i<inp.length; i++){
		if(inp[i].getAttribute("type").toLocaleLowerCase()=="checkbox"||inp[i].getAttribute("type").toLocaleLowerCase()=="radio"){
			inp[i].onclick = function(){
				//alert(inp[0].checked);
				if(this.checked){
					this.checked=null;
				}else{
					this.checked="checked";
				}
				for(var j=0; j<inp.length; j++){
					if(inp[j].checked){
						var n = 0;
						n = j+1;
						break;
					}
				}
				IsvoteBt(n);
			}
		}
	}
	changeInputStatue();
	if(isVoted){
		showdd();	
	}
});