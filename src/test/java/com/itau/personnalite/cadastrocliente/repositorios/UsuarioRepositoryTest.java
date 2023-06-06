package com.itau.personnalite.cadastrocliente.repositorios;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.itau.personnalite.cadastrocliente.entidades.Usuario;
import com.itau.personnalite.cadastrocliente.utils.DataUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository usuarioRepository;

	private static final String EMAIL = "marcelpaa@hotmail.com";
	private static final String CPF = "06618938635";
	private static final String DATA_NASCIMENTO = "21-08-1988";

	@Before
	public void setUp() throws Exception {
		this.usuarioRepository.save(obterDadosUsuario());
	}

	@After
	public void tearDown() {
		this.usuarioRepository.deleteAll();
	}

	@Test
	public void testBuscarUsuarioPorEmail() {
		Usuario usuario = this.usuarioRepository.findByEmail(EMAIL);
		assertEquals(EMAIL, usuario.getEmail());
	}

	@Test
	public void testBuscarUsuarioPorCpf() {
		Usuario usuario = this.usuarioRepository.findByCpf(CPF);
		assertEquals(CPF, usuario.getCpf());
	}

	@Test
	public void testBuscarUsuarioPorEmailECpf() {
		List<Usuario> usuario = this.usuarioRepository.findByCpfOrEmail(CPF, EMAIL);
		assertNotNull(usuario);
	}

	@Test
	public void testBuscarUsuarioPorEmailOuCpfParaEmailInvalido() {
		List<Usuario> usuario = this.usuarioRepository.findByCpfOrEmail(CPF, "marcel.andrade@hotmail.com");
		assertNotNull(usuario);
	}

	@Test
	public void testBuscarUsuarioPorEmailECpfParaCpfInvalido() {
		List<Usuario> usuario = this.usuarioRepository.findByCpfOrEmail("32014552447", EMAIL);
		assertNotNull(usuario);
	}

	private Usuario obterDadosUsuario() {
		Usuario usuario = new Usuario();
		usuario.setCpf("06618938635");
		usuario.setDataNascimento(new DataUtil().transformarStringEmLocalDate(DATA_NASCIMENTO));
		usuario.setEmail("marcelpaa@hotmail.com");
		usuario.setNome("Marcel");
		usuario.setEndereco("Rua: Manoel Rubim Nº: 409 Bairro: São Paulo Cep: 31910-030");
		return usuario;
	}
}
