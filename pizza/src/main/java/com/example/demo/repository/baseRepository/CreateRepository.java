package com.example.demo.repository.baseRepository;

public interface CreateRepository<T> {
    T save(T value);
}
