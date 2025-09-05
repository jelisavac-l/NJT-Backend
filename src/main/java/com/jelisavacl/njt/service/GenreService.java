package com.jelisavacl.njt.service;

import com.jelisavacl.njt.entity.Genre;
import com.jelisavacl.njt.repository.GenreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class GenreService {

    public final GenreRepository genreRepository;

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Genre getGenreById(Long id) {
        return genreRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Genre not found with id: " + id));
    }

    public Genre getGenreByName(String name) {
        return genreRepository.findByName(name)
            .orElseThrow(() -> new RuntimeException("Genre not found with name: " + name));
    }

}
