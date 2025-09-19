package com.jelisavacl.njt.controller;

import com.jelisavacl.njt.dto.SongDTO;
import com.jelisavacl.njt.entity.Song;
import com.jelisavacl.njt.entity.User;
import com.jelisavacl.njt.service.SongService;
import com.jelisavacl.njt.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/songs")
public class SongController {

    private final SongService songService;
    private final UserService userService;

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @GetMapping("/latest")
    public ResponseEntity<List<SongDTO>> getLatestSongs() {
        return ResponseEntity.ok(songService.getLatestSongs());
    }

    @GetMapping("/popular")
    public ResponseEntity<List<SongDTO>> getPopularSongs() {
        return ResponseEntity.ok(songService.getMostPopular());
    }

//    @GetMapping
//    public ResponseEntity<List<SongDTO>> getAllSongs() {
//        return ResponseEntity.ok(songService.getAllSongs());
//    }
        @GetMapping
        public List<SongDTO> getSongs(@RequestParam(required = false) String search) {
            if (search != null && !search.isBlank()) {
                return songService.search(search);
            } else return songService.getAllSongs();
        }

    @GetMapping("/{id}")
    public ResponseEntity<SongDTO> getSongById(@PathVariable Long id) {
        Song s = songService.getSongById(id);
        s.setViewCount(s.getViewCount() + 1);
        songService.updateSong(s.getId(), s);
        return ResponseEntity.ok(SongDTO.toDTO(s));
    }

    @PostMapping
    public ResponseEntity<SongDTO> createSong(@RequestBody Song song) {
        String username = getCurrentUsername();
        User creator = userService.getUserByUsername(username);

        song.setCreatedBy(creator); // Force logged user as creator
        Song s = songService.createSong(song);
        return ResponseEntity.ok(SongDTO.toDTO(s));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSong(@PathVariable Long id, @RequestBody Song updatedSong) {
        Song existingSong = songService.getSongById(id);
        String username = getCurrentUsername();
        if (!existingSong.getCreatedBy().getUsername().equals(username)) {
            return ResponseEntity.status(403).body("You can only update your own songs.");
        }
        Song s = songService.updateSong(id, updatedSong);
        return ResponseEntity.ok(SongDTO.toDTO(s));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSong(@PathVariable Long id) {
        Song existingSong = songService.getSongById(id);
        String username = getCurrentUsername();

        if (!existingSong.getCreatedBy().getUsername().equals(username)) {
            return ResponseEntity.status(403).body("You can only delete your own songs.");
        }

        songService.deleteSong(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<SongDTO>> getSongsByUsername(@PathVariable String username) {
        return ResponseEntity.ok(songService.getSongsByUser(username));
    }

    @PostMapping("/favorites/{songId}")
    public void addFavorite(@PathVariable Long songId) {

        songService.addFavorite(getCurrentUsername(), songId);
    }

    @DeleteMapping("/favorites/{songId}")
    public void removeFavorite(@PathVariable Long songId) {
        songService.removeFavorite(getCurrentUsername(), songId);
    }

    @GetMapping("/favorites")
    public List<SongDTO> getFavorites() {
        return songService.getFavorites(getCurrentUsername());
    }
}
