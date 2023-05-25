package com.application.piunivesp.service;

import com.application.piunivesp.exception.type.BusinessException;
import com.application.piunivesp.model.Produto;
import com.application.piunivesp.repository.ProdutoRepository;
import com.application.piunivesp.resources.AbstractService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

@Service
public class ProdutoService extends AbstractService<Produto> {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public JpaRepository getRepository() {
        return produtoRepository;
    }

    @Override
    public void validateCreateOrUpdate(Produto entity) {
        if (ObjectUtils.isEmpty(StringUtils.trimAllWhitespace(entity.getDescricao()))) {
            throw new BusinessException("Produto não pode estar com descrição vazia.");
        }

        if (ObjectUtils.isEmpty(entity.getValor())) {
            throw new BusinessException("Produto não pode estar com valor vazio.");
        }

        if (ObjectUtils.isEmpty(entity.getMargem())) {
            throw new BusinessException("Produto não pode estar com margem vazia.");
        }

        if (ObjectUtils.isEmpty(entity.getQuantidade())) {
            throw new BusinessException("Produto não pode estar com quantidade vazia.");
        }

        if (this.produtoRepository.countByIdNotLikeAndDescricaoIgnoreCase(
                ObjectUtils.isEmpty(entity.getId()) ? 0L : entity.getId(),
                entity.getDescricao().trim()) != 0L) {
            throw new BusinessException("Já existe produto cadastrado com mesma descrição.");
        }
    }

    public Page<Produto> read(Pageable pageable, Produto filter) {
        return this.produtoRepository.findPaginationFilter(pageable, filter.getDescricao());
    }

    public ArrayList<Produto> getListDTO(){
        return produtoRepository.getListDTO();
    }
}
