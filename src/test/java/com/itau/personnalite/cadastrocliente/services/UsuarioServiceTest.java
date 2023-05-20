package com.itau.personnalite.cadastrocliente.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.itau.personnalite.cadastrocliente.entidades.Usuario;
import com.itau.personnalite.cadastrocliente.repositorios.UsuarioRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

	@MockBean
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuarioService usuarioService;

	@Before
	public void setUp() {
		BDDMockito.given(this.usuarioRepository.save(Mockito.any(Usuario.class))).willReturn(new Usuario());
		BDDMockito.given(this.usuarioRepository.findById(Mockito.anyLong())).willReturn(Optional.ofNullable(new Usuario()));
		BDDMockito.given(this.usuarioRepository.findByEmail(Mockito.anyString())).willReturn(new Usuario());
		BDDMockito.given(this.usuarioRepository.findByCpf(Mockito.anyString())).willReturn(new Usuario());
	}
	
	@Test
	public void testPersisteUsuario() {
		Usuario usuario = this.usuarioService.persistirUsuario(new Usuario());
		assertNotNull(usuario);
	}
	
	@Test
	public void testBuscaUsuarioPorId() {
		Optional<Usuario> usuario = this.usuarioService.buscarPorId(1L);
		assertTrue(usuario.isPresent());
	}
	
	@Test
	public void testBuscarUsuarioPorEmail() {
		Optional<Usuario> usuario = this.usuarioService.buscarPorEmail("");
		assertTrue(usuario.isPresent());
	}
	
	@Test
	public void testBuscarUsuarioPorCpf() {
		Optional<Usuario> usuario = this.usuarioService.buscarPorCpf("06618938635");
		assertTrue(usuario.isPresent());
	}
}
