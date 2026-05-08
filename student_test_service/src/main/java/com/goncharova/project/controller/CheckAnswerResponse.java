package com.goncharova.project.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckAnswerResponse {
    private boolean correct;
    private String explanation;
}