package twitter.clone.auth_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import twitter.clone.auth_service.DTO.LoginRequestPayload;
import twitter.clone.auth_service.utils.JWTUtil;

@RestController
public class AuthRestController {
    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/auth/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestPayload body) {
        String token = jwtUtil.generateToken(body.getUsername());

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/auth/register")
    public ResponseEntity<String> register(@RequestBody String username) {
        // Persist user to some persistent storage
        System.out.println("Info saved...");

        return new ResponseEntity<String>("Registered", HttpStatus.OK);
    }
}
