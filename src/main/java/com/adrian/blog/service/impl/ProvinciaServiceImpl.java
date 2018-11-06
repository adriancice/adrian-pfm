package com.adrian.blog.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrian.blog.model.Provincia;
import com.adrian.blog.repository.IProvinciaRepository;
import com.adrian.blog.service.IProvinciaService;

@Service
public class ProvinciaServiceImpl implements IProvinciaService {

	@Autowired
	private IProvinciaRepository provinciaRepository;

	@Override
	public Collection<Provincia> findAll() {
		return provinciaRepository.findAll();
	}

}
