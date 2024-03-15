package br.com.fiap.fiaphotel.dominio;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "localidade")
public class Localidade {

    @JsonProperty
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @JsonProperty
    private String nome;

    @JsonProperty
    private String rua;

    @JsonProperty
    private String cep;

    @JsonProperty
    private String cidade;

    @JsonProperty
    private String estado;

    public Localidade(String nome, String rua, String cep, String cidade, String estado) {
        this.nome = nome;
        this.rua = rua;
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;
    }

    public Localidade() {}



}
