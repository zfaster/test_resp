package com.zzy.base.repository.order;

import org.apache.ibatis.annotations.Param;

import com.zzy.base.entity.Order;
import com.zzy.base.repository.MyBatisRepository;

@MyBatisRepository
public interface OrderDao {

	public abstract Order findById(@Param("id") Long id);
	
	public abstract void deleteById(Long[] array);
}
