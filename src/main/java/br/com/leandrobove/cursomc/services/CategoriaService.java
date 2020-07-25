package br.com.leandrobove.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.leandrobove.cursomc.entities.Categoria;
import br.com.leandrobove.cursomc.exceptions.DataIntegrityException;
import br.com.leandrobove.cursomc.exceptions.ObjectNotFoundException;
import br.com.leandrobove.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;

	public Categoria find(Integer id) {
		Optional<Categoria> categoria = repo.findById(id);

		return categoria.orElseThrow(() -> new ObjectNotFoundException(
				"Categoria não encontrada -  id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	public Categoria insert(Categoria obj) {
		obj.setId(null);

		return repo.save(obj);
	}

	public Categoria update(Categoria cat) {
		find(cat.getId());

		return repo.save(cat);
	}

	public void delete(Integer id) {
		find(id);

		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não foi possível excluir, pois a categoria possui produtos relacionados.");
		}

	}

}
