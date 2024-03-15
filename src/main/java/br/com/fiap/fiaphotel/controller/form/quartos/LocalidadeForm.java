package br.com.fiap.fiaphotel.controller.form.quartos;

import br.com.fiap.fiaphotel.dto.quartos.LocalidadeDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LocalidadeForm {

    @JsonProperty
    @NotBlank(message = "Campo RUA é obrigatorio")
    private String nome;

    @JsonProperty
    @NotBlank(message = "Campo RUA é obrigatorio")
    private String rua;

    @JsonProperty
    @NotBlank(message = "Campo CEP é obrigatorio")
    private String cep;

    @JsonProperty
    @NotBlank(message = "Campo CIDADE é obrigatorio")
    private String cidade;

    @JsonProperty
    @NotBlank(message = "Campo ESTADO é obrigatorio")
    private String estado;

    public LocalidadeDto toLocalidadeDto() {
        return new LocalidadeDto(nome, rua, cep, cidade, estado);
    }

}
