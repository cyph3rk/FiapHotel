package br.com.fiap.fiaphotel.repositorio.quartos;

import br.com.fiap.fiaphotel.dominio.quartos.Localidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IlocalidadeRepositorio extends JpaRepository<Localidade, Long> {

    List<Localidade> findByNome(String nome);

}
