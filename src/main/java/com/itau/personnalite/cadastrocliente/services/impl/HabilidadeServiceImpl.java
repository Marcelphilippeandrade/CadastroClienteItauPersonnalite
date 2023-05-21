package com.itau.personnalite.cadastrocliente.services.impl;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.itau.personnalite.cadastrocliente.entidades.Habilidade;
import com.itau.personnalite.cadastrocliente.repositorios.HabilidadeRepository;
import com.itau.personnalite.cadastrocliente.services.HabilidadeService;

@Service
public class HabilidadeServiceImpl implements HabilidadeService {

	@Autowired
	private HabilidadeRepository habilidadeRepository;

	@Override
	public Page<Habilidade> buscarUsuarioPorId(Long usuarioId, PageRequest pageRequest) {
		return this.habilidadeRepository.findByUsuarioId(usuarioId, pageRequest);
	}

	@Override
	public Optional<Habilidade> buscarPorId(Long id) {
		return this.habilidadeRepository.findById(id);
	}

	@Override
	public Habilidade persistir(Habilidade habilidade) {
		return this.habilidadeRepository.save(habilidade);
	}

	@Override
	public void remover(Long id) {
		this.habilidadeRepository.deleteById(id);
	}
}
