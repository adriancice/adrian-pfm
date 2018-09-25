package com.adrian.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.adrian.blog.model.Entrada;
import com.adrian.blog.repository.IEntradaRepository;
import com.adrian.blog.service.IEntradaService;

public class EntradaServiceImpl implements IEntradaService {

	@Autowired
	private IEntradaRepository entradaRepository;

	@Override
	public Entrada save(Entrada entrada) {
		return entradaRepository.save(entrada);
	}

	@Override
	public Boolean delete(int id) {
		if (entradaRepository.existsById(id)) {
			entradaRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public Entrada update(Entrada entrada) {
		return entradaRepository.save(entrada);
	}

	@Override
	public Entrada findById(int id) {
		return entradaRepository.findById(id).get();
	}

}
