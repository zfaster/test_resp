package com.zzy.base.service;

import java.io.Serializable;

import org.springframework.stereotype.Service;

@Service
public interface BaseService<T> {

	public int save(T entity);

	public int delete(T entity);
	
	public int updateNotNull(T entity);
	
	public T findById(Serializable id);
}