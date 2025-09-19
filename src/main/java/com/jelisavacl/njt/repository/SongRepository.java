package com.jelisavacl.njt.repository;

import com.jelisavacl.njt.entity.Song;
import com.jelisavacl.njt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    Optional<Song> findById(Long id);
    List<Song> findByCreatedBy(User user);
    List<Song> findByArtistName(String artistName);
    List<Song> findByArtistId(Long id);
    List<Song> findByGenreName(String genreName);
    List<Song> findByTagsNameIn(List<String> tagNames);
    List<Song> findTop10ByOrderByIdDesc();
    List<Song> findTop10ByOrderByViewCountDesc();

    @Query("""
        SELECT s FROM Song s
        JOIN s.artist a
        JOIN s.genre g
        WHERE LOWER(s.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(a.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(g.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    List<Song> searchSongs(@Param("keyword") String keyword);
}
