package com.practice.enroll.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.practice.enroll.service.controller.EnrollmentController;
import com.practice.enroll.service.dto.NewStudentEnrollmentDTO;
import com.practice.enroll.service.model.Enrollment;
import com.practice.enroll.service.service.EnrollmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
public class EnrollmentControllerTests {

    @InjectMocks
    private EnrollmentController enrollmentController;

    @Mock
    private EnrollmentService enrollmentService;


    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());


    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(enrollmentController).build();
    }

    @Test
    public void testEnrollNewStudent() throws Exception {
        // Assuming the correct constructor parameters are: name, email, age, nationalityCode, courseId, enrollmentDate
        NewStudentEnrollmentDTO newStudent = new NewStudentEnrollmentDTO("John Doe", "john.doe@example.com", 20, "IND001", 1L, "02-02-25");
        Enrollment enrollment = new Enrollment(1L, 1L, "01-01-25");

        // Mock the service layer to return a predefined enrollment object
        given(enrollmentService.enrollNewStudent(any(NewStudentEnrollmentDTO.class))).willReturn(enrollment);

        // Perform the POST request, serialize the DTO to JSON, and set the content type
        mockMvc.perform(post("/enrollments/new-student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newStudent)))
                .andExpect(status().isOk()) // Assert the response status code
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Assert the response content type
                .andExpect(jsonPath("$.studentId").value(enrollment.getStudentId())); // Assert the value of studentId in the JSON response
    }

    @Test
    public void testGetEnrollmentById() throws Exception {
        Enrollment enrollment = new Enrollment(1L, 1L, "02-02-2025");
        given(enrollmentService.getEnrollmentById(1L)).willReturn(Optional.of(enrollment));

        mockMvc.perform(get("/enrollments/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentId").value(enrollment.getStudentId()));
    }

    @Test
    public void testDeleteEnrollment() throws Exception {
        mockMvc.perform(delete("/enrollments/{id}", 1L))
                .andExpect(status().isOk());
    }
}