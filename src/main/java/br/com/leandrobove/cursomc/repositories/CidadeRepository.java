package br.com.leandrobove.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.leandrobove.cursomc.entities.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

}
