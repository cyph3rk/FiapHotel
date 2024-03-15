package br.com.fiap.fiaphotel.repositorio.servicos;

import br.com.fiap.fiaphotel.dominio.quartos.Localidade;
import br.com.fiap.fiaphotel.dominio.servicos.Servicos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IServicosRepositorio extends JpaRepository<Servicos, Long> {

    List<Servicos> findByNome(String nome);

}
