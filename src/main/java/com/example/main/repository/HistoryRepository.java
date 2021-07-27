package com.example.main.repository;

import com.example.main.model.History;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface HistoryRepository extends MongoRepository<History, String> {
    List<History> findById(UUID id);
}
