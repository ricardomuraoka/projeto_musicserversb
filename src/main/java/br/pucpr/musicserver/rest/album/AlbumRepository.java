package br.pucpr.musicserver.rest.album;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AlbumRepository extends JpaRepository <Album, Long>{
    public List<Album> searchByName(String name);
    public List<Album> search(Long artist, Integer from, Integer to, String genre);
}