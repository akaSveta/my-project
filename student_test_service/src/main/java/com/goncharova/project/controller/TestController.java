package com.goncharova.project.controller;

import com.goncharova.project.entity.QuestionEntity;
import com.goncharova.project.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {

    private final TestService testService;

    @GetMapping("/random")
    public QuestionEntity getRandomQuestion() {
        log.info("Request to get random question");
        return testService.getRandomQuestion();
    }

    @GetMapping("/{id}")
    public QuestionEntity getQuestionById(@PathVariable Long id) {
        log.info("Request to get question by id: {}", id);
        return testService.getQuestionById(id);
    }

    @PostMapping("/check")
    public CheckAnswerResponse checkAnswer(@RequestParam Long id, @RequestParam String answer) {
        log.info("Request to check answer for question: {}", id);
        boolean correct = testService.checkAnswer(id, answer);
        QuestionEntity question = testService.getQuestionById(id);
        return new CheckAnswerResponse(correct, question != null ? question.getExplanation() : null);
    }
}