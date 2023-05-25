package com.application.piunivesp.service;

import com.application.piunivesp.exception.type.BusinessException;
import com.application.piunivesp.model.Cliente;
import com.application.piunivesp.model.TipoPessoa;
import com.application.piunivesp.repository.ClienteRepository;
import com.application.piunivesp.resources.AbstractService;
import com.application.piunivesp.util.Util;
import com.application.piunivesp.util.ValidaCPFCNPJ;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class ClienteService extends AbstractService<Cliente> {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public JpaRepository getRepository() {
        return clienteRepository;
    }

    @Override
    public void validateCreateOrUpdate(Cliente entity) {
        if (ObjectUtils.isEmpty(StringUtils.trimAllWhitespace(entity.getNome()))) {
            throw new BusinessException("Cliente não pode ter campo nome vazio.");
        }

        if (ObjectUtils.isEmpty(StringUtils.trimAllWhitespace(entity.getTelefone()))) {
            throw new BusinessException("Cliente não pode ter campo telefone vazio.");
        }

        entity.setTelefone(StringUtils.trimAllWhitespace(Util.removeCaracteresEspeciais(entity.getTelefone())));

        if (ObjectUtils.isEmpty(StringUtils.trimAllWhitespace(entity.getCpfcnpj()))) {
            throw new BusinessException("Cliente não pode ter campo CPF/CNPJ vazio.");
        }

        if (ObjectUtils.isEmpty(entity.getTipoPessoa())) {
            throw new BusinessException("Cliente não pode ter campo tipo pessoa vazio.");
        }

        if (this.clienteRepository.countByIdNotLikeAndCpfcnpjIgnoreCase(ObjectUtils.isEmpty(entity.getId()) ? 0L : entity.getId(),
                entity.getCpfcnpj()) != 0L) {
            throw new BusinessException("Cliente já cadastrado na base de dados.");
        }

        if (TipoPessoa.FISICA == entity.getTipoPessoa()) {
            if (!ValidaCPFCNPJ.isCPF(entity.getCpfcnpj())) {
                throw new BusinessException("CPF inválido.");
            }
        } else {
            if (!ValidaCPFCNPJ.isCNPJ(entity.getCpfcnpj())) {
                throw new BusinessException("CNPJ inválido.");
            }
        }
    }

    public Page<Cliente> read(Pageable pageable, Cliente filter) {
        return this.clienteRepository.findPaginationFilter(pageable, filter.getNome());
    }

}
