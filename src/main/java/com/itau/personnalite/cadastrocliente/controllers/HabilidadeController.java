package com.itau.personnalite.cadastrocliente.controllers;

import java.text.ParseException;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.itau.personnalite.cadastrocliente.dtos.HabilidadeDto;
import com.itau.personnalite.cadastrocliente.entidades.Habilidade;
import com.itau.personnalite.cadastrocliente.entidades.Usuario;
import com.itau.personnalite.cadastrocliente.response.Response;
import com.itau.personnalite.cadastrocliente.services.HabilidadeService;

@RestController
@RequestMapping("/api/habilidades")
@CrossOrigin(origins = "*")
public class HabilidadeController {

	@Autowired
	private HabilidadeService habilidadeService;

	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;

	public HabilidadeController() {
	}

	/**
	 * Retorna a listagem de Habilidades de um usuario
	 * 
	 * @param usuarioId
	 * @param pag
	 * @param ord
	 * @param dir
	 * @return ResponseEntity<Response<Page<HabilidadeDto>>>
	 */
	@GetMapping(value = "/usuario/{usuarioId}")
	public ResponseEntity<Response<Page<HabilidadeDto>>> listarPorUsuarioId(@PathVariable("usuarioId") Long usuarioId,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir) {

		Response<Page<HabilidadeDto>> response = new Response<Page<HabilidadeDto>>();

		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<Habilidade> habiblidades = this.habilidadeService.buscarUsuarioPorId(usuarioId, pageRequest);
		Page<HabilidadeDto> habilidadeDto = habiblidades.map(habilidade -> this.converterHabilidadeDto(habilidade));

		response.setData(habilidadeDto);
		return ResponseEntity.ok(response);
	}

	/**
	 * Retorna uma Habilidade por ID
	 * 
	 * @param id
	 * @return ResponseEntity<Response<HabilidadeDto>>
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<HabilidadeDto>> listarPorId(@PathVariable("id") Long id) {
		Response<HabilidadeDto> response = new Response<HabilidadeDto>();
		Optional<Habilidade> habilidade = this.habilidadeService.buscarPorId(id);

		if (!habilidade.isPresent()) {
			response.getErros().add("Habilidade não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.converterHabilidadeDto(habilidade.get()));
		return ResponseEntity.ok(response);
	}

	/**
	 * Adiciona uma nova habilidade
	 * 
	 * @param habilidadeDto
	 * @param result
	 * @return esponseEntity<Response<HabilidadeDto>>
	 * @throws ParseException
	 */
	@PostMapping
	public ResponseEntity<Response<HabilidadeDto>> adicionar(@Valid @RequestBody HabilidadeDto habilidadeDto,
			BindingResult result) throws ParseException {
		Response<HabilidadeDto> response = new Response<HabilidadeDto>();
		validarUsuario(habilidadeDto, result);
		Habilidade habilidade = this.converterDtoParaHabilidade(habilidadeDto, result);

		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		habilidade = this.habilidadeService.persistir(habilidade);
		response.setData(this.converterHabilidadeDto(habilidade));
		return ResponseEntity.ok(response);
	}

	/**
	 * Atualizar os dados de uma habilidade
	 * 
	 * @param id
	 * @param habilidadeDto
	 * @param result
	 * @return ResponseEntity<Response<HabilidadeDto>>
	 * @throws ParseException
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<HabilidadeDto>> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody HabilidadeDto habilidadeDto, BindingResult result) throws ParseException {

		Response<HabilidadeDto> response = new Response<HabilidadeDto>();
		validarUsuario(habilidadeDto, result);
		habilidadeDto.setId(Optional.of(id));
		Habilidade habilidade = this.converterDtoParaHabilidade(habilidadeDto, result);

		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.habilidadeService.persistir(habilidade);
		response.setData(this.converterHabilidadeDto(habilidade));
		return ResponseEntity.ok(response);

	}

	/**
	 * Remove uma habilidade por ID
	 * 
	 * @param id
	 * @return ResponseEntity<Response<String>>
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		Response<String> response = new Response<String>();
		Optional<Habilidade> habilidade = this.habilidadeService.buscarPorId(id);

		if (!habilidade.isPresent()) {
			response.getErros().add("Erro ao remover habilidade. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}
		this.habilidadeService.remover(id);
		response.setData(habilidade.get().getId().toString());
		response.setData(habilidade.get().getNome());
		return ResponseEntity.ok(new Response<String>());
	}

	private Habilidade converterDtoParaHabilidade(@Valid HabilidadeDto habilidadeDto, BindingResult result) {
		Habilidade habilidade = new Habilidade();

		if (habilidadeDto.getId().isPresent()) {
			Optional<Habilidade> habilidadeOptional = this.habilidadeService.buscarPorId(habilidadeDto.getId().get());

			if (habilidadeOptional.isPresent()) {
				habilidade = habilidadeOptional.get();
			} else {
				result.addError(new ObjectError("habilidade", "Habilidade não encontrado."));
			}
		} else {
			habilidade.setUsuario(new Usuario());
			habilidade.getUsuario().setId(habilidadeDto.getUsuarioId());
		}

		habilidade.setNome(habilidadeDto.getNome());

		return habilidade;
	}

	/**
	 * Valida um usuario verificando se ele existente e válido no sistema.
	 * 
	 * @param habilidadeDto
	 * @param result
	 */
	private void validarUsuario(@Valid HabilidadeDto habilidadeDto, BindingResult result) {
		if (habilidadeDto.getUsuarioId() == null) {
			result.addError(new ObjectError("usuario", "Usuario não informado."));
			return;
		}
	}

	/**
	 * Converte uma Habilidade em um DTO
	 * 
	 * @param habilidade
	 * @return HabilidadeDto
	 */
	private HabilidadeDto converterHabilidadeDto(Habilidade habilidade) {
		HabilidadeDto habilidadeDto = new HabilidadeDto();
		habilidadeDto.setId(Optional.ofNullable(habilidade.getId()));
		habilidadeDto.setNome(habilidade.getNome());
		habilidadeDto.setUsuarioId(habilidade.getUsuario().getId());
		return habilidadeDto;
	}
}
