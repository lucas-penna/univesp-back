package com.application.piunivesp.controller;

import com.application.piunivesp.model.Produto;
import com.application.piunivesp.resources.AbstractCrudController;
import com.application.piunivesp.resources.AbstractService;
import com.application.piunivesp.service.ProdutoService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/produtos")
public class ProdutoController extends AbstractCrudController<Produto> {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Override
    public AbstractService getService() {
        return this.produtoService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findAll(Pageable pageable, Produto filter) {
        return ResponseEntity.ok(this.produtoService.read(pageable, filter));
    }

    @GetMapping(value = "/list-dto")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getListDTO() {
        return ResponseEntity.ok(this.produtoService.getListDTO());
    }

    @PostMapping(value = "/create-product")
    public ResponseEntity<?> createClient(@RequestBody Produto produto) {
        this.produtoService.create(produto);
        return ResponseEntity.ok().build();
    }
}
