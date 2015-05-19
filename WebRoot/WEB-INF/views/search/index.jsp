<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/common/common_jsp.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>Solr Search</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
		<meta http-equiv="Content-Language" content="zh-cn">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="keywords" content=" ">
		<meta name="description" content="">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link href="${ctx }/static/search/style/search_layout.css" rel="stylesheet" type="text/css" />
		<link href="${ctx }/static/search/style/search_master.css" rel="stylesheet" type="text/css" />
		<link href="${ctx }/static/search/style/combobox.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${ctx }/static/search/javascript/jquery-1.6.2.min.js"></script>
<script src="${ctx }/static/plugin/kissy/seed.js" ></script>
		<link rel="stylesheet" href="${ctx }/static/search/preview/style.css" type="text/css"></link>
<script src="${ctx }/static/plugin/kissy/seed.js"></script>
<script type="text/javaScript">
       //层面显示全部js
		function toggleDisplay(e){
			var e1 = $("#"+e.getAttribute("id1"));
			var e2 = $("#"+e.getAttribute("id2"));
			if(e1.css("display")=="none"){
				e1.css("display","block");
				e2.css("display","none");
				$(e).text("显示部分");
			}else{
				e1.css("display","none");
				e2.css("display","block");
				$(e).text("显示全部");
			}
		}
		function changeTab(q){
			if(q){
				window.location.href="${ctx}/search/query.do?indexType="+q;
			}else{
				window.location.href="${ctx}/search/query.do";
			}
			
		}	
</script>
	</head>

	<body>
		<div class="s_list_warp">
			<div class="tool">
			
			</div>
			<div class="s_list_main">
				<div class="s_list_l">
					<div class="s_list_logo">
						<h1>
						</h1>
					</div>
						<div class="s_l_search">
							<div class="on">
								过滤条件
							</div>
							<ul>
								<c:forEach items="${model.filterParam}" var="filterParam" >
									<c:if test="${filterParam.key!=null}">
										<c:if test="${filterParam.key!='q'&&filterParam.key!='orderByTime'&&filterParam.key!='index_type' }">
								     		<li>
								     		<!-- 地址为排除自身过滤条件的其他过滤条件组合 -->
												<a href="<%=basePath%>${filterParam.value.url}" title="${filterParam.value.convertedValue}">${filterParam.value.convertedValue}</a>
											</li>
										</c:if>
									</c:if>
								</c:forEach>
							</ul>
						</div>
						<c:forEach items="${model.facetList}" var="allFacet">
						<c:set var="len" value="${len+1}" />
						<div class="s_search_item">

							<ul>
								<li class="on">
									${allFacet.cnName}
								</li>
								<span id="topid${len}"> 
									<c:forEach items="${allFacet.list}" var="facet" end="5">

										<li>
											<a title="${facet.convertedValue}" href="${ctx}${facet.url}">${facet.convertedValue}(${facet.hit})</a>
										</li>
									</c:forEach> 
								</span>
								<c:if test="${fn:length(allFacet.list)>6}">
									<span id="subtopid${len}" style="display: none"> <c:forEach items="${allFacet.list}" var="facet">

											<li>
												<a title="${facet.convertedValue}" href="${ctx}${facet.url}">${facet.convertedValue}(${facet.hit})</a>
											</li>
										</c:forEach> 
									</span>
									<li>
										<a href="javascript:void(0);" id2="topid${len}" id1="subtopid${len}" onclick="toggleDisplay(this);">显示全部</a>
									</li>
								</c:if>

							</ul>
						</div>
					</c:forEach>

				</div>
				<div class="s_list_r">
					<div class="search_list_tab">
						<div class="tab">
							<ul id="search_tab">

								<li <c:if test="${indexType eq null }">class="on"</c:if> >
									<a href="javascript:changeTab(0)">全部</a>
								</li>
								<li <c:if test="${indexType eq 1 }">class="on"</c:if> >
									<a href="javascript:changeTab(1)">文章</a>
								</li>
								<li <c:if test="${indexType eq 2 }">class="on"</c:if> >
									<a href="javascript:changeTab(2)">附件</a>
								</li>
								</li>
							</ul>
						</div>
							
								<form action="${ctx }/search/query.do" method="get">
									<input name="orderByTime" value="n" type="hidden">
									<input name="indexType" value="${indexType }" type="hidden">
								<div class="con search-combobox" id="combobox">
									<input id="q" name="q" accesskey="s" value="${keyword }"
                               class="search-combobox-input inp"
                               autofocus="true" autocomplete="off"
                               x-webkit-grammar="builtin:translate" />
                               </div>
									<input onclick="this.form.submit();"
class="search_bt" onmousedown="this.className='search_bt_on'" onmouseout="this.className='search_bt'" value="搜 索" type="button">
								
								</form>
					</div>

					<div class="search_abt_info">
						<div class="search_num">
							搜索到约${model.count}项结果(用时${model.searchTime}秒)
						</div>
						<div class="search_is_words">您是不是找：
						<a href="<%=basePath%>${cheak.url}"><em>测试</em></a>&nbsp;
						</div>
                    <c:if test="${model.filterParam['orderByTime'].value=='n'}">
                    	<div class="search_abt">按相关度排序 | <a class="on" href="${ctx}/${model.filterParam['orderByTime'].url}&orderByTime=y">按时间排序</a></div>
					</c:if>
						<c:if test="${model.filterParam['orderByTime'].value=='y'}">
						 <div class="search_abt"   ><a class="on" href="${ctx}/${model.filterParam['orderByTime'].url}&orderByTime=n">按相关度排序 </a>| 按时间排序</div>
						</c:if>
					</div>

					<div class="search_list">
						<ul>
							<c:if test="${model.count eq 0}">
								<p>
									<font size='+1'>找不到相符的内容或信息。</font>
									<br>
									<br>
									<font size='+2'>建议：</font>
									<br>
									<br>
									<font size='+1'>请输入关键词进行搜索。</font>
									<br>
									<br>
									<font size='+1'>请检查输入字词有无错误。</font>
									<br>
									<br>
									<font size='+1'>请换用另外的查询字词。</font>
									<br>
									<br>
									<font size='+1'>请改用较常见的字词。</font>
									<br>
									<br>
								<p />
							</c:if>
						
							<c:if test="${model.resultList ne null}">
								<c:forEach items="${model.resultList}" var="art">
									<c:if test="${art['index_type'].value eq 1}">
										<li>
											<div class="file_info">
												<div class="title">
													<a href="${art['source'].value}" target="_blank">${art['title'].convertedValue}</a>
												</div>
												<div class="info">
													<p class="desc">
												
	 													${art['intro'].convertedValue}
													
													</p>
													<p>
														所属栏目:<a href="http://www.minhou.gov.cn/ca/${art['category'].convertedValue}.htm" target="_blank">${art['category'].convertedValue}</a> 发布机构:<span>${art['source'].convertedValue}</span> 发布时间:<span><fmt:formatDate value="${art['createtime'].value}" pattern="yyyy-MM-dd" /> </span>
													</p>
												</div>
											</div>
										</li>
									</c:if>
									<c:if test="${art['index_type'].value eq 2}">
									<li class="filetype">
										<div class="photo">
										<c:if test="${art['content_type'].convertedValue eq 'image'}">
										<img width="120" height="80" 
												style="margin-top: 10px;" src="${ctx}/search/img/${art['id'].value}">
										</c:if>
										<c:if test="${art['content_type'].convertedValue ne 'image'}">
										<img width="84" height="90" 
												style="margin-top: 10px;" src="${ctx}/search/img/${art['id'].value}">
										</c:if>
											
										</div>
										<div class="file_info">
											<div class="title">[${art['suffix'].value}]<a target="_blank"
													href="#">${art['title'].convertedValue}</a>
											</div>
											<div class="info">
												<p class="desc">${art['intro'].value}</p>
												<p>
													类型：图片  发布时间：<span><fmt:formatDate value="${art['createtime'].value}" pattern="yyyy-MM-dd" /></span>
												</p>
												<p class="url">
													来源：<a
														href="#"
														target="_blank">${art['source'].convertedValue}</a> &nbsp;-&nbsp;<a
														target="blank" class="preview"
														href="${ctx}/search/download/${art['id'].value}">下载</a>
												</p>
											</div>
										</div></li>
								</c:if>
								</c:forEach>
							</c:if>
						</ul>
					</div>
<div class="search_others">
					<table border="0" cellpadding="0" cellspacing="0">
							<tbody>
							<tr>
								<th rowspan="2" valign="top">
									相关搜索：
								</th>
								
								<td valign="top">
									<a href="<%=basePath%>${model.url}&q=${word}">测试</a>
								</td>
								
								</tr>
						</tbody></table>
						
					</div>
			
					<div class="modpage">
						<div class="fenye clearfix">
							<pg:pager url="${ctx}/search/query.do" items="${model.count }" maxPageItems="10" export="currentPageNumber = pageNumber">
							<pg:param name="q" />
							<pg:param name="category" />
							<pg:param name="indexType"/>
								<pg:prev><a href="${pageUrl }" class="up_down_page">上一条</a></pg:prev>
								<pg:pages>
									<c:if test="${currentPageNumber eq pageNumber }">
										<a href="${pageUrl }" class="on">${pageNumber}</a>
									</c:if>
									<c:if test="${currentPageNumber ne pageNumber }">
										<a href="${pageUrl }">${pageNumber}</a>
									</c:if>
								</pg:pages>
								<pg:next>
									<a href="${pageUrl }" class="up_down_page">下一条</a>
								</pg:next>
							</pg:pager>
						</div>
					</div>

				</div>
			</div>
			<div class="search_bottom">
				<p>
					<a href="javascript:void(0);" target="_blank">联系我们</a> |
					<a href="javascript:void(0);" target="_blank">使用帮助</a> |
					<a href="javascript:void(0);">设为主页</a> |
					<a href="javascript:void(0);">加入收藏</a> |
					<a href="javascript:void(0);" target="_blank">RSS订阅服务</a>|
					<a href="javascript:void(0);" target="_blank">网站建议</a>
				</p>
				<!-- <p>
					版权所有 Copyright © 2004-2011 闽侯县人民政府办公室
				</p>
				<p>
					技术支持：
					<a href="http://www.rongji.com/" target="_blank">榕基软件</a>
				</p> -->
			</div>
		</div>
		<div id="fancybox-tmp"></div>
		<div id="fancybox-loading">
			<div></div>
		</div>
		<div id="fancybox-overlay"></div>
		<div id="fancybox-wrap">
			<div id="fancybox-outer">
				<div class="fancybox-bg" id="fancybox-bg-n"></div>
				<div class="fancybox-bg" id="fancybox-bg-ne"></div>
				<div class="fancybox-bg" id="fancybox-bg-e"></div>
				<div class="fancybox-bg" id="fancybox-bg-se"></div>
				<div class="fancybox-bg" id="fancybox-bg-s"></div>
				<div class="fancybox-bg" id="fancybox-bg-sw"></div>
				<div class="fancybox-bg" id="fancybox-bg-w"></div>
				<div class="fancybox-bg" id="fancybox-bg-nw"></div>
				<div id="fancybox-content"></div>
				<a id="fancybox-close"></a>
				<div id="fancybox-title"></div>
				<a href="javascript:;" id="fancybox-left"><span class="fancy-ico" id="fancybox-left-ico"></span> </a><a href="javascript:;" id="fancybox-right"><span class="fancy-ico" id="fancybox-right-ico"></span> </a>
			</div>
		</div>
	</body>
	<script type="text/javascript">

	KISSY.use("event,combobox", function (S, Event, ComboBox, SearchMenuItem) {
	    /*
	     remote dataSource
	     */
	    (function () {

	    	var tmpl = "<div class='item-wrapper'>" +
            "<span class='item-text'>{text}</span>" +
            //"<span class='item-count'>约{count}条记录</span>" +
            "</div>";

	        var basicComboBox = new ComboBox({
	            prefixCls: 'search-',
	            placeholder: '点我搜索',
	            srcNode: S.one("#combobox"),
	            // width: 500,
	            dataSource: new ComboBox.RemoteDataSource({
	                xhrCfg: {
	                    url: '${ctx}/search/suggest.do',
	                    dataType: 'json',
	                    data: {
	                        k: 1,
	                        code: "utf-8"
	                    }
	                },
	                paramName: "q",
	                parse: function (query, results) {
	                    // 返回结果对象数组
	                    return results;
	                },
	                cache: true
	            }),
	            format: function (query, results) {
	                var ret = [];
	                S.each(results, function (r) {
	                    var item = {
	                        // 点击菜单项后要放入 input 中的内容
	                        textContent: r[0],
	                        // 菜单项的
	                        content: S.substitute(tmpl, {
	                            text: r[0],
	                            count: r[1]
	                        })
	                    };
	                    ret.push(item);
	                });
	                return ret;
	            }
	        });
	        basicComboBox.render();
	    })();
	});
  </script>
</html>
