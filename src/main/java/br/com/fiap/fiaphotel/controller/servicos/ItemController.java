package br.com.fiap.fiaphotel.controller.servicos;

import br.com.fiap.fiaphotel.controller.form.servicos.ItemForm;
import br.com.fiap.fiaphotel.controller.form.servicos.ServicosForm;
import br.com.fiap.fiaphotel.dominio.servicos.Item;
import br.com.fiap.fiaphotel.dto.servicos.ItemDto;
import br.com.fiap.fiaphotel.dto.servicos.ServicosDto;
import br.com.fiap.fiaphotel.facade.servicos.ItemFacade;
import br.com.fiap.fiaphotel.facade.servicos.ServicosFacade;
import br.com.fiap.fiaphotel.repositorio.servicos.IItemRepositorio;
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
@RequestMapping("/item")
public class ItemController {

    private final IItemRepositorio itemRepositorio;

    private final Validator validator;

    private final ItemFacade itemFacade;

    @Autowired
    public ItemController(IItemRepositorio itemRepositorio, Validator validator, ItemFacade itemFacade) {
        this.itemRepositorio = itemRepositorio;
        this.validator = validator;
        this.itemFacade = itemFacade;
    }


    private <T> Map<Path, String> validar(T form) {
        Set<ConstraintViolation<T>> violacoes = validator.validate(form);

        return violacoes.stream().collect(Collectors.toMap(
                ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage));
    }

    @PostMapping
    public ResponseEntity<Object> salva(@RequestBody ItemForm itemForm) {

        Map<Path, String> violacoesToMap = validar(itemForm);

        if (!violacoesToMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToMap);
        }

        ItemDto itemDto = itemForm.toItemDto();
        Long resp = itemFacade.salvar(itemDto);
        if ( resp == -1) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Item JÁ cadastrado.\"}");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("{\"Mensagem\": \"Item CADASTRADO com sucesso.\", " +
                "\"id\": \"" + resp +"\"}");
    }

    @GetMapping
    public ResponseEntity<String> getAll() {
        String json = "Erro Inesperado";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            json = objectMapper.writeValueAsString(itemFacade.getAll());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(String.format(json));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscaPorId(@PathVariable Long id) {
        Optional<ItemDto> itemDto = itemFacade.buscarPorId(id);

        boolean existeRegistro = itemDto.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Item NÃO cadastrado.\"}");
        }
        return ResponseEntity.status(HttpStatus.OK).body(itemDto);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Object> buscaPorNome(@PathVariable String nome) {

        List<ItemDto> listaItemDto = itemFacade.buscarPorNome(nome);

        if (listaItemDto.size() == 0) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Item NÃO cadastrado.\"}");
        }
        return ResponseEntity.status(HttpStatus.OK).body(listaItemDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> apagaPorId(@PathVariable Long id) {

        Optional<ItemDto> itemDto = itemFacade.buscarPorId(id);

        boolean existeRegistro = itemDto.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Item NÃO cadastrado.\"}");
        }

        itemFacade.remove(id);
        return ResponseEntity.ok("{\"Mensagem\": \"Item DELETADO com sucesso.\"}");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> alteraPorId(@PathVariable Long id, @RequestBody ItemForm itemForm) {
        Map<Path, String> violacoesToMap = validar(itemForm);

        if (!violacoesToMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToMap);
        }

        Optional<ItemDto> itemDto_old = itemFacade.buscarPorId(id);

        boolean existeRegistro = itemDto_old.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Item NÃO cadastrado.\"}");
        }

        ItemDto itemDto = itemForm.toItemDto();
        itemDto.setId(id);
        itemFacade.altera(itemDto);
        return ResponseEntity.status(HttpStatus.OK).body(itemDto);
    }

}
