package com.adrian.blog.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrian.blog.model.Combustible;
import com.adrian.blog.repository.ICombustibleRepository;
import com.adrian.blog.service.ICombustibleService;

@Service("combustibleService")
public class CombustibleServiceImpl implements ICombustibleService {

	@Autowired
	private ICombustibleRepository combustibleRepository;

	@Override
	public Collection<Combustible> findAll() {
		return combustibleRepository.findAll();
	}

}
