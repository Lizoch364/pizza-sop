package com.example.demo.repository.baseRepository;

import java.util.List;
import java.util.Optional;

public interface GetRepository<T> {
    Optional<T> findById(int id);
    List<T> findAll();
}
