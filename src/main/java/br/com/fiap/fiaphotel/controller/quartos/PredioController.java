package br.com.fiap.fiaphotel.controller.quartos;

import br.com.fiap.fiaphotel.controller.form.quartos.PredioForm;
import br.com.fiap.fiaphotel.dto.quartos.LocalidadeDto;
import br.com.fiap.fiaphotel.dto.quartos.PredioDto;
import br.com.fiap.fiaphotel.facade.quartos.LocalidadeFacade;
import br.com.fiap.fiaphotel.facade.quartos.PredioFacade;
import br.com.fiap.fiaphotel.repositorio.quartos.IPredioRepositorio;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/predio")
public class PredioController {

    private final IPredioRepositorio predioRepositorio;

    private final Validator validator;

    private final PredioFacade predioFacade;

    private final LocalidadeFacade localidadeFacade;

    @Autowired
    public PredioController(IPredioRepositorio predioRepositorio, Validator validator, PredioFacade predioFacade, LocalidadeFacade localidadeFacade) {
        this.predioRepositorio = predioRepositorio;
        this.validator = validator;
        this.predioFacade = predioFacade;
        this.localidadeFacade = localidadeFacade;
    }

    private <T> Map<Path, String> validar(T form) {
        Set<ConstraintViolation<T>> violacoes = validator.validate(form);

        return violacoes.stream().collect(Collectors.toMap(
                ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Object> salva(@PathVariable Long id,
                                        @RequestBody PredioForm predioForm) {

        Map<Path, String> violacoesToMap = validar(predioForm);

        if (!violacoesToMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToMap);
        }

        Optional<LocalidadeDto> localidadeDto = localidadeFacade.buscarPorId(id);
        boolean existeRegistro = localidadeDto.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Localidade NÃO cadastrada.\"}");
        }

        PredioDto predioDto = predioForm.toPredioDto();
        predioDto.setLocalidadeDto(localidadeDto.get());

        Long resp = predioFacade.salvar(predioDto);
        if ( resp == -1) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Predio JÁ cadastrado.\"}");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("{\"Messagem\": \"Predio CADASTRADO com sucesso.\", " +
                "\"id\": \"" + resp +"\"}");
    }

    @GetMapping
    public ResponseEntity<String> getAll() {
        String json = "Erro Inesperado";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            json = objectMapper.writeValueAsString(predioFacade.getAll());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(String.format(json));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscaPorId(@PathVariable Long id) {
        Optional<PredioDto> predioDto = predioFacade.buscarPorId(id);

        boolean existeRegistro = predioDto.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Predio NÃO cadastrada.\"}");
        }
        return ResponseEntity.status(HttpStatus.OK).body(predioDto);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Object> buscaPorNome(@PathVariable String nome) {

        List<PredioDto> listaPredioDto = predioFacade.buscarPorNome(nome);

        if (listaPredioDto.size() == 0) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Predio NÃO cadastrada.\"}");
        }
        return ResponseEntity.status(HttpStatus.OK).body(listaPredioDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> apagaPorId(@PathVariable Long id) {

        Optional<PredioDto> predioDto = predioFacade.buscarPorId(id);

        boolean existeRegistro = predioDto.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Predio NÃO cadastrada.\"}");
        }

        predioFacade.remove(id);
        return ResponseEntity.ok("{\"Mensagem\": \"Predio DELETADO com sucesso.\"}");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> alteraPorId(@PathVariable Long id, @RequestBody PredioForm predioForm) {
        Map<Path, String> violacoesToMap = validar(predioForm);

        if (!violacoesToMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToMap);
        }

        Optional<PredioDto> predioDto_old = predioFacade.buscarPorId(id);

        boolean existeRegistro = predioDto_old.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Predio NÃO cadastrado.\"}");
        }

        PredioDto predioDto = predioForm.toPredioDto();
        predioDto.setId(id);
        predioFacade.altera(predioDto);
        return ResponseEntity.status(HttpStatus.OK).body(predioDto);
    }

}
