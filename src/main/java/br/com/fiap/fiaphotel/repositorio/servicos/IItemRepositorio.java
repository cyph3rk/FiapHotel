package br.com.fiap.fiaphotel.repositorio.servicos;

import br.com.fiap.fiaphotel.dominio.servicos.Item;
import br.com.fiap.fiaphotel.dominio.servicos.Servicos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IItemRepositorio extends JpaRepository<Item, Long> {

    List<Item> findByNome(String nome);

}
