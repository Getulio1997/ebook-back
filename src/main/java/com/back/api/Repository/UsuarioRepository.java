package com.back.api.Repository;

import java.util.Optional;

// Importações necessárias para funcionar a repository dividamente.
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import com.back.api.Model.Usuario;


@Repository // Usando a repository como uma API REST.
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);
    
};  // Fazendo a comunicação do back com o banco de dados.
