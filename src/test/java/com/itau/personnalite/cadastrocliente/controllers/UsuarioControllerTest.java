package com.itau.personnalite.cadastrocliente.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itau.personnalite.cadastrocliente.dtos.UsuarioDto;
import com.itau.personnalite.cadastrocliente.entidades.Usuario;
import com.itau.personnalite.cadastrocliente.services.UsuarioService;
import com.itau.personnalite.cadastrocliente.utils.DataUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private UsuarioService usuarioService;

	private static final String URL_BASE = "/api/usuarios";
	private static final Long ID = 1L;
	private static final String NOME = "Marcel";
	private static final String CPF = "066.189.386-35";
	private static final String EMAIL = "marcelpaa@hotmail.com";
	private static final String DATA_NASCIMENTO = "21-08-1988";
	private static final String ENDERECO = "Rua: Manoel Rubim, Nº: 409, Bairro: São Paulo, Cep: 31910-030";
	
	@Test
	@WithMockUser
	public void testCadastroUsuario() throws Exception {
		Usuario usuario = obterDadosUsuario();
		BDDMockito.given(this.usuarioService.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(new Usuario()));
		BDDMockito.given(this.usuarioService.persistirUsuario(Mockito.any(Usuario.class))).willReturn(usuario);
		
		mvc.perform(MockMvcRequestBuilders.post(URL_BASE + "/cadastro")
				.content(this.obterJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(ID))
				.andExpect(jsonPath("$.data.nome").value(NOME))
				.andExpect(jsonPath("$.data.dataNascimento").value(DATA_NASCIMENTO))
				.andExpect(jsonPath("$.data.email").value(EMAIL))
				.andExpect(jsonPath("$.data.endereco").value(ENDERECO))
				.andExpect(jsonPath("$.erros").isEmpty());
	}

	@Test
	@WithMockUser
	public void testBuscarUsuarioCpfInvalido() throws Exception {
		BDDMockito.given(this.usuarioService.buscarPorCpf(Mockito.anyString())).willReturn(Optional.empty());

		mvc.perform(MockMvcRequestBuilders.get(URL_BASE + "/cpf/" + CPF).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.erros").value("Usuário não encontrado para o CPF: {}" + CPF));
	}

	@Test
	@WithMockUser
	public void testBuscarUsuarioCpfValido() throws Exception {
		BDDMockito.given(this.usuarioService.buscarPorCpf(Mockito.anyString())).willReturn(Optional.of(this.obterDadosUsuario()));

		mvc.perform(MockMvcRequestBuilders.get(URL_BASE + "/cpf/" + CPF)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(ID))
				.andExpect(jsonPath("$.data.email", equalTo(EMAIL)))
				.andExpect(jsonPath("$.data.cpf", equalTo(CPF)))
				.andExpect(jsonPath("$.erros").isEmpty());
	}
	
	@Test
	@WithMockUser
	public void testBuscarUsuarioEmailInvalido() throws Exception {
		BDDMockito.given(this.usuarioService.buscarPorEmail(Mockito.anyString())).willReturn(Optional.empty());

		mvc.perform(MockMvcRequestBuilders.get(URL_BASE + "/email/" + EMAIL).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.erros").value("Usuário não encontrado para o Email: {}" + EMAIL));
	}
	
	@Test
	@WithMockUser
	public void testBuscarUsuarioEmailValido() throws Exception {
		BDDMockito.given(this.usuarioService.buscarPorEmail(Mockito.anyString())).willReturn(Optional.of(this.obterDadosUsuario()));

		mvc.perform(MockMvcRequestBuilders.get(URL_BASE + "/email/" + EMAIL)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(ID))
				.andExpect(jsonPath("$.data.email", equalTo(EMAIL)))
				.andExpect(jsonPath("$.data.cpf", equalTo(CPF)))
				.andExpect(jsonPath("$.erros").isEmpty());
	}
	
	private String obterJsonRequisicaoPost() throws JsonProcessingException {
		UsuarioDto usuarioDto = new UsuarioDto();
		usuarioDto.setId(ID);
		usuarioDto.setNome(NOME);
		usuarioDto.setCpf(CPF);
		usuarioDto.setDataNascimento(DATA_NASCIMENTO);
		usuarioDto.setEmail(EMAIL);
		usuarioDto.setEndereco(ENDERECO);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(usuarioDto);
	}

	private Usuario obterDadosUsuario() {
		Usuario usuario = new Usuario();
		usuario.setId(ID);
		usuario.setNome(NOME);
		usuario.setEmail(EMAIL);
		usuario.setCpf(CPF);
		usuario.setDataNascimento(new DataUtil().transformarStringEmLocalDate(DATA_NASCIMENTO));
		usuario.setEndereco(ENDERECO);
		return usuario;
	}
}
