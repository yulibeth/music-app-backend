package com.quipux.music_app.service;

import com.quipux.music_app.model.Playlist;
import com.quipux.music_app.repository.PlaylistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)  // Integrar Mockito con JUnit 5
public class PlaylistServiceTest {

    @Mock
    private PlaylistRepository playlistRepository;  // Mockear el repositorio

    @InjectMocks
    private PlaylistService playlistService;  // Instanciar el servicio con el repositorio mockeado

    private Playlist playlist;

    @BeforeEach
    public void setUp() {
        playlist = new Playlist();
        playlist.setNombre("Mi lista de reproducción");
        playlist.setDescripcion("Una lista genial");
    }

    @Test
    public void testCreatePlaylistValidName() {
        // Configurar el comportamiento del repositorio mockeado
        when(playlistRepository.save(playlist)).thenReturn(playlist);

        // Llamar al método de servicio
        Playlist createdPlaylist = playlistService.createPlaylist(playlist);

        // Verificar que el repositorio fue llamado
        verify(playlistRepository, times(1)).save(playlist);

        // Verificar que el resultado no es nulo y que el nombre es correcto
        assertNotNull(createdPlaylist);
        assertEquals("Mi lista de reproducción", createdPlaylist.getNombre());
    }

    @Test
    public void testCreatePlaylistInvalidName() {
        // Configurar el playlist con un nombre vacío
        playlist.setNombre("");

        // Llamar al método de servicio y verificar que se lanza la excepción
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            playlistService.createPlaylist(playlist);
        });

        // Verificar el mensaje de la excepción
        assertEquals("El nombre de la lista no puede ser nulo o vacío", thrown.getMessage());
    }

    @Test
    public void testGetAllPlaylists() {
        // Simular una lista de playlists
        when(playlistRepository.findAll()).thenReturn(List.of(playlist));

        // Llamar al método de servicio
        List<Playlist> playlists = playlistService.getAllPlaylists();

        // Verificar que se devuelve la lista correcta
        assertNotNull(playlists);
        assertEquals(1, playlists.size());
        assertEquals("Mi lista de reproducción", playlists.get(0).getNombre());
    }

    @Test
    public void testGetPlaylistByName() {
        // Simular que la playlist existe
        when(playlistRepository.findByNombre("Mi lista de reproducción")).thenReturn(Optional.of(playlist));

        // Llamar al método de servicio
        Playlist foundPlaylist = playlistService.getPlaylistByName("Mi lista de reproducción");

        // Verificar que se encontró la playlist
        assertNotNull(foundPlaylist);
        assertEquals("Mi lista de reproducción", foundPlaylist.getNombre());
    }

    @Test
    public void testGetPlaylistByNameNotFound() {
        // Simular que la playlist no existe
        when(playlistRepository.findByNombre("Lista no encontrada")).thenReturn(Optional.empty());

        // Llamar al método de servicio y verificar que lanza la excepción
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            playlistService.getPlaylistByName("Lista no encontrada");
        });

        // Verificar el mensaje de la excepción
        assertEquals("Lista no encontrada", thrown.getMessage());
    }

    @Test
    public void testDeletePlaylistByName() {
        // Simular que la playlist existe
        when(playlistRepository.findByNombre("Mi lista de reproducción")).thenReturn(Optional.of(playlist));

        // Llamar al método de servicio
        playlistService.deletePlaylistByName("Mi lista de reproducción");

        // Verificar que se eliminó la playlist
        verify(playlistRepository, times(1)).delete(playlist);
    }

    @Test
    public void testDeletePlaylistByNameNotFound() {
        // Simular que la playlist no existe
        when(playlistRepository.findByNombre("Lista no encontrada")).thenReturn(Optional.empty());

        // Llamar al método de servicio y verificar que lanza la excepción
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            playlistService.deletePlaylistByName("Lista no encontrada");
        });

        // Verificar el mensaje de la excepción
        assertEquals("Lista no encontrada", thrown.getMessage());
    }
}
