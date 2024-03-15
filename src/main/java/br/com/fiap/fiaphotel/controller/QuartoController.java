package br.com.fiap.fiaphotel.controller;

import br.com.fiap.fiaphotel.controller.form.PredioForm;
import br.com.fiap.fiaphotel.controller.form.QuartoForm;
import br.com.fiap.fiaphotel.dto.PredioDto;
import br.com.fiap.fiaphotel.dto.QuartoDto;
import br.com.fiap.fiaphotel.facade.LocalidadeFacade;
import br.com.fiap.fiaphotel.facade.PredioFacade;
import br.com.fiap.fiaphotel.facade.QuartoFacade;
import br.com.fiap.fiaphotel.repositorio.IPredioRepositorio;
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
@RequestMapping("/quarto")
public class QuartoController {

    private final IPredioRepositorio quartoRepositorio;

    private final Validator validator;

    private final PredioFacade predioFacade;

    private final QuartoFacade quartoFacade;

    private final LocalidadeFacade localidadeFacade;

    @Autowired
    public QuartoController(IPredioRepositorio quartoRepositorio, Validator validator, PredioFacade predioFacade, QuartoFacade quartoFacade, LocalidadeFacade localidadeFacade) {
        this.quartoRepositorio = quartoRepositorio;
        this.validator = validator;
        this.predioFacade = predioFacade;
        this.quartoFacade = quartoFacade;
        this.localidadeFacade = localidadeFacade;
    }

    private <T> Map<Path, String> validar(T form) {
        Set<ConstraintViolation<T>> violacoes = validator.validate(form);

        return violacoes.stream().collect(Collectors.toMap(
                ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Object> salva(@PathVariable Long id,
                                        @RequestBody QuartoForm quartoForm) {

        Map<Path, String> violacoesToMap = validar(quartoForm);

        if (!violacoesToMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToMap);
        }

        Optional<PredioDto> predioDto = predioFacade.buscarPorId(id);
        boolean existeRegistro = predioDto.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Predio NÃO cadastrada.\"}");
        }

        QuartoDto quartoDto = quartoForm.toQuartoDto();
        quartoDto.setPredioDto(predioDto.get());

        Long resp = quartoFacade.salvar(quartoDto);
        if ( resp == -1) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Quarto JÁ cadastrado.\"}");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("{\"Messagem\": \"Quarto CADASTRADO com sucesso.\", " +
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
        Optional<QuartoDto> quartoDto = quartoFacade.buscarPorId(id);

        boolean existeRegistro = quartoDto.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Quarto NÃO cadastrada.\"}");
        }
        return ResponseEntity.status(HttpStatus.OK).body(quartoDto);
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<Object> buscaPorTipo(@PathVariable String tipo) {

        List<QuartoDto> listaQuartoDto = quartoFacade.buscarPorTipo(tipo);

        if (listaQuartoDto.size() == 0) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Quarto NÃO cadastrada.\"}");
        }
        return ResponseEntity.status(HttpStatus.OK).body(listaQuartoDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> apagaPorId(@PathVariable Long id) {

        Optional<QuartoDto> quartoDto = quartoFacade.buscarPorId(id);

        boolean existeRegistro = quartoDto.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Quarto NÃO cadastrada.\"}");
        }

        predioFacade.remove(id);
        return ResponseEntity.ok("{\"Mensagem\": \"Quarto DELETADO com sucesso.\"}");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> alteraPorId(@PathVariable Long id, @RequestBody QuartoForm QuartoForm) {
        Map<Path, String> violacoesToMap = validar(QuartoForm);

        if (!violacoesToMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToMap);
        }

        Optional<QuartoDto> quartoDto_old = quartoFacade.buscarPorId(id);

        boolean existeRegistro = quartoDto_old.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Quarto NÃO cadastrado.\"}");
        }

        QuartoDto quartoDto = QuartoForm.toQuartoDto();
        quartoDto.setId(id);
        quartoFacade.altera(quartoDto);
        return ResponseEntity.status(HttpStatus.OK).body(quartoDto);
    }

}
