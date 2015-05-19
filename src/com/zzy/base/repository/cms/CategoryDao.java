package com.zzy.base.repository.cms;

import org.apache.ibatis.annotations.Param;

import com.zzy.base.entity.Order;
import com.zzy.base.entity.cms.Category;
import com.zzy.base.repository.MyBatisRepository;

@MyBatisRepository
public interface CategoryDao {

	public abstract Order findById(@Param("id") Long id);
	public abstract Category findByName(String name); 
	public abstract void add(Category category); 
	public abstract String findNameById(Long id); 
	public abstract void deleteById(Long[] array);
}
