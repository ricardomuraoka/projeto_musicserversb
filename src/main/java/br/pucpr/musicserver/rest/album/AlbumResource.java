package br.pucpr.musicserver.rest.album;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/albuns")
public class AlbumResource {
    private AlbumService service;

    public AlbumResource(AlbumService service) {
        this.service = service;
    }

    @GetMapping("/searchByName")
    @Transactional
    public List<Album> searchByName(@RequestParam(value = "name", required = false) String name) {
        return service.searchByName(name);
    }

    @GetMapping("/search")
    @Transactional
    public List<Album> search(
            @RequestParam(value = "artist", required = false) Long artist,
            @RequestParam(value = "from", required = false) Integer from,
            @RequestParam(value = "to", required = false) Integer to,
            @RequestParam(value = "genre", required = false) String genre
    ) {
        return service.search(artist, from, to, genre);
    }

    @PostMapping
    @Transactional
    @SecurityRequirement(name="AuthServer")
    @RolesAllowed({"ADMIN"})
    public Album add(@Valid @RequestBody Album album) {
        return service.add(album);
    }

    @DeleteMapping("{id}")
    @Transactional
    @SecurityRequirement(name="AuthServer")
    @RolesAllowed({"ADMIN"})
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }
}
