package com.pecodigos.registration_system.controllers;

import com.pecodigos.registration_system.dtos.QuestionDTO;
import com.pecodigos.registration_system.entities.QuestionEntity;
import com.pecodigos.registration_system.repositories.QuestionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class QuestionController {

    @Autowired
    QuestionRepository questionRepository;

    @PostMapping("/questions")
    public ResponseEntity<QuestionEntity> saveQuestion(@RequestBody @Valid QuestionDTO questionDTO) {
        var questionEntity = new QuestionEntity();
        BeanUtils.copyProperties(questionDTO, questionEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(questionRepository.save(questionEntity));
    }

    @GetMapping("/questions")
    public ResponseEntity<List<QuestionEntity>> getAllQuestions() {
        List<QuestionEntity> questionsList = questionRepository.findAll();
        if (!questionsList.isEmpty()) {
            for (QuestionEntity question : questionsList) {
                question.add(linkTo(methodOn(QuestionController.class).getOneQuestion(question.getId())).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(questionsList);
    }

    @GetMapping("/questions/{id}")
    public ResponseEntity<Object> getOneQuestion(@PathVariable(value = "id") Long id) {
        Optional<QuestionEntity> optionalQuestion = questionRepository.findById(id);
        if(optionalQuestion.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question not found.");
        }
        optionalQuestion.get().add(linkTo(methodOn(QuestionController.class).getAllQuestions()).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(optionalQuestion.get());
    }

    @PutMapping("/questions/{id}")
    public ResponseEntity<Object> updateQuestion(@PathVariable(value = "id") Long id,
                                                 @RequestBody @Valid QuestionDTO questionDTO) {
        Optional<QuestionEntity> optionalQuestion = questionRepository.findById(id);
        if (optionalQuestion.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question not found.");
        }
        var questionEntity = optionalQuestion.get();
        BeanUtils.copyProperties(questionDTO, questionEntity);
        return ResponseEntity.status(HttpStatus.OK).body(questionRepository.save(questionEntity));
    }

    @DeleteMapping("/questions/{id}")
    public ResponseEntity<Object> deleteQuestion(@PathVariable(value = "id") Long id) {
        Optional<QuestionEntity> optionalQuestion = questionRepository.findById(id);
        if (optionalQuestion.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question not found.");
        }
        questionRepository.delete(optionalQuestion.get());
        return ResponseEntity.status(HttpStatus.OK).body("Question deleted successfully.");
    }
}
