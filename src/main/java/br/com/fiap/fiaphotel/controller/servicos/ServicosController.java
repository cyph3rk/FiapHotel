package br.com.fiap.fiaphotel.controller.servicos;

import br.com.fiap.fiaphotel.controller.form.quartos.LocalidadeForm;
import br.com.fiap.fiaphotel.controller.form.servicos.ServicosForm;
import br.com.fiap.fiaphotel.dominio.servicos.Servicos;
import br.com.fiap.fiaphotel.dto.quartos.LocalidadeDto;
import br.com.fiap.fiaphotel.dto.servicos.ServicosDto;
import br.com.fiap.fiaphotel.facade.servicos.ServicosFacade;
import br.com.fiap.fiaphotel.repositorio.servicos.IServicosRepositorio;
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
@RequestMapping("/servicos")
public class ServicosController {

    private final IServicosRepositorio servicosRepositorio;

    private final Validator validator;

    private final ServicosFacade servicosFacade;

    @Autowired
    public ServicosController(IServicosRepositorio servicosRepositorio, Validator validator, ServicosFacade servicosFacade) {
        this.servicosRepositorio = servicosRepositorio;
        this.validator = validator;
        this.servicosFacade = servicosFacade;
    }


    private <T> Map<Path, String> validar(T form) {
        Set<ConstraintViolation<T>> violacoes = validator.validate(form);

        return violacoes.stream().collect(Collectors.toMap(
                ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage));
    }

    @PostMapping
    public ResponseEntity<Object> salva(@RequestBody ServicosForm servicosForm) {

        Map<Path, String> violacoesToMap = validar(servicosForm);

        if (!violacoesToMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToMap);
        }

        ServicosDto servicosDto = servicosForm.toServiosDto();
        Long resp = servicosFacade.salvar(servicosDto);
        if ( resp == -1) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Servicos JÁ cadastrado.\"}");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("{\"Messagem\": \"Servicos CADASTRADA com sucesso.\", " +
                "\"id\": \"" + resp +"\"}");
    }

    @GetMapping
    public ResponseEntity<String> getAll() {
        String json = "Erro Inesperado";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            json = objectMapper.writeValueAsString(servicosFacade.getAll());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(String.format(json));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscaPorId(@PathVariable Long id) {
        Optional<ServicosDto> servicosDto = servicosFacade.buscarPorId(id);

        boolean existeRegistro = servicosDto.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Servicos NÃO cadastrada.\"}");
        }
        return ResponseEntity.status(HttpStatus.OK).body(servicosDto);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Object> buscaPorNome(@PathVariable String nome) {

        List<ServicosDto> listaServicosDto = servicosFacade.buscarPorNome(nome);

        if (listaServicosDto.size() == 0) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Servicos NÃO cadastrada.\"}");
        }
        return ResponseEntity.status(HttpStatus.OK).body(listaServicosDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> apagaPorId(@PathVariable Long id) {

        Optional<ServicosDto> servicosDto = servicosFacade.buscarPorId(id);

        boolean existeRegistro = servicosDto.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Servicos NÃO cadastrada.\"}");
        }

        servicosFacade.remove(id);
        return ResponseEntity.ok("{\"Mensagem\": \"Servicos DELETADA com sucesso.\"}");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> alteraPorId(@PathVariable Long id, @RequestBody ServicosForm servicoForm) {
        Map<Path, String> violacoesToMap = validar(servicoForm);

        if (!violacoesToMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToMap);
        }

        Optional<ServicosDto> servicosDto_old = servicosFacade.buscarPorId(id);

        boolean existeRegistro = servicosDto_old.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Servicos NÃO cadastrado.\"}");
        }

        ServicosDto servicoDto = servicoForm.toServiosDto();
        servicoDto.setId(id);
        servicosFacade.altera(servicoDto);
        return ResponseEntity.status(HttpStatus.OK).body(servicoDto);
    }

}
