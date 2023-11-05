package com.back.api.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.back.api.Model.ImageEntity;
import com.back.api.Model.Livros;
import com.back.api.Repository.CameraRepository;
import com.back.api.Repository.LivrosRepository;

@Service
public class LivrosService {
    @Autowired // Autorizando o uso do private na service.
    private LivrosRepository livrosRepository; 
    @Autowired
    private CameraRepository imagemRepository;

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

    public boolean novoLivros(String nome, String autor, String genero, String ano, Integer idImagem) {
        // Verifica se já existem livros com o mesmo nome no banco de dados
        List<Livros> livrosComMesmoNome = livrosRepository.findByNome(nome);

        System.out.println("ID da imagem recebido no serviço: " + idImagem);

        if (!imagemExiste(idImagem)) {
            return false; // A imagem não existe, não permita o cadastro
        }

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
            novoLivro.setIdImagem(idImagem);
            livrosRepository.save(novoLivro);
            return true;
        }
    }    

    public boolean imagemExiste(Integer idImagem) {
        Optional<ImageEntity> imagem = imagemRepository.findById(idImagem);
        return imagem.isPresent();
    }
    
    public Livros editarLivrosPorId(Integer id, Livros livros) {
    Optional<Livros> livrosExistente = livrosRepository.findById(id);

    if (livrosExistente.isPresent()) {
        Livros livrosAtual = livrosExistente.get();
        
        // Validação do nome do livro
        String novoNome = livros.getNome();
        if (novoNome == null || novoNome.trim().isEmpty()) {
            return null; // Nome não pode estar vazio
        }

        // Validação do autor do livro
        String novoAutor = livros.getAutor();
        if (novoAutor == null || novoAutor.trim().isEmpty()) {
            return null; // Autor não pode estar vazio
        }

        // Validação do genero do livro
        String novoGenero = livros.getGenero();
        if (novoGenero == null || novoGenero.trim().isEmpty()) {
            return null; // Gênero não pode estar vazio
        }

        // Validação do ano do livro
        String novoAno = livros.getAno();
        if (novoAno == null || novoAno.trim().isEmpty()) {
            return null; // Ano não pode estar vazio
        }

        Integer novaidImagem = livros.getIdImagem();
        if (novaidImagem != null && !novaidImagem.equals(livrosAtual.getIdImagem())) {
            // Verifique se a nova imagem existe e associe-a ao livro.
            if (imagemExiste(novaidImagem)) {
                livrosAtual.setIdImagem(novaidImagem);
            } else {
                // Lidar com o caso em que a nova imagem não existe.
                return null;
            }
        }

        // Verifique se o novo nome do livro já existe no banco de dados, exceto para o livro atual.
        if (livrosRepository.existsByNomeAndIdNot(novoNome, id)) {
            // Nome de livro já cadastrado em outro livro, retorne null.
            return null;
        } else {
            livrosAtual.setNome(novoNome);
            livrosAtual.setAutor(novoAutor);
            livrosAtual.setGenero(livros.getGenero());
            livrosAtual.setAno(novoAno);
            livrosAtual.setIdImagem(novaidImagem);;
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
