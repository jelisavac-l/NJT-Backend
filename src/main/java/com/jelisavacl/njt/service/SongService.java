package com.jelisavacl.njt.service;

import com.jelisavacl.njt.dto.SongDTO;
import com.jelisavacl.njt.entity.Song;
import com.jelisavacl.njt.entity.User;
import com.jelisavacl.njt.repository.SongRepository;
import com.jelisavacl.njt.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
@Service
public class SongService {

    private final SongRepository songRepository;
    private final UserRepository userRepository;

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

    public List<SongDTO> getAllSongs() {
        List<Song> songs =  songRepository.findAll();
        List<SongDTO> dtos = new ArrayList<>();
        songs.forEach(song -> {
            dtos.add(new SongDTO().toDTO(song));
        });
        return dtos;
    }

    public List<SongDTO> getLatestSongs() {
        List<Song> songs = songRepository.findTop10ByOrderByIdDesc();
        List<SongDTO> dtos = new ArrayList<>();
        songs.forEach(song -> {
            dtos.add(new SongDTO().toDTO(song));
        });
        return dtos;
    }

    public Song getSongById(Long id) {
        return songRepository.findById(id).orElseThrow(
            () -> new NoSuchElementException("Invalid id")
        );
    }

    public List<SongDTO> getMostPopular() {
        List<Song> songs = songRepository.findTop10ByOrderByViewCountDesc();
        List<SongDTO> dtos = new ArrayList<>();
        songs.forEach(song -> {
            dtos.add(new SongDTO().toDTO(song));
        });
        return dtos;
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

    @Transactional
    public List<SongDTO> getSongsByUser(String username) {
        User u = userRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);
        List<Song> songs = songRepository.findByCreatedBy(u);
        List<SongDTO> dtos = new ArrayList<>();
        songs.forEach(song -> {
            dtos.add(SongDTO.toDTO(song));
        });
        return dtos;
    }
}
