package com.back.api.Service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.back.api.Model.ImageEntity;
import com.back.api.Repository.CameraRepository;

@Service
public class CameraService {
    @Autowired
    private CameraRepository imageRepository; // Você deve criar um repositório para lidar com as imagens

    public void saveImage(MultipartFile image) {
        try {
            ImageEntity imageEntity = new ImageEntity();
            imageEntity.setData(image.getBytes());

            // Salve a entidade da imagem no banco de dados
            imageRepository.save(imageEntity);
        } catch (IOException e) {
            throw new ImageUploadException("Erro ao salvar a imagem.", e);
        }
    }
}
