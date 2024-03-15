package br.com.fiap.fiaphotel.controller.form.quartos;

import br.com.fiap.fiaphotel.dto.quartos.PredioDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PredioForm {

    @JsonProperty
    @NotBlank(message = "Campo RUA é obrigatorio")
    private String nome;

    public PredioDto toPredioDto() {
        return new PredioDto(nome);
    }

}
