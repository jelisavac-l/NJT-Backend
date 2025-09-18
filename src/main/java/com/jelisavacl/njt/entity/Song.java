package com.jelisavacl.njt.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.Set;

@Entity
@Table(name = "songs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "beat_mark", nullable = false, length = 10)
    private String beatMark; // e.g., 4/4, 7/8, 11/8 (hehehe)

    @Column
    @ColumnDefault("0")
    private Integer viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @Lob
    @Column(nullable = false)
    private String lyrics; // Full song text with chords inline

    @Column(name = "youtube_link")
    private String youtubeLink;

    @ManyToMany
    @JoinTable(
        name = "song_tags",
        joinColumns = @JoinColumn(name = "song_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

//    @ManyToMany
//    @JoinTable(
//        name = "song_chords",
//        joinColumns = @JoinColumn(name = "song_id"),
//        inverseJoinColumns = @JoinColumn(name = "chord_id")
//    )
//    private Set<Chord> chords;
}
