package com.quipux.music_app.controller;

import com.quipux.music_app.model.Playlist;
import com.quipux.music_app.service.PlaylistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/lists")
public class PlaylistController {
    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @PostMapping
    public ResponseEntity<Playlist> createPlaylist(@RequestBody Playlist playlist) {
        Playlist createdPlaylist = playlistService.createPlaylist(playlist);
        URI location = URI.create("/lists/" + createdPlaylist.getNombre());
        return ResponseEntity.created(location).body(createdPlaylist);
    }

    @GetMapping
    public List<Playlist> getAllPlaylists() {
        return playlistService.getAllPlaylists();
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<Playlist> getPlaylistByName(@PathVariable String nombre) {
        Playlist playlist = playlistService.getPlaylistByName(nombre);
        return ResponseEntity.ok(playlist);
    }

    @DeleteMapping("/{nombre}")
    public ResponseEntity<Void> deletePlaylistByName(@PathVariable String nombre) {
        playlistService.deletePlaylistByName(nombre);
        return ResponseEntity.noContent().build();
    }
}
