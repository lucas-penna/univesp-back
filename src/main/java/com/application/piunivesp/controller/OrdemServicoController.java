package com.application.piunivesp.controller;

import com.application.piunivesp.model.OrdemServico;
import com.application.piunivesp.resources.AbstractCrudController;
import com.application.piunivesp.resources.AbstractService;
import com.application.piunivesp.service.OrdemServicoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/ordens-servicos")
public class OrdemServicoController extends AbstractCrudController<OrdemServico> {

    private final OrdemServicoService ordemServicoService;

    public OrdemServicoController(OrdemServicoService ordemServicoService) {
        this.ordemServicoService = ordemServicoService;
    }

    @Override
    public AbstractService getService() {
        return ordemServicoService;
    }
}
