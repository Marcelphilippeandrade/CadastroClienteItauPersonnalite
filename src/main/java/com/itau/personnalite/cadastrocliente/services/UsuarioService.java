package com.itau.personnalite.cadastrocliente.services;

import java.util.Optional;

import com.itau.personnalite.cadastrocliente.entidades.Usuario;

public interface UsuarioService {

	/**
	 * Persiste um usuario na base de dados
	 * 
	 * @param usuario
	 * @return usuario
	 */
	Usuario persistirUsuario(Usuario usuario);

	/**
	 * Busca e retorna um usuario dado um CPF
	 * 
	 * @param cpf
	 * @return Optional<Usuario>
	 */
	Optional<Usuario> buscarPorCpf(String cpf);

	/**
	 * Busca e retorna um usuario dado um Email
	 * 
	 * @param email
	 * @return Optional<Usuario>
	 */
	Optional<Usuario> buscarPorEmail(String email);

	/**
	 * Busca e retorna um usuario dado um id
	 * 
	 * @param id
	 * @return Optional<Usuario>
	 */
	Optional<Usuario> buscarPorId(Long id);
}
