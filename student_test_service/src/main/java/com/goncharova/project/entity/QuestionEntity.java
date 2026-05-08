package com.goncharova.project.entity;

import lombok.Data;

@Data
public class QuestionEntity {
    private Long id;
    private String questionsText;
    private String questionType;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String correctAnswer;
    private String explanation;
}