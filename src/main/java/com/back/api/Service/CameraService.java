package com.back.api.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.back.api.Model.ImageEntity;
import com.back.api.Repository.CameraRepository;

@Service
public class CameraService {
    @Autowired
    private CameraRepository imageRepository; // Você deve criar um repositório para lidar com as imagens

    public List<String> getTodasAsImagens() {
        List<String> imagens = new ArrayList<>();
        List<ImageEntity> entidadesDeImagem = imageRepository.findAll();
    
        for (ImageEntity imagem : entidadesDeImagem) {
            imagens.add(Base64.getEncoder().encodeToString(imagem.getData()));
        }
    
        return imagens;
    }
    
    public byte[] getImagemById(Integer id) {
        Optional<ImageEntity> imagemOptional = imageRepository.findById(id);
        if (imagemOptional.isPresent()) {
            return imagemOptional.get().getData();
        }
        return null;
    }

    public ImageEntity saveImage(MultipartFile image) {
        try {
            // Valide a imagem, processe-a e, se necessário, salve-a no banco de dados.
            byte[] imageData = image.getBytes();
            ImageEntity savedImage = new ImageEntity();
            savedImage.setData(imageData);

            return imageRepository.save(savedImage); // Salva a imagem no banco de dados e retorna a entidade da imagem.
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar a imagem", e);
        }
    }
}