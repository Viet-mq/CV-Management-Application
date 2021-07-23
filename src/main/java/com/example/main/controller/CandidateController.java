package com.example.main.controller;

import com.example.main.model.Candidate;
import com.example.main.model.Gender;
import com.example.main.repository.CandidateRepository;
import com.example.main.service.CandidateService;
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

    @RequestMapping(value = "/candidates", method = RequestMethod.GET)
    public ResponseEntity<List<Candidate>> getAllCandidates(@RequestParam(required = false) String name){
        try{
            List<Candidate> candidates = new ArrayList<>();
            if(name == null){
                candidates.addAll(candidateRepository.findAll());
            } else {
                candidates.addAll(candidateRepository.findByName(name));
            }

            if(candidates.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(candidates, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/addcv", method = RequestMethod.POST)
    public String addCv(@RequestParam String name,
                           @RequestParam Gender gender,
                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dob,
                           @RequestParam String address,
                           @RequestParam String school,
                           @RequestParam String applyPosition,
                           @RequestParam(required = false) String cvSource){
        Candidate candidate = candidateService.addCandidateCv(name, gender, dob, address, school, applyPosition, cvSource);
        return candidate.toString();
    }

    @RequestMapping(value = "/updatecv", method = RequestMethod.PUT)
    public String updateCv(@RequestParam(required = false) UUID id,
                           @RequestParam String name,
                           @RequestParam Gender gender,
                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dob,
                           @RequestParam String address,
                           @RequestParam String school,
                           @RequestParam String applyPosition,
                           @RequestParam(required = false) String cvSource){
        Candidate candidate = candidateService.updateCandidateCv(id, name, gender, dob, address, school, applyPosition, cvSource);
        return candidate.toString();
    }

    @RequestMapping(value = "/uploadcv", method = RequestMethod.PUT)
    public void uploadCv(@RequestParam (required = false) UUID id,
                         @RequestParam (required = false) String cvUrl){
        candidateService.uploadCV(id, cvUrl);
    }

    @RequestMapping(value = "candidates/cv", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getAllCv(@RequestParam(required = false) String name){
        try{
            List<String> candidatesCv = new ArrayList<>();
            List<Candidate> candidates;

            if(name == null)
                candidates = new ArrayList<>(candidateRepository.findAll());
            else
                candidates = new ArrayList<>(candidateRepository.findByName(name));

            for(Candidate candidate : candidates){
                candidatesCv.add(candidate.getCvUrl());
            }

            if(candidatesCv.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(candidatesCv, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/candidates/{id}", method = RequestMethod.PUT)
    public void modifyCandidateById(@PathVariable("id") UUID id, @Valid @RequestBody Candidate candidate) {
        candidate.setId(id);
        candidateRepository.save(candidate);
    }

    @RequestMapping(value = "/candidates", method = RequestMethod.POST)
    public Candidate addCandidate(@Valid @RequestBody Candidate candidate) {
        candidate.setId(UUID.randomUUID());
        candidateRepository.save(candidate);
        return candidate;
    }

    @RequestMapping(value = "/candidates/{id}", method = RequestMethod.DELETE)
    public void deleteCandidate(@PathVariable UUID id) {

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
