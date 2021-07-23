package com.example.main.model;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class GenerateDataForObject {
    public static void main(String[] args) {

        GenerateDataForObject writeObjectToFile = new GenerateDataForObject();

        Candidate candidate = new Candidate(
                "Mai Viet",
                Gender.MALE,
                LocalDate.of(2000, 6, 15),
                "Hoang Mai, Ha Noi",
                "HUST",
                "Java Dev",
                LocalDate.of(2021, 7, 1),
                "Mr Phan",
                "Ms Hue",
                "http...",
                "Mr Phan",
                List.of("Mr Phan", "Mr Dau"),
                List.of("Ms Hue"),
                true,
                "Smart Solution",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "admin",
                "admin"
        );

        writeObjectToFile.writeDataToFile(candidate.toString(), "src/main/java/com/example/main/model/candidates.json");
    }

    public void writeDataToFile(String t, String filepath) {
        try {
            FileWriter myWriter = new FileWriter(filepath);
            myWriter.write(t);
            myWriter.close();
            System.out.println("Write successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
