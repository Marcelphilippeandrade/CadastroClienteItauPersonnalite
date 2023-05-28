package com.itau.personnalite.cadastrocliente.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import com.itau.personnalite.cadastro.annotation.IdadeMinima;
import com.itau.personnalite.cadastro.annotation.Nome;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UsuarioDto {
	
	private Long id;
	
	@NotEmpty(message = "CPF não pode ser vazio.")
	@CPF(message = "CPF inváliido.")
	private String cpf;
	
	@NotEmpty(message = "Nome não pode ser vazio.")
	@Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres.")
	@Nome
	private String nome;
	
	@NotEmpty(message = "Email não pode ser vazio.")
	@Length(min = 5, max = 200, message = "Email deve conter entre 5 e 200 caracteres.")
	@Email(message = "Email inválido.")
	private String email;
	
	@NotEmpty(message = "Data de nascimento não pode ser vazia. Formato Válido: dd-MM-yyyy")
	@IdadeMinima(valor = 18)
	private String dataNascimento;

	@NotEmpty(message = "Endereço não pode ser vazio.")
	@Length(min = 5, max = 400, message = "Endereço deve conter entre 5 e 400 caracteres.")
	private String endereco;
}
