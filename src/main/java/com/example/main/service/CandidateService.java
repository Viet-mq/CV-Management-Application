package com.example.main.service;

import com.example.main.model.Candidate;
import com.example.main.model.Gender;
import com.example.main.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
        if(cvSource != null) candidate.setCvSource(cvSource);
        return candidateRepository.save(candidate);
    }

    public String getCvUrl(){
        return null;
    }

    public void uploadCV(UUID id, String cvUrl){
        Candidate candidate;
        if(id == null) {
            candidate = new Candidate();
            candidate.setId(UUID.randomUUID());
        }
        else{
            candidate = candidateRepository.findById(id);
        }
        candidate.setCvUrl(cvUrl);
        candidate.setUpdateTime(LocalDateTime.now());
        candidateRepository.save(candidate);
    }

    public Candidate updateCv(UUID id,
                                String cvSource,
                                String cvReceiver,
                                String cvUrl,
                                String presenter,
                                List<String>interviewer,
                                List<String> interviewerSecretary,
                                boolean interviewResult,
                                String receptionDepartment){
        Candidate candidate = candidateRepository.findById(id);
        if(cvSource != null) candidate.setCvSource(cvSource);
        if(cvReceiver != null)  candidate.setCvReceiver(cvReceiver);
        if(cvUrl != null) candidate.setCvUrl(cvUrl);
        if(presenter != null) candidate.setPresenter(presenter);
        if(interviewer != null) candidate.setInterviewer(interviewer);
        if(interviewerSecretary != null) candidate.setInterviewerSecretary(interviewerSecretary);
        if(candidate.isInterviewResult() != interviewResult && !candidate.isInterviewResult())
            candidate.setInterviewResult(interviewResult);
        if(receptionDepartment != null) candidate.setReceptionDepartment(receptionDepartment);
        candidate.setUpdateTime(LocalDateTime.now());
        return candidateRepository.save(candidate);
    }

    public void deleteCV(){

    }

}
