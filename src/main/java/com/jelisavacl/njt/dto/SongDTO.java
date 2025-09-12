package com.jelisavacl.njt.dto;

import com.jelisavacl.njt.entity.Song;
import com.jelisavacl.njt.entity.Tag;
import lombok.*;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SongDTO {

    private Long id;
    private String title;
    private String beatMark;
    private String lyrics;
    private String youtubeLink;
    private Integer viewCount;

    // Flattened references
    private String artistName;
    private String genreName;
    private String createdByUsername;

    private Set<String> tags;

    public SongDTO toDTO(Song song) {
        return SongDTO.builder()
            .id(song.getId())
            .title(song.getTitle())
            .beatMark(song.getBeatMark())
            .lyrics(song.getLyrics())
            .youtubeLink(song.getYoutubeLink())
            .artistName(song.getArtist().getName())
            .genreName(song.getGenre().getName())
            .createdByUsername(song.getCreatedBy().getUsername())
            .tags(song.getTags().stream().map(Tag::getName).collect(Collectors.toSet()))
            .viewCount(song.getViewCount())
            .build();
    }
}
