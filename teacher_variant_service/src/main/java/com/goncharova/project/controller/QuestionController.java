package com.goncharova.project.controller;

import com.goncharova.project.entity.QuestionEntity;
import com.goncharova.project.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping
    public String listQuestions(Model model) {
        model.addAttribute("questions", questionService.getAllQuestions());
        return "questions";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("question", new QuestionEntity());
        return "add-question";
    }

    @PostMapping("/add")
    public String addQuestion(@ModelAttribute QuestionEntity question) {
        questionService.save(question);
        return "redirect:/questions";
    }

    @GetMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable("id") Long id) {
        questionService.deleteById(id);
        return "redirect:/questions";
    }
}