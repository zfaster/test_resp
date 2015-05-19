package com.zzy.base.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonMapper {

	private static final ObjectMapper mapper = new ObjectMapper();

	private JacksonMapper() {

	}

	public static ObjectMapper getInstance() {

		return mapper;
	}

}