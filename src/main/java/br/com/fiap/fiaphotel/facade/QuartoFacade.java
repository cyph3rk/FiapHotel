package br.com.fiap.fiaphotel.facade;

import br.com.fiap.fiaphotel.dominio.Predio;
import br.com.fiap.fiaphotel.dominio.Quarto;
import br.com.fiap.fiaphotel.dto.PredioDto;
import br.com.fiap.fiaphotel.dto.QuartoDto;
import br.com.fiap.fiaphotel.repositorio.IPredioRepositorio;
import br.com.fiap.fiaphotel.repositorio.IQuartoRepositorio;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuartoFacade {

    private final IQuartoRepositorio quartoRepositorio;

    private final IPredioRepositorio predioRepositorio;

    @Autowired
    public QuartoFacade(IQuartoRepositorio quartoRepositorio, IPredioRepositorio predioRepositorio) {
        this.quartoRepositorio = quartoRepositorio;
        this.predioRepositorio = predioRepositorio;
    }

    public Long salvar(QuartoDto quartoDto) {
        List<QuartoDto> encontrado = buscarPorNome(quartoDto.getTipo());
        if (encontrado.size() >= 1) {
            return -1L;
        }

        Quarto quarto = new Quarto();
        quarto.setTipo(quartoDto.getTipo());
        quarto.setPessoas(quartoDto.getPessoas());
        quarto.setCamas(quartoDto.getCamas());
        quarto.setMoveis(quartoDto.getMoveis());
        quarto.setBanheiro(quartoDto.getBanheiro());
        quarto.setValor(quartoDto.getValor());

        Predio predio = new Predio();
        predio.setId(quartoDto.getPredioDto().getId());
        predio.setNome(quartoDto.getPredioDto().getNome());
        quarto.setPredio(predio);

        quartoRepositorio.save(quarto);

        return quarto.getId();
    }

    public List<QuartoDto> buscarPorNome(String nome) {
        List<Quarto> listaQuarto = quartoRepositorio.findByNome(nome);

        return listaQuarto.stream()
                .map(this::converter).collect(Collectors.toList());
    }

    public Optional<QuartoDto> buscarPorId(Long id) {

        try {
            Quarto quarto = quartoRepositorio.getReferenceById(id);

            QuartoDto quartoDto = new QuartoDto();
            quartoDto.setId(quarto.getId());
            quartoDto.setTipo(quarto.getTipo());
            quartoDto.setPessoas(quarto.getPessoas());
            quartoDto.setCamas(quarto.getCamas());
            quartoDto.setMoveis(quarto.getMoveis());
            quartoDto.setBanheiro(quarto.getBanheiro());
            quartoDto.setValor(quarto.getValor());

            PredioDto predioDto = new PredioDto();
            predioDto.setId(quarto.getPredio().getId());
            predioDto.setNome(quarto.getPredio().getNome());

            return Optional.of(quartoDto);
        } catch (EntityNotFoundException ex) {
            return Optional.empty();
        }
    }

    public void remove(Long id) {
        //Todo: Implementar a verificação se cadastro existe antes de deletar
        quartoRepositorio.deleteById(id);
    }

    public void altera(QuartoDto quartoDto_new) {
        //TODO: Resolver o problema de alterar o nome para um que ja existe quebrando a regra de duplicidade
        Quarto quarto = quartoRepositorio.getReferenceById(quartoDto_new.getId());
        quarto.setId(quartoDto_new.getId());
        quarto.setTipo(quartoDto_new.getTipo());
        quarto.setPessoas(quartoDto_new.getPessoas());
        quarto.setCamas(quartoDto_new.getCamas());
        quarto.setMoveis(quartoDto_new.getMoveis());
        quarto.setBanheiro(quartoDto_new.getBanheiro());
        quarto.setValor(quartoDto_new.getValor());

        Predio predio = new Predio();
        predio.setId(quartoDto_new.getPredioDto().getId());
        predio.setNome(quartoDto_new.getPredioDto().getNome());

        quarto.setPredio(predio);

        quartoRepositorio.save(quarto);
    }

    private QuartoDto converter (Quarto quarto) {
        QuartoDto quartoDto = new QuartoDto();
        quartoDto.setId(quarto.getId());
        quartoDto.setTipo(quarto.getTipo());
        quartoDto.setPessoas(quarto.getPessoas());
        quartoDto.setCamas(quarto.getCamas());
        quartoDto.setMoveis(quarto.getMoveis());
        quartoDto.setBanheiro(quarto.getBanheiro());
        quartoDto.setValor(quarto.getValor());

        PredioDto predioDto = new PredioDto();
        predioDto.setId(quarto.getPredio().getId());
        predioDto.setNome(quarto.getPredio().getNome());
        quartoDto.setPredioDto(predioDto);

        return quartoDto;
    }

    public List<QuartoDto> getAll() {
        return quartoRepositorio
                .findAll()
                .stream()
                .map(this::converter).collect(Collectors.toList());
    }
}
