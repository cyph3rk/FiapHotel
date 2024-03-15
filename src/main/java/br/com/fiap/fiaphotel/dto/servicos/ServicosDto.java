package br.com.fiap.fiaphotel.dto.servicos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ServicosDto {

    private Long id;
    private String nome;
    private String valor;

    public ServicosDto() { }

    public ServicosDto(String nome, String valor) {
        this.nome = nome;
        this.valor = valor;
    }

}
