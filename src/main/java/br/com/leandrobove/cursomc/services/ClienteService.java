package br.com.leandrobove.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.leandrobove.cursomc.dto.ClienteDTO;
import br.com.leandrobove.cursomc.dto.ClienteNewDTO;
import br.com.leandrobove.cursomc.entities.Cidade;
import br.com.leandrobove.cursomc.entities.Cliente;
import br.com.leandrobove.cursomc.entities.Endereco;
import br.com.leandrobove.cursomc.entities.enums.TipoCliente;
import br.com.leandrobove.cursomc.exceptions.DataIntegrityException;
import br.com.leandrobove.cursomc.exceptions.ObjectNotFoundException;
import br.com.leandrobove.cursomc.repositories.ClienteRepository;
import br.com.leandrobove.cursomc.repositories.EnderecoRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;

	@Autowired
	private EnderecoRepository enderecoRepository;

	public Cliente find(Integer id) {
		Optional<Cliente> cliente = repo.findById(id);

		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Cliente não encontrado -  id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}

	public Cliente fromDTO(ClienteDTO clienteDTO) {
		Cliente c = new Cliente();

		c.setId(clienteDTO.getId());
		c.setNome(clienteDTO.getNome());
		c.setEmail(clienteDTO.getEmail());

		return c;
	}

	public Cliente fromDTO(ClienteNewDTO clienteDto) {

		Cliente cliente = new Cliente(null, clienteDto.getNome(), clienteDto.getEmail(), clienteDto.getCpfOuCnpj(),
				TipoCliente.toEnum(clienteDto.getTipo()));

		Cidade cidade = new Cidade(clienteDto.getCidadeId(), null, null);

		Endereco end = new Endereco(null, clienteDto.getLogradouro(), clienteDto.getNumero(),
				clienteDto.getComplemento(), clienteDto.getBairro(), clienteDto.getCep(), cliente, cidade);

		cliente.getEnderecos().add(end);
		cliente.getTelefones().add(clienteDto.getTelefone1());

		if (clienteDto.getTelefone2() != null) {
			cliente.getTelefones().add(clienteDto.getTelefone2());
		}
		if (clienteDto.getTelefone2() != null) {
			cliente.getTelefones().add(clienteDto.getTelefone3());
		}

		return cliente;
	}

	@Transactional
	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		
		cliente = repo.save(cliente);

		enderecoRepository.saveAll(cliente.getEnderecos());
		
		return cliente;
	}

	public Cliente update(Cliente obj) {

		Cliente clienteNovo = find(obj.getId());

		updateData(clienteNovo, obj);

		return repo.save(clienteNovo);
	}

	private void updateData(Cliente newCliente, Cliente cliente) {
		newCliente.setNome(cliente.getNome());
		newCliente.setEmail(cliente.getEmail());
	}

	public void delete(Integer id) {
		find(id);

		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não foi possível excluir, pois o cliente possui relacionamentos.");
		}
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		return repo.findAll(pageRequest);
	}

}
