package com.application.piunivesp.repository;

import com.application.piunivesp.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Long countByIdNotLikeAndCpfcnpjIgnoreCase(Long id, String cpfcnpj);

    @Query(value = "SELECT c FROM clientes c WHERE c.deletado IS NULL AND UPPER(c.nome) LIKE UPPER(CONCAT('%',?1,'%')) ")
    Page<Cliente> findPaginationFilter(Pageable pageable, String nome);
}
