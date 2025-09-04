package com.jelisavacl.njt.repository;

import com.jelisavacl.njt.entity.Artist;
import com.jelisavacl.njt.entity.Chord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    List<Artist> findByNameContainingIgnoreCase(String name);
}
