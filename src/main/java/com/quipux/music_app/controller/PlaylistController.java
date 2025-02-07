package com.quipux.music_app.controller;

import com.quipux.music_app.model.Playlist;
import com.quipux.music_app.repository.PlaylistRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lists")
public class PlaylistController {

    private final PlaylistRepository playlistRepository;

    public PlaylistController(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    // POST /lists - Añadir una nueva lista de reproducción
    @PostMapping
    public ResponseEntity<?> addPlaylist(@RequestBody Playlist playlist) {
        if (playlist.getNombre() == null || playlist.getNombre().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El nombre de la lista no puede ser nulo o vacío.");
        }

        Playlist savedPlaylist = playlistRepository.save(playlist);
        URI location = URI.create("/lists/" + savedPlaylist.getNombre());
        return ResponseEntity.created(location).body(savedPlaylist);
    }

    // GET /lists - Ver todas las listas de reproducción existentes
    @GetMapping
    public ResponseEntity<List<Playlist>> getAllPlaylists() {
        List<Playlist> playlists = playlistRepository.findAll();
        return ResponseEntity.ok(playlists);
    }

    // GET /lists/{listName} - Ver descripción de una lista de reproducción seleccionada
    @GetMapping("/{listName}")
    public ResponseEntity<?> getPlaylistByName(@PathVariable String listName) {
        Optional<Playlist> playlist = playlistRepository.findByNombre(listName);
        if (playlist.isPresent()) {
            return ResponseEntity.ok(playlist.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La lista de reproducción no fue encontrada.");
        }
    }

    // DELETE /lists/{listName} - Borrar una lista de reproducción
    @DeleteMapping("/{listName}")
    public ResponseEntity<?> deletePlaylist(@PathVariable String listName) {
        Optional<Playlist> playlist = playlistRepository.findByNombre(listName);
        if (playlist.isPresent()) {
            playlistRepository.delete(playlist.get());
            return ResponseEntity.noContent().build();  // 204 No Content
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La lista de reproducción no fue encontrada.");
        }
    }
}