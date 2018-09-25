package com.adrian.blog.service;

import com.adrian.blog.model.Entrada;

/**
 * Interfaz de las entradas
 * 
 * @author Adrian Stan
 *
 */
public interface IEntradaService {

	Entrada save(Entrada entrada);

	Boolean delete(int id);

	Entrada update(Entrada entrada);

	Entrada findById(int id);

}
