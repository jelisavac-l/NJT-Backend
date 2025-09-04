package com.jelisavacl.njt.controller;

import com.jelisavacl.njt.entity.Artist;
import com.jelisavacl.njt.entity.Chord;
import com.jelisavacl.njt.service.ArtistService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/artists")
public class ArtistController {

    private final ArtistService artistService;

    @PostMapping
    public ResponseEntity<Artist> createArtist(@RequestBody Artist artist) {
        return new ResponseEntity<>(artistService.createArtist(artist), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Artist>> getAllArtists() {
        return new ResponseEntity<>(artistService.getAllArtists(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Artist>> getAllByNameContaining(
        @RequestParam(required = false, defaultValue = "") String name) {

        List<Artist> artists = artistService.getAllByNameContaining(name);
        return ResponseEntity.ok(artists);
    }



}
