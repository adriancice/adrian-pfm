package com.adrian.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Filtro {

	private String palabra;

	private String precioMin;

	private String precioMax;

	private String anioMin;

	private String anioMax;

	private String kmMin;

	private String kmMax;

}
