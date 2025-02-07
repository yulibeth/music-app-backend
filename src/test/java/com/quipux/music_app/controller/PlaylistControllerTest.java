package com.quipux.music_app.controller;

import com.quipux.music_app.model.Playlist;
import com.quipux.music_app.repository.PlaylistRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class PlaylistControllerTest {

    @Autowired
    private PlaylistController playlistController;  // Controlador que estamos probando

    @MockBean
    private PlaylistRepository playlistRepository;  // Mockeamos el repositorio que interactúa con la base de datos

    // Test para añadir una nueva lista de reproducción
    @Test
    public void testAddPlaylist() {
        // Creamos una playlist de ejemplo
        Playlist playlist = new Playlist();
        playlist.setNombre("Playlist Test");
        playlist.setDescripcion("Descripción de prueba");

        // Simulamos la respuesta del repositorio al guardar la lista
        Mockito.when(playlistRepository.save(Mockito.any(Playlist.class))).thenReturn(playlist);

        // Realizamos la llamada al método del controlador
        ResponseEntity<?> response = playlistController.addPlaylist(playlist);

        // Verificamos que el código de estado sea 201 CREATED
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Verificamos que la respuesta contenga la playlist creada
        Playlist savedPlaylist = (Playlist) response.getBody();
        Assertions.assertNotNull(savedPlaylist);
        Assertions.assertEquals("Playlist Test", savedPlaylist.getNombre());
        Assertions.assertEquals("Descripción de prueba", savedPlaylist.getDescripcion());
    }

    // Test para obtener todas las listas de reproducción
    @Test
    public void testGetAllPlaylists() {
        // Creamos una lista de playlists
        Playlist playlist1 = new Playlist();
        playlist1.setNombre("Playlist 1");
        playlist1.setDescripcion("Descripción 1");

        Playlist playlist2 = new Playlist();
        playlist2.setNombre("Playlist 2");
        playlist2.setDescripcion("Descripción 2");

        List<Playlist> playlists = List.of(playlist1, playlist2);

        // Simulamos la respuesta del repositorio al obtener todas las listas
        Mockito.when(playlistRepository.findAll()).thenReturn(playlists);

        // Llamamos al controlador
        ResponseEntity<List<Playlist>> response = playlistController.getAllPlaylists();

        // Verificamos que el código de estado sea 200 OK
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verificamos que la respuesta contenga las playlists
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(2, response.getBody().size());
    }

    // Test para obtener una lista de reproducción por nombre
    @Test
    public void testGetPlaylistByName() {
        // Creamos una playlist de ejemplo
        Playlist playlist = new Playlist();
        playlist.setNombre("Playlist Test");
        playlist.setDescripcion("Descripción de prueba");

        // Simulamos la respuesta del repositorio al buscar una lista por nombre
        Mockito.when(playlistRepository.findByNombre("Playlist Test")).thenReturn(Optional.of(playlist));

        // Llamamos al controlador
        ResponseEntity<?> response = playlistController.getPlaylistByName("Playlist Test");

        // Verificamos que el código de estado sea 200 OK
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verificamos que la playlist devuelta no sea nula y que tenga los valores esperados
        Assertions.assertNotNull(response.getBody());
        Playlist returnedPlaylist = (Playlist) response.getBody();
        Assertions.assertEquals("Playlist Test", returnedPlaylist.getNombre());
        Assertions.assertEquals("Descripción de prueba", returnedPlaylist.getDescripcion());
    }

    // Test para borrar una lista de reproducción
    @Test
    public void testDeletePlaylist() {
        // Creamos una playlist de ejemplo
        Playlist playlist = new Playlist();
        playlist.setNombre("Playlist Test");
        playlist.setDescripcion("Descripción de prueba");

        // Simulamos la respuesta del repositorio al buscar la lista por nombre
        Mockito.when(playlistRepository.findByNombre("Playlist Test")).thenReturn(Optional.of(playlist));
        Mockito.doNothing().when(playlistRepository).delete(playlist);

        // Llamamos al controlador para borrar la playlist
        ResponseEntity<?> response = playlistController.deletePlaylist("Playlist Test");

        // Verificamos que el código de estado sea 204 No Content
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    // Test para el caso de que la playlist no exista al intentar obtenerla por nombre
    @Test
    public void testGetPlaylistByNameNotFound() {
        // Simulamos que el repositorio no encuentre la playlist
        Mockito.when(playlistRepository.findByNombre("NonExistentPlaylist")).thenReturn(Optional.empty());

        // Llamamos al controlador y verificamos el comportamiento
        ResponseEntity<?> response = playlistController.getPlaylistByName("NonExistentPlaylist");

        // Verificamos que el código de estado sea 404 NOT FOUND
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals("La lista de reproducción no fue encontrada.", response.getBody());
    }

    // Test para el caso de que la playlist no exista al intentar borrarla
    @Test
    public void testDeletePlaylistNotFound() {
        // Simulamos que el repositorio no encuentre la playlist
        Mockito.when(playlistRepository.findByNombre("NonExistentPlaylist")).thenReturn(Optional.empty());

        // Llamamos al controlador para borrar la playlist
        ResponseEntity<?> response = playlistController.deletePlaylist("NonExistentPlaylist");

        // Verificamos que el código de estado sea 404 NOT FOUND
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals("La lista de reproducción no fue encontrada.", response.getBody());
    }
}
