package br.com.fiap.fiaphotel.facade.cliente;

import br.com.fiap.fiaphotel.dominio.cliente.Cliente;
import br.com.fiap.fiaphotel.dominio.quartos.Localidade;
import br.com.fiap.fiaphotel.dto.cliente.ClienteDto;
import br.com.fiap.fiaphotel.dto.quartos.LocalidadeDto;
import br.com.fiap.fiaphotel.repositorio.cliente.IClienteRepositorio;
import br.com.fiap.fiaphotel.repositorio.quartos.IlocalidadeRepositorio;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteFacade {

    private final IClienteRepositorio clienteRepositorio;

    @Autowired
    public ClienteFacade(IClienteRepositorio localidadeRepositorio) {
        this.clienteRepositorio = localidadeRepositorio;
    }

    public Long salvar(ClienteDto clienteDto) {
        List<ClienteDto> encontrado = buscarPorNome(clienteDto.getNome());
        if (encontrado.size() >= 1) {
            return -1L;
        }

        Cliente cliente = new Cliente();
        cliente.setPais(clienteDto.getPais());
        cliente.setCpf(clienteDto.getCpf());
        cliente.setPassaporte(clienteDto.getPassaporte());
        cliente.setNome(clienteDto.getNome());
        cliente.setDatanascimento(clienteDto.getDatanascimento());
        cliente.setTelefone(clienteDto.getTelefone());
        cliente.setEmail(clienteDto.getEmail());
        clienteRepositorio.save(cliente);

        return cliente.getId();
    }

    public List<ClienteDto> buscarPorNome(String nome) {
        List<Cliente> listaCliente = clienteRepositorio.findByNome(nome);

        return listaCliente.stream()
                .map(this::converter).collect(Collectors.toList());
    }

    public Optional<ClienteDto> buscarPorId(Long id) {

        try {
            Cliente cliente = clienteRepositorio.getReferenceById(id);

            ClienteDto clienteDto = new ClienteDto();
            clienteDto.setId(cliente.getId());
            clienteDto.setPais(cliente.getPais());
            clienteDto.setCpf(cliente.getCpf());
            clienteDto.setPassaporte(cliente.getPassaporte());
            clienteDto.setNome(cliente.getNome());
            clienteDto.setDatanascimento(cliente.getDatanascimento());
            clienteDto.setTelefone(cliente.getTelefone());
            clienteDto.setEmail(cliente.getEmail());

            return Optional.of(clienteDto);
        } catch (EntityNotFoundException ex) {
            return Optional.empty();
        }
    }

    public void remove(Long id) {
        //Todo: Implementar a verificação se cadastro existe antes de deletar
        clienteRepositorio.deleteById(id);
    }

    public void altera(ClienteDto clienteDto_new) {
        //TODO: Resolver o problema de alterar o nome para um que ja existe quebrando a regra de duplicidade
        Cliente cliente = clienteRepositorio.getReferenceById(clienteDto_new.getId());
        cliente.setId(clienteDto_new.getId());
        cliente.setPais(clienteDto_new.getPais());
        cliente.setCpf(clienteDto_new.getCpf());
        cliente.setPassaporte(clienteDto_new.getPassaporte());
        cliente.setNome(clienteDto_new.getNome());
        cliente.setDatanascimento(clienteDto_new.getDatanascimento());
        cliente.setTelefone(clienteDto_new.getTelefone());
        cliente.setEmail(clienteDto_new.getEmail());

        clienteRepositorio.save(cliente);
    }

    private ClienteDto converter (Cliente cliente) {
        ClienteDto result = new ClienteDto();
        result.setId(cliente.getId());
        result.setPais(cliente.getPais());
        result.setCpf(cliente.getCpf());
        result.setPassaporte(cliente.getPassaporte());
        result.setNome(cliente.getNome());
        result.setDatanascimento(cliente.getDatanascimento());
        result.setTelefone(cliente.getTelefone());
        result.setEmail(cliente.getEmail());
        return result;
    }

    public List<ClienteDto> getAll() {
        return clienteRepositorio
                .findAll()
                .stream()
                .map(this::converter).collect(Collectors.toList());
    }

}


