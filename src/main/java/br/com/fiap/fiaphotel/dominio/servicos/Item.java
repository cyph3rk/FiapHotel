package br.com.fiap.fiaphotel.dominio.servicos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "item")
public class Item {

    @JsonProperty
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @JsonProperty
    private String nome;

    @JsonProperty
    private String valor;

    public Item(String nome, String valor) {
        this.nome = nome;
        this.valor = valor;
    }

    public Item() { }


}
