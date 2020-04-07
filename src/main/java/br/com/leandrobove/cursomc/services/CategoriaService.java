package br.com.leandrobove.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leandrobove.cursomc.entity.Categoria;
import br.com.leandrobove.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;

	public Categoria buscar(Integer id) {
		Optional<Categoria> categoria = repo.findById(id);

		return categoria.orElse(null);
	}

}
