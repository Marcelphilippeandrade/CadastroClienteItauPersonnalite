package com.itau.personnalite.cadastrocliente.dtos;

import java.util.Optional;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class HabilidadeDto {

	private Optional<Long> id = Optional.empty();

	@NotEmpty(message = "Nome da habilidade não pode ser vazio.")
	@Length(min = 3, max = 200, message = "Nome da habilidade deve conter entre 3 e 200 caracteres.")
	private String nome;
	private Long usuarioId;
}
