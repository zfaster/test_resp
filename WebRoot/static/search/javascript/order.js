	/**
	 * 对链接参数按指定顺序进行排序
	 * @author lzw
	 * @date 2011-12-21
	 */
	 
	 
	 
	/**@param target:要替换的目标区域，某个父元素下的链接
	 * @param orderListArgs:排序数组,按数组的顺序进行参数排序
	 * @return 返回排序完的新链接
	 */
	function orderHref(target,orderListArgs){
		var orderList = [];
		if(orderListArgs&&orderListArgs.length>0){
			orderList = orderListArgs;
		}else{
			return ;
		}
		var aes = target.getElementsByTagName('a');
		if(aes&&aes.length){
			for(var i=0;i<aes.length;i++){
				var ae = aes[i];
				var aeHref = ae.href;
				//不是真正的链接
				//alert('aeHref='+aeHref); 
				//alert(aeHref.search(/javascript:/));
				//alert(aeHref.search(/#/))
				if(aeHref.search(/javascript/)>=0){
					continue ;
				}
				if(aeHref.search(/#/)>=0){
					//alert(aeHref);
					continue ;
				}
				var paramJson = getParamsJson(aeHref);
				var newHref = aeHref.split('?')[0]+"?";
				if(paramJson){
					for(var j=0;j<orderList.length;j++){
						 var field = orderList[j];
						 var fieldValues = paramJson[field];
						 if(fieldValues){
							var value = paramJson[field][0];
							if(value){
								newHref = newHref + field+ "="+ value +"&";
								paramJson[field][1] = 1;
							}
						 }
					}
					//未被匹配的字段添加到链接后面
					for(var field in paramJson){
						var flag = paramJson[field][1];
						if(flag == 0){
							newHref = newHref + field+ "="+ paramJson[field][0] +"&";
						}
					}
					ae.href=newHref.substring(0,newHref.length-1);
				}
			}
		}
	}
	/**
	 * 获取某个链接地址的所有参数JSON
	 * 参数名为JSON的属性，Json值为一个数组，[0]:参数值，[1]:状态值，默认为0
	 */
	function getParamsJson(href) 
	{ 
		var paramsString="";
		// 获取链接中参数部分 
		var queryString = href.substring(href.indexOf("?")+1); 
		
		// 分离参数对 ?key=value&key2=value2 
		var parameters = queryString.split("&"); 
		
		var pos, paraName, paraValue; 
		for(var i=0; i<parameters.length; i++) 
		{ 
			// 获取等号位置 
			pos = parameters[i].indexOf('='); 
			if(pos == -1) { continue; } 
			
			// 获取name 和 value 
			paraName = parameters[i].substring(0, pos); 
			paraValue = parameters[i].substring(pos + 1); 
			if(paraValue&&paraValue.length>0){
				paramsString = paramsString + ",'" + paraName + "':['" + paraValue + "',0]" ;	
			}
		} 
		if(paramsString.length>1){
			paramsString = "({"+paramsString.substring(1)+"})";
		}
		//alert(paramsString);
		return eval(paramsString); 
	}