package com.goncharova.project.dto;

import com.goncharova.project.entity.QuestionEntity;
import lombok.Data;

import java.util.List;

@Data
public class VariantPreviewDto {
    private Long id;
    private String title;
    private String description;
    private String createdAt;
    private List<QuestionEntity> questions;
}