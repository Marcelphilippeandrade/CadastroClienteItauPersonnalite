package com.itau.personnalite.cadastrocliente.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itau.personnalite.cadastrocliente.entidades.Usuario;
import com.itau.personnalite.cadastrocliente.repositorios.UsuarioRepository;
import com.itau.personnalite.cadastrocliente.services.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public Usuario persistirUsuario(Usuario usuario) {
		return this.usuarioRepository.save(usuario);
	}

	@Override
	public Optional<Usuario> buscarPorCpf(String cpf) {
		return Optional.ofNullable(this.usuarioRepository.findByCpf(cpf));
	}

	@Override
	public Optional<Usuario> buscarPorEmail(String email) {
		return Optional.ofNullable(this.usuarioRepository.findByEmail(email));
	}

	@Override
	public Optional<Usuario> buscarPorId(Long id) {
		return this.usuarioRepository.findById(id);
	}

}
