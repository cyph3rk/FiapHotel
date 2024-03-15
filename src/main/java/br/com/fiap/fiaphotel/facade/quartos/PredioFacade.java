package br.com.fiap.fiaphotel.facade.quartos;

import br.com.fiap.fiaphotel.dominio.quartos.Predio;
import br.com.fiap.fiaphotel.dto.quartos.LocalidadeDto;
import br.com.fiap.fiaphotel.dto.quartos.PredioDto;
import br.com.fiap.fiaphotel.repositorio.quartos.IPredioRepositorio;
import br.com.fiap.fiaphotel.repositorio.quartos.IlocalidadeRepositorio;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PredioFacade {

    private final IPredioRepositorio predioRepositorio;

    private final IlocalidadeRepositorio localidadeRepositorio;

    @Autowired
    public PredioFacade(IPredioRepositorio predioRepositorio, IlocalidadeRepositorio localidadeRepositorio) {
        this.predioRepositorio = predioRepositorio;
        this.localidadeRepositorio = localidadeRepositorio;
    }

    public Long salvar(PredioDto predioDto) {
        List<PredioDto> encontrado = buscarPorNome(predioDto.getNome());
        if (encontrado.size() >= 1) {
            return -1L;
        }

        var localidade = localidadeRepositorio.getReferenceById(predioDto.getLocalidadeDto().getId());

        Predio predio = new Predio();
        predio.setNome(predioDto.getNome());
        predio.setLocalidade(localidade);
        predioRepositorio.save(predio);

        return predio.getId();
    }

    public List<PredioDto> buscarPorNome(String nome) {
        List<Predio> listaPredio = predioRepositorio.findByNome(nome);

        return listaPredio.stream()
                .map(this::converter).collect(Collectors.toList());
    }

    public Optional<PredioDto> buscarPorId(Long id) {

        try {
            Predio predio = predioRepositorio.getReferenceById(id);

            PredioDto predioDto = new PredioDto();
            predioDto.setId(predio.getId());
            predioDto.setNome(predio.getNome());

            LocalidadeDto localidadeDto = new LocalidadeDto();
            localidadeDto.setId(predio.getLocalidade().getId());
            localidadeDto.setNome(predio.getLocalidade().getNome());
            localidadeDto.setRua(predio.getLocalidade().getRua());
            localidadeDto.setCidade(predio.getLocalidade().getCidade());
            localidadeDto.setEstado(predio.getLocalidade().getEstado());

            predioDto.setLocalidadeDto(localidadeDto);

            return Optional.of(predioDto);
        } catch (EntityNotFoundException ex) {
            return Optional.empty();
        }
    }

    public void remove(Long id) {
        //Todo: Implementar a verificação se cadastro existe antes de deletar
        predioRepositorio.deleteById(id);
    }

    public void altera(PredioDto predioDto_new) {
        //TODO: Resolver o problema de alterar o nome para um que ja existe quebrando a regra de duplicidade
        Predio predio = predioRepositorio.getReferenceById(predioDto_new.getId());
        predio.setId(predioDto_new.getId());
        predio.setNome(predioDto_new.getNome());

        predioRepositorio.save(predio);
    }

    private PredioDto converter(Predio predio) {

        PredioDto predioDto = new PredioDto();
        predioDto.setId(predio.getId());
        predioDto.setNome(predio.getNome());

        LocalidadeDto localidadeDto = new LocalidadeDto();
        localidadeDto.setId(predio.getLocalidade().getId());
        localidadeDto.setNome(predio.getLocalidade().getNome());
        localidadeDto.setRua(predio.getLocalidade().getRua());
        localidadeDto.setCidade(predio.getLocalidade().getCidade());
        localidadeDto.setEstado(predio.getLocalidade().getEstado());

        predioDto.setLocalidadeDto(localidadeDto);

        return predioDto;
    }

    public List<PredioDto> getAll() {
        return predioRepositorio
                .findAll()
                .stream()
                .map(this::converter).collect(Collectors.toList());
    }
}
