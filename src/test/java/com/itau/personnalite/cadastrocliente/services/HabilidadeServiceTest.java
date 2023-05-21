package com.itau.personnalite.cadastrocliente.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import com.itau.personnalite.cadastrocliente.entidades.Habilidade;
import com.itau.personnalite.cadastrocliente.repositorios.HabilidadeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HabilidadeServiceTest {

	@MockBean
	private HabilidadeRepository habilidadeRepository;

	@Autowired
	private HabilidadeService habilidadeService;

	@Before
	public void setUp() throws Exception {
		BDDMockito.given(this.habilidadeRepository.findByUsuarioId(Mockito.anyLong(), Mockito.any(PageRequest.class)))
				.willReturn(new PageImpl<Habilidade>(new ArrayList<Habilidade>()));
		BDDMockito.given(this.habilidadeRepository.findById(Mockito.anyLong())).willReturn(Optional.ofNullable(new Habilidade()));
		BDDMockito.given(this.habilidadeRepository.save(Mockito.any(Habilidade.class))).willReturn(new Habilidade());
	}
	
	@Test
	public void testBuscarHabilidadePorUsuarioId() {
		Page<Habilidade> habilidade = this.habilidadeService.buscarUsuarioPorId(1L, new PageRequest(0, 10));
		assertNotNull(habilidade);
	}
	
	@Test
	public void testBuscarHabilidadePorId() {
		Optional<Habilidade> habilidade = this.habilidadeService.buscarPorId(1L);
		assertTrue(habilidade.isPresent());
	}
	
	@Test
	public void testPersistirHabilidade() {
		Habilidade habilidade = this.habilidadeService.persistir(new Habilidade());
		assertNotNull(habilidade);
	}
}
