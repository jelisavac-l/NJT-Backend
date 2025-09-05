package com.jelisavacl.njt.service;

import com.jelisavacl.njt.entity.Artist;
import com.jelisavacl.njt.entity.Song;
import com.jelisavacl.njt.repository.ArtistRepository;
import com.jelisavacl.njt.repository.SongRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
@Service
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final SongRepository songRepository;

    public Artist createArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    public Artist updateArtist(Long id, Artist updatedArtist) {
        Artist existing = getArtistById(id);
        existing.setName(updatedArtist.getName());
        return artistRepository.save(existing);
    }

    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    public List<Artist> searchArtistsByName(String name) {
        return artistRepository.findByNameContainingIgnoreCase(name);
    }

    public Artist getArtistById(Long id) {
        return artistRepository.findById(id).orElseThrow(
            () -> new NoSuchElementException("Invalid id"));
    }

    public List<Song> getSongsByArtist(Long artistId) {
        getArtistById(artistId); // ensures artist exists
        return songRepository.findByArtistId(artistId);
    }
}
