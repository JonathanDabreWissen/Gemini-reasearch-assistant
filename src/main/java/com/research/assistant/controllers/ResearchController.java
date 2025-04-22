package com.research.assistant.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.research.assistant.models.ResearchRequest;
import com.research.assistant.services.ResearchService;

import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/api/process")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ResearchController {
    private final ResearchService researchService;

    @PostMapping("/summarize")
    public ResponseEntity<String> summarizeContent(@RequestBody ResearchRequest request){
        String result = researchService.processContent(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/meaning")
    public ResponseEntity<String> getMeaningOfContent(@RequestBody ResearchRequest request) {
        String result = researchService.processContent(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/translate")
    public ResponseEntity<String> translateContent(@RequestBody ResearchRequest request) {
        String result = researchService.processContent(request);
        return ResponseEntity.ok(result);
    }
    

}
