package com.back.api.Controller;

// Importações necessárias para a API REST funcinar devidamente.
import java.util.List;
// import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.back.api.Model.Usuario;
import com.back.api.Service.UsuarioService;

@RestController // API REST.
@RequestMapping("/usuarios") // Requisição das APIs por meio da URL /usuarios.
public class UsuarioController {
    
    @Autowired // Autorizando o uso do private nos métodos GET, POST, PUT, DELETE.
    private UsuarioService usuarioService;
    
    @GetMapping // Metodo GET para listar todos os usuários.
    // Fazendo uma listagem do modelo usuario trazendo todos os usuários registrados no banco de dados.
    public List<Usuario> listarUsuarios() { 
        return usuarioService.listarUsuarios(); // colocando na Service para validar a listagem.
    }

    @GetMapping("/{id}") // Metodo GET para listar os usuários por ID.
     // Fazendo uma listagem do modelo usuário trazendo por ID os usuários registrados no banco de dados.
    public ResponseEntity<Usuario> listarUsuarioPorId(@PathVariable Integer id) {
        return usuarioService.listarUsuarioPorId(id); // colocando na Service para validar a listagem por ID.
    }

    @PostMapping // Metodo POST para criar um novo usuario.
    public Usuario novoUsuario(@RequestBody Usuario usuario) { // Criação de um novo usuário.
        Usuario NovoUsuario = usuarioService.novoUsuario(usuario); // Fazendo as validações na service
        return NovoUsuario;
    }

    @PutMapping("/{id}") // Metodo PUT para poder atualizar o usuário por ID.
    // Edição do usuario por ID.
    public ResponseEntity<Usuario> editarUsuarioPorId(@PathVariable Integer id, @RequestBody Usuario usuario) {
    Usuario usuarioEditado = usuarioService.editarUsuarioPorId(id, usuario); // Validando na service.
    
    if (usuarioEditado != null) { // Validando caso tenha um ID nulo.
        return ResponseEntity.ok(usuarioEditado); // Se encontrar o ID mostra um status code 200 ok.
    } else {
        return ResponseEntity.notFound().build(); // Caso não encontre mostra um status code 404 not Found.
    }
}

    @DeleteMapping("/{id}") // Metodo delete para excluir o usuário por ID.
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) { // Deleta um usuário por ID.
    usuarioService.deleteUsuario(id); // Faz a validação na service.
    return ResponseEntity.ok().build(); // Se tiver o ID para excluir mostra um status code 200 ok.
    }
}
