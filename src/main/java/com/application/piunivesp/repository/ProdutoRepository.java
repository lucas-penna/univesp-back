package com.application.piunivesp.repository;

import com.application.piunivesp.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {


    @Query(value = "SELECT p FROM produtos p WHERE p.deletado IS NULL AND UPPER(p.descricao) LIKE UPPER(CONCAT('%',?1,'%')) ")
    Page<Produto> findPaginationFilter(Pageable pageable, String descricao);

    Long countByIdNotLikeAndDescricaoIgnoreCase(Long id, String descricao);

    @Query(value = "SELECT p FROM produtos p WHERE p.deletado IS NULL")
    ArrayList<Produto> getListDTO();
}
