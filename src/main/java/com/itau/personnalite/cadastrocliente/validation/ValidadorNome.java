package com.itau.personnalite.cadastrocliente.validation;

import java.lang.reflect.Field;

import com.itau.personnalite.cadastro.annotation.IdadeMinima;
import com.itau.personnalite.cadastro.annotation.Nome;
import com.itau.personnalite.cadastrocliente.dtos.UsuarioDto;

public class ValidadorNome {

	private static final String REGEX_NOME = "^(?:[\\p{Lu}&&[\\p{IsLatin}]])(?:(?:')?(?:[\\p{Ll}&&[\\p{IsLatin}]]))+(?:\\-(?:[\\p{Lu}&&[\\p{IsLatin}]])(?:(?:')?(?:[\\p{Ll}&&[\\p{IsLatin}]]))+)*(?: (?:(?:e|y|de(?:(?: la| las| lo| los))?|do|dos|da|das|del|van|von|bin|le) )?(?:(?:(?:d'|D'|O'|Mc|Mac|al\\-))?(?:[\\p{Lu}&&[\\p{IsLatin}]])(?:(?:')?(?:[\\p{Ll}&&[\\p{IsLatin}]]))+|(?:[\\p{Lu}&&[\\p{IsLatin}]])(?:(?:')?(?:[\\p{Ll}&&[\\p{IsLatin}]]))+(?:\\-(?:[\\p{Lu}&&[\\p{IsLatin}]])(?:(?:')?(?:[\\p{Ll}&&[\\p{IsLatin}]]))+)*))+(?: (?:Jr\\.|II|III|IV))?$";

	public static <T> boolean validador(T objeto) {
		Class<?> classe = objeto.getClass();
		for (Field field : classe.getDeclaredFields()) {
			if (field.isAnnotationPresent(Nome.class)) {
				Nome nomeComRegexValido = field.getAnnotation(Nome.class);

//				if (classe.getClass().isInstance(UsuarioDto.class)) {
//					UsuarioDto usuarioDto = (UsuarioDto) objeto;
//
//					if (usuarioDto.getNome().matches(REGEX_NOME)) {
//						return true;
//					}
//				}
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
