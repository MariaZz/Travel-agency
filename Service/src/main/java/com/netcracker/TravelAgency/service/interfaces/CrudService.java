package com.netcracker.TravelAgency.service.interfaces;
import java.util.List;

public interface CrudService<T> {
    int create(final T t);

    T findById(final int id);

    List<T> findAll();

    int update(final T t);

    void deleteById(final int id);

    void delete(final T t);
}