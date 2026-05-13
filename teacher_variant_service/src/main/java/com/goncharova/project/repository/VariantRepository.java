package com.goncharova.project.repository;

import com.goncharova.project.entity.VariantEntity;
import org.springframework.data.repository.CrudRepository;

public interface VariantRepository extends CrudRepository<VariantEntity, Long> {
}
