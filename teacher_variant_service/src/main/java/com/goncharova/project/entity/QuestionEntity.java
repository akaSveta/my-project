package com.goncharova.project.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "questions")
@Data
public class QuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "questions_text")
    private String questionsText;
    @Column(name = "question_type")
    private String questionType;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    @Column(name = "correct_answer")
    private String correctAnswer;
    @Column(columnDefinition = "TEXT")
    private String explanation;
}