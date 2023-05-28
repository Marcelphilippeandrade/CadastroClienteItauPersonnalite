package com.itau.personnalite.cadastrocliente.validation;

import java.lang.reflect.Field;
import com.itau.personnalite.cadastro.annotation.Nome;

public class ValidadorNome {

	public static <T> boolean validador(T objeto) {
		Class<?> classe = objeto.getClass();
		for (Field field : classe.getDeclaredFields()) {
			if (field.isAnnotationPresent(Nome.class)) {
				Nome nomeComRegexValido = field.getAnnotation(Nome.class);
				try {
					field.setAccessible(true);
					String nome = (String) field.get(objeto);
					return nome.matches(nomeComRegexValido.regex());
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
}
