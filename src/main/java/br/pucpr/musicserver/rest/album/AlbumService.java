package br.pucpr.musicserver.rest.album;

import br.pucpr.musicserver.lib.exception.BadRequestException;
import br.pucpr.musicserver.lib.exception.NotFoundException;
import br.pucpr.musicserver.rest.artists.Artist;
import br.pucpr.musicserver.rest.artists.ArtistsRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AlbumService {
    private AlbumRepository repository;
    private ArtistsRepository artistRepository;

    public AlbumService(AlbumRepository repository, ArtistsRepository artistRepository) {
        this.repository = repository;
        this.artistRepository = artistRepository;
    }

    public List<Album> searchByName(String name) {

        return repository.searchByName("%" + name + "%");
    }


    public List<Album> search(Long artist, Integer from, Integer to, String genre) {
        if (from == null) {
            from = 0;
        }
        if (to == null) {
            to = Integer.MAX_VALUE;
        }
        return repository.search(artist, from, to, genre);
    }

    public Album add(Album album) {
        if (album == null) {
            throw new BadRequestException("Album cannot be null!");
        }
        if (album.getId() != null) {
            throw new BadRequestException("Cannot save with an id!");
        }
        Set<Artist> artists = new HashSet<>();
        for (Artist artist : album.getArtists()) {
            Optional<Artist> existingArtist = Optional.ofNullable(artistRepository.findByName(artist.getName()));
            if (existingArtist.isPresent()) {
                artists.add(existingArtist.get());
            } else {
                artists.add(artistRepository.save(artist));
            }
        }
        album.setArtists(artists);
        return repository.save(album);
    }


    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException(id);
        }
        repository.deleteById(id);
    }
}
