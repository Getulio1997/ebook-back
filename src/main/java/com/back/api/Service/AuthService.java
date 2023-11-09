package com.back.api.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.back.api.Model.JwtTokenProvider;
import com.back.api.Model.Usuario;
import com.back.api.Repository.UsuarioRepository;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public ResponseEntity<Map<String, Object>> authenticate(String email, String senha) {
        Optional<Usuario> userOptional = usuarioRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            Usuario user = userOptional.get();

            if (senha.equals(user.getSenha())) {
                // Autenticação bem-sucedida
                String token = jwtTokenProvider.generateToken(user);

                // Crie um objeto JSON que contenha as informações do usuário e o token
                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("email", user.getEmail());
                userInfo.put("nome", user.getNome());

                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                response.put("user", userInfo);

                return ResponseEntity.ok(response);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
}
