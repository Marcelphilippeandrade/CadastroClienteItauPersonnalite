package com.itau.personnalite.cadastrocliente.validation;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.Period;
import com.itau.personnalite.cadastro.annotation.IdadeMinima;
import com.itau.personnalite.cadastrocliente.utils.DataUtil;

public class ValidadorIdadeMinima {

	public static <T> boolean validador(T objeto) {
		Class<?> classe = objeto.getClass();

		for (Field field : classe.getDeclaredFields()) {
			if (field.isAnnotationPresent(IdadeMinima.class)) {
				IdadeMinima idadeMinima = field.getAnnotation(IdadeMinima.class);
				try {
					field.setAccessible(true);
					String dataString = (String) field.get(objeto);
					DataUtil data = new DataUtil();
					LocalDate dataNascimento = data.transformarStringEmLocalDate(dataString);
					return Period.between(dataNascimento, LocalDate.now()).getYears() >= idadeMinima.valor();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
}
