var jq=jQuery.noConflict();
jQuery.fn.rater	= function(options) {
		
	// 默认参数
	var settings = {
		key		: '', //项的唯一标识
		enabled	: true, //是否可用,可以打分
		url		: '', //总评分项必要、分项可选
		method	: 'post',//总评分项必要
	    rank : 10, //平分等级,星星的个数
		minScore		: 0,  //最小分值 如(0-10),能打出的最小数分是1,但这里必须是 0, minScore = 能打出的最小分-每个等级间分数的间隔
		maxScore		: 5,  //最大分数
		title   : null, //评分项的名字
		describe   :['很差','差','一般','好','很好'], //各等级描述
		//currentRank	: 0, //
        currentScore: 0, //当前得分
		after_click	: null,
		before_ajax	: null,
		after_ajax	: null,
		describe_format: null, //自定义描述的显示 function
		isShow:false,//是否显示描述 
		isDescribeEnabled:true,//描述是否用,如果是false描述的div不加入,用于自定义显示描述的位置
		_mouseover:null,
        _mouseout:null,
        items:null //评分的分项 可用于扩展使用
	}; 

	// 自定义参数
	if(options) {  
		jQuery.extend(settings, options); 
	}
	var average = (settings.maxScore - settings.minScore)/ settings.rank;  //等级的间隔分数
   //根据分数判断等级
   var judgeRank = function(s){
		return (Math.floor((s- settings.minScore)/average)-1);
   }
   //根据等级判断分数
   var judgeScore = function(r){
		return settings.minScore + (r+1)*average;
   }
   //判断是否加半个
   var isHalf = function(s){
		var r = judgeRank(s);
		var cs = judgeScore(r);
		return s != judgeScore(judgeRank(s));
   }
   
   var obj = $(this);
   
   var desc = function(data,isShow){
      if(!settings.isDescribeEnabled){
	     return;
	  }
       if(isShow){
	     $(obj).find('.rate-score').show();
		 $(obj).find('.rate-describe').show();
		 var score=data.score.toString().split('.');
		 //var score="5.6".split('.');
		 //$(obj).find('.rate-score').text(data.score[0]);
		 $(obj).find('.rate-score').append("<em>"+score[0]+"</em>");
		 if(score[1]!=undefined){
		 	$(obj).find('.rate-score').append("."+score[1]);
		 }
		 if(typeof describe_format == 'function'){
			 $(obj).find('.rate-describe').text(describe_format(data));
		 } else {
		     $(obj).find('.rate-describe').text(settings.describe[data.rank]);
		 }	
	   }else {
		  if(!settings.isShow){
	         $(obj).find('.rate-describe').hide();
		     $(obj).find('.rate-score').hide();
	      } else {
		      $(obj).find('.rate-score').text(settings.currentScore);
		      if(typeof describe_format == 'function'){
			       $(obj).find('.rate-describe').text(describe_format(data));
		      } else {
		         $(obj).find('.rate-describe').text(settings.describe[data.rank]);
	    	 }	
		  } 
	   }
   }
	
  // 主容器
   var content	= jQuery('<ul class="rater-star"></ul>');
	
	// 当前分数的星星
    var init = function() {
	    var half = isHalf(settings.currentScore);
		var r = judgeRank(settings.currentScore);
        var isLawful = false; //当前分数是否合法
        if((settings.minScore<=settings.currentScore)&&(settings.currentScore <=settings.maxScore)){
		   isLawful = true;
           settings.isShow = true;
		} 	

		for(var i = 0;i < settings.rank;i++) {
    	   var item	= jQuery('<li class="off"></li>');
		   
		   if(isLawful){
		         if(i <= r){
			        item	= jQuery('<li class="on"></li>');
		         } else if(i == r+1 && half) {
			        item	= jQuery('<li class="half"></li>');
		         }
		   }

	       content.append(item);
	    }
	    
	 if(settings.title) {
		 $(obj).append('<h5 class="title" style="float:left">'+ settings.title +':</h5>');
	 }
	 $(obj).append(content);
	
	if(settings.isDescribeEnabled){
//	   $(obj).append($('<span class="rate-score"></span>')); //分数
	   $(obj).append($('<span class="rate-score"></span>')); //分数
	   $(obj).append($('<span class="rate-describe"></span>'));//等级描述
	}	
		
        var data = {
		   score:settings.currentScore,
		   rank: judgeRank(settings.currentScore)
		}
		desc(data,settings.isShow);
	}

	init(settings.currentRank);
	
 
	
   // 添加鼠标悬停/点击事件
if(settings.enabled) {
	 content.find('li').mouseover(function() {
		var rank=0;
		rank = content.find('li').index($(this)[0]);
		
		if(typeof settings._mouseover == 'function') {
		    settings._mouseover({score:judgeScore(rank),rank:rank});
		}
		content.find('li').each(function(i){
			                    if(i<=rank) {
		                           $(this).attr('class' , 'on');
							    }else {
								   $(this).attr('class' , 'off');
								}
								if(i==rank){
								  desc({score:judgeScore(i),rank:i},true);
								}
                               
			                   });
		
	}).mouseout(function() {
            var rank=judgeRank(settings.currentScore);
			var half = isHalf(settings.currentScore);
			var isRun = false;
		
			if(typeof settings._mouseover == 'function') {
		      settings._mouseout({score:settings.currentScore,rank:rank});
		     }
			content.find('li').each(function(i){
			                    if(i<=rank) {
		                           $(this).attr('class' , 'on');
							    } else if(i == (rank+1) && half) {
								   $(this).attr('class' , 'half');
								} else {
								  $(this).attr('class' , 'off');
								}

								if(!isRun) {
								   var data = {
								       score:settings.currentScore,
								       rank: i
								   }
								  desc(data,false);
								 // isRun = true;
								} 
		
			                   });

    }).click(function() {
	     settings.currentRank = content.find('li').index($(this)[0]);
		 settings.currentScore = judgeScore(settings.currentRank);
         settings.isShow = true;
		 var data	= {
			score	: settings.currentScore,
			rank	: settings.currentRank,
			key     : settings.key 
		}
        desc(data, settings.isShow);
        // 处理回调事件
		if (typeof settings.after_click == 'function') {
			settings.after_click(data);
		}
		// 处理ajax调用
		if (settings.url) {		
			jQuery.ajax({
				data		: data,
				type		: settings.method,
				url			: settings.url,
				beforeSend	: function() {
					if (typeof settings.before_ajax == 'function') {
						settings.before_ajax(data);
					}
				},
				success	: function(ret) {
					if (typeof settings.after_ajax == 'function') {
						data.ajax	= ret;
						settings.after_ajax(data);
					}
				}
			});
        }
		
	});
} 	
	return obj;
}

//评分展示
// 数据格式
//	var rateData = {
//		rId     : '',唯一标识
//		enabled	: true, //是否可用,可以打分
//		url		: '',
//		method	: 'post',
//	    rank : 10, //平分等级,星星的个数
//		minScore		: 0,  //最小分值 如(0-10),能打出的最小数分是1,但这里必须是 0, minScore = 能打出的最小分-每个等级间分数的间隔
//		maxScore		: 5,  //最大分数
//		title   : null,
//		describe   :['很差','差','一般','好','很好'], //各等级描述
//      currentScore: 0, //当前得分
//		after_click	: null,
//		before_ajax	: null,
//		after_ajax	: null,
//		describe_format: null, //自定义描述的显示 function
//		isShow:false,//是否显示描述 
//		isDescribeEnabled:true,//描述是否用,如果是false描述的div不加入,用于自定义显示描述的位置
//		_mouseover:null,
//      _mouseout:null,
//      items:[ //评分的分项 可用于扩展使用
//      {
//        		enabled	: true, //是否可用,可以打分
//			    rank : 10, //平分等级,星星的个数
//				minScore		: 0,  //最小分值 如(0-10),能打出的最小数分是1,但这里必须是 0, minScore = 能打出的最小分-每个等级间分数的间隔
//				maxScore		: 5,  //最大分数
//				title   : null,
//				describe   :['很差','差','一般','好','很好'], //各等级描述
//				currentRank	: 0, //当前等级
//		        currentScore: 0, //当前得分
//				after_click	: null,
//				before_ajax	: null,
//				after_ajax	: null,
//				describe_format: null, //自定义描述的显示 function
//				isShow:false,//是否显示描述 
//				isDescribeEnabled:true,//描述是否用,如果是false描述的div不加入,用于自定义显示描述的位置
//				_mouseover:null,
//		        _mouseout:null,
//        	},{},{}] 	
//	};
jQuery.fn.ratingShow1=function(rateData){
	var ratingDiv=$(this).empty();
	ratingDiv.append("<input type=\"hidden\" name=\"action\" value=\""+rateData.url+"\">");
	ratingDiv.append("<input type=\"hidden\" name=\"method\" value=\""+rateData.method+"\">");
	if(rateData){ //设置评分总项
		ratingDiv.append("<div>"+rateData.title+"<input type=\"hidden\" name=\"rateId\" value=\""+rateData.key+"</div>");
		ratingDiv.append("<h5 class=\"title\">总分:</h5>");
		ratingDiv.append("<div id=\""+rateData.key+"\"></div>");
		ratingDiv.append("<input type=\"hidden\" name=\""+rateData.key+"\" value=\""+rateData.currentScore+"\">");
		//可在这里修改rateData
		rateData.title=null;
		ratingDiv.find('#'+rateData.key).rater(rateData);
		if(rateData.items){
			var subRating=$("<div id=\"subRating\"></div>");
			ratingDiv.append(subRating);
			var count=0;
			for(var r in rateData.items){
				count++;
			}
			for(var rate in rateData.items){
				subRating.append("<div id=\""+rateData.items[rate].key+"\" style=\"clear:left;\"></div>");
				rateData.items[rate].after_click=function(data) {
                     ratingDiv.find("input[name=\""+data.key+"\"]").attr('value', data.score);
                     var inputs = subRating.find('input[type="hidden"]');
                     var total ;
                     var score = 0;
                     for(var i=0;i<inputs.length;i++) {
                         score= Number(score) + Number($(inputs[i]).val());
                     }
                     var vv = score/count;
                     rateData.currentScore=vv.toFixed(2);
                     ratingDiv.find("#"+rateData.key).empty();
                     ratingDiv.find("#"+rateData.key).rater(rateData);
                     ratingDiv.find("input[name=\""+rateData.key+"\"]").attr('value', rateData.currentScore);
                 }
                 subRating.append("<input type=\"hidden\" name=\""+rateData.items[rate].key+"\"  value=\""+rateData.items[rate].currentScore+"\">");
                 ratingDiv.find('#'+rateData.items[rate].key).rater(rateData.items[rate]);
			}
		}
	}
	ratingDiv.append("<div style='clear:both;'></div>");
	return ratingDiv;
}
jQuery.fn.digger=function(digg){
	var a=$(this);
	a.attr("id",digg.key);
	a.attr("href","javascript:;");
	a.text(digg.title);
	a.bind("click",{key:digg.key,value:digg.score},function(e){
		jq.ajax({
			type: "POST",
			url: digg.url,
			data: e.data.key+"="+e.data.value+"&objId="+digg.objId+"&mdrId="+digg.mdrId,
			success: function(data){
				var json=eval("("+data+")");//转换为json对象
				if(json.ret==1){
					$("#"+e.data.key).unbind();
					$("#"+e.data.key).bind("click",function(){alert("你已操作过")});
				}else{
					alert(json.ret);
				}
			}
		});
	});
	return a;
}
jQuery.fn.ajaxSubmit=function(url,id){
	var inputs=$("#"+id).find("input[type=\"hidden\"]");
	var data="";
	for(var i=0;i<inputs.length;i++){
		data+=inputs[i].name+"="+inputs[i].value+"&";
	}
	data=data.substr(0,data.length-1);
	jq.ajax({
		type: "POST",
		url: url,
		data: data,
		success: function(data){
			alert(data);
			var json=eval("("+data+")");//转换为json对象
			//ratingDiv.empty();
			//ratingDiv.ratingShow1(data);
		}
	});
	return false;
}

// 搜索页面的评分展示
jQuery.fn.showSearchEvaluate=function(objId,mdrId){
	var evaluate=$(this).empty();
	jq.ajax({
		type: "GET",
		url:"http://localhost:8080/modoer/evaluate.sp?act=showEvaluate",
		data: "objId="+objId+"&mdrId="+mdrId,
		success: function(data){
			var json=eval("("+data+")");//转换为json对象
			
			var rate=$("<div class=\"rater_1 clearfix\"></div>");
			rate.append("<span>评分：</span>");
			var rateHtml=$("<ul></ul>");
			json.rate.title=null; // 去掉总分说明
			rateHtml.rater(json.rate);
			rateHtml.children(":first").attr("class",rateHtml.children(":first").attr("class")+" clearfix")
			rateHtml.find(".rate-score").attr("class","scores");
			rate.append(rateHtml.html());
			
			var diggHtml=$("<a class=\"rater_btn_on\"></a>");
			var digg=$("<div class=\"rater_2 clearfix\"></div>");
			digg.append(diggHtml);
			for(var item in json.digg.items){
				diggHtml.digger(json.digg.items[item]);
				digg.append("<span>"+json.digg.items[item].count+"人认为有用 | "+json.rate.count+"人评分</span>");
				break;
			}
			
			var eva=$("<div class=\"rater\"></div>");
			eva.append(rate);
			eva.append(digg);
			
			evaluate.append(eva);
		}
	});
	return evaluate;
}