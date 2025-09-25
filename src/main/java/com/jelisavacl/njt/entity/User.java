package com.jelisavacl.njt.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private boolean enabled = false;

    @Column
    private String confirmationToken;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Song> songs;

    @ManyToMany
    @JoinTable(
        name = "favorites",
        joinColumns = @JoinColumn(name = "song_id"),
        inverseJoinColumns = @JoinColumn (name = "user_id")
    )
    private Set<Song> favorites;

}
