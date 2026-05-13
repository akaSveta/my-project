package com.goncharova.project.controller;

import com.goncharova.project.service.QuestionService;
import com.goncharova.project.service.VariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/variants")
@RequiredArgsConstructor
public class VariantController {
    private final VariantService variantService;
    private final QuestionService questionService;

    @GetMapping
    public String listVariants(Model model) {
        model.addAttribute("variants", variantService.getAllVariants());
        return "variants";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("allQuestions", questionService.getAllQuestions());
        return "add-variant";
    }

    @PostMapping("/add")
    public String addVariant(@RequestParam("title") String title, @RequestParam("description") String description, @RequestParam(value = "questionIds", required = false) List<Long> questionIds) {
        if (questionIds == null || questionIds.isEmpty()) {
            return "redirect:/variants/add?error=no-questions";
        }
        variantService.createVariant(title, description, questionIds);
        return "redirect:/variants";
    }

    @GetMapping("/delete/{id}")
    public String deleteVariant(@PathVariable("id") Long id) {
        variantService.deleteVariant(id);
        return "redirect:/variants";
    }

    @GetMapping("/preview/{id}")
    public String previewVariant(@PathVariable("id") Long id, Model model) {
        model.addAttribute("variant", variantService.getVariantWithQuestions(id));
        return "preview-variant";
    }
}