package com.zzy.base.service.cms;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zzy.base.entity.cms.Category;
import com.zzy.base.repository.cms.CategoryDao;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
	@Resource
	private CategoryDao categoryDao;
	@Override
	public void add(Category category) {
		categoryDao.add(category);
	}
	@Override
	public Category findByName(String name) {
		return categoryDao.findByName(name);
	}
	public Category addIfNotExists(String name,String pName){
			Category cat = categoryDao.findByName(name);
			if(cat != null){
				return cat;
			}else{
				Category parent = null;
				if(pName!=null){
					parent = categoryDao.findByName(pName);
					if(parent ==null){
						parent = new Category(null, pName, pName, null);
						categoryDao.add(parent);
					}
				}
				cat = new Category(null, name, name, parent==null?null:parent.getCategoryId());
				categoryDao.add(cat);
				return cat;
			}
	}
	@Override
	public String findNameById(Long id) {
		return categoryDao.findNameById(id);
	}
}
