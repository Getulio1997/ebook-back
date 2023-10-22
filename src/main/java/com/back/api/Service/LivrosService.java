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

    public boolean novoLivros(String nome, String autor, String genero, String ano) {
        // Verifica se já existem livros com o mesmo nome no banco de dados
        List<Livros> livrosComMesmoNome = livrosRepository.findByNome(nome);
        if (!livrosComMesmoNome.isEmpty()) {
            // Já existem livros com o mesmo nome, não permita o cadastro
            return false;
        } else {
            // O nome não existe, pode prosseguir com o cadastro
            Livros novoLivro = new Livros();
            novoLivro.setNome(nome);
            novoLivro.setAutor(autor);
            novoLivro.setGenero(genero);
            novoLivro.setAno(ano);
            livrosRepository.save(novoLivro);
            return true;
        }
    }    

    public Livros editarLivrosPorId(Integer id, Livros livros) {
        Optional<Livros> livrosExistente = livrosRepository.findById(id);

        if (livrosExistente.isPresent()) {
            Livros livrosAtual = livrosExistente.get();
            String novoNome = livros.getNome();

            // Verifique se o novo nome do livro já existe no banco de dados, exceto para o livro atual.
            if (livrosRepository.existsByNomeAndIdNot(novoNome, id)) {
                // Nome de livro já cadastrado em outro livro, retorne null.
                return null;
            } else {
                livrosAtual.setNome(novoNome);
                livrosAtual.setAutor(livros.getAutor());
                livrosAtual.setGenero(livros.getGenero());
                livrosAtual.setAno(livros.getAno());
                return livrosRepository.save(livrosAtual);
            }
        } else {
            // Livro não encontrado, retorne null.
            return null;
        }
    }

    public void deleteLivros(Integer id) { // Chamando o atributo deleteUsuario da controller.
        livrosRepository.deleteById(id); // Validar a exclusão do usuário por ID.
    }
}
