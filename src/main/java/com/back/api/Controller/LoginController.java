package com.back.api.Controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.back.api.Dto.LoginRequest;
import com.back.api.Service.AuthService;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String senha = loginRequest.getSenha();

        ResponseEntity<Map<String, Object>> authResult = authService.authenticate(email, senha);

        // Verifica o status da resposta
        if (authResult.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> authResponse = authResult.getBody();
            // Faça algo com a resposta bem-sucedida, por exemplo, retorne a resposta ao cliente
            return ResponseEntity.ok(authResponse);
        } else if (authResult.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            // Lide com a resposta de não autorizado, por exemplo, retorne um erro ao cliente
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authResult.getBody());
        } else {
            // Lide com outros cenários, se necessário
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(authResult.getBody());
        }
    }
}
