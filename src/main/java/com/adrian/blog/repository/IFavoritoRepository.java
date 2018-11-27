package com.adrian.blog.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.adrian.blog.model.Favorito;

@Repository("favoritoRepository")
public interface IFavoritoRepository extends CrudRepository<Favorito, Integer> {

	Collection<Favorito> findByIdUser(int idUser);

}
