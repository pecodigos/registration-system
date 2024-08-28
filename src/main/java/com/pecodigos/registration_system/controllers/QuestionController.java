package com.pecodigos.registration_system.controllers;

import com.pecodigos.registration_system.dtos.QuestionDTO;
import com.pecodigos.registration_system.entities.QuestionEntity;
import com.pecodigos.registration_system.repositories.QuestionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
