package mybatis.spring;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.FileUtils;
import org.apache.http.impl.client.AutoRetryHttpClient;
import org.apache.tika.metadata.Metadata;
import org.htmlparser.tags.LinkTag;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zzy.base.entity.cms.Article;
import com.zzy.base.entity.cms.Attachment;
import com.zzy.base.service.cms.ArticleService;
import com.zzy.base.service.cms.AttachmentService;
import com.zzy.base.service.cms.CategoryService;
import com.zzy.base.service.spider.SpiderService;
import com.zzy.base.utils.HttpUtils;
import com.zzy.base.utils.ParseUtils;
import com.zzy.base.utils.TikaBasicUtil;
import com.zzy.base.utils.TikaBasicUtil.FileModel;

public class SpiderTest {
	static ApplicationContext context = null;
	CategoryService categoryService;
	ArticleService articleService;
	SpiderService spiderService;
	AttachmentService attachmentService;
	@Before
	public void init() {
		context = new ClassPathXmlApplicationContext("application*.xml");
		categoryService = (CategoryService) context.getBean("categoryService");
		articleService = (ArticleService) context.getBean("articleService");
		spiderService = (SpiderService) context.getBean("spiderService");
		attachmentService = (AttachmentService) context.getBean("attachmentService");
	}
	
	private Set<String> idSets = new ConcurrentSkipListSet<String>();
	private Set<String> hasVisits = new ConcurrentSkipListSet<String>();
	ExecutorService executor = Executors.newCachedThreadPool();
	ExecutorCompletionService<Boolean> submitService = new ExecutorCompletionService<>(executor);
	File file = new File("D:/data.txt");
	private  void spiderUrl(String url,int level){
		
		//spiderService.collect("http://www.jj59.com/jjart/292431.html");
		String html = HttpUtils.getHtml(new AutoRetryHttpClient(), url);
		//System.out.println(html);
		List<LinkTag> linktags = ParseUtils.parseTags(html, LinkTag.class);
		for(LinkTag link : linktags ){
			String linkStr = link.getLink().trim();
			if(linkStr.lastIndexOf("/")<linkStr.lastIndexOf(".") && !linkStr.contains("list_")){
				final String id = linkStr.substring(linkStr.lastIndexOf("/")+1, linkStr.lastIndexOf("."));
				
				try {
					Integer.parseInt(id);
				} catch (NumberFormatException e) {
					continue;
				}
				if(id.startsWith("0")){
					continue;
				}
				//System.out.println("收集文章："+id+",第:"+idSets.size());
				idSets.add(linkStr);
				
				try {
					bw.write(linkStr);
					bw.newLine();
					//FileUtils.writeStringToFile(file, linkStr+"\n", true);
				} catch (IOException e) {
					e.printStackTrace();
				}
				//spiderService.collect(linkStr);
				//spiderService.collect(linkStr);
			}
			else if(level <= 100){
				
				if((linkStr.startsWith("http://www.jj59.com") || linkStr.contains("list_"))){
					//System.out.println("下钻"+level+"："+linkStr);
					if(linkStr.contains("list_")){
						if(url.contains("list_")){
							url = url.substring(0,url.lastIndexOf("/"));
						}
						if(linkStr.contains("/list_")){
							linkStr = url+linkStr;
						}else{
							linkStr = url+"/"+linkStr;
						}
						
					}
					if(!hasVisits.contains(linkStr)){
						System.out.println(linkStr);
						hasVisits.add(linkStr);
						spiderUrl(linkStr,level+1);
					}
					
				}
			}
			
		}
	}
	private BufferedWriter bw = null;
	
	@Test
	public void getWeb() throws IOException{
		file.deleteOnExit();
			bw = new BufferedWriter(new FileWriter(file));
			spiderUrl("http://www.jj59.com/",1);
			System.out.println(idSets.size());
			//FileUtils.writeLines(file, idSets);
	}
	@Test
	public void spider() throws IOException{
		if(!file.exists()){
			bw = new BufferedWriter(new FileWriter(file));
			spiderUrl("http://www.jj59.com/",1);
			bw.flush();
			bw.close();
			//FileUtils.writeLines(file, idSets);
		}else{
			idSets.addAll(FileUtils.readLines(file));
		}
		
		int i=1;
		for(String link : idSets){
			//System.out.println(link);
			i = i+1;
			if(i<5175){
				continue;
			}
			System.out.println("收集文章："+link+",第:"+(i));
			try {
				spiderService.collect(link);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}
	public boolean isCommonFile(File f){
		if(f.getName().contains(".js")|| f.getName().contains(".css")){
			return false;
		}
		if(f.length()/1024/1024<5 && f.length()>10){
			return true;
		}
		return false;
	}
	public static String basePath = "D:/upload";
	/**
	 * 
	 * 抓取文件
	 */
	@Test
	public void fileSpider(){
		File file = new File("E:\\java\\Workspaces\\doc");
		spiderFile(file);
	}
	public void spiderFile(File file){
		
		if(!file.exists()){
			return;
		}
		if(file.isDirectory()){
			for(File ff : file.listFiles(new FileFilter() {
				
				@Override
				public boolean accept(File pathname) {
					if(pathname.getName().contains(".svn")){
						return false;
					}
					return true;
				}
			})){
				spiderFile(ff);
			}
		}else if(isCommonFile(file)){
			boolean success = true;
			FileModel fm =null;
			Article article= articleService.getRandomArticle();
			article.setCategoryId(articleService.getRelateCategory(article.getArticleId()));
			String path = basePath+"/"+article.getCategoryId();
			File dest = new File(path);
			if(!dest.exists())
				dest.mkdir();
			try {
				FileUtils.copyFileToDirectory(file, dest);
				fm = TikaBasicUtil.fileToFileModel(file);
				if(fm==null){
					success = false;
				}
			} catch (Exception e) {
				success = false;
				e.printStackTrace();
			}
			if(success){
				Attachment att = new Attachment();
				
				System.out.println(file.getAbsolutePath());
				Metadata meta = fm.getMetadata();
				String filename = meta.get(Metadata.RESOURCE_NAME_KEY);
				String content = fm.getContentHandler().toString();
				String contentType = meta.get(Metadata.CONTENT_TYPE);
				att.setArticleId(article.getArticleId());
				att.setTitle(filename);
				
				att.setContent(content+"  "+article.getTitle());
				Calendar c = Calendar.getInstance();
				c.add(Calendar.YEAR, -new Random().nextInt(4));
				c.add(Calendar.MONTH, -new Random().nextInt(11));
				c.add(Calendar.DAY_OF_YEAR, -new Random().nextInt(28));
				att.setUploadTime(c.getTime());
				att.setPath(path+"/"+filename);
				att.setContentType(contentType);
				attachmentService.add(att);
			}
			
			
		}
		
	}
}
