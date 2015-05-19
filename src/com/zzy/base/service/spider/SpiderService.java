package com.zzy.base.service.spider;

import java.util.List;

import com.zzy.base.entity.cms.Article;

public interface SpiderService {
	public List<Article> collect (String url);
}
