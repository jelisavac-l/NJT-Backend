package com.jelisavacl.njt.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chords")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Lob
    private String diagram; // Optional ASCII or JSON representation
}
