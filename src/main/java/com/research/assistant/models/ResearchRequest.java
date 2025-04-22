package com.research.assistant.models;

import lombok.Data;

@Data
public class ResearchRequest {
    private String content;
    private String operation;
    private String language = "english"; // default value
}
