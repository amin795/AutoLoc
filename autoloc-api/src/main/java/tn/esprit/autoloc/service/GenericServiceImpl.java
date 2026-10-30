package tn.esprit.autoloc.service;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.exception.ResourceNotFoundException;

public abstract class GenericServiceImpl<T, ID> implements IGenericService<T, ID> {

    protected final JpaRepository<T, ID> repository;

    protected GenericServiceImpl(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public T create(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("L'entité ne peut pas être nulle");
        }
        if (!check(entity, true)) {
            throw new IllegalArgumentException("Les informations de l'entité sont invalides");
        }
        return repository.save(entity);
    }

    @Override
    public T findById(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(getEntityName(), String.valueOf(id)));
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public T update(ID id, T entity) {
        T existingEntity = findById(id);
        if (entity == null) {
            throw new IllegalArgumentException("L'entité ne peut pas être nulle");
        }
        if (!check(entity, false)) {
            throw new IllegalArgumentException("Les informations de l'entité sont invalides");
        }
        setFields(entity, existingEntity);
        return repository.save(existingEntity);
    }

    @Override
    public void deleteById(ID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(getEntityName(), String.valueOf(id));
        }
        repository.deleteById(id);
    }

    protected abstract String getEntityName();

    protected abstract ID getId(T entity);

    protected abstract void setFields(T source, T target);
}
