package com.pecodigos.registration_system.controllers;

import com.pecodigos.registration_system.dtos.AnswerDTO;
import com.pecodigos.registration_system.dtos.QuestionDTO;
import com.pecodigos.registration_system.entities.AnswerEntity;
import com.pecodigos.registration_system.repositories.AnswerRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/answers")
    public ResponseEntity <List<AnswerEntity>> getAllAnswers() {
        List<AnswerEntity> answersList = answerRepository.findAll();
        if (!answersList.isEmpty()) {
            for (AnswerEntity answer : answersList) {
                answer.add(linkTo(methodOn(AnswerController.class).getOneAnswer(answer.getId())).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(answersList);
    }

    @GetMapping("/answers/{id}")
    public ResponseEntity<Object> getOneAnswer(@PathVariable(value="id") Long id) {
        Optional<AnswerEntity> optionalAnswer = answerRepository.findById(id);
        if(optionalAnswer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Answer not found.");
        }
        optionalAnswer.get().add(linkTo(methodOn(AnswerController.class).getAllAnswers()).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(optionalAnswer.get());
    }

    @PutMapping("/answers/{id}")
    public ResponseEntity<Object> updateQuestion(@PathVariable(value = "id") Long id,
                                                 @RequestBody @Valid AnswerDTO answerDTO) {
        Optional<AnswerEntity> optionalAnswer = answerRepository.findById(id);
        if (optionalAnswer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Answer not found.");
        }
        var answerEntity = optionalAnswer.get();
        BeanUtils.copyProperties(answerDTO, answerEntity);
        return ResponseEntity.status(HttpStatus.OK).body(answerRepository.save(answerEntity));
    }
}
