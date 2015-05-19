package com.zzy.base.repository.order;

import org.apache.ibatis.annotations.Param;

import com.zzy.base.entity.Person;
import com.zzy.base.repository.MyBatisRepository;

@MyBatisRepository
public interface PersonDao {

	public abstract Person findById(@Param("id") Long id);
	public abstract void deleteById(Long[] array);
}
