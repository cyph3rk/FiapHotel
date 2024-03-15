package br.com.fiap.fiaphotel.dominio.quartos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "predio")
public class Predio {


    @JsonProperty
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @JsonProperty
    private String nome;

    @ManyToOne
    @JoinColumn(name = "id_localidade", nullable = false)
    private Localidade localidade;

    public Predio(String nome) {
        this.nome = nome;
    }

    public Predio() {

    }

}
