package tr.edu.duzce.mf.bm.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.util.Map;
import java.util.HashMap;

@Service
public class AiServiceImpl implements AiService {
    
    private final RestTemplate restTemplate = new RestTemplate();
    private final String AI_SERVICE_URL = "http://localhost:8088/analyze";

    @Override
    public String analyzeAndGetSummary(String url) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("url", url);
            
            HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(AI_SERVICE_URL, request, Map.class);
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> body = response.getBody();
                if (Boolean.TRUE.equals(body.get("ok"))) {
                    Map<String, Object> analysis = (Map<String, Object>) body.get("analysis");
                    if (analysis != null) {
                        return (String) analysis.get("summary");
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("AI Service Hatası: " + e.getMessage());
        }
        return "Özet bulunamadı.";
    }
}
