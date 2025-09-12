package com.jelisavacl.njt.repository;

import com.jelisavacl.njt.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    Optional<Song> findById(Long id);
    List<Song> findByArtistName(String artistName);
    List<Song> findByArtistId(Long id);
    List<Song> findByGenreName(String genreName);
    List<Song> findByTagsNameIn(List<String> tagNames);
    List<Song> findTop10ByOrderByIdDesc();
    List<Song> findTop10ByOrderByViewCountDesc();
}
