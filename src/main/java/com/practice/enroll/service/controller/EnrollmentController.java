package com.practice.enroll.service.controller;

import com.practice.enroll.service.dto.NewStudentEnrollmentDTO;
import com.practice.enroll.service.model.Enrollment;
import com.practice.enroll.service.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("/new-student")
    public ResponseEntity<Enrollment> enrollNewStudent(@RequestBody NewStudentEnrollmentDTO newStudentEnrollment) {
        Enrollment enrollment = enrollmentService.enrollNewStudent(newStudentEnrollment);
        return ResponseEntity.ok(enrollment);
    }

    @PostMapping("/existing-student")
    public ResponseEntity<Enrollment> enrollExistingStudent(@RequestParam Long studentId, @RequestParam Long courseId) {
        System.out.println("The request is received for /enrollments/existing-student");
        Enrollment enrollment = enrollmentService.enrollExistingStudent(studentId, courseId);
        return ResponseEntity.ok(enrollment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Enrollment> getEnrollmentById(@PathVariable Long id) {
        return enrollmentService.getEnrollmentById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long id) {
        enrollmentService.deleteEnrollment(id);
        return ResponseEntity.ok().build();
    }
}
