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

}
