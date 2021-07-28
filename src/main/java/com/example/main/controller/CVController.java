package com.example.main.controller;

import com.example.main.model.CV;
import com.example.main.repository.HistoryRepository;
import com.example.main.service.CVService;
import com.example.main.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin("http://localhost:8081")
public class CVController {
    @Autowired
    private CVService cvService;

    @Autowired
    private HistoryService historyService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String message, action;
        try {
            cvService.save(file);
            action = "Upload file " + file.getOriginalFilename() + " to server";
            historyService.addHistory(action);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @GetMapping("/cv")
    public ResponseEntity<List<CV>> getListCV() {
        List<CV> cvList = cvService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(CVController.class, "getCV", path.getFileName().toString()).build().toString();

            return new CV(filename, url);
        }).collect(Collectors.toList());
        String action = "Get all CV";
        historyService.addHistory(action);

        return ResponseEntity.status(HttpStatus.OK).body(cvList);
    }

    @GetMapping("/cv/{filename:.+}")
    public ResponseEntity<Resource> getCV(@PathVariable String filename) {
        Resource file = cvService.load(filename);
        String action = "Get CV: " + filename;
        historyService.addHistory(action);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
