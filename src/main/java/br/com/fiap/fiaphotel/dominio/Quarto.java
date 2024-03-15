package br.com.fiap.fiaphotel.dominio;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "quarto")
public class Quarto {

    @JsonProperty
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @JsonProperty
    private String tipo;

    @JsonProperty
    private String pessoa;

    @JsonProperty
    private String camas;

    @JsonProperty
    private String moveis;

    @JsonProperty
    private String banheiro;

    @JsonProperty
    private String valor;

    @ManyToOne
    @JoinColumn(name = "id_predio", nullable = false)
    private Predio predio;

    public Quarto(String tipo, String pessoa, String camas, String moveis, String banheiro, String valor, Predio predio) {
        this.tipo = tipo;
        this.pessoa = pessoa;
        this.camas = camas;
        this.moveis = moveis;
        this.banheiro = banheiro;
        this.valor = valor;
        this.predio = predio;
    }

    public Quarto() {

    }

}
