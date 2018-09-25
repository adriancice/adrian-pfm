package com.adrian.blog.utils;

/**
 * Clase Enum para las categorias de las entradas
 * 
 * @author Adrian Stan
 *
 */
public enum EnumCategorias {

	CAT_ANDROID(1), CAT_APPLE(2), CAT_MOVILES(3), CAT_TECNOLOGIAS(4);

	private int value;

	private EnumCategorias(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
