package br.com.leandrobove.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.leandrobove.cursomc.entities.Categoria;
import br.com.leandrobove.cursomc.entities.Produto;
import br.com.leandrobove.cursomc.exceptions.ObjectNotFoundException;
import br.com.leandrobove.cursomc.repositories.CategoriaRepository;
import br.com.leandrobove.cursomc.repositories.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repo;

	@Autowired
	private CategoriaRepository categoriasRepository;

	public Produto find(Integer id) {
		Optional<Produto> obj = repo.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Produto não encontrado -  id: " + id + ", Tipo: " + Produto.class.getName()));
	}

	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy,
			String direction) {
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		List<Categoria> categorias = categoriasRepository.findAllById(ids);

		return repo.search(nome, categorias, pageRequest);
	}
}
