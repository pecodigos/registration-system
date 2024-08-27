package com.pecodigos.registration_system.controllers;

import com.pecodigos.registration_system.dtos.StudentDTO;
import com.pecodigos.registration_system.entities.StudentEntity;
import com.pecodigos.registration_system.repositories.StudentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    @PostMapping("/students")
    public ResponseEntity<StudentEntity> saveStudent(@RequestBody @Valid StudentDTO studentDTO) {
        var studentEntity = new StudentEntity();
        BeanUtils.copyProperties(studentDTO, studentEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(studentRepository.save(studentEntity));
    }

    @GetMapping("/students")
    public ResponseEntity<List<StudentEntity>> getAllStudents() {
        List<StudentEntity> studentsList = studentRepository.findAll();
        if (!studentsList.isEmpty()) {
            for (StudentEntity student : studentsList) {
                student.add(linkTo(methodOn(StudentController.class).getOneStudent(student.getId())).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(studentsList);
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Object> getOneStudent(@PathVariable(value = "id") UUID id) {
        Optional<StudentEntity> studentO = studentRepository.findById(id);
        return studentO.<ResponseEntity<Object>>map(studentEntity -> ResponseEntity.status(HttpStatus.OK).body(studentEntity))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found."));
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<Object> updateStudent(@PathVariable(value = "id") UUID id,
                                                @RequestBody @Valid StudentDTO studentDTO) {
        Optional<StudentEntity> studentO = studentRepository.findById(id);
        if (studentO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        }
        var studentEntity = studentO.get();
        BeanUtils.copyProperties(studentDTO, studentEntity);
        return ResponseEntity.status(HttpStatus.OK).body(studentRepository.save(studentEntity));
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable(value = "id") UUID id) {
        Optional<StudentEntity> studentO = studentRepository.findById(id);
        if (studentO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");
        }
        studentRepository.delete(studentO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully.");
    }
}
