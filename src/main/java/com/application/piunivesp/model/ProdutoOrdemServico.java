package com.application.piunivesp.model;

import com.application.piunivesp.resources.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@SQLDelete(sql = "UPDATE produto_ordem_servico SET deletado = CURRENT_TIMESTAMP WHERE prdtos_id = ?")
@Where(clause = "DELETADO IS NULL")
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "produto_ordem_servico")
public class ProdutoOrdemServico extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prdtos_seq")
    @SequenceGenerator(name = "prdtos_seq", allocationSize = 1, sequenceName = "prdtos_seq")
    @Column(name = "prdtos_id")
    private Long id;

    @Column(name = "prdtos_descricao")
    private String descricao;

    @Column(name = "prdtos_quantidade")
    private Long quantidade;

    @Column(name = "prdtos_valor")
    private BigDecimal valor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prdtos_os_id")
    @JsonBackReference
    private OrdemServico ordemServico;

}
