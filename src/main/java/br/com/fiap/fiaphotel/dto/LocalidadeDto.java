package br.com.fiap.fiaphotel.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LocalidadeDto {

    private Long id;
    private String nome;
    private String rua;
    private String cep;
    private String cidade;
    private String estado;

    public LocalidadeDto() { }

    public LocalidadeDto(String nome, String rua, String cep, String cidade, String estado) {
        this.nome = nome;
        this.rua = rua;
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;
    }
}
