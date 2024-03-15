package br.com.fiap.fiaphotel.controller.form;

import br.com.fiap.fiaphotel.dto.QuartoDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QuartoForm {

    @JsonProperty
    @NotBlank(message = "Campo TIPO é obrigatorio")
    private String tipo;

    @JsonProperty
    @NotBlank(message = "Campo PESSOA é obrigatorio")
    private String pessoas;

    @JsonProperty
    @NotBlank(message = "Campo CAMAS é obrigatorio")
    private String camas;

    @JsonProperty
    @NotBlank(message = "Campo MOVEIS é obrigatorio")
    private String moveis;

    @JsonProperty
    @NotBlank(message = "Campo BANHEIRO é obrigatorio")
    private String banheiro;

    @JsonProperty
    @NotBlank(message = "Campo VALOR é obrigatorio")
    private String valor;

    public QuartoDto toQuartoDto() {
        return new QuartoDto(tipo, pessoas, camas, moveis, banheiro, valor);
    }

}
