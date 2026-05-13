package com.goncharova.project.repository;

import com.goncharova.project.entity.QuestionEntity;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<QuestionEntity, Long> {
}
