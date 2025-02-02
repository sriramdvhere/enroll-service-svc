package com.practice.enroll.service.client;

import com.practice.enroll.service.dto.StudentRespDTO;
import com.practice.enroll.service.dto.StudentReqDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class StudentServiceClient {
    private final RestTemplate restTemplate;
    private final String studentServiceUrl; // Adjust the URL based on your configuration

    @Autowired
    public StudentServiceClient(@Value("${student-service.url}") final String studentServiceUrl) {
        this.studentServiceUrl = studentServiceUrl;
        this.restTemplate = new RestTemplate();
    }

    public StudentRespDTO getStudentById(Long studentId) {
        String url = UriComponentsBuilder.fromHttpUrl(studentServiceUrl + "/students")
                .path("/" + studentId)
                .toUriString();
        return restTemplate.getForObject(url, StudentRespDTO.class);
    }

    public StudentRespDTO createStudent(StudentReqDTO student) {
        String url = UriComponentsBuilder.fromHttpUrl(studentServiceUrl + "/students")
                .toUriString();
        return restTemplate.postForObject(url, student, StudentRespDTO.class);
    }
}