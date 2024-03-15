package br.com.fiap.fiaphotel.facade;

import br.com.fiap.fiaphotel.dominio.Localidade;
import br.com.fiap.fiaphotel.dto.LocalidadeDto;
import br.com.fiap.fiaphotel.repositorio.IlocalidadeRepositorio;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuartoFacade {


    private static final Logger logger = LoggerFactory.getLogger(QuartoFacade.class);

    private final IlocalidadeRepositorio localidadeRepositorio;

    @Autowired
    public QuartoFacade(IlocalidadeRepositorio localidadeRepositorio) {
        this.localidadeRepositorio = localidadeRepositorio;
    }

    public Long salvar(LocalidadeDto localidadeDto) {
        List<LocalidadeDto> encontrado = buscarPorNome(localidadeDto.getNome());
        if (encontrado.size() >= 1) {
            return -1L;
        }

        Localidade localidade = new Localidade();
        localidade.setNome(localidadeDto.getNome());
        localidade.setRua(localidadeDto.getRua());
        localidade.setCep(localidadeDto.getCep());
        localidade.setCidade(localidadeDto.getCidade());
        localidade.setEstado(localidadeDto.getEstado());
        localidadeRepositorio.save(localidade);

        return localidade.getId();
    }

    public List<LocalidadeDto> buscarPorNome(String nome) {
        List<Localidade> listaLocalidade = localidadeRepositorio.findByNome(nome);

        return listaLocalidade.stream()
                .map(this::converter).collect(Collectors.toList());
    }

    public Optional<LocalidadeDto> buscarPorId(Long id) {

        try {
            Localidade endereco = localidadeRepositorio.getReferenceById(id);

            LocalidadeDto localidadeDto = new LocalidadeDto();
            localidadeDto.setId(endereco.getId());
            localidadeDto.setNome(endereco.getNome());
            localidadeDto.setRua(endereco.getRua());
            localidadeDto.setCep(endereco.getCep());
            localidadeDto.setCidade(endereco.getCidade());
            localidadeDto.setEstado(endereco.getEstado());

            return Optional.of(localidadeDto);
        } catch (EntityNotFoundException ex) {
            logger.info("LocalidadeFacade - buscarPorId Id: " + id + (" Não cadastrado"));
            return Optional.empty();
        }
    }

    public void remove(Long id) {
        //Todo: Implementar a verificação se cadastro existe antes de deletar
        localidadeRepositorio.deleteById(id);
    }

    public void altera(LocalidadeDto localidadeDto_new) {
        //TODO: Resolver o problema de alterar o nome para um que ja existe quebrando a regra de duplicidade
        Localidade localidade = localidadeRepositorio.getReferenceById(localidadeDto_new.getId());
        localidade.setId(localidadeDto_new.getId());
        localidade.setNome(localidadeDto_new.getNome());
        localidade.setRua(localidadeDto_new.getRua());
        localidade.setCep(localidadeDto_new.getCep());
        localidade.setCidade(localidadeDto_new.getCidade());
        localidade.setEstado(localidadeDto_new.getEstado());

        localidadeRepositorio.save(localidade);
    }

    private LocalidadeDto converter (Localidade localidade) {
        LocalidadeDto result = new LocalidadeDto();
        result.setId(localidade.getId());
        result.setNome(localidade.getNome());
        result.setRua(localidade.getRua());
        result.setCep(localidade.getCep());
        result.setCidade(localidade.getCidade());
        result.setEstado(localidade.getEstado());
        return result;
    }

    public List<LocalidadeDto> getAll() {
        return localidadeRepositorio
                .findAll()
                .stream()
                .map(this::converter).collect(Collectors.toList());
    }
}
