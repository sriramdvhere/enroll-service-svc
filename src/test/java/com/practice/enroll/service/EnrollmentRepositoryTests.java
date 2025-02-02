package com.practice.enroll.service;

import com.practice.enroll.service.model.Enrollment;
import com.practice.enroll.service.repository.EnrollmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EnrollmentRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Test
    public void testFindById() {
        // Given
        Enrollment enrollment = new Enrollment();
        enrollment.setStudentId(1L);
        enrollment.setCourseId(100L);
        enrollment.setEnrollmentDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        entityManager.persist(enrollment);
        entityManager.flush();

        // When
        Enrollment foundEnrollment = enrollmentRepository.findById(enrollment.getId()).orElse(null);

        // Then
        assertThat(foundEnrollment).isNotNull();
        assertThat(foundEnrollment.getStudentId()).isEqualTo(enrollment.getStudentId());
    }
}