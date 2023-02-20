package br.pucpr.musicserver.rest.artists;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(
        name="Artist.findByGenre",
        query="SELECT a FROM Artist a" +
                " JOIN a.genres g" +
                " WHERE g = :genre" +
                " ORDER BY a.name"
)
@NamedQuery(
        name="Artist.findByName",
        query="SELECT a FROM Artist a" +
                " WHERE a.name LIKE CONCAT('%', :name, '%')" +
                " ORDER BY a.name"
)
public class Artist {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String name;

    @ElementCollection
    @CollectionTable(name="howGenres", joinColumns = @JoinColumn(name="id"))
    @Column(name="genre")
    @NotEmpty
    private Set<String> genres;
}
