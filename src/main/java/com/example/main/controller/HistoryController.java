package com.example.main.controller;

import com.example.main.model.History;
import com.example.main.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/history")
public class HistoryController {

    @Autowired
    HistoryRepository historyRepository;

    @GetMapping
    public ResponseEntity<List<History>> getAllHistory(@RequestParam(required = false)UUID id){
        try {
            List<History> histories = new ArrayList<>();
            if(id == null)
                histories.addAll(historyRepository.findAll());
            else
                histories.addAll(historyRepository.findById(id));

            if(histories.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(histories, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
