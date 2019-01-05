package com.adrian.blog.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrian.blog.model.Modelo;
import com.adrian.blog.repository.IModeloRepository;
import com.adrian.blog.service.IModeloService;

@Service
public class ModeloServiceImpl implements IModeloService {

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

	@Override
	public void save(Modelo modelo) {
		// tuve que hacer este apaño porque al guardar un nuevo modelo, se intentaba
		// guardar con un id que ya existia
		modelo.setIdModelo(Math.toIntExact(modeloRepository.count() + 1));
		modeloRepository.save(modelo);
	}

}
