package com.quipux.music_app.repository;

import com.quipux.music_app.model.Playlist;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PlaylistRepositoryTest {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Test
    public void testFindByNombre_ReturnsPlaylist_WhenPlaylistExists() {
        // Crear y guardar una playlist de prueba
        Playlist playlist = new Playlist();
        playlist.setNombre("Rock Clásico");
        playlist.setDescripcion("Una lista con clásicos del rock");
        playlistRepository.save(playlist);

        // Ejecutar la consulta personalizada
        Optional<Playlist> foundPlaylist = playlistRepository.findByNombre("Rock Clásico");

        // Verificar el resultado
        assertTrue(foundPlaylist.isPresent());
        assertEquals("Rock Clásico", foundPlaylist.get().getNombre());
        assertEquals("Una lista con clásicos del rock", foundPlaylist.get().getDescripcion());
    }

    @Test
    public void testFindByNombre_ReturnsEmpty_WhenPlaylistDoesNotExist() {
        // Ejecutar la consulta con un nombre que no existe
        Optional<Playlist> foundPlaylist = playlistRepository.findByNombre("No Existente");

        // Verificar que el resultado esté vacío
        assertFalse(foundPlaylist.isPresent());
    }
}

