package com.goncharova.project.controller;

import com.goncharova.project.dto.UserAnswer;
import com.goncharova.project.entity.QuestionEntity;
import com.goncharova.project.service.TestService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/exam")
public class ExamController {
    private final TestService testService;

    public ExamController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/start")
    public String startExam(HttpSession session) {
        List<QuestionEntity> allQuestions = testService.getAllQuestions();
        if (allQuestions.isEmpty()) {
            return "redirect:/?error=no-questions";
        }
        session.setAttribute("examQuestions", allQuestions);
        session.setAttribute("currentIndex", 0);
        session.setAttribute("userAnswers", new ArrayList<UserAnswer>());
        return "redirect:/exam/question";
    }

    @GetMapping("/question")
    public String showQuestion(HttpSession session, Model model) {
        List<QuestionEntity> questions = getQuestionsFromSession(session);
        Integer currentIndex = (Integer) session.getAttribute("currentIndex");
        if (currentIndex == null || questions == null || currentIndex >= questions.size()) {
            return "redirect:/exam/result";
        }
        QuestionEntity question = questions.get(currentIndex);
        model.addAttribute("question", question);
        model.addAttribute("questionNumber", currentIndex + 1);
        model.addAttribute("totalQuestions", questions.size());
        return "exam-question";
    }

    @PostMapping("/answer")
    public String saveAnswer(@RequestParam("id") Long questionId, @RequestParam(value = "answer", required = false) String singleAnswer, @RequestParam(value = "answers", required = false) List<String> multipleAnswers, HttpSession session) {
        String userAnswer;
        if (multipleAnswers != null && !multipleAnswers.isEmpty()) {
            userAnswer = String.join(",", multipleAnswers);
        } else {
            userAnswer = singleAnswer;
        }
        @SuppressWarnings("unchecked")
        List<QuestionEntity> questions = (List<QuestionEntity>) session.getAttribute("examQuestions");
        Integer currentIndex = (Integer) session.getAttribute("currentIndex");
        @SuppressWarnings("unchecked")
        List<UserAnswer> userAnswers = (List<UserAnswer>) session.getAttribute("userAnswers");
        if (userAnswers == null) {
            userAnswers = new ArrayList<>();
            session.setAttribute("userAnswers", userAnswers);
        }
        boolean isCorrect = testService.checkAnswer(questionId, userAnswer);
        QuestionEntity question = testService.getQuestionById(questionId);
        UserAnswer userAnswerObj = new UserAnswer(questionId, userAnswer, isCorrect, question.getCorrectAnswer(), question.getExplanation(), question.getQuestionsText());
        userAnswers.add(userAnswerObj);
        currentIndex++;
        session.setAttribute("currentIndex", currentIndex);
        if (currentIndex >= questions.size()) {
            return "redirect:/exam/result";
        }
        return "redirect:/exam/question";
    }

    @GetMapping("/result")
    public String showResult(HttpSession session, Model model) {
        List<UserAnswer> userAnswers = (List<UserAnswer>) session.getAttribute("userAnswers");
        List<QuestionEntity> questions = getQuestionsFromSession(session);
        if (userAnswers == null || questions == null) {
            return "redirect:/exam/start";
        }
        long correctCount = userAnswers.stream().filter(UserAnswer::isCorrect).count();
        int totalQuestions = questions.size();
        int percentage = (int) ((double) correctCount / totalQuestions * 100);
        model.addAttribute("userAnswers", userAnswers);
        model.addAttribute("totalQuestions", totalQuestions);
        model.addAttribute("correctCount", correctCount);
        model.addAttribute("percentage", percentage);
        session.removeAttribute("examQuestions");
        session.removeAttribute("currentIndex");
        session.removeAttribute("userAnswers");
        return "exam-result";
    }

    @SuppressWarnings("unchecked")
    private List<QuestionEntity> getQuestionsFromSession(HttpSession session) {
        return (List<QuestionEntity>) session.getAttribute("examQuestions");
    }
}