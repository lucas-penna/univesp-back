package com.application.piunivesp.service;

import com.application.piunivesp.exception.type.BusinessException;
import com.application.piunivesp.model.OrdemServico;
import com.application.piunivesp.repository.OrdemServicoRepository;
import com.application.piunivesp.resources.AbstractService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class OrdemServicoService extends AbstractService<OrdemServico> {

    private final OrdemServicoRepository ordemServicoRepository;

    public OrdemServicoService(OrdemServicoRepository ordemServicoRepository) {
        this.ordemServicoRepository = ordemServicoRepository;
    }

    @Override
    public JpaRepository getRepository() {
        return ordemServicoRepository;
    }

    @Override
    public void validateCreateOrUpdate(OrdemServico entity) {
        if (ObjectUtils.isEmpty(entity.getDescricao())) {
            throw new BusinessException("Ordem de Serviço não pode estar com descrição vazia.");
        }

        if (ObjectUtils.isEmpty(entity.getProdutosOrdemServico())) {
            throw new BusinessException("Ordem de Serviço deve conter ao menos um item.");
        }

        if (ObjectUtils.isEmpty(entity.getMarca())) {
            throw new BusinessException("Ordem de Serviço deve conter marca e modelo do veículo.");
        }

        if (ObjectUtils.isEmpty(entity.getModelo())) {
            throw new BusinessException("Ordem de Serviço deve conter marca e modelo do veículo.");
        }

        if (ObjectUtils.isEmpty(entity.getNomeCliente())) {
            throw new BusinessException("Ordem de Serviço deve conter nome do cliente.");
        }

    }
}
