package com.ust.IASpringBootProject.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SentimentResult {
    private String label;
    private double score;
}
