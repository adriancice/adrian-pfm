package com.adrian.blog.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrian.blog.model.Anio;
import com.adrian.blog.repository.IAnioRepository;
import com.adrian.blog.service.IAnioService;

@Service("anioService")
public class AnioServiceImpl implements IAnioService {

	@Autowired
	private IAnioRepository anioRepository;

	@Override
	public Collection<Anio> findAll() {

		ArrayList<Anio> anios = (ArrayList<Anio>) anioRepository.findAll();
		Collection<Anio> aniosAlReves = new ArrayList<>();

		for (int i = anioRepository.findAll().size(); i-- > 0;) {
			aniosAlReves.add(anios.get(i));
		}
		return aniosAlReves;
	}

}
