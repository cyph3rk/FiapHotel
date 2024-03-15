package br.com.fiap.fiaphotel.facade.servicos;

import br.com.fiap.fiaphotel.dominio.servicos.Item;
import br.com.fiap.fiaphotel.dto.servicos.ItemDto;
import br.com.fiap.fiaphotel.repositorio.servicos.IItemRepositorio;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemFacade {

    private final IItemRepositorio itemRepositorio;

    @Autowired
    public ItemFacade(IItemRepositorio localidadeRepositorio) {
        this.itemRepositorio = localidadeRepositorio;
    }

    public Long salvar(ItemDto ItemDto) {
        List<ItemDto> encontrado = buscarPorNome(ItemDto.getNome());
        if (encontrado.size() >= 1) {
            return -1L;
        }

        Item item = new Item();
        item.setNome(ItemDto.getNome());
        item.setValor(ItemDto.getValor());
        itemRepositorio.save(item);

        return item.getId();
    }

    public List<ItemDto> buscarPorNome(String nome) {
        List<Item> itemLocalidade = itemRepositorio.findByNome(nome);

        return itemLocalidade.stream()
                .map(this::converter).collect(Collectors.toList());
    }

    public Optional<ItemDto> buscarPorId(Long id) {

        try {
            Item item = itemRepositorio.getReferenceById(id);

            ItemDto itemDto = new ItemDto();
            itemDto.setId(item.getId());
            itemDto.setNome(item.getNome());
            itemDto.setValor(item.getValor());

            return Optional.of(itemDto);
        } catch (EntityNotFoundException ex) {
            return Optional.empty();
        }
    }

    public void remove(Long id) {
        //Todo: Implementar a verificação se cadastro existe antes de deletar
        itemRepositorio.deleteById(id);
    }

    public void altera(ItemDto itemDto_new) {
        //TODO: Resolver o problema de alterar o nome para um que ja existe quebrando a regra de duplicidade
        Item item = itemRepositorio.getReferenceById(itemDto_new.getId());
        item.setId(itemDto_new.getId());
        item.setNome(itemDto_new.getNome());
        item.setValor(itemDto_new.getValor());

        itemRepositorio.save(item);
    }

    private ItemDto converter (Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setNome(item.getNome());
        itemDto.setValor(item.getValor());
        return itemDto;
    }

    public List<ItemDto> getAll() {
        return itemRepositorio
                .findAll()
                .stream()
                .map(this::converter).collect(Collectors.toList());
    }
}
