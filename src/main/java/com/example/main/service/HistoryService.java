package com.example.main.service;

import com.example.main.model.History;
import com.example.main.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class HistoryService {

    @Autowired
    HistoryRepository historyRepository;

    public void addHistory(String action){
        History history = new History(action);
        history.setId(UUID.randomUUID());
        history.setAction(action);
        history.setTime(LocalDateTime.now());
        historyRepository.save(history);
    }

}
