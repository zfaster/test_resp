package com.zzy.base.utils;

import java.util.ArrayList;
import java.util.List;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.ScriptTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class ParseUtils {

	public static <T extends TagNode> List<T> parseTags(String html,final Class<T> tagType,final String arrtibuteName,final String arrtibuteValue){
		try {
			Parser parser = new Parser();
			parser.setInputHTML(html);
			
			NodeList tagList = parser.parse(new NodeFilter(){

				public boolean accept(Node node) {
					if(node.getClass() == tagType){
						if(arrtibuteName == null){
							return true;
						}
						T t = (T)node;
						if(t.getAttribute(arrtibuteName)!=null&&t.getAttribute(arrtibuteName).equals(arrtibuteValue)){
							return true;
						}
					}
					return false;
				}
			});
			List<T> tags = new ArrayList<T>();
			for(int i = 0;i<tagList.size();i++){
				T mt = (T)tagList.elementAt(i);
				tags.add(mt);
			}
			return tags;
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T extends TagNode> List<T> parseTags(String html,final Class<T> tagType){
		return parseTags(html,tagType,null,null);
	}
	
	public static String modifyImageUrl(String html,String prefix){
		try {
			StringBuffer sb = new StringBuffer();
			Parser parser = new Parser();
			parser.setInputHTML(html);
			NodeList nodelist = parser.parse(new NodeFilter(){
				public boolean accept(Node arg0) {
					return true;
				}
			});
			for(int i = 0;i<nodelist.size();i++){
				Node node = nodelist.elementAt(i);
				if(node instanceof ImageTag){
					ImageTag it = (ImageTag)node;
					String url = it.getImageURL().substring(it.getImageURL().lastIndexOf("/")+1);
					it.setImageURL("upload_image/"+prefix+"_"+url);
					sb.append(it.toHtml());
				}else if(node instanceof TextNode){
					TextNode tn = (TextNode)node;
					sb.append(tn.getText());
				}else if(node instanceof LinkTag){
					LinkTag lt = (LinkTag)node;
					if(lt.getAttribute("class").equals("qzOpenerDiv")){
						sb.append(lt.toHtml());
					}
				}else if(node instanceof ScriptTag){
					ScriptTag st = (ScriptTag)node;
					if(st.getAttribute("src")!=null && st.getAttribute("src").equals("http://qzonestyle.gtimg.cn/qzone/app/qzlike/qzopensl.js#jsdate=20110603&style=3&showcount=1&width=130&height=30"))
					sb.append(st.toHtml());
				}else if(node instanceof TagNode){
					TagNode tagNode = (TagNode)node;
					sb.append("<");
					sb.append(tagNode.getText());
					sb.append(">");
				}
			}
			return sb.toString();
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return null;
	}
}
