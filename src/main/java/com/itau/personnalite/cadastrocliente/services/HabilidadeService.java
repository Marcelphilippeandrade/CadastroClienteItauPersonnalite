package com.itau.personnalite.cadastrocliente.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.itau.personnalite.cadastrocliente.entidades.Habilidade;

public interface HabilidadeService {

	/**
	 * Retorna uma lista paginada de habilidade de um determinado usuario
	 * 
	 * @param usuarioId
	 * @param pageRequest
	 * @return
	 */
	Page<Habilidade> buscarUsuarioPorId(Long usuarioId, PageRequest pageRequest);

	/**
	 * Retorna uma habilidade por id
	 * 
	 * @param id
	 * @return Optional<Habilidade>
	 */
	Optional<Habilidade> buscarPorId(Long id);

	/**
	 * Persiste uma habilidade na base de dados
	 * 
	 * @param habilidade
	 * @return Habilidade
	 */
	Habilidade persistir(Habilidade habilidade);

	/**
	 * Remove um habilidade da base de dados
	 * 
	 * @param id
	 */
	void remover(Long id);
}
