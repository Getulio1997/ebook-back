package com.back.api.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.back.api.Model.Usuario;
import com.back.api.Repository.UsuarioRepository;

@Service
public class CadastroService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean cadastro(String nome, String email, String senha) {
        // Verifica se o email já existe no banco de dados
        Optional<Usuario> userOptional = usuarioRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            // Email já está em uso, não permita o cadastro
            return false;
        } else {
            // O email não existe, pode prosseguir com o cadastro
            Usuario novoUsuario = new Usuario();
            novoUsuario.setNome(nome);
            novoUsuario.setEmail(email);
            novoUsuario.setSenha(senha);
            usuarioRepository.save(novoUsuario);
            return true;
        }
    }
}
