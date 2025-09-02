package com.jelisavacl.njt.repository;

import com.jelisavacl.njt.entity.Chord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChordRepository extends JpaRepository<Chord, Long> {
}
