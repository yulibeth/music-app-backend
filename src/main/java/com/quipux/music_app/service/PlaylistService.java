package com.quipux.music_app.service;

import com.quipux.music_app.model.Playlist;
import com.quipux.music_app.repository.PlaylistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {
    private final PlaylistRepository playlistRepository;

    public PlaylistService(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    public Playlist createPlaylist(Playlist playlist) {
        if (playlist.getNombre() == null || playlist.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la lista no puede ser nulo o vacío");
        }
        return playlistRepository.save(playlist);
    }

    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    public Playlist getPlaylistByName(String nombre) {
        return playlistRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Lista no encontrada"));
    }

    public void deletePlaylistByName(String nombre) {
        Playlist playlist = getPlaylistByName(nombre);
        playlistRepository.delete(playlist);
    }
}

