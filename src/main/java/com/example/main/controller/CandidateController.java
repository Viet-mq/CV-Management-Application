package com.example.main.controller;

import com.example.main.model.Candidate;
import com.example.main.model.Gender;
import com.example.main.repository.CandidateRepository;
import com.example.main.service.CandidateService;
import com.example.main.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api")
public class CandidateController {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private HistoryService historyService;

    @GetMapping(value = "/candidates")
    public ResponseEntity<List<Candidate>> getAllCandidates(@RequestParam(required = false) String name) {
        try {
            List<Candidate> candidates = new ArrayList<>();
            String action;
            if (name == null) {
                candidates.addAll(candidateRepository.findAll());
                action = "Get all candidates";
            } else {
                candidates.addAll(candidateRepository.findByName(name));
                action = "Get candidates by name = " + name;
            }

            historyService.addHistory(action);
            if (candidates.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(candidates, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/addcv")
    public String addCv(@RequestParam String name,
                        @RequestParam Gender gender,
                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dob,
                        @RequestParam String address,
                        @RequestParam String school,
                        @RequestParam String applyPosition,
                        @RequestParam(required = false) String cvSource) {
        Candidate candidate = candidateService.addCandidateCv(name, gender, dob, address, school, applyPosition, cvSource);
        String action = "Add CV with candidate's name: " + name;
        historyService.addHistory(action);
        return candidate.toString();
    }

    @PutMapping(value = "/updatecv")
    public String updateCv(@RequestParam(required = false) UUID id,
                           @RequestParam String name,
                           @RequestParam Gender gender,
                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dob,
                           @RequestParam String address,
                           @RequestParam String school,
                           @RequestParam String applyPosition,
                           @RequestParam(required = false) String cvSource) {
        Candidate candidate = candidateService.updateCandidateCv(id, name, gender, dob, address, school, applyPosition, cvSource);
        String action = "Update CV with candidate's name: " + name;
        historyService.addHistory(action);
        return candidate.toString();
    }

    @PutMapping(value = "/candidates/updatecv")
    public String updateCv2(@RequestParam(required = false) UUID id,
                            @RequestParam(required = false) String cvSource,
                            @RequestParam(required = false) String cvReceiver,
                            @RequestParam(required = false) String cvUrl,
                            @RequestParam(required = false) String presenter,
                            @RequestParam(required = false) List<String> interviewer,
                            @RequestParam(required = false) List<String> interviewerSecretary,
                            @RequestParam(required = false) boolean interviewResult,
                            @RequestParam(required = false) String receptionDepartment) {
        if (id == null) {
            return null;
        } else {
            String action = "Update CV, id: " + id;
            historyService.addHistory(action);
            Candidate candidate = candidateService.updateCv(id, cvSource, cvReceiver, cvUrl, presenter, interviewer, interviewerSecretary, interviewResult, receptionDepartment);
            return candidate.toString();
        }
    }

    @PutMapping(value = "/uploadcv")
    public void uploadCv(@RequestParam(required = false) UUID id,
                         @RequestParam(required = false) String cvUrl) {
        String action;
        if(id != null)
            action = "Upload CV to id: " + id;
        else
            action = "Upload CV";
        historyService.addHistory(action);
        candidateService.uploadCV(id, cvUrl);
    }

    @GetMapping(value = "candidates/cv")
    public ResponseEntity<List<String>> getAllCv(@RequestParam(required = false) String name) {
        try {
            List<String> candidatesCv = new ArrayList<>();
            List<Candidate> candidates;
            String action;

            if (name == null){
                candidates = new ArrayList<>(candidateRepository.findAll());
                action = "Get all cv url";
            }
            else {
                candidates = new ArrayList<>(candidateRepository.findByName(name));
                action = "Get " + name +"'s cv url";
            }

            historyService.addHistory(action);
            for (Candidate candidate : candidates) {
                candidatesCv.add(candidate.getCvUrl());
            }

            if (candidatesCv.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(candidatesCv, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/candidates/{id}")
    public void modifyCandidateById(@PathVariable("id") UUID id, @Valid @RequestBody Candidate candidate) {
        String action = "Update candidate by id: " + id;
        candidate.setId(id);
        historyService.addHistory(action);
        candidateRepository.save(candidate);
    }

    @PostMapping(value = "/candidates")
    public Candidate addCandidate(@Valid @RequestBody Candidate candidate) {
        candidate.setId(UUID.randomUUID());
        candidateRepository.save(candidate);
        return candidate;
    }

    @DeleteMapping(value = "/candidates/{id}")
    public void deleteCandidate(@PathVariable UUID id) {
        String action = "Delete candidate by id: " + id;
        historyService.addHistory(action);
        candidateRepository.delete(candidateRepository.findById(id));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
