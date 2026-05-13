package com.goncharova.project.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "variant_questions")
@Data
public class VariantQuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "variant_id")
    private Long variantId;
    @Column(name = "question_id")
    private Long questionId;
    private Integer position;
}
