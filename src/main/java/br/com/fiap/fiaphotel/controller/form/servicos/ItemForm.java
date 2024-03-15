package br.com.fiap.fiaphotel.controller.form.servicos;

import br.com.fiap.fiaphotel.dto.servicos.ItemDto;
import br.com.fiap.fiaphotel.dto.servicos.ServicosDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemForm {

    @JsonProperty
    @NotBlank(message = "Campo NOME é obrigatorio")
    private String nome;

    @JsonProperty
    @NotBlank(message = "Campo VALOR é obrigatorio")
    private String valor;

    public ItemDto toItemDto() {
        return new ItemDto(nome, valor);
    }


}
