package iw20232024robafone.backend.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ApiService {

    private HttpClient httpClient = HttpClient.newHttpClient();
    private String url = "http://omr-simulator.us-east-1.elasticbeanstalk.com/";
    private String carrier = "iw20232024robafone";

    public Map<String, String> getAllCarrierLlamada(){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "?carrier=" + carrier))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            String responseBody = response.body();
            Pattern pattern = Pattern.compile("\"sender\":\"(.?)\".?\"duration\":\"(.*?)\"");
            Matcher matcher = pattern.matcher(responseBody);

            Map<String, String> mapaPersonas = new HashMap<>();
            while (matcher.find()) {
                mapaPersonas.put(matcher.group(1), matcher.group(2));
            }

            return mapaPersonas;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }


}

