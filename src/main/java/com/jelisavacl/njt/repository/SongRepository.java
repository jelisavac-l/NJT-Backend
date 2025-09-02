package com.jelisavacl.njt.repository;

import com.jelisavacl.njt.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    List<Song> findByArtistName(String artistName);
    List<Song> findByGenreName(String genreName);
    List<Song> findByTagsNameIn(List<String> tagNames);
}
