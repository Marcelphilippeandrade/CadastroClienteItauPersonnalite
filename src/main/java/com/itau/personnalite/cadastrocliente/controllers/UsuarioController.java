package com.itau.personnalite.cadastrocliente.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itau.personnalite.cadastrocliente.dtos.UsuarioDto;
import com.itau.personnalite.cadastrocliente.entidades.Usuario;
import com.itau.personnalite.cadastrocliente.response.Response;
import com.itau.personnalite.cadastrocliente.services.UsuarioService;
import com.itau.personnalite.cadastrocliente.utils.DataUtil;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	public UsuarioController() {
	}

	/**
	 * Cadastra um usuário no sistema
	 * 
	 * @param cadastroUsuarioDto
	 * @param result
	 * @return ResponseEntity<Response<CadastroUsuarioDto>>
	 * @throws NoSuchAlgorithmException
	 */
	@PostMapping(value = "/cadastro")
	public ResponseEntity<Response<UsuarioDto>> cadastrar(@Valid @RequestBody UsuarioDto cadastroUsuarioDto,
			BindingResult result) throws NoSuchAlgorithmException {

		Response<UsuarioDto> response = new Response<UsuarioDto>();
		validarDadosExistentes(cadastroUsuarioDto, result);
		Usuario usuario = this.conveterDtoParaUsuario(cadastroUsuarioDto, result);

		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.usuarioService.persistirUsuario(usuario);

		return ResponseEntity.ok(response);
	}

	/**
	 * Retorna um usuário dado um CPF
	 * 
	 * @param cpf
	 * @returnResponseEntity<Response<UsuarioDto>>
	 */
	@GetMapping(value = "/cpf/{cpf}")
	public ResponseEntity<Response<UsuarioDto>> buscarPorCpf(@PathVariable("cpf") String cpf) {
		Response<UsuarioDto> response = new Response<UsuarioDto>();
		Optional<Usuario> usuario = usuarioService.buscarPorCpf(cpf);

		if (!usuario.isPresent()) {
			response.getErros().add("Usuário não encontrado para o CPF: {}" + cpf);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.converterUsuarioDto(usuario.get()));
		return ResponseEntity.ok(response);
	}

	/**
	 * Retorna um usuário dado um Email
	 * 
	 * @param email
	 * @return ResponseEntity<Response<UsuarioDto>>
	 */
	@GetMapping(value = "/email/{email}")
	public ResponseEntity<Response<UsuarioDto>> buscarPorEmail(@PathVariable("email") String email) {
		Response<UsuarioDto> response = new Response<UsuarioDto>();
		Optional<Usuario> usuario = usuarioService.buscarPorEmail(email);

		if (!usuario.isPresent()) {
			response.getErros().add("Usuário não encontrado para o Email: {}" + email);
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(this.converterUsuarioDto(usuario.get()));
		return ResponseEntity.ok(response);
	}

	@PutMapping(value = "/{cpf}")
	public ResponseEntity<Response<UsuarioDto>> atualizar(@PathVariable("cpf") String cpf,
			@Valid @RequestBody UsuarioDto funcionarioDto, BindingResult result) throws NoSuchAlgorithmException {

		Response<UsuarioDto> response = new Response<>();

		return null;
	}

	/**
	 * Converte os dados de DTO para usuário
	 * 
	 * @param cadastroUsuarioDto
	 * @param result
	 * @return Usuario
	 */
	private Usuario conveterDtoParaUsuario(@Valid UsuarioDto cadastroUsuarioDto, BindingResult result) {
		Usuario usuario = new Usuario();
		DataUtil dataNascimento = new DataUtil();
		usuario.setCpf(cadastroUsuarioDto.getCpf());
		usuario.setNome(cadastroUsuarioDto.getNome());
		usuario.setEmail(cadastroUsuarioDto.getEmail());
		usuario.setDataNascimento(dataNascimento.transformarStringEmLocalDate(cadastroUsuarioDto.getDataNascimento()));
		usuario.setEndereco(cadastroUsuarioDto.getEndereco());

		return usuario;
	}

	private UsuarioDto converterUsuarioDto(Usuario usuario) {
		UsuarioDto usuarioDto = new UsuarioDto();
		DataUtil dataNascimento = new DataUtil();
		usuarioDto.setId(usuario.getId());
		usuarioDto.setCpf(usuario.getCpf());
		usuarioDto.setNome(usuario.getNome());
		usuarioDto.setDataNascimento(dataNascimento.transformarLocalDateEmString(usuario.getDataNascimento()));
		usuarioDto.setEmail(usuario.getEmail());
		usuarioDto.setEndereco(usuario.getEndereco());

		return usuarioDto;
	}

	/**
	 * Verifica se o usuário já existe na base de dados
	 * 
	 * @param cadastroUsuarioDto
	 * @param result
	 */
	private void validarDadosExistentes(@Valid UsuarioDto cadastroUsuarioDto, BindingResult result) {

		this.usuarioService.buscarPorCpf(cadastroUsuarioDto.getCpf())
				.ifPresent(func -> result.addError(new ObjectError("usuário", "CPF já existe.")));

		this.usuarioService.buscarPorEmail(cadastroUsuarioDto.getEmail())
				.ifPresent(func -> result.addError(new ObjectError("usuario", "Email já existe.")));
	}
}
