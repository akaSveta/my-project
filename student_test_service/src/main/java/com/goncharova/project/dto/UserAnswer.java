package com.goncharova.project.dto;

public class UserAnswer {
    private Long questionId;
    private String userAnswer;
    private boolean correct;
    private String correctAnswer;
    private String explanation;
    private String questionText;

    public UserAnswer() {
    }

    public UserAnswer(Long questionId, String userAnswer, boolean correct, String correctAnswer, String explanation, String questionText) {
        this.questionId = questionId;
        this.userAnswer = userAnswer;
        this.correct = correct;
        this.correctAnswer = correctAnswer;
        this.explanation = explanation;
        this.questionText = questionText;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
}