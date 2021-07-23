package com.example.main.service;

import com.example.main.model.Candidate;
import com.example.main.model.Gender;
import com.example.main.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    public Candidate addCandidateCv(String name,
                               Gender gender,
                               LocalDate dob,
                               String address,
                               String school,
                               String applyPosition,
                               String cvSource){
        Candidate candidate = new Candidate(name, gender, dob, address, school, applyPosition, cvSource);
        candidate.setId(UUID.randomUUID());
        candidate.setCreateTime(LocalDateTime.now());
        return candidateRepository.save(candidate);
    }

    public Candidate updateCandidateCv(UUID id,
                                       String name,
                                  Gender gender,
                                  LocalDate dob,
                                  String address,
                                  String school,
                                  String applyPosition,
                                  String cvSource){
        Candidate candidate = candidateRepository.findById(id);
        candidate.setUpdateTime(LocalDateTime.now());
        candidate.setName(name);
        candidate.setGender(gender);
        candidate.setDob(dob);
        candidate.setAddress(address);
        candidate.setSchool(school);
        candidate.setApplyPosition(applyPosition);
        candidate.setCvSource(cvSource);
        return candidateRepository.save(candidate);
    }

    public String getCvUrl(){
        return null;
    }

    public Candidate uploadCV(UUID id, String cvUrl){
        if(id == null) {
            Candidate candidate = new Candidate();
            candidate.setId(UUID.randomUUID());
            candidate.setCvUrl(cvUrl);
            return candidate;
        }
        else{
            Candidate candidate = candidateRepository.findById(id);
            candidate.setCvUrl(cvUrl);
            return candidate;
        }

    }

    public void deleteCV(){

    }


}
