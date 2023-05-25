package com.application.piunivesp.resources;

import com.application.piunivesp.exception.type.InternalException;
import com.application.piunivesp.exception.type.NotFoundException;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public abstract class AbstractService<T extends AbstractEntity> {

    public abstract JpaRepository getRepository();

    public abstract void validateCreateOrUpdate(T entity);

    @Transactional
    public void create(T entity) {
        this.validateCreateOrUpdate(entity);
        try {
            this.getRepository().save(entity);
        } catch (Exception exception) {
            throw new InternalException("Erro ao inserir entidade!");
        }
    }

    public Page<T> read(Pageable pageable) {
        return this.getRepository().findAll(pageable);
    }

    public T readById(Long id) {
        Optional<T> record = this.getRepository().findById(id);
        if (!record.isPresent()) {
            throw new NotFoundException("Entidade não encontrado");
        }
        return record.get();
    }

    @SneakyThrows
    @Transactional
    public void update(Long id, T entity) {
        this.getRepository().findById(id).orElseThrow(() -> new NotFoundException("Entidade não encontrado"));
        this.validateCreateOrUpdate(entity);
        try {
            this.getRepository().save(entity);
        } catch (Exception exception) {
            throw new InternalException("Erro ao atualizar entidade!");
        }
    }

    @SneakyThrows
    @Transactional
    public void delete(Long id) {
        this.getRepository().findById(id).orElseThrow(() -> new NotFoundException("Entidade não encontrado"));
        try {
        this.getRepository().deleteById(id);
        } catch (Exception exception) {
            throw new InternalException("Erro ao deletar entidade!");
        }
    }
}
