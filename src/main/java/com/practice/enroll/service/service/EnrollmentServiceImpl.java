package com.practice.enroll.service.service;

import com.practice.enroll.service.client.StudentServiceClient;
import com.practice.enroll.service.dto.NewStudentEnrollmentDTO;
import com.practice.enroll.service.dto.StudentRespDTO;
import com.practice.enroll.service.dto.StudentReqDTO;
import com.practice.enroll.service.model.Enrollment;
import com.practice.enroll.service.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired
    EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentServiceClient studentServiceClient;

    @Override
    public Enrollment enrollNewStudent(NewStudentEnrollmentDTO newStudentEnrollment) {
        // Create a new student in the student-service
        StudentReqDTO newStudent = new StudentReqDTO();
        newStudent.setName(newStudentEnrollment.getStudentName());
        newStudent.setEmail(newStudentEnrollment.getStudentEmail());
        newStudent.setAge(newStudentEnrollment.getAge());
        newStudent.setGovtId(newStudentEnrollment.getGovtId());
        StudentRespDTO createdStudent = studentServiceClient.createStudent(newStudent);

        // Enroll the new student
        Enrollment enrollment = new Enrollment(createdStudent.getId(), newStudentEnrollment.getCourseId(), newStudentEnrollment.getEnrollmentDate());
        return enrollmentRepository.save(enrollment);
    }

    @Override
    public Enrollment enrollExistingStudent(Long studentId, Long courseId) {
        StudentRespDTO student = studentServiceClient.getStudentById(studentId);
        if (student == null) {
            throw new RuntimeException("Student not found with ID: " + studentId);
        }
        Enrollment enrollment = new Enrollment(student.getId(), courseId, LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        return enrollmentRepository.save(enrollment);
    }

    @Override
    public Optional<Enrollment> getEnrollmentById(Long id) {
        return enrollmentRepository.findById(id);
    }

    @Override
    public void deleteEnrollment(Long id) {
        enrollmentRepository.deleteById(id);
    }
}
