package com.pecodigos.registration_system.repositories;

import com.pecodigos.registration_system.entities.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
}
