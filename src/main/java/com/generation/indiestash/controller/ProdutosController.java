package com.generation.indiestash.controller;

import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.generation.indiestash.model.Produtos;
import com.generation.indiestash.repository.ProdutosRepository;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutosController {

    @Autowired
    private ProdutosRepository produtosRepository;

    @Autowired
    private com.generation.indiestash.repository.CategoriaRepository categoriaRepository;

    @GetMapping
    public ResponseEntity<List<Produtos>> getAll() {
        return ResponseEntity.ok(produtosRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produtos> getById(@PathVariable Long id) {
        return produtosRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Produtos>> getByNome(@PathVariable String nome) {
        return ResponseEntity.ok(produtosRepository.findAllByNomeContainingIgnoreCase(nome));
    }

    @PostMapping
    public ResponseEntity<Produtos> post(@Valid @RequestBody Produtos produtos) {
        if (categoriaRepository.existsById(produtos.getCategoria().getId()))
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(produtosRepository.save(produtos));

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não existe!", null);
    }

    @PutMapping
    public ResponseEntity<Produtos> put(@Valid @RequestBody Produtos produtos) {
        if (produtosRepository.existsById(produtos.getId())) {

            if (categoriaRepository.existsById(produtos.getCategoria().getId()))
                return ResponseEntity.status(HttpStatus.OK)
                        .body(produtosRepository.save(produtos));

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não existe!", null);

        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Optional<Produtos> postagem = produtosRepository.findById(id);

        if (postagem.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        produtosRepository.deleteById(id);
    }
}
