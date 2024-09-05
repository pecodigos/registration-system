package com.pecodigos.registration_system.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jdk.jfr.Timestamp;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_students")
public class StudentEntity extends RepresentationModel<StudentEntity> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    @Pattern(regexp = "\\S+", message = "Username field must not contain spaces.")
    private String username;

    @Email(message = "Email field must contain a valid email address.")
    private String email;

    @Length(min = 10, max = 100)
    private String password;

    private Integer age;
    private Double height;

    @Timestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnswerEntity> answer = new ArrayList<>();
}
