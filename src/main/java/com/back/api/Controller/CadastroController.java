package com.back.api.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.back.api.Dto.CadastroRequest;
import com.back.api.Service.CadastroService;

@RestController
@RequestMapping("/cadastro")
public class CadastroController {

    @Autowired
    private CadastroService cadastroService;

    @PostMapping
    public ResponseEntity<String> cadastro(@RequestBody CadastroRequest cadastroRequest) {
        String nome = cadastroRequest.getNome();
        String email = cadastroRequest.getEmail();
        String senha = cadastroRequest.getSenha();
        
        if (cadastroService.cadastro(nome, email, senha)) {
            return ResponseEntity.ok("Cadastro bem-sucedido");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha ao cadastrar");
        }
    }
}
