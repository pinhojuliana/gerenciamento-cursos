package com.juliana.gerenciamento_cursos.controller;

import com.juliana.gerenciamento_cursos.domain.course.Course;
import com.juliana.gerenciamento_cursos.domain.enrollment.Enrollment;
import com.juliana.gerenciamento_cursos.DTOs.EnrollmentResponse;
import com.juliana.gerenciamento_cursos.service.EnrollmentService;
import com.juliana.gerenciamento_cursos.domain.client.Student;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<Enrollment>> showAllEnrollments(){
        List<Enrollment> enrollments = service.showAllEnrollments();
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/course/{id}")
    public ResponseEntity<List<Enrollment>> showEnrollmentsCourse(@PathVariable UUID courseId){
        List<Enrollment> enrollments =  service.showEnrollmentsCourse(courseId);
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<List<Enrollment>> showEnrollmentsStudent(@PathVariable UUID studentId){
        List<Enrollment> enrollments =  service.showEnrollmentsStudent(studentId);
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/course/active?{id}")
    public ResponseEntity<List<Enrollment>> showEnrollmentsActive(@PathVariable UUID courseId){
        List<Enrollment> enrollments =  service.showStudentsActive(courseId);
        return ResponseEntity.ok(enrollments);
    }

    @PostMapping("/subscrible")
    public EnrollmentResponse subscribleStudentInCourse(@RequestBody Student student, @RequestBody Course course){
        return service.enrollStudentInCourse(course, student);
    }

    @PutMapping("/unsubscrible")
    public ResponseEntity<String> subscribleStudentInCourse(@RequestBody UUID studentId, @RequestBody UUID courseId){
       service.unsubscribleStudentOfCourse(studentId, courseId);
       return ResponseEntity.ok("Esta inscrição foi inativada");
    }

}
