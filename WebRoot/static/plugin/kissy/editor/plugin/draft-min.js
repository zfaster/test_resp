/*
Copyright 2014, KISSY v1.47
MIT Licensed
build time: May 22 12:20
*/
KISSY.add("editor/plugin/draft","editor,json,event,./local-storage,overlay,./menubutton".split(","),function(k,g){function h(a,b,c){for(a=a+"";a.length<b;)a=c+a;return a}function n(a){typeof a==="number"&&(a=new Date(a));return a instanceof Date?[a.getFullYear(),"-",h(a.getMonth()+1,2,"0"),"-",h(a.getDate(),2,"0")," ",h(a.getHours(),2,"0"),":",h(a.getMinutes(),2,"0"),":",h(a.getSeconds(),2,"0")].join(""):a}function o(a,b){this.editor=a;this.config=b;this._init()}function p(a,b){var c=new o(a,b);a.on("destroy",
function(){c.destroy()})}function q(a){this.config=a||{}}var l=g("editor"),r=g("json"),s=g("event"),f=g("./local-storage"),t=g("overlay"),u=g("./menubutton"),i=k.Node,j=l.Utils.addRes,v=l.Utils.destroyRes;k.augment(o,{_getSaveKey:function(){var a=this.config;return a.draft&&a.draft.saveKey||"ks-editor-draft-save20110503"},_getDrafts:function(){if(!this.drafts){var a=f.getItem(this._getSaveKey()),b=[];a&&(b=f===window.localStorage?r.parse(k.urlDecode(a)):a);this.drafts=b}return this.drafts},_init:function(){var a=
this,b=a.editor,c=b.get("prefixCls"),d=b.get("statusBarEl"),e=this.config;e.draft=e.draft||{};a.draftInterval=e.draft.interval=e.draft.interval||5;a.draftLimit=e.draft.limit=e.draft.limit||5;d=(new i('<div class="'+c+'editor-draft"><span class="'+c+'editor-draft-title">\u5185\u5bb9\u6b63\u6587\u6bcf'+e.draft.interval+"\u5206\u949f\u81ea\u52a8\u4fdd\u5b58\u4e00\u6b21\u3002</span></div>")).appendTo(d);a.timeTip=(new i('<span class="'+c+'editor-draft-time"/>')).appendTo(d);var g=(new i(k.substitute('<a href="#" onclick="return false;" class="{prefixCls}editor-button {prefixCls}editor-draft-save-btn ks-inline-block" style="vertical-align:middle;padding:1px 9px;"><span class="{prefixCls}editor-draft-save"></span><span>\u7acb\u5373\u4fdd\u5b58</span></a>',
{prefixCls:c}))).unselectable(void 0).appendTo(d),f=(new u({render:d,collapseOnClick:true,width:"100px",prefixCls:c+"editor-",menu:{width:"225px",align:{points:["tr","br"]}},matchElWidth:false,content:"\u6062\u590d\u7f16\u8f91\u5386\u53f2"})).render();a.versions=f;f.on("beforeCollapsedChange",function w(b){if(!b.newValue){f.detach("beforeCollapsedChange",w);a.sync()}});g.on("click",function(b){a.save(false);b.halt()});j.call(a,g);b.get("textarea")[0].form&&function(){function c(){a.save(true)}var d=b.get("textarea")[0].form;s.on(d,
"submit",c);j.call(a,function(){s.remove(d,"submit",c)})}();var h=setInterval(function(){a.save(true)},a.draftInterval*6E4);j.call(a,function(){clearInterval(h)});f.on("click",a.recover,a);j.call(a,f);a.holder=d;if(e.draft.helpHTML){var m=(new i('<a tabindex="0" hidefocus="hidefocus" class="'+c+'editor-draft-help" title="\u70b9\u51fb\u67e5\u770b\u5e2e\u52a9" href="javascript:void(\'\u70b9\u51fb\u67e5\u770b\u5e2e\u52a9 \')">\u70b9\u51fb\u67e5\u770b\u5e2e\u52a9</a>')).unselectable(void 0).appendTo(d);m.on("click",function(){m[0].focus();a.helpPopup&&a.helpPopup.get("visible")?a.helpPopup.hide():
a._prepareHelp()});m.on("blur",function(){a.helpPopup&&a.helpPopup.hide()});a.helpBtn=m;j.call(a,m);l.Utils.lazyRun(a,"_prepareHelp","_realHelp")}j.call(a,d)},_prepareHelp:function(){var a=this.editor.get("prefixCls"),b=new i(this.config.draft.helpHTML||""),c=new i('<div style="height:0;position:absolute;font-size:0;width:0;border:8px #000 solid;border-color:#000 transparent transparent transparent;border-style:solid dashed dashed dashed;border-top-color:#CED5E0;"><div style="height:0;position:absolute;font-size:0;width:0;border:8px #000 solid;border-color:#000 transparent transparent transparent;border-style:solid dashed dashed dashed;left:-8px;top:-10px;border-top-color:white;"></div></div>');
b.append(c);b.css({border:"1px solid #ACB4BE","text-align":"left"});this.helpPopup=(new t({content:b,prefixCls:a+"editor-",width:b.width()+"px",zIndex:l.baseZIndex(l.ZIndexManager.OVERLAY),mask:false})).render();this.helpPopup.get("el").css("border","none");this.helpPopup.arrow=c},_realHelp:function(){var a=this.helpPopup,b=this.helpBtn,c=a.arrow;a.show();b=b.offset();a.get("el").offset({left:b.left-a.get("el").width()+17,top:b.top-a.get("el").height()-7});c.offset({left:b.left-2,top:b.top-8})},disable:function(){this.holder.css("visibility",
"hidden")},enable:function(){this.holder.css("visibility","")},sync:function(){var a;a=this.draftLimit;var b=this.timeTip,c=this.versions,d=this._getDrafts(),e;d.length>a&&d.splice(0,d.length-a);c.removeItems(true);for(a=0;a<d.length;a++){e=d[a];e=(e.auto?"\u81ea\u52a8":"\u624b\u52a8")+"\u4fdd\u5b58\u4e8e : "+n(e.date);c.addItem({content:e,value:a})}d.length||c.addItem({disabled:true,content:"\u5c1a\u65e0\u5386\u53f2",value:""});b.html(e);f.setItem(this._getSaveKey(),f===window.localStorage?encodeURIComponent(r.stringify(d)):d)},save:function(a){var b=
this._getDrafts(),c=this.editor.getFormatData();if(c){if(b[b.length-1]&&c===b[b.length-1].content)b.length=b.length-1;this.drafts=b.concat({content:c,date:(new Date).getTime(),auto:a});this.sync()}},recover:function(a){var b=this.editor,c=this._getDrafts(),d=a.target.get("value");if(window.confirm("\u786e\u8ba4\u6062\u590d "+n(c[d].date)+" \u7684\u7f16\u8f91\u5386\u53f2\uff1f")){b.execCommand("save");b.setData(c[d].content);b.execCommand("save")}a.halt()},destroy:function(){v.call(this)}});k.augment(q,{pluginRenderUI:function(a){var b=this.config;
f.ready?f.ready(function(){p(a,b)}):p(a,b)}});return q});
