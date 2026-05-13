package com.goncharova.project.service;

import com.goncharova.project.entity.QuestionEntity;
import com.goncharova.project.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository repository;

    public List<QuestionEntity> getAllQuestions() {
        List<QuestionEntity> list = new ArrayList<>();
        repository.findAll().forEach(list::add);
        return list;
    }

    public Optional<QuestionEntity> getQuestionById(Long id) {
        return repository.findById(id);
    }

    public QuestionEntity save(QuestionEntity question) {
        return repository.save(question);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}