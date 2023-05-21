package com.itau.personnalite.cadastrocliente.repositorios;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import com.itau.personnalite.cadastrocliente.entidades.Habilidade;
import com.itau.personnalite.cadastrocliente.entidades.Usuario;
import com.itau.personnalite.cadastrocliente.utils.DataUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HabilidadeRepositoryTest {

	@Autowired
	private HabilidadeRepository habilidadeRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	private Long usuarioId;

	@Before
	public void setUp() throws Exception {
		Usuario usuario = this.usuarioRepository.save(ObterDadosUsuario());
		this.usuarioId = usuario.getId();

		this.habilidadeRepository.save(obterDadosHabilidades(usuario));
		this.habilidadeRepository.save(obterDadosHabilidades(usuario));
	}

	@Test
	public void testBuscarHabilidadesPorUsuarioId() {
		List<Habilidade> habilidade = this.habilidadeRepository.findByUsuarioId(usuarioId);
		assertEquals(2, habilidade.size());
	}

	@Test
	public void testBuscarHabilidadesPorUsuarioIdPaginado() {
		PageRequest page = new PageRequest(0, 10);
		Page<Habilidade> habilidades = this.habilidadeRepository.findByUsuarioId(usuarioId, page);

		assertEquals(2, habilidades.getTotalElements());
	}
	
	private Habilidade obterDadosHabilidades(Usuario usuario) {
		Habilidade habilidade = new Habilidade();
		habilidade.setNome("Desenvolvedor Java");
		habilidade.setUsuario(usuario);
		return habilidade;
	}

	private Usuario ObterDadosUsuario() {
		Usuario usuario = new Usuario();
		usuario.setNome("Marcel Philippe Abreu Andrade");
		usuario.setCpf("06618938635");
		usuario.setDataNascimento(new DataUtil().transformarStringEmLocalDate("21-08-1988"));
		usuario.setEmail("marcelpaa@hotmail.com");
		usuario.setEndereco("Rua: Manoel Rumbim, Nº:409, Bairro: São Paulo, Cep: 31910-030");
		return usuario;
	}

}
