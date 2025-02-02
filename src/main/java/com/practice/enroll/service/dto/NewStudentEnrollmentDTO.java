package com.practice.enroll.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class NewStudentEnrollmentDTO {

    private String studentName;
    private String studentEmail;
    private int age;
    private String govtId;
    private Long courseId;
    private String enrollmentDate;

    public NewStudentEnrollmentDTO(String studentName, String studentEmail, int age, String govtId, Long courseId, String enrollmentDate) {
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.age = age;
        this.govtId = govtId;
        this.courseId = courseId;
        this.enrollmentDate = enrollmentDate;
    }
}

