package com.adrian.blog.service;

import java.util.Collection;

import com.adrian.blog.model.Favorito;

public interface IFavoritoService {

	Favorito save(Favorito favorito);

	Collection<Favorito> findByIdUser(int idUser);

	Favorito existeFav(int idVehiculo, int idUser);

	void delete(Favorito favorito);

	void deletebyIdUser(int id);

}
