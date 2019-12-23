package com.netcracker.converter;

public interface Converter<T,Q> {
    Q toDto(T entity);

    T toEntity(Q dto);
}
