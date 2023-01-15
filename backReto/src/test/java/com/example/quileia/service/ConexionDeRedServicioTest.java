package com.example.quileia.service;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.quileia.model.ConexionDeRed;
import com.example.quileia.repository.ConexionDeRedRepositorio;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ConexionDeRedServicioTest {

    @Mock
    private ConexionDeRedRepositorio conexionDeRedRepositorio;

    @InjectMocks
    private ConexionDeRedServicio conexionDeRedServicio;

    private List<ConexionDeRed> conexionesDeRed;

    @Before
    public void setUp() {
        conexionesDeRed = Arrays.asList(new ConexionDeRed(1, "Wi-Fi", "WPA2", "admin", "password"), new ConexionDeRed(2, "Ethernet", "None", "", ""));
    }

    @Test
    public void testGetAll_success() {
        when(conexionDeRedRepositorio.getAll()).thenReturn(conexionesDeRed);
        List<ConexionDeRed> result = conexionDeRedServicio.getAll();
        assertEquals(conexionesDeRed, result);
    }

    @Test
    public void testGetConexion_validTipo_success() {
        when(conexionDeRedRepositorio.getConexion(1)).thenReturn(Optional.of(conexionesDeRed.get(0)));
        Optional<ConexionDeRed> result = conexionDeRedServicio.getConexion(1);
        assertTrue(result.isPresent());
        assertEquals(conexionesDeRed.get(0), result.get());
    }

    @Test
    public void testGetConexion_invalidTipo_returnsEmpty() {
        Optional<ConexionDeRed> result = conexionDeRedServicio.getConexion(3);
        assertFalse(result.isPresent());
    }

    @Test
    public void testSave_validConexion_success() {
        ConexionDeRed conexionDeRed = new ConexionDeRed(3, "Cellular", "None", "user", "pass");
        when(conexionDeRedRepositorio.save(conexionDeRed)).thenReturn(conexionDeRed);
        ConexionDeRed result = conexionDeRedServicio.save(conexionDeRed);
        assertEquals(conexionDeRed, result);
    }

    @Test
    public void testSave_invalidConexion_returnsInput() {
        ConexionDeRed conexionDeRed = new ConexionDeRed();
        ConexionDeRed result = conexionDeRedServicio.save(conexionDeRed);
        assertEquals(conexionDeRed, result);
    }

    @Test
    public void testUpdate_validConexion_success() {
        ConexionDeRed conexionDeRed = conexionesDeRed.get(0);
        conexionDeRed.setTipoDeCifrado("WPA3");
        conexionDeRed.setContrasenaDeConexion("newpass");
        when(conexionDeRedRepositorio.getConexion(1)).thenReturn(Optional.of(conexionDeRed));
        when(conexionDeRedRepositorio.save(conexionDeRed)).thenReturn(conexionDeRed);
        ConexionDeRed result = conexionDeRedServicio.update(conexionDeRed);
        assertEquals(conexionDeRed, result);
    }


    @Test
    public void testUpdate_invalidConexion_returnsInput() {
        ConexionDeRed conexionDeRed = new ConexionDeRed();
        ConexionDeRed result = conexionDeRedServicio.update(conexionDeRed);
        assertEquals(conexionDeRed, result);
    }

    @Test
    public void testDelete_validTipo_returnsTrue() {
        when(conexionDeRedRepositorio.getConexion(1)).thenReturn(Optional.of(conexionesDeRed.get(0)));
        boolean result = conexionDeRedServicio.delete(1);
        assertTrue(result);
    }

    @Test
    public void testDelete_invalidTipo_returnsFalse() {
        boolean result = conexionDeRedServicio.delete(3);
        assertFalse(result);
    }

}


