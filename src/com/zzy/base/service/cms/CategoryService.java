package com.zzy.base.service.cms;

import com.zzy.base.entity.cms.Category;

public interface CategoryService {
	public void add(Category category);
	public Category findByName(String name);
	public Category addIfNotExists(String name,String pName);
	public String findNameById(Long id);
}
