package twitter.clone.auth_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import twitter.clone.auth_service.DTO.LoginRequestPayload;
import twitter.clone.auth_service.utils.JWTUtil;

@RestController
public class AuthRestController {
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/auth/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestPayload body) {
        String email = body.getEmail();
        String url = "http://user_service_app/user/email/" + email;
        String result = restTemplate.getForObject(url, String.class);
        System.out.println(result);
        String token = jwtUtil.generateToken(email);

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/auth/register")
    public ResponseEntity<String> register(@RequestBody String username) {
        // Persist user to some persistent storage
        System.out.println("Info saved...");

        return new ResponseEntity<String>("Registered", HttpStatus.OK);
    }
}
