package com.jelisavacl.njt.service;

import com.jelisavacl.njt.entity.Song;
import com.jelisavacl.njt.repository.SongRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class SongService {

    private final SongRepository songRepository;

    public Song createSong(Song song) {
        return songRepository.save(song);
    }

    public Song updateSong(Long id, Song updatedSong) {
        Song song = songRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Invalid id")
        );
        updatedSong.setId(id);
        return songRepository.save(updatedSong);
    }

    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }

    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    public List<Song> getSongsByArtist(String name) {
        return songRepository.findByArtistName(name);
    }

    public List<Song> getSongsByGenre(String genre) {
        return songRepository.findByGenreName(genre);
    }

    public List<Song> getSongsByTag(List<String> tags) {
        return songRepository.findByTagsNameIn(tags);
    }

}
