package com.juliana.gerenciamento_cursos.modules.enrollment.controller;

import com.juliana.gerenciamento_cursos.modules.enrollment.dto.EnrollmentDTO;
import com.juliana.gerenciamento_cursos.modules.enrollment.dto.EnrollmentRequestPayload;
import com.juliana.gerenciamento_cursos.modules.enrollment.dto.EnrollmentResponse;
import com.juliana.gerenciamento_cursos.modules.enrollment.service.EnrollmentService;
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

    @GetMapping("/course/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<EnrollmentDTO>> showCourseEnrollments(@PathVariable UUID courseId){
        List<EnrollmentDTO> enrollments =  service.showCourseEnrollments(courseId);
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/course/{courseId}/active")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<EnrollmentDTO>> showCourseActiveEnrollments(@PathVariable UUID courseId){
        List<EnrollmentDTO> enrollments =  service.showCourseActiveEnrollments(courseId);
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/student/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<EnrollmentDTO>> showStudentEnrollments(@PathVariable UUID studentId){
        List<EnrollmentDTO> enrollments =  service.showStudentEnrollments(studentId);
        return ResponseEntity.ok(enrollments);
    }

    @PostMapping("/subscribe")
    @ResponseStatus(HttpStatus.CREATED)
    public EnrollmentResponse enrollStudentInCourse(@Valid @RequestBody EnrollmentRequestPayload requestPayload){
        return service.enrollStudentInCourse(requestPayload);
    }

    @PutMapping("/unsubscribe")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> unsubscribeStudentOfCourse(@Valid @RequestBody EnrollmentRequestPayload requestPayload){
       service.unsubscribeStudentOfCourse(requestPayload);
       return ResponseEntity.ok("Esta inscrição foi inativada");
    }

}
