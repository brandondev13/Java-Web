package com.example.quileia.repository;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.quileia.model.ConexionDeRed;
import com.example.quileia.repository.interfaces.ConexionDeRedInterface;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ConexionDeRedRepositorioTest {
    @Mock
    private ConexionDeRedInterface conexionDeRedInterface;

    @InjectMocks
    private ConexionDeRedRepositorio conexionDeRedRepositorio;

    private List<ConexionDeRed> conexionesDeRed;
    private ConexionDeRed conexionDeRed;

    @Before
    public void setUp() {
        conexionDeRed = new ConexionDeRed(1, "WiFi");
        conexionesDeRed = Arrays.asList(conexionDeRed);
    }

    @Test
    public void testGetAll() {
        when(conexionDeRedInterface.findAll()).thenReturn(conexionesDeRed);
        List<ConexionDeRed> result = conexionDeRedRepositorio.getAll();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(conexionDeRed, result.get(0));
    }

    @Test
    public void testGetConexion_with_valid_tipo() {
        when(conexionDeRedInterface.findById(1)).thenReturn(Optional.of(conexionDeRed));
        Optional<ConexionDeRed> result = conexionDeRedRepositorio.getConexion(1);
        assertTrue(result.isPresent());
        assertEquals(conexionDeRed, result.get());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetConexion_with_null_tipo() {
        conexionDeRedRepositorio.getConexion(null);
    }

    @Test
    public void testSave() {
        when(conexionDeRedInterface.save(conexionDeRed)).thenReturn(conexionDeRed);
        ConexionDeRed result = conexionDeRedRepositorio.save(conexionDeRed);
        assertEquals(conexionDeRed, result);
    }

    @Test
    public void testDelete() {
        conexionDeRedRepositorio.delete(conexionDeRed);
    }
}

