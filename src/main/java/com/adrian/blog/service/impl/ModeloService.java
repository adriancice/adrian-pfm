package com.adrian.blog.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrian.blog.model.Modelo;
import com.adrian.blog.repository.IModeloRepository;
import com.adrian.blog.service.IModeloService;

@Service
public class ModeloService implements IModeloService {

	@Autowired
	private IModeloRepository modeloRepository;

	@Override
	public List<Modelo> findByIdMarca(int idMarca) {
		List<Modelo> modelos = new ArrayList<>();
		for (Modelo m : modeloRepository.findByIdMarca(idMarca)) {
			modelos.add(m);
		}
		return modelos;
	}

}
