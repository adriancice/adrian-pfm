package com.adrian.blog.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrian.blog.model.Marca;
import com.adrian.blog.repository.IMarcaRepository;
import com.adrian.blog.service.IMarcaService;

@Service("marcaService")
public class MarcaServiceImpl implements IMarcaService {

	@Autowired
	private IMarcaRepository marcaRepository;

	@Override
	public Collection<Marca> findAll() {
		return marcaRepository.findAll();
	}

	@Override
	public Marca findByIdMarca(int id) {
		return marcaRepository.findByIdMarca(id);
	}

	@Override
	public Marca findByMarca(String marca) {
		return marcaRepository.findByMarca(marca);
	}

}
