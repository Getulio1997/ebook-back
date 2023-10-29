package com.back.api.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.back.api.Service.ImagemService;

@RestController // API REST.
@RequestMapping("/upload") // Requisição da API por meio da URL /upload.
public class CameraController {

    @Autowired
    private ImagemService imageService; // Um serviço para manipular as imagens.

    @PostMapping("/imagem") // Rota para usar quando quiser salvar a imagem no front-end.
    public ResponseEntity<String> uploadImage(@RequestParam("imagem") MultipartFile imagem) {
        try {
            // Valida a imagem, processa e se necessário salva no banco de dados.
            imageService.saveImage(imagem);

            return ResponseEntity.ok("Imagem salva com sucesso."); // Mostra uma mensagem no postman quando a imagem vai para o banco
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar a imagem."); // Mostra esse erro no postman
        }
    }
}
