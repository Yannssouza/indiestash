package com.generation.indiestash.repository;

import com.generation.indiestash.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    public List<Categoria> findAllByDescricaoContainingIgnoreCase(@Param("descricao") String descricao);

}
