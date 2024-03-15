package br.com.fiap.fiaphotel.repositorio.quartos;

import br.com.fiap.fiaphotel.dominio.quartos.Quarto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IQuartoRepositorio extends JpaRepository<Quarto, Long> {

    List<Quarto> findByTipo(String tipo);

}
