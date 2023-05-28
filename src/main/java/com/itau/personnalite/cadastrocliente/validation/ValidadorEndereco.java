package com.itau.personnalite.cadastrocliente.validation;

import java.lang.reflect.Field;

import com.itau.personnalite.cadastro.annotation.Endereco;

public class ValidadorEndereco {

	public static <T> boolean validador(T objeto) {
		Class<?> classe = objeto.getClass();
		for (Field field : classe.getDeclaredFields()) {
			if (field.isAnnotationPresent(Endereco.class)) {
				Endereco enderecoComRegexValido = field.getAnnotation(Endereco.class);
				try {
					field.setAccessible(true);
					String endereco = (String) field.get(objeto);
					return endereco.matches(enderecoComRegexValido.regex());
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
}
