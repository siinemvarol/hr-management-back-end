package com.bilgeadam.utility;

import com.bilgeadam.repository.entity.BaseEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public class ServiceManager<T extends BaseEntity, ID> implements IService<T, ID> {
    private final JpaRepository<T, ID> repository;

    @Override
    public T save(T t) {
        t.setCreatedDate(System.currentTimeMillis());
        t.setUpdatedDate(System.currentTimeMillis());

        return repository.save(t);
    }

    @Override
    public Iterable<T> saveAll(Iterable<T> t) {
        t.forEach(x -> {
            x.setCreatedDate(System.currentTimeMillis());
            x.setUpdatedDate(System.currentTimeMillis());

        });
        return repository.saveAll(t);
    }

    @Override
    public T update(T t) {
        t.setUpdatedDate(System.currentTimeMillis());
        return repository.save(t);
    }

    @Override
    public void delete(T t) {
        repository.delete(t);
    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }
}
