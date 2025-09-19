package com.jelisavacl.njt.service;

import com.jelisavacl.njt.dto.SongDTO;
import com.jelisavacl.njt.entity.Song;
import com.jelisavacl.njt.entity.User;
import com.jelisavacl.njt.repository.SongRepository;
import com.jelisavacl.njt.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@AllArgsConstructor
@Service
public class SongService {

    private final SongRepository songRepository;
    private final UserRepository userRepository;

    public Song createSong(Song song) {
        song.setViewCount(0);
        return songRepository.save(song);
    }

    public Song updateSong(Long id, Song updatedSong) {
        User creator = songRepository.findById(id)
            .orElseThrow(NoSuchElementException::new)
            .getCreatedBy();
        Song song = songRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Invalid id")
        );
        updatedSong.setId(id);
        updatedSong.setCreatedBy(creator);
        updatedSong.setViewCount(song.getViewCount());
        return songRepository.save(updatedSong);
    }

    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }

    public List<SongDTO> getAllSongs() {
        List<Song> songs =  songRepository.findAll();
        List<SongDTO> dtos = new ArrayList<>();
        songs.forEach(song -> {
            dtos.add(SongDTO.toDTO(song));
        });
        return dtos;
    }

    @Transactional
    public List<SongDTO> getLatestSongs() {
        List<Song> songs = songRepository.findTop10ByOrderByIdDesc();
        List<SongDTO> dtos = new ArrayList<>();
        songs.forEach(song -> {
            dtos.add(SongDTO.toDTO(song));
        });
        return dtos;
    }

    public Song getSongById(Long id) {
        return songRepository.findById(id).orElseThrow(
            () -> new NoSuchElementException("Invalid id")
        );
    }

    @Transactional
    public List<SongDTO> getMostPopular() {
        List<Song> songs = songRepository.findTop10ByOrderByViewCountDesc();
        List<SongDTO> dtos = new ArrayList<>();
        songs.forEach(song -> {
            dtos.add(SongDTO.toDTO(song));
        });
        return dtos;
    }

    @Transactional
    public List<SongDTO> getSongsByArtist(String name) {
        List<Song> songs = songRepository.findByArtistName(name);
        List<SongDTO> dtos = new ArrayList<>();
        songs.forEach(song -> {
            dtos.add(SongDTO.toDTO(song));
        });
        return dtos;
    }

    @Transactional
    public List<SongDTO> getSongsByGenre(String genre) {
        List<Song> songs = songRepository.findByGenreName(genre);
        List<SongDTO> dtos = new ArrayList<>();
        songs.forEach(song -> {
            dtos.add(SongDTO.toDTO(song));
        });
        return dtos;
    }

    @Transactional
    public List<SongDTO> getSongsByTag(List<String> tags) {
        List<Song> songs = songRepository.findByTagsNameIn(tags);
        List<SongDTO> dtos = new ArrayList<>();
        songs.forEach(song -> {
            dtos.add(SongDTO.toDTO(song));
        });
        return dtos;
    }

    @Transactional
    public List<SongDTO> search(String keyword) {
        List<Song> songs = songRepository.searchSongs(keyword);
        List<SongDTO> dtos = new ArrayList<>();
        songs.forEach(song -> {
            dtos.add(SongDTO.toDTO(song));
        });
        return dtos;
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

    public void addFavorite(String username, Long songId) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Song song = songRepository.findById(songId).orElseThrow();

        if (!user.getFavorites().contains(song)) {
            user.getFavorites().add(song);
            userRepository.save(user);
        }
    }

    public void removeFavorite(String username, Long songId) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Song song = songRepository.findById(songId).orElseThrow();

        if (user.getFavorites().contains(song)) {
            user.getFavorites().remove(song);
            userRepository.save(user);
        }
    }

    @Transactional
    public List<SongDTO> getFavorites(String username) {
        User u = userRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);
        return songRepository.findFavoritesByUser(u).stream()
            .map(SongDTO::toDTO)
            .toList();
    }
}
