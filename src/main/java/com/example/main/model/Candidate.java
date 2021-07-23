package com.example.main.model;

import com.google.gson.Gson;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "candidates")
public class Candidate {
    @Id
    private UUID id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Gender is mandatory")
    private Gender gender;

    @NotBlank(message = "Date of birth is mandatory")
    private LocalDate dob;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotBlank(message = "School is mandatory")
    private String school;

    @NotBlank(message = "Apply position is mandatory")
    private String applyPosition;

    @NotBlank(message = "Apply day is mandatory")
    private LocalDate applyDay;
    private String cvSource;
    private String cvReceiver;
    private String cvUrl;
    private String presenter;
    private List<String> interviewer;
    private List<String> interviewerSecretary;
    private boolean interviewResult;
    private String receptionDepartment;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime updateTime;
    private String whoCreated;
    private String whoUpdated;

    public Candidate(String name, Gender gender, LocalDate dob, String address, String school, String applyPosition, LocalDate applyDay, String cvSource, String cvReceiver, String cvUrl, String presenter, List<String> interviewer, List<String> interviewerSecretary, boolean interviewResult, String receptionDepartment, LocalDateTime createTime, LocalDateTime updateTime, String whoCreated, String whoUpdated) {
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.address = address;
        this.school = school;
        this.applyPosition = applyPosition;
        this.applyDay = applyDay;
        this.cvSource = cvSource;
        this.cvReceiver = cvReceiver;
        this.cvUrl = cvUrl;
        this.presenter = presenter;
        this.interviewer = interviewer;
        this.interviewerSecretary = interviewerSecretary;
        this.interviewResult = interviewResult;
        this.receptionDepartment = receptionDepartment;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.whoCreated = whoCreated;
        this.whoUpdated = whoUpdated;
    }

    public Candidate(String name, Gender gender, LocalDate dob, String address, String school, String applyPosition, String cvSource) {
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.address = address;
        this.school = school;
        this.applyPosition = applyPosition;
        this.cvSource = cvSource;
    }

    @Override
    public String toString() {
        String interviewerJson = new Gson().toJson(interviewer);
        String interviewerSecretaryJson = new Gson().toJson(interviewerSecretary);
        return "{" +
                "\n \"name\": " + "\"" + name + "\"" +
                ",\n \"gender\": " + "\"" +  gender + "\"" +
                ",\n \"dob\": \"" + dob + '\"' +
                ",\n \"address\": \"" + address + '\"' +
                ",\n \"school\": \"" + school + '\"' +
                ",\n \"applyPosition\": \" " + applyPosition + '\"' +
                ",\n \"applyDay\": \"" + applyDay + '\"' +
                ",\n \"cvSource\": \"" + cvSource + '\"' +
                ",\n \"cvReceiver\": \"" + cvReceiver + '\"' +
                ",\n \"cvUrl\": \"" + cvUrl + '\"' +
                ",\n \"presenter\": \"" + presenter + '\"' +
                ",\n \"interviewer\": " + interviewerJson +
                ",\n \"interviewerSecretary\": " + interviewerSecretaryJson +
                ",\n \"interviewResult\": \"" + interviewResult + '\"' +
                ",\n \"receptionDepartment\": \"" + receptionDepartment + '\"' +
                ",\n \"createTime\": \"" + createTime + '\"' +
                ",\n \"updateTime\": \"" + updateTime + '\"' +
                ",\n \"whoCreated\": \"" + whoCreated + '\"' +
                ",\n \"whoUpdated\": \"" + whoUpdated + '\"' + "\n" +
                '}';
    }
}
