package com.back.api.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.back.api.Model.Livros;

@Repository
public interface LivrosRepository extends JpaRepository<Livros, Integer> {

    List<Livros> findByNome(String nome);

    boolean existsByNomeAndIdNot(String nome, Integer id);
}

