package com.juliana.gerenciamento_cursos.controller;

import com.juliana.gerenciamento_cursos.DTOs.EnrollmentDTO;
import com.juliana.gerenciamento_cursos.DTOs.request_payload.EnrollmentRequestPayload;
import com.juliana.gerenciamento_cursos.domain.enrollment.Enrollment;
import com.juliana.gerenciamento_cursos.DTOs.response.EnrollmentResponse;
import com.juliana.gerenciamento_cursos.service.EnrollmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {
    @Autowired
    EnrollmentService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<EnrollmentDTO>> showAllEnrollments(){
        List<EnrollmentDTO> enrollments = service.showAllEnrollments();
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/course/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<EnrollmentDTO>> showEnrollmentsCourse(@PathVariable UUID courseId){
        List<EnrollmentDTO> enrollments =  service.showCourseEnrollments(courseId);
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/student/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<EnrollmentDTO>> showEnrollmentsStudent(@PathVariable UUID studentId){
        List<EnrollmentDTO> enrollments =  service.showStudentEnrollments(studentId);
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/course/active?{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<EnrollmentDTO>> showCourseEnrollmentsActive(@PathVariable UUID courseId){
        List<EnrollmentDTO> enrollments =  service.showCourseEnrollmentsActive(courseId);
        return ResponseEntity.ok(enrollments);
    }

    @PostMapping("/subscrible")
    @ResponseStatus(HttpStatus.CREATED)
    public EnrollmentResponse enrollStudentInCourse(@Valid @RequestBody EnrollmentRequestPayload requestPayload){
        return service.enrollStudentInCourse(requestPayload);
    }

    @PutMapping("/unsubscribe")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> unsubscribeStudentInCourse(@Valid @RequestBody EnrollmentRequestPayload requestPayload){
       service.unsubscribeStudentOfCourse(requestPayload);
       return ResponseEntity.ok("Esta inscrição foi inativada");
    }

}
