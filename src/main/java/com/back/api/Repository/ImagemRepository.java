package com.back.api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.back.api.Model.ImageEntity;

@Repository
public interface ImagemRepository extends JpaRepository<ImageEntity, Integer> {
    
}
