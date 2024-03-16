package br.com.fiap.fiaphotel.dto.cliente;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClienteDto {

    private Long id;
    private String pais;
    private String cpf;
    private String passaporte;
    private String nome;
    private String datanascimento;
    private String telefone;
    private String email;

    public ClienteDto() { }

    public ClienteDto(String pais, String cpf, String passaporte, String nome, String datanascimento,
                      String telefone, String email) {
        this.pais = pais;
        this.cpf = cpf;
        this.passaporte = passaporte;
        this.nome = nome;
        this.datanascimento = datanascimento;
        this.telefone = telefone;
        this.email = email;
    }
}
