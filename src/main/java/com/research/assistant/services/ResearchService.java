package com.research.assistant.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.research.assistant.models.GeminiResponse;
import com.research.assistant.models.ResearchRequest;

@Service
public class ResearchService {

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    private final WebClient webClient;

    private final ObjectMapper objectMapper;

    public ResearchService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper){
        this.webClient = webClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    

    public String processContent(ResearchRequest request)   {
        // Build the Promp
        String prompt = buildPrompt(request);

        // Query the API
        Map<String, Object> requestBody = Map.of(
            "contents", new Object[]{
                Map.of(
                    "parts", new Object[]{
                        Map.of(
                            "text", prompt + request.getContent()
                        )
                    }
                )
            }
        );

        String response = webClient.post()
            .uri(geminiApiUrl + geminiApiKey)
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(String.class)
            .block();
        // Parse the response
        // Return response

        return extractTextFromResponse(response);
    }

    private String extractTextFromResponse(String response){
        try {
            GeminiResponse geminiResponse = objectMapper.readValue(response, GeminiResponse.class);

            if(geminiResponse.getCandidates() != null && !geminiResponse.getCandidates().isEmpty()){
                GeminiResponse.Candidate firsCandidate = geminiResponse.getCandidates().get(0);
                
                if(firsCandidate.getContent() != null && firsCandidate.getContent().getParts() != null && !firsCandidate.getContent().getParts().isEmpty()){
                    return firsCandidate.getContent().getParts().get(0).getText();
                }
            }
            return "No Content found in response";
        } catch (Exception e) {
            return "Error Parsing: " + e.getMessage();
        }
    }


    private String buildPrompt(ResearchRequest request){
        StringBuilder prompt = new StringBuilder();

        switch (request.getOperation()) {
            case "summarize":
                prompt.append("Provide a clear and concise summary of the following text:\n\n");
                break;
            
            case "suggest":
                prompt.append("Based on the following content: suggest related topics and further reading. Format the response with clear headings and bullet points:  \n\n");
                break;
        
            default:
                throw new IllegalArgumentException("Unknown Operation: " + request.getOperation());
        }

        return prompt.toString();
    }
}
