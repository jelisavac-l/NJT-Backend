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

    @GetMapping
    public ResponseEntity<List<SongDTO>> getAllSongs() {
        return ResponseEntity.ok(songService.getAllSongs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable Long id) {
        return ResponseEntity.ok(songService.getSongById(id));
    }

    @PostMapping
    public ResponseEntity<Song> createSong(@RequestBody Song song) {
        String username = getCurrentUsername();
        User creator = userService.getUserByUsername(username);

        song.setCreatedBy(creator); // Force logged user as creator
        return ResponseEntity.ok(songService.createSong(song));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSong(@PathVariable Long id, @RequestBody Song updatedSong) {
        Song existingSong = songService.getSongById(id);
        String username = getCurrentUsername();

        if (!existingSong.getCreatedBy().getUsername().equals(username)) {
            return ResponseEntity.status(403).body("You can only update your own songs.");
        }

        return ResponseEntity.ok(songService.updateSong(id, updatedSong));
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
}
