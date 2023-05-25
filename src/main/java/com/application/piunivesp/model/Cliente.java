package com.application.piunivesp.model;

import com.application.piunivesp.resources.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Data
@SQLDelete(sql = "UPDATE clientes SET deletado = CURRENT_TIMESTAMP WHERE cli_id = ?")
@Where(clause = "DELETADO IS NULL")
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "clientes")
public class Cliente extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cli_seq")
    @SequenceGenerator(name = "cli_seq", allocationSize = 1, sequenceName = "cli_seq")
    @Column(name = "cli_id")
    private Long id;

    @Column(name = "cli_nome")
    private String nome;

    @Column(name = "cli_telefone")
    private String telefone;

    @Column(name = "cli_email")
    private String email;

    @Column(name = "cli_cpf_cnpj")
    private String cpfcnpj;

    @Column(name = "cli_tipo_pessoa")
    @Enumerated(EnumType.STRING)
    private TipoPessoa tipoPessoa;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<OrdemServico> ordemServicos;

}
