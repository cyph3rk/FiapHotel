package br.com.fiap.fiaphotel.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QuartoDto {

    private Long id;
    private String tipo;
    private String pessoas;
    private String camas;
    private String moveis;
    private String banheiro;
    private String valor;

    private PredioDto predioDto;

    public QuartoDto() { }

    public QuartoDto(String tipo, String pessoas, String camas, String moveis, String banheiro, String valor) {
        this.tipo = tipo;
        this.pessoas = pessoas;
        this.camas = camas;
        this.moveis = moveis;
        this.banheiro = banheiro;
        this.valor = valor;
    }

    public QuartoDto(String tipo, String pessoas, String camas, String moveis, String banheiro, String valor, PredioDto predioDto) {
        this.tipo = tipo;
        this.pessoas = pessoas;
        this.camas = camas;
        this.moveis = moveis;
        this.banheiro = banheiro;
        this.valor = valor;
        this.predioDto = predioDto;
    }
}
