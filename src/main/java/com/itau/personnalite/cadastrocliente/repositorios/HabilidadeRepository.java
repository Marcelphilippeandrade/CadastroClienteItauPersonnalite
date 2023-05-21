package com.itau.personnalite.cadastrocliente.repositorios;

import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import com.itau.personnalite.cadastrocliente.entidades.Habilidade;

@Transactional(readOnly = true)
@NamedQueries({
	@NamedQuery( name = "HabilidadeRepository.findByUsuarioId", 
			query = "SELECT hab FROM Habilidade hab WHERE hab.usuario.id = :usuarioId")})
public interface HabilidadeRepository extends JpaRepository<Habilidade, Long>{
	
	List<Habilidade> findByUsuarioId(@Param("usuarioId") Long usuarioId);

	Page<Habilidade> findByUsuarioId(@Param("usuarioId") Long usuarioId, Pageable pageable);
}
