package com.pecodigos.registration_system.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_answers")
public class AnswerEntity extends RepresentationModel<AnswerEntity> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String answer;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private StudentEntity student;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionEntity question;
}
