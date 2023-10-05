package com.back.api.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.back.api.Model.Usuario;
import com.back.api.Repository.UsuarioRepository;

@Service
public class AuthService {
        
        @Autowired
        private UsuarioRepository usuarioRepository;
    
        public boolean authenticate(String email, String senha) {
            Optional<Usuario> userOptional = usuarioRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                Usuario user = userOptional.get();
                
                // Compare a senha fornecida com a senha armazenada no banco de dados (sem criptografia)
            if (senha.equals(user.getSenha())) {
                return true;
            }
        }
         return false;
    }
}

