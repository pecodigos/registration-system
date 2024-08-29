package com.pecodigos.registration_system.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "tb_questions")
public class QuestionEntity extends RepresentationModel<QuestionEntity> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;
}
