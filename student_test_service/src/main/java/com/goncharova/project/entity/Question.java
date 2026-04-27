package com.goncharova.project.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "questions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question
{
    @Id
    @Column("id")
    private Long id;

    @Column("questions_text")
    private String questionsText;

    @Column("question_type")
    private String questionType;

    @Column("option1")
    private String option1;

    @Column("option2")
    private String option2;

    @Column("option3")
    private String option3;

    @Column("option4")
    private String option4;

    @Column("correct_answer")
    private String correctAnswer;

    @Column("explanation")
    private String explanation;
}
