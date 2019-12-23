package com.netcracker.service;

import java.util.List;

public interface CrudService<T> {
    void create(T t);

    T get(int id);

    List<T> getAll();

    void update(T t);

    void delete(int id);
}
