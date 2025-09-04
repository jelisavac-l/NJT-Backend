package com.jelisavacl.njt.service;

import com.jelisavacl.njt.entity.Artist;
import com.jelisavacl.njt.repository.ArtistRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    public Artist createArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    public Artist updateArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    public List<Artist> getAllByNameContaining(String name) {
        return artistRepository.findByNameContainingIgnoreCase(name);
    }

}
