package com.pecodigos.registration_system.controllers;

import com.pecodigos.registration_system.dtos.AnswerDTO;
import com.pecodigos.registration_system.entities.AnswerEntity;
import com.pecodigos.registration_system.repositories.AnswerRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnswerController {

    @Autowired
    AnswerRepository answerRepository;

    @PostMapping("/answers")
    public ResponseEntity<AnswerEntity> saveAnswer(@RequestBody @Valid AnswerDTO answerDTO) {
        var answerEntity = new AnswerEntity();
        BeanUtils.copyProperties(answerDTO, answerEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(answerRepository.save(answerEntity));
    }
}
