package com.back.api.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.back.api.Model.ImageEntity;
import com.back.api.Service.CameraService;

@RestController // API REST.
@RequestMapping("/upload") // Requisição da API por meio da URL /upload.
public class CameraController {

    @Autowired
    private CameraService imagemService; // Um serviço para manipular as imagens.

    @GetMapping
    public ResponseEntity<List<String>> getTodasAsImagens() {
        try {
            List<String> imagens = imagemService.getTodasAsImagens();
    
            if (imagens.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
    
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON); // Use APPLICATION_JSON para a lista de strings Base64.
    
            return new ResponseEntity<>(imagens, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
        
    @GetMapping("/imagem/{id}")
    public ResponseEntity<byte[]> getImagem(@PathVariable Integer id) {
        try {
            byte[] imagem = imagemService.getImagemById(id);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(imagem, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/imagem")
    public ResponseEntity<Map<String, Integer>> uploadImage(@RequestParam("imagem") MultipartFile imagem) {
        try {
            // Valide a imagem, processe-a e, se necessário, salve-a no banco de dados.
            ImageEntity savedImage = imagemService.saveImage(imagem);

            // Crie um mapa (JSON) que contém o ID da imagem.
            Map<String, Integer> response = new HashMap<>();
            response.put("idImagem", savedImage.getId());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyMap());
        }
    }
}
