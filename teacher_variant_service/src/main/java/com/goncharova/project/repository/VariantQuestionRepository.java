package com.goncharova.project.repository;

import com.goncharova.project.entity.VariantQuestionEntity;
import org.springframework.data.repository.CrudRepository;

public interface VariantQuestionRepository extends CrudRepository<VariantQuestionEntity, Long> {
    Iterable<VariantQuestionEntity> findByVariantId(Long variantId);

    void deleteByVariantId(Long variantId);
}
