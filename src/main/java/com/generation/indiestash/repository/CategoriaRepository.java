package com.generation.indiestash.repository;

import com.generation.indiestash.model.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TemaRepository extends JpaRepository<Categorias, Long> {

    public List<Categorias> findAllByDescricaoContainingIgnoreCase(@Param("descricao") String descricao);

}
