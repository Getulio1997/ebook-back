package com.back.api.Service;

// Importações necessárias para funcionar a service dividamente.
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.back.api.Model.Usuario;
import com.back.api.Repository.UsuarioRepository;

@Service // Usando a service como uma API REST.
public class UsuarioService {

    @Autowired // Autorizando o uso do private na service.
    private UsuarioRepository usuarioRepository;
    
    public List<Usuario> listarUsuarios() { // Chamando o atributo listarUsuarios da controller.
        return (List<Usuario>) usuarioRepository.findAll(); // Validando para listar todos os usuários na controller.
    }

    public ResponseEntity<Usuario> listarUsuarioPorId(Integer id) { // Chamando o atributo listarUsuariosPorId da controller.
        Usuario usuario = usuarioRepository.findById(id).orElse(null); // Validando para listar usuários por ID na controller.
    
        if (usuario != null) { // Validando caso tenha um ID nulo.
            return ResponseEntity.ok(usuario); // Se encontrar o ID mostra um status code 200 ok.
        } else {
            return ResponseEntity.notFound().build(); // Caso não encontre mostra um status code 404 not Found.
        }
    }    

    public Usuario novoUsuario(Usuario usuario) { // Chamando o atributo novoUsuario da controller.
        return usuarioRepository.save(usuario); // Validando a criação de um novo usuário para a controller.
    }

    public Usuario editarUsuarioPorId(Integer id, Usuario usuario) { // Chamando o atributo editarUsuarioPorId da controller.
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id); // Validando a edição do usuário por ID.
        
        if (usuarioExistente.isPresent()) { // Critérios de validação para a edição.
            Usuario usuarioAtual = usuarioExistente.get(); // Listar os atributos que existe para serem validados.
            usuarioAtual.setEmail(usuario.getEmail()); // Validar a validação por email.
            usuarioAtual.setNome(usuario.getNome()); // Validar a validação por nome.
            usuarioAtual.setSenha(usuario.getSenha()); // Validar a validação por senha.
            return usuarioRepository.save(usuarioAtual); // Salvar todas as alterações.
        } else {
            return null;
        }
    }
    
    public void deleteUsuario(Integer id) { // Chamando o atributo deleteUsuario da controller.
        usuarioRepository.deleteById(id); // Validar a exclusão do usuário por ID.
    }

}
