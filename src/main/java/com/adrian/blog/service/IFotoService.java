package com.adrian.blog.service;

import java.util.Collection;

import org.springframework.web.multipart.MultipartFile;

import com.adrian.blog.model.Foto;

public interface IFotoService {

	Foto save(Foto foto);

	Collection<Foto> findByIdVehiculo(int idVehiculo);

}
