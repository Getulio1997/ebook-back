package com.back.api.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.back.api.Model.Livros;
import com.back.api.Repository.LivrosRepository;

@Service
public class LivrosService {
    @Autowired // Autorizando o uso do private na service.
    private LivrosRepository livrosRepository;
    
    public List<Livros> listarLivros() { // Chamando o atributo listarLivros da controller.
        return (List<Livros>) livrosRepository.findAll(); // Validando para listar todos os usuários na controller.
    }

    public ResponseEntity<Livros> listarLivrosPorId(Integer id) { // Chamando o atributo listarLivrosPorId da controller.
        Livros livros = livrosRepository.findById(id).orElse(null); // Validando para listar usuários por ID na controller.
    
        if (livros != null) { // Validando caso tenha um ID nulo.
            return ResponseEntity.ok(livros); // Se encontrar o ID mostra um status code 200 ok.
        } else {
            return ResponseEntity.notFound().build(); // Caso não encontre mostra um status code 404 not Found.
        }
    }    

    public Livros novoLivros(Livros livros) { // Chamando o atributo novoLivros da controller.
        return livrosRepository.save(livros); // Validando a criação de um novo usuário para a controller.
    }

    public Livros editarLivrosPorId(Integer id, Livros livros) { // Chamando o atributo editarLivrosPorId da controller.
        Optional<Livros> livrosExistente = livrosRepository.findById(id); // Validando a edição do usuário por ID.
        
        if (livrosExistente.isPresent()) { // Critérios de validação para a edição.
            Livros livrosAtual = livrosExistente.get(); // Listar os atributos que existe para serem validados.
            livrosAtual.setNome(livros.getNome()); // Validar a validação por nome.
            livrosAtual.setAutor(livros.getAutor()); // Validar a validação por autor.
            livrosAtual.setGenero(livros.getGenero()); // Validar a validação por genero.
            livrosAtual.setAno(livros.getAno()); // Validar a validação por ano.
            return livrosRepository.save(livrosAtual); // Salvar todas as alterações.
        } else {
            return null;
        }
    }
    
    public void deleteLivros(Integer id) { // Chamando o atributo deleteUsuario da controller.
        livrosRepository.deleteById(id); // Validar a exclusão do usuário por ID.
    }
}
