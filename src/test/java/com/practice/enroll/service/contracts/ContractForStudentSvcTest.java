package com.practice.enroll.service.contracts;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.practice.enroll.service.client.StudentServiceClient;
import com.practice.enroll.service.dto.StudentReqDTO;
import com.practice.enroll.service.dto.StudentRespDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@PactTestFor(providerName = "student-service-svc")
@ExtendWith(PactConsumerTestExt.class)
public class ContractForStudentSvcTest {

    StudentServiceClient studentServiceClient;


    @Pact(consumer = "enroll-service-svc", provider = "student-service-svc")
    V4Pact createStudentForTest(PactDslWithProvider pactBuilder) {
        return pactBuilder.given("for non-existing student")
                .uponReceiving("create student before enrolling for course")
                .method("POST")
                .path("/students")
                .headers("Content-Type", "application/json")
                .body(new PactDslJsonBody()
                        .stringType("name", "John Doe")
                        .numberValue("age", 23)
                        .stringType("govtId", "IND001")
                        .stringValue("email", "john@example.com"))
                .willRespondWith()
                .status(200)
                .headers(Map.of("Content-Type", "application/json"))
                .body(new PactDslJsonBody()
                        .numberType("id", 1)
                        .stringType("name", "John Doe")
                        .numberValue("age", 23)
                        .stringType("govtId", "IND001")
                        .stringValue("email", "john@example.com"))
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "createStudentForTest")
    void createStudent_beforeEnroll(MockServer mockServer) {
        studentServiceClient = new StudentServiceClient(mockServer.getUrl());
        StudentReqDTO studentReqDTO = new StudentReqDTO("John Doe", 23, "IND001", "john@example.com");
        StudentRespDTO expectedStudentRespDTO = new StudentRespDTO(1L, "John Doe", 23, "IND001", "john@example.com");
        StudentRespDTO actualStudentRespDTO = studentServiceClient.createStudent(studentReqDTO);

        assertEquals(expectedStudentRespDTO, actualStudentRespDTO);
    }

    @Pact(consumer = "enroll-service-svc", provider = "student-service-svc")
    V4Pact checkStudentIdTest(PactDslWithProvider pactBuilder) {
        return pactBuilder.given("existing student")
                .uponReceiving("check student Id before enrolling for course")
                .method("GET")
                .path("/students/1")
                .willRespondWith()
                .status(200)
                .headers(Map.of("Content-Type", "application/json"))
                .body(new PactDslJsonBody()
                        .numberType("id", 1)
                        .stringType("name", "John Doe")
                        .numberValue("age", 23)
                        .stringType("govtId", "IND001")
                        .stringValue("email", "john@example.com"))
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "checkStudentIdTest")
    void checkStudent_beforeEnroll(MockServer mockServer) {
        studentServiceClient = new StudentServiceClient(mockServer.getUrl());
        StudentRespDTO actualStudentRespDTO = studentServiceClient.getStudentById(1L);
        StudentRespDTO expectedStudentRespDTO = new StudentRespDTO(1L, "John Doe", 23, "IND001", "john@example.com");

        assertEquals(expectedStudentRespDTO, actualStudentRespDTO);
    }
}