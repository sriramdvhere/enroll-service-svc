package com.practice.enroll.service.service;


import com.practice.enroll.service.dto.NewStudentEnrollmentDTO;
import com.practice.enroll.service.model.Enrollment;

import java.util.Optional;

public interface EnrollmentService {


    Enrollment enrollNewStudent(NewStudentEnrollmentDTO newStudentEnrollment);

    Enrollment enrollExistingStudent(Long studentId, Long courseId);

    Optional<Enrollment> getEnrollmentById(Long id);

    void deleteEnrollment(Long id);
}
