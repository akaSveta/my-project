package com.goncharova.project.service;

import com.goncharova.project.entity.QuestionEntity;
import com.goncharova.project.entity.VariantEntity;
import com.goncharova.project.entity.VariantQuestionEntity;
import com.goncharova.project.repository.VariantQuestionRepository;
import com.goncharova.project.repository.VariantRepository;
import com.goncharova.project.dto.VariantPreviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VariantService {
    private final VariantRepository variantRepository;
    private final VariantQuestionRepository variantQuestionRepository;
    private final QuestionService questionService;

    public List<VariantEntity> getAllVariants() {
        List<VariantEntity> list = new ArrayList<>();
        variantRepository.findAll().forEach(list::add);
        return list;
    }

    public VariantEntity getVariantById(Long id) {
        return variantRepository.findById(id).orElse(null);
    }

    @Transactional
    public VariantEntity createVariant(String title, String description, List<Long> questionIds) {
        VariantEntity variant = new VariantEntity();
        variant.setTitle(title);
        variant.setDescription(description);
        variant.setCreatedAt(LocalDateTime.now());
        VariantEntity saved = variantRepository.save(variant);
        int position = 0;
        for (Long qId : questionIds) {
            VariantQuestionEntity vq = new VariantQuestionEntity();
            vq.setVariantId(saved.getId());
            vq.setQuestionId(qId);
            vq.setPosition(position++);
            variantQuestionRepository.save(vq);
        }
        return saved;
    }

    @Transactional
    public void deleteVariant(Long id) {
        variantQuestionRepository.deleteByVariantId(id);
        variantRepository.deleteById(id);
    }

    public List<Long> getQuestionIdsByVariantId(Long variantId) {
        List<Long> ids = new ArrayList<>();
        variantQuestionRepository.findByVariantId(variantId)
                .forEach(vq -> ids.add(vq.getQuestionId()));
        return ids;
    }

    public VariantPreviewDto getVariantWithQuestions(Long variantId) {
        VariantEntity variant = getVariantById(variantId);
        if (variant == null) return null;
        List<Long> questionIds = getQuestionIdsByVariantId(variantId);
        List<QuestionEntity> questions = new ArrayList<>();
        for (Long qId : questionIds) {
            Optional<QuestionEntity> qOpt = questionService.getQuestionById(qId);
            qOpt.ifPresent(questions::add);
        }
        VariantPreviewDto dto = new VariantPreviewDto();
        dto.setId(variant.getId());
        dto.setTitle(variant.getTitle());
        dto.setDescription(variant.getDescription());
        dto.setCreatedAt(String.valueOf(variant.getCreatedAt()));
        dto.setQuestions(questions);
        return dto;
    }
}