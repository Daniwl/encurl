package com.daniwl.encurl.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daniwl.encurl.model.Link;

public interface LinkRepository extends JpaRepository<Link, Long> {

	//	save(Link entity): Faz o INSERT ou UPDATE.
	//
	//	findById(Long id): Faz o SELECT por ID.
	//
	//	findAll(): Faz o SELECT *.
	//
	//	deleteById(Long id): Faz o DELETE.
	
	Optional<Link> findByUrl(String url);
	
	// JPQL Complexo (Lendo as Entidades)
    //@Query("SELECT l FROM Link l JOIN l.usuario u WHERE u.ativo = true AND l.cliques > :minCliques")
    //List<Link> buscarLinksPopularesDeUsuariosAtivos(@Param("minCliques") int minCliques);
}
