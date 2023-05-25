package com.application.piunivesp.controller;

import com.application.piunivesp.model.Cliente;
import com.application.piunivesp.resources.AbstractCrudController;
import com.application.piunivesp.resources.AbstractService;
import com.application.piunivesp.service.ClienteService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/clientes")
public class ClienteController extends AbstractCrudController<Cliente> {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Override
    public AbstractService getService() {
        return this.clienteService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findAll(Pageable pageable, Cliente filter) {
        return ResponseEntity.ok(this.clienteService.read(pageable, filter));
    }

    @PostMapping(value = "/create-client")
    public ResponseEntity<?> createClient(Cliente cliente) {
        this.clienteService.create(cliente);
        return ResponseEntity.ok().build();
    }
}
