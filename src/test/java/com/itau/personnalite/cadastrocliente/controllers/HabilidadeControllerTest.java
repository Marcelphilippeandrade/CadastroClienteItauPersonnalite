package com.itau.personnalite.cadastrocliente.controllers;

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
import com.itau.personnalite.cadastrocliente.dtos.HabilidadeDto;
import com.itau.personnalite.cadastrocliente.entidades.Habilidade;
import com.itau.personnalite.cadastrocliente.entidades.Usuario;
import com.itau.personnalite.cadastrocliente.services.HabilidadeService;
import com.itau.personnalite.cadastrocliente.services.UsuarioService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(secure=false)
public class HabilidadeControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	public HabilidadeService habilidadeService;

	@MockBean
	public UsuarioService usuarioService;

	private static final String URL_BASE = "/api/habilidades/";
	private static final Long ID_USUARIO = 1L;
	private static final Long ID_HABILIDADE = 1L;
	private static final String NOME = "Desenvolvedor Java";

	@Test
	@WithMockUser
	public void testCadastroHabilidade() throws Exception {
		Habilidade habilidade = obterDadosHabilidade();
		BDDMockito.given(this.usuarioService.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(new Usuario()));
		BDDMockito.given(this.habilidadeService.persistir(Mockito.any(Habilidade.class))).willReturn(habilidade);
		
		mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
				.content(this.obterJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(ID_HABILIDADE))
				.andExpect(jsonPath("$.data.nome").value(NOME))
				.andExpect(jsonPath("$.data.usuarioId").value(ID_USUARIO))
				.andExpect(jsonPath("$.erros").isEmpty());
	}

	private String obterJsonRequisicaoPost() throws JsonProcessingException {
		HabilidadeDto habilidadeDto = new HabilidadeDto();
		habilidadeDto.setId(null);
		habilidadeDto.setNome(NOME);
		habilidadeDto.setUsuarioId(ID_USUARIO);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(habilidadeDto);
	}

	private Habilidade obterDadosHabilidade() {
		Habilidade habilidade = new Habilidade();
		habilidade.setId(ID_HABILIDADE);
		habilidade.setNome(NOME);
		habilidade.setUsuario(new Usuario());
		habilidade.getUsuario().setId(ID_USUARIO);
		return habilidade;
	}
}
