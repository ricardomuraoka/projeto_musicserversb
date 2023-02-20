package br.pucpr.musicserver.rest.album;

import br.pucpr.musicserver.rest.artists.Artist;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(
        name = "Album.searchByName",
        query = "SELECT a FROM Album a WHERE a.name LIKE CONCAT('%', :name, '%')"
)
@NamedQuery(
        name = "Album.search",
        query = "SELECT a FROM Album a LEFT JOIN a.artists artist " +
                "WHERE COALESCE(artist.id, null) = COALESCE(:artist, artist.id) " +
                "AND a.year >= :from AND a.year <= :to " +
                "AND (:genre IS NULL OR EXISTS (SELECT 1 FROM Artist ar JOIN ar.genres g WHERE ar.id = artist.id AND g = :genre))"
)




public class Album {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    private int year;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "artist_albums",
            joinColumns = {@JoinColumn(name = "album_id")},
            inverseJoinColumns = {@JoinColumn(name = "artist_id")})
    @ToString.Exclude
    private Set<Artist> artists;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return year == album.year && Objects.equals(id, album.id) && Objects.equals(name, album.name) && Objects.equals(artists, album.artists);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, year, artists);
    }
}
