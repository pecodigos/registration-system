package com.pecodigos.registration_system.entities;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@Table(name = "tb_students")
public class StudentEntity extends RepresentationModel<StudentEntity> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private String email;
    private Integer age;
    private Double height;
}
