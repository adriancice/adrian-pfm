package com.adrian.blog.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrian.blog.model.Favorito;
import com.adrian.blog.repository.IFavoritoRepository;
import com.adrian.blog.service.IFavoritoService;

@Service
public class FavoritoServiceImpl implements IFavoritoService {

	@Autowired
	private IFavoritoRepository favoritoRepository;

	@Override
	public Collection<Favorito> findByIdUser(int idUser) {
		return favoritoRepository.findByIdUser(idUser);
	}

	@Override
	public Favorito save(Favorito favorito) {
		return favoritoRepository.save(favorito);
	}

	@Override
	public Favorito existeFav(int idVehiculo, int idUser) {

		for (Favorito f : favoritoRepository.findByIdUser(idUser)) {
			if (f.getIdVehiculo() == idVehiculo) {
				return f;
			}
		}
		return null;
	}

	@Override
	public void delete(Favorito favorito) {
		favoritoRepository.delete(favorito);

	}

}
