package com.igh.crud.service;

import java.util.Map;

import org.springframework.data.domain.Page;

public interface PageableMapper<T> {

	public Map<String, Object> mapperPageable(Page<T> mapper);
}
