package dev.rohit.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import java.net.URI;


@RestController
@RequestMapping("/zoom")
public class ZoomController {

    @Value("${zoom.client-id}")
    private String clientId;

    @Value("${zoom.client-secret}")
    private String clientSecret;

    @Value("${zoom.redirect-uri}")
    private String redirectUri;

    private static final String ZOOM_AUTH_URL = "https://zoom.us/oauth/authorize";
    private static final String ZOOM_TOKEN_URL = "https://zoom.us/oauth/token";

    private Map<String, String> tokens = new HashMap<>();

    @GetMapping("/connect")
    public ResponseEntity<String> connectZoom() {
        String authUrl = ZOOM_AUTH_URL + "?response_type=code&client_id=" + clientId + "&redirect_uri=" + redirectUri;
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", authUrl).build();
    }

    @GetMapping("/callback")
    public ResponseEntity<String> handleCallback(@RequestParam String code) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(clientId, clientSecret);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", code);
        body.add("redirect_uri", redirectUri);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(ZOOM_TOKEN_URL, HttpMethod.POST, request, Map.class);
            Map<String, Object> responseBody = response.getBody();

            if (responseBody != null && responseBody.containsKey("access_token") && responseBody.containsKey("refresh_token")) {
                tokens.put("access_token", (String) responseBody.get("access_token"));
                tokens.put("refresh_token", (String) responseBody.get("refresh_token"));

                String frontendRedirectUrl = "http://localhost:3000?success=true";
                HttpHeaders redirectHeaders = new HttpHeaders();
                redirectHeaders.setLocation(URI.create(frontendRedirectUrl));
                return new ResponseEntity<>(redirectHeaders, HttpStatus.FOUND);
            } else {
                String frontendRedirectUrl = "http://localhost:3000?success=false";
                HttpHeaders redirectHeaders = new HttpHeaders();
                redirectHeaders.setLocation(URI.create(frontendRedirectUrl));
                return new ResponseEntity<>(redirectHeaders, HttpStatus.FOUND);
            }
        } catch (Exception e) {
            String frontendRedirectUrl = "http://localhost:3000?success=false";
            HttpHeaders redirectHeaders = new HttpHeaders();
            redirectHeaders.setLocation(URI.create(frontendRedirectUrl));
            return new ResponseEntity<>(redirectHeaders, HttpStatus.FOUND);
        }
    }


    @GetMapping("/meetings")
    public ResponseEntity<List<Map<String, Object>>> getMeetings() {
        if (!tokens.containsKey("access_token")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        String accessToken = tokens.get("access_token");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(
                "https://api.zoom.us/v2/users/me/meetings", HttpMethod.GET, entity, Map.class);

        List<Map<String, Object>> meetings = (List<Map<String, Object>>) response.getBody().get("meetings");
        return ResponseEntity.ok(meetings);
    }
}
