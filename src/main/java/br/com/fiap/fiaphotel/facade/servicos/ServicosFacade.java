package br.com.fiap.fiaphotel.facade.servicos;

import br.com.fiap.fiaphotel.dominio.quartos.Localidade;
import br.com.fiap.fiaphotel.dominio.servicos.Servicos;
import br.com.fiap.fiaphotel.dto.quartos.LocalidadeDto;
import br.com.fiap.fiaphotel.dto.servicos.ServicosDto;
import br.com.fiap.fiaphotel.repositorio.quartos.IlocalidadeRepositorio;
import br.com.fiap.fiaphotel.repositorio.servicos.IServicosRepositorio;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServicosFacade {

    private final IServicosRepositorio servicosRepositorio;

    @Autowired
    public ServicosFacade(IServicosRepositorio localidadeRepositorio) {
        this.servicosRepositorio = localidadeRepositorio;
    }

    public Long salvar(ServicosDto servicosDto) {
        List<ServicosDto> encontrado = buscarPorNome(servicosDto.getNome());
        if (encontrado.size() >= 1) {
            return -1L;
        }

        Servicos servicos = new Servicos();
        servicos.setNome(servicosDto.getNome());
        servicos.setValor(servicosDto.getValor());
        servicosRepositorio.save(servicos);

        return servicos.getId();
    }

    public List<ServicosDto> buscarPorNome(String nome) {
        List<Servicos> servicosLocalidade = servicosRepositorio.findByNome(nome);

        return servicosLocalidade.stream()
                .map(this::converter).collect(Collectors.toList());
    }

    public Optional<ServicosDto> buscarPorId(Long id) {

        try {
            Servicos servico = servicosRepositorio.getReferenceById(id);

            ServicosDto servicoDto = new ServicosDto();
            servicoDto.setId(servico.getId());
            servicoDto.setNome(servico.getNome());
            servicoDto.setValor(servico.getValor());

            return Optional.of(servicoDto);
        } catch (EntityNotFoundException ex) {
            return Optional.empty();
        }
    }

    public void remove(Long id) {
        //Todo: Implementar a verificação se cadastro existe antes de deletar
        servicosRepositorio.deleteById(id);
    }

    public void altera(ServicosDto ServicoDto_new) {
        //TODO: Resolver o problema de alterar o nome para um que ja existe quebrando a regra de duplicidade
        Servicos servico = servicosRepositorio.getReferenceById(ServicoDto_new.getId());
        servico.setId(ServicoDto_new.getId());
        servico.setNome(ServicoDto_new.getNome());
        servico.setValor(ServicoDto_new.getValor());

        servicosRepositorio.save(servico);
    }

    private ServicosDto converter (Servicos servico) {
        ServicosDto servicosDto = new ServicosDto();
        servicosDto.setId(servico.getId());
        servicosDto.setNome(servico.getNome());
        servicosDto.setValor(servico.getValor());
        return servicosDto;
    }

    public List<ServicosDto> getAll() {
        return servicosRepositorio
                .findAll()
                .stream()
                .map(this::converter).collect(Collectors.toList());
    }
}
