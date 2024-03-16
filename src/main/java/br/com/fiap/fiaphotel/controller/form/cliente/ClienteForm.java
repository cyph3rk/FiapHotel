package br.com.fiap.fiaphotel.controller.form.cliente;

import br.com.fiap.fiaphotel.dto.cliente.ClienteDto;
import br.com.fiap.fiaphotel.dto.quartos.LocalidadeDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClienteForm {

    @JsonProperty
    @NotBlank(message = "Campo PAIS é obrigatorio")
    private String pais;

    @JsonProperty
    @NotBlank(message = "Campo CPF é obrigatorio")
    private String cpf;

    @JsonProperty
    @NotBlank(message = "Campo PASSPORTE é obrigatorio")
    private String passaporte;

    @JsonProperty
    @NotBlank(message = "Campo NOME é obrigatorio")
    private String nome;

    @JsonProperty
    @NotBlank(message = "Campo DATA NASCIMENTO é obrigatorio")
    private String datanascimento;

    @JsonProperty
    @NotBlank(message = "Campo TELEFONE é obrigatorio")
    private String telefone;

    @JsonProperty
    @NotBlank(message = "Campo EMAIL é obrigatorio")
    private String email;

    public ClienteDto toClienteDto() {
        return new ClienteDto(pais, cpf, passaporte, nome, datanascimento, telefone, email);
    }

}
