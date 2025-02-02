package com.practice.enroll.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentRespDTO {
    private Long id;
    private String name;
    private int age;
    private String govtId;
    private String email;
}