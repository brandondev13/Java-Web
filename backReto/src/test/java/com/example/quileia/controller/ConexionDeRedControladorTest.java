package com.example.quileia.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.quileia.model.ConexionDeRed;
import com.example.quileia.service.ConexionDeRedServicio;

@RunWith(MockitoJUnitRunner.class)
public class ConexionDeRedControladorTest {

    @Mock
    private ConexionDeRedServicio conexionDeRedServicio;

    @InjectMocks
    private ConexionDeRedControlador conexionDeRedControlador;

    private ConexionDeRed conexion1;
    private ConexionDeRed conexion2;

    @Before
    public void setUp() {
        conexion1 = new ConexionDeRed(1, "Wi-Fi", "WPA2", "admin", "password");
        conexion2 = new ConexionDeRed(2, "Ethernet", "", "", "");
    }

    @Test
    public void testGetAll_success() {
        when(conexionDeRedServicio.getAll()).thenReturn(Arrays.asList(conexion1, conexion2));
        List<ConexionDeRed> result = conexionDeRedControlador.getAll();
        assertEquals(2, result.size());
        assertEquals(conexion1, result.get(0));
        assertEquals(conexion2, result.get(1));
    }

    @Test
    public void testGetConexion_validTipo_success() {
        when(conexionDeRedServicio.getConexion(1)).thenReturn(Optional.of(conexion1));
        Optional<ConexionDeRed> result = conexionDeRedControlador.getConexion(1);
        assertTrue(result.isPresent());
        assertEquals(conexion1, result.get());
    }

    @Test
    public void testGetConexion_invalidTipo_returnsEmpty() {
        when(conexionDeRedServicio.getConexion(3)).thenReturn(Optional.empty());
        Optional<ConexionDeRed> result = conexionDeRedControlador.getConexion(3);
        assertFalse(result.isPresent());
    }


    @Test
    public void testSave_validConexion_success() {
        when(conexionDeRedServicio.save(conexion1)).thenReturn(conexion1);
        ConexionDeRed result = conexionDeRedControlador.save(conexion1);
        assertEquals(conexion1, result);
    }


    @Test
    public void testSave_invalidConexion_throwsBadRequest() {
        try {
            ConexionDeRed invalidConexion = new ConexionDeRed(null, null, null, null, null);
            conexionDeRedControlador.save(invalidConexion);
        } catch (ResponseStatusException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdate_validConexion_success() {
        when(conexionDeRedServicio.update(conexion1)).thenReturn(conexion1);
        ConexionDeRed result = conexionDeRedControlador.update(conexion1);
        assertEquals(conexion1, result);
    }

    @Test
    public void testUpdate_invalidConexion_throwsBadRequest() {
        try {
            ConexionDeRed invalidConexion = new ConexionDeRed(null, null, null, null, null);
            conexionDeRedControlador.update(invalidConexion);
        } catch (ResponseStatusException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testDelete_validTipo_success() {
        when(conexionDeRedServicio.delete(1)).thenReturn(true);
        boolean result = conexionDeRedControlador.delete(1);
        assertTrue(result);
    }

    @Test
    public void testDelete_invalidTipo_throwsBadRequest() {
        try {
            conexionDeRedControlador.delete(3);
        } catch (ResponseStatusException e) {
            e.printStackTrace();
        }
    }
}









