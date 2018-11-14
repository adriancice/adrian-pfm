package com.adrian.blog.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.adrian.blog.model.Foto;
import com.adrian.blog.repository.IFotoRepository;
import com.adrian.blog.service.IFotoService;

@Service("fotoService")
public class FotoServiceImpl implements IFotoService {

	@Autowired
	private IFotoRepository fotoRepository;

	@Override
	public Collection<Foto> findByIdVehiculo(int idVehiculo) {
		return fotoRepository.findByIdVehiculo(idVehiculo);
	}

	@Override
	public Foto save(Foto foto) {
		return fotoRepository.save(foto);
	}

}
