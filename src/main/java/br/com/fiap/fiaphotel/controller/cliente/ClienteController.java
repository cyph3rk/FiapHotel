package br.com.fiap.fiaphotel.controller.cliente;

import br.com.fiap.fiaphotel.controller.form.cliente.ClienteForm;
import br.com.fiap.fiaphotel.controller.form.servicos.ServicosForm;
import br.com.fiap.fiaphotel.dominio.cliente.Cliente;
import br.com.fiap.fiaphotel.dto.cliente.ClienteDto;
import br.com.fiap.fiaphotel.dto.servicos.ServicosDto;
import br.com.fiap.fiaphotel.facade.cliente.ClienteFacade;
import br.com.fiap.fiaphotel.facade.servicos.ServicosFacade;
import br.com.fiap.fiaphotel.repositorio.cliente.IClienteRepositorio;
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
@RequestMapping("/cliente")
public class ClienteController {


    private final IClienteRepositorio clienteRepositorio;

    private final Validator validator;

    private final ClienteFacade clienteFacade;

    @Autowired
    public ClienteController(IClienteRepositorio servicosRepositorio, Validator validator, ClienteFacade clienteFacade) {
        this.clienteRepositorio = servicosRepositorio;
        this.validator = validator;
        this.clienteFacade = clienteFacade;
    }


    private <T> Map<Path, String> validar(T form) {
        Set<ConstraintViolation<T>> violacoes = validator.validate(form);

        return violacoes.stream().collect(Collectors.toMap(
                ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage));
    }

    @PostMapping
    public ResponseEntity<Object> salva(@RequestBody ClienteForm clienteForm) {

        Map<Path, String> violacoesToMap = validar(clienteForm);

        if (!violacoesToMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToMap);
        }

        ClienteDto clienteDto = clienteForm.toClienteDto();
        Long resp = clienteFacade.salvar(clienteDto);
        if ( resp == -1) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Cliente JÁ cadastrado.\"}");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("{\"Messagem\": \"Cliente CADASTRADO com sucesso.\", " +
                "\"id\": \"" + resp +"\"}");
    }

    @GetMapping
    public ResponseEntity<String> getAll() {
        String json = "Erro Inesperado";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            json = objectMapper.writeValueAsString(clienteFacade.getAll());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(String.format(json));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscaPorId(@PathVariable Long id) {
        Optional<ClienteDto> clienteDto = clienteFacade.buscarPorId(id);

        boolean existeRegistro = clienteDto.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Cliente NÃO cadastrado.\"}");
        }
        return ResponseEntity.status(HttpStatus.OK).body(clienteDto);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Object> buscaPorNome(@PathVariable String nome) {

        List<ClienteDto> listaClienteDto = clienteFacade.buscarPorNome(nome);

        if (listaClienteDto.size() == 0) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Cliente NÃO cadastrado.\"}");
        }
        return ResponseEntity.status(HttpStatus.OK).body(listaClienteDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> apagaPorId(@PathVariable Long id) {

        Optional<ClienteDto> clienteDto = clienteFacade.buscarPorId(id);

        boolean existeRegistro = clienteDto.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Cliente NÃO cadastrado.\"}");
        }

        clienteFacade.remove(id);
        return ResponseEntity.ok("{\"Mensagem\": \"Cliente DELETADO com sucesso.\"}");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> alteraPorId(@PathVariable Long id, @RequestBody ClienteForm clienteForm) {
        Map<Path, String> violacoesToMap = validar(clienteForm);

        if (!violacoesToMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToMap);
        }

        Optional<ClienteDto> clienteDto_old = clienteFacade.buscarPorId(id);

        boolean existeRegistro = clienteDto_old.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Cliente NÃO cadastrado.\"}");
        }

        ClienteDto clienteDto = clienteForm.toClienteDto();
        clienteDto.setId(id);
        clienteFacade.altera(clienteDto);
        return ResponseEntity.status(HttpStatus.OK).body(clienteDto);
    }


}
