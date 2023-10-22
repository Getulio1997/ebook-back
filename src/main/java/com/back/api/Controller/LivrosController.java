package com.back.api.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.back.api.Dto.NovoLivroRequest;
import com.back.api.Model.Livros;
import com.back.api.Service.LivrosService;

@RestController
@RequestMapping("/livros")
public class LivrosController {
    @Autowired // Autorizando o uso do private nos métodos GET, POST, PUT, DELETE.
    private LivrosService livrosService;

    @GetMapping // Metodo GET para listar todos os livros.
    // Fazendo uma listagem do modelo livros trazendo todos os livros registrados no
    // banco de dados.
    public List<Livros> listarLivros() {
        return livrosService.listarLivros(); // colocando na Service para validar a listagem.
    }

    @GetMapping("/{id}") // Metodo GET para listar os livros por ID.
    // Fazendo uma listagem do modelo usuário trazendo por ID os livros registrados
    // no banco de dados.
    public ResponseEntity<Livros> listarLivrosPorId(@PathVariable Integer id) {
        return livrosService.listarLivrosPorId(id); // colocando na Service para validar a listagem por ID.
    }

    // @PostMapping // Metodo POST para criar um novo livros.
    // public Livros novoLivros(@RequestBody Livros livros) { // Criação de um novo
    // livros.
    // Livros novoLivros = livrosService.novoLivros(livros); // Fazendo as
    // validações na service
    // return novoLivros;
    // }

    @PostMapping
    public ResponseEntity<String> novoLivros(@RequestBody NovoLivroRequest novoLivroRequest) {
        String nome = novoLivroRequest.getNome();
        String autor = novoLivroRequest.getAutor();
        String genero = novoLivroRequest.getGenero();
        String ano = novoLivroRequest.getAno();

        if (livrosService.novoLivros(nome, autor, genero, ano)) {
            return ResponseEntity.ok("Livro cadastrado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha ao cadastrar livro!");
        }
    }

    @PutMapping("/{id}") // Metodo PUT para poder atualizar o livros por ID.
    // Edição do livros por ID.
    public ResponseEntity<Livros> editarLivrosPorId(@PathVariable Integer id, @RequestBody Livros livros) {
        Livros livrosEditado = livrosService.editarLivrosPorId(id, livros); // Validando na service.

        if (livrosEditado != null) { // Validando caso tenha um ID nulo.
            return ResponseEntity.ok(livrosEditado); // Se encontrar o ID mostra um status code 200 ok.
        } else {
            return ResponseEntity.notFound().build(); // Caso não encontre mostra um status code 404 not Found.
        }
    }

    @DeleteMapping("/{id}") // Metodo delete para excluir o usuário por ID.
    public ResponseEntity<Void> deleteLivros(@PathVariable Integer id) { // Deleta um usuário por ID.
        livrosService.deleteLivros(id); // Faz a validação na service.
        return ResponseEntity.ok().build(); // Se tiver o ID para excluir mostra um status code 200 ok.
    }
}
