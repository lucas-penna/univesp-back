package com.application.piunivesp.model;

import com.application.piunivesp.resources.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@SQLDelete(sql = "UPDATE ordem_servicos SET deletado = CURRENT_TIMESTAMP WHERE os_id = ?")
@Where(clause = "DELETADO IS NULL")
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ordem_servicos")
public class OrdemServico extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "os_seq")
    @SequenceGenerator(name = "os_seq", allocationSize = 1, sequenceName = "os_seq")
    @Column(name = "os_id")
    private Long id;

    @Column(name = "os_descricao")
    private String descricao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "os_cli_id")
    @JsonProperty
    @JsonBackReference
    private Cliente cliente;

    @Column(name = "os_cliente")
    private String nomeCliente;

    @Column(name = "os_telefone_cliente")
    private String telefoneCliente;

    @Column(name = "os_placa")
    private String placa;

    @Column(name = "os_marca")
    private String marca;

    @Column(name = "os_modelo")
    private String modelo;

    @Column(name = "os_valor")
    private BigDecimal total;

    @Column(name = "os_data_inicio")
    private LocalDateTime dataInicio;

    @Column(name = "os_data_fim")
    private LocalDateTime dataFim;

    @Column(name = "os_status")
    @Enumerated(EnumType.STRING)
    private StatusOrdemServico statusOrdemServico;

    @OneToMany(mappedBy = "ordemServico", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, orphanRemoval = true)
    @Fetch(value = FetchMode.JOIN)
    @JsonManagedReference
    private List<ProdutoOrdemServico> produtosOrdemServico;

}
