package br.com.fiap.fiaphotel.repositorio.cliente;

import br.com.fiap.fiaphotel.dominio.cliente.Cliente;
import br.com.fiap.fiaphotel.dominio.quartos.Localidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IClienteRepositorio extends JpaRepository<Cliente, Long> {

    List<Cliente> findByNome(String nome);
}
