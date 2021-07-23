package com.example.main.repository;

import com.example.main.model.Candidate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CandidateRepository extends MongoRepository<Candidate, String> {
    Candidate findById(UUID id);
    List<Candidate> findByName(String name);
}
