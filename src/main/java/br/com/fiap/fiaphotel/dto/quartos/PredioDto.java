package br.com.fiap.fiaphotel.dto.quartos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PredioDto {

    private Long id;
    private String nome;

    private LocalidadeDto localidadeDto;

    public PredioDto() { }

    public PredioDto(String nome) {
        this.nome = nome;
    }

    public PredioDto(String nome, LocalidadeDto localidadeDto) {
        this.nome = nome;
        this.localidadeDto = localidadeDto;
    }
}
