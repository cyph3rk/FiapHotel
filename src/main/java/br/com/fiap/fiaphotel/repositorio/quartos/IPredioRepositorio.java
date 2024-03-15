package br.com.fiap.fiaphotel.repositorio.quartos;

import br.com.fiap.fiaphotel.dominio.quartos.Predio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPredioRepositorio extends JpaRepository<Predio, Long> {

    List<Predio> findByNome(String nome);

}
