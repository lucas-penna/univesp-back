package com.application.piunivesp.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.annotation.PreDestroy;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    @Column(name = "data_criacao", updatable = false)
    @JsonProperty
    private LocalDateTime dataCriacao;

    @Column(name = "data_alteracao")
    @Nullable
    @JsonProperty
    private LocalDateTime dataAlteracao;

    @Column(name = "deletado")
    @JsonProperty
    private LocalDateTime deletado;

    @PrePersist
    protected void onCreate() {
        this.dataCriacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.dataAlteracao = LocalDateTime.now();
    }

    @PreDestroy
    protected void onDelete() {
        this.deletado = LocalDateTime.now();
    }
}
