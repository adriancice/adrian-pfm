package com.adrian.blog.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vehiculos")
public class Vehiculo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true)
	private int id;

	private String marca;

	private String modelo;

	private int precio;

	private int anio;

	private int kilometros;

	private String combustible;

	private String color;

	private int puertas;

	private String foto;

	private String tipoCambio;

	private String descripcion;

	private String provincia;

	@Column(name = "id_user")
	private int idUser;


}
