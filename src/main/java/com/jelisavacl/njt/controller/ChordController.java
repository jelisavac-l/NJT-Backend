//package com.jelisavacl.njt.controller;
//
//import com.jelisavacl.njt.entity.Chord;
//import com.jelisavacl.njt.service.ChordService;
//import lombok.AllArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@AllArgsConstructor
//@RestController
//@RequestMapping("/api/chords")
//public class ChordController {
//
//    private final ChordService chordService;
//
//    @GetMapping
//    public ResponseEntity<List<Chord>> getAllChords() {
//        List<Chord> chords = chordService.getAllChords();
//        return new ResponseEntity<>(chords, HttpStatus.OK);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Chord> getChordById(@PathVariable Long id) {
//        return chordService.getChordById(id)
//            .map(chord -> new ResponseEntity<>(chord, HttpStatus.OK))
//            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//
//}
