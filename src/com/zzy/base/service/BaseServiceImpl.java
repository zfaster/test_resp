package com.zzy.base.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zzy.mybatis.mapper.Mapper;

@Service
public abstract class BaseServiceImpl<T> {

	@Autowired
	protected Mapper<T> mapper;

	public int save(T entity) {
		return mapper.insert(entity);
	}

	public int delete(T entity) {
		return mapper.deleteByPrimaryKey(entity);
	}
	public int updateNotNull(T entity){
		return mapper.updateByPrimaryKeySelective(entity);
	}
	
	public T findById(Serializable id){
		return mapper.selectByPrimaryKey(id);
	}

}