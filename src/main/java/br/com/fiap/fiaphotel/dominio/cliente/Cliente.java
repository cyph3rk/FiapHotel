package br.com.fiap.fiaphotel.dominio.cliente;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "cliente")
public class Cliente {

    @JsonProperty
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @JsonProperty
    private String pais;

    @JsonProperty
    private String cpf;

    @JsonProperty
    private String passaporte;

    @JsonProperty
    private String nome;

    @JsonProperty
    private String datanascimento;

    @JsonProperty
    private String telefone;

    @JsonProperty
    private String email;

    public Cliente(String pais, String cpf, String passaporte, String nome, String datanascimento,
                   String telefone, String email) {
        this.pais = pais;
        this.cpf = cpf;
        this.passaporte = passaporte;
        this.nome = nome;
        this.datanascimento = datanascimento;
        this.telefone = telefone;
        this.email = email;
    }

    public Cliente() {}

}
