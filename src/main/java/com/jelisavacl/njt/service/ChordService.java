package com.jelisavacl.njt.service;

import com.jelisavacl.njt.entity.Chord;
import com.jelisavacl.njt.repository.ChordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ChordService {

    private final ChordRepository chordRepository;

    public List<Chord> getAllChords() {
        return chordRepository.findAll();
    }

    public List<Chord> getAllByNameContaining(String name) {
        return chordRepository.findByNameContainingIgnoreCase(name);
    }

}
