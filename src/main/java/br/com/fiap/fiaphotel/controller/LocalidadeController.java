package br.com.fiap.fiaphotel.controller;

import br.com.fiap.fiaphotel.controller.form.LocalidadeForm;
import br.com.fiap.fiaphotel.dto.LocalidadeDto;
import br.com.fiap.fiaphotel.facade.LocalidadeFacade;
import br.com.fiap.fiaphotel.repositorio.IlocalidadeRepositorio;
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
import java.util.stream.Collectors;
import java.util.Set;

@RestController
@RequestMapping("/localidade")
public class LocalidadeController {

    private final IlocalidadeRepositorio localidadeRepositorio;

    private final Validator validator;

    private final LocalidadeFacade localidadeFacade;

    @Autowired
    public LocalidadeController(IlocalidadeRepositorio localidadeRepositorio, Validator validator, LocalidadeFacade localidadeFacade) {
        this.localidadeRepositorio = localidadeRepositorio;
        this.validator = validator;
        this.localidadeFacade = localidadeFacade;
    }

    private <T> Map<Path, String> validar(T form) {
        Set<ConstraintViolation<T>> violacoes = validator.validate(form);

        return violacoes.stream().collect(Collectors.toMap(
                ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage));
    }

    @PostMapping
    public ResponseEntity<Object> salva(@RequestBody LocalidadeForm localidadeForm) {

        Map<Path, String> violacoesToMap = validar(localidadeForm);

        if (!violacoesToMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToMap);
        }

        LocalidadeDto localidadeDto = localidadeForm.toLocalidadeDto();
        Long resp = localidadeFacade.salvar(localidadeDto);
        if ( resp == -1) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Localidade JÁ cadastrado.\"}");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("{\"Messagem\": \"Localidade CADASTRADA com sucesso.\", " +
                "\"id\": \"" + resp +"\"}");
    }

    @GetMapping
    public ResponseEntity<String> getAll() {
        String json = "Erro Inesperado";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            json = objectMapper.writeValueAsString(localidadeFacade.getAll());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(String.format(json));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscaPorId(@PathVariable Long id) {
        Optional<LocalidadeDto> localidadeDto = localidadeFacade.buscarPorId(id);

        boolean existeRegistro = localidadeDto.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Localidade NÃO cadastrada.\"}");
        }
        return ResponseEntity.status(HttpStatus.OK).body(localidadeDto);
    }

    @GetMapping("/rua/{nome}")
    public ResponseEntity<Object> buscaPorNome(@PathVariable String nome) {

        List<LocalidadeDto> listaLocalidadeDto = localidadeFacade.buscarPorNome(nome);

        if (listaLocalidadeDto.size() == 0) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Localidade NÃO cadastrada.\"}");
        }
        return ResponseEntity.status(HttpStatus.OK).body(listaLocalidadeDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> apagaPorId(@PathVariable Long id) {

        Optional<LocalidadeDto> localidadeDto = localidadeFacade.buscarPorId(id);

        boolean existeRegistro = localidadeDto.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Localidade NÃO cadastrada.\"}");
        }

        localidadeFacade.remove(id);
        return ResponseEntity.ok("{\"Mensagem\": \"Localidade DELETADA com sucesso.\"}");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> alteraPorId(@PathVariable Long id, @RequestBody LocalidadeForm localidadeForm) {
        Map<Path, String> violacoesToMap = validar(localidadeForm);

        if (!violacoesToMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToMap);
        }

        Optional<LocalidadeDto> enderecoDto_old = localidadeFacade.buscarPorId(id);

        boolean existeRegistro = enderecoDto_old.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Localidade NÃO cadastrado.\"}");
        }

        LocalidadeDto localidadeDto = localidadeForm.toLocalidadeDto();
        localidadeDto.setId(id);
        localidadeFacade.altera(localidadeDto);
        return ResponseEntity.status(HttpStatus.OK).body(localidadeDto);
    }

}
