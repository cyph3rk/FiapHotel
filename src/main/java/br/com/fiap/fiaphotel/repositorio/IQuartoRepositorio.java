package br.com.fiap.fiaphotel.repositorio;

import br.com.fiap.fiaphotel.dominio.Localidade;
import br.com.fiap.fiaphotel.dominio.Quarto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IQuartoRepositorio extends JpaRepository<Quarto, Long> {

    List<Quarto> findByNome(String nome);

}
