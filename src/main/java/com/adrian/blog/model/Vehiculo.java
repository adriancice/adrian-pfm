package com.adrian.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vehiculos")
public class Vehiculo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true)
	private int id;

	private String matricula;

	private String marca;

	private String modelo;

	private int precio;

	private int anio;

	private int kilometros;

	private String combustible;

	private String color;

	private int puertas;

}
