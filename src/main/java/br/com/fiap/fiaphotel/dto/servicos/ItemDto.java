package br.com.fiap.fiaphotel.dto.servicos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemDto {

    private Long id;
    private String nome;
    private String valor;

    public ItemDto() { }

    public ItemDto(String nome, String valor) {
        this.nome = nome;
        this.valor = valor;
    }

}
