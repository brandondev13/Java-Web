package com.example.quileia.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.quileia.model.ConexionDeRed;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.quileia.model.DispositivoElectronico;
import com.example.quileia.service.DispositivoElectronicoServicio;

@RunWith(MockitoJUnitRunner.class)
public class DispositivoElectronicoControladorTest {

    @Mock
    private DispositivoElectronicoServicio dispositivoElectronicoServicio;

    @InjectMocks
    private DispositivoElectronicoControlador dispositivoElectronicoControlador;
    private ConexionDeRed conexion1;
    private ConexionDeRed conexion2;
    private DispositivoElectronico dispositivo1;
    private DispositivoElectronico dispositivo2;


    @Before
    public void setUp() {
        conexion1 = new ConexionDeRed("Wi-Fi", "WPA");
        conexion2 = new ConexionDeRed("Bluetooth", "LTK");
        dispositivo1 = new DispositivoElectronico("A1:B2:C3:D4:E5:F6", "Samsung", "192.168.1.101", conexion1);
        dispositivo2 = new DispositivoElectronico("F6:E5:D4:C3:B2:A1", "Apple", "192.168.1.101", conexion2);
    }

    @Test
    public void testGetAll_success() {
        when(dispositivoElectronicoServicio.getAll()).thenReturn(Arrays.asList(dispositivo1, dispositivo2));
        List<DispositivoElectronico> result = dispositivoElectronicoControlador.getAll();
        assertEquals(2, result.size());
        assertEquals(dispositivo1, result.get(0));
        assertEquals(dispositivo2, result.get(1));
    }

    @Test
    public void testGetDispositivo_validMac_success() {
        when(dispositivoElectronicoServicio.getDispositivo("A1:B2:C3:D4:E5:F6")).thenReturn(Optional.of(dispositivo1));
        Optional<DispositivoElectronico> result = dispositivoElectronicoControlador.getDispositivo("A1:B2:C3:D4:E5:F6");
        assertTrue(result.isPresent());
        assertEquals(dispositivo1, result.get());
    }

    @Test
    public void testGetDispositivo_invalidMac_returnsEmpty() {
        when(dispositivoElectronicoServicio.getDispositivo("G7:H8:I9:J0")).thenReturn(Optional.empty());
        Optional<DispositivoElectronico> result = dispositivoElectronicoControlador.getDispositivo("G7:H8:I9:J0");
        assertFalse(result.isPresent());
    }

    @Test
    public void testFiltro_validId_success() {
        when(dispositivoElectronicoServicio.filtro(1)).thenReturn(Arrays.asList(dispositivo1));
        List<DispositivoElectronico> result = dispositivoElectronicoControlador.filtro(1);
        assertEquals(1, result.size());
        assertEquals(dispositivo1, result.get(0));
    }

    @Test
    public void testFiltro_invalidId_returnsEmpty() {
        when(dispositivoElectronicoServicio.filtro(3)).thenReturn(Arrays.asList());
        List<DispositivoElectronico> result = dispositivoElectronicoControlador.filtro(3);
        assertEquals(0, result.size());
    }

    @Test
    public void testSave_validDispositivo_success() {
        when(dispositivoElectronicoServicio.save(dispositivo1)).thenReturn(Optional.of(dispositivo1));
        Optional<DispositivoElectronico> result = dispositivoElectronicoControlador.save(dispositivo1);
        assertTrue(result.isPresent());
        assertEquals(dispositivo1, result.get());
    }

    @Test
    public void testSave_invalidDispositivo_throwsBadRequest() {
        try {
            DispositivoElectronico invalidDispositivo = new DispositivoElectronico(null, null, null, null);
            dispositivoElectronicoControlador.save(invalidDispositivo);
        } catch (ResponseStatusException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testUpdate_validDispositivo_success() {
        when(dispositivoElectronicoServicio.update(dispositivo1)).thenReturn(Optional.of(dispositivo1));
        Optional<DispositivoElectronico> result = dispositivoElectronicoControlador.update(dispositivo1);
        assertTrue(result.isPresent());
        assertEquals(dispositivo1, result.get());
    }

    @Test
    public void testUpdate_invalidDispositivo_throwsBadRequest() {
        try {

            DispositivoElectronico invalidDispositivo = new DispositivoElectronico(null, null, null, null);
            dispositivoElectronicoControlador.update(invalidDispositivo);
        } catch (ResponseStatusException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDelete_validMac_success() {
        when(dispositivoElectronicoServicio.delete("A1:B2:C3:D4:E5:F6")).thenReturn(true);
        boolean result = dispositivoElectronicoControlador.delete("A1:B2:C3:D4:E5:F6");
        assertTrue(result);
    }

    @Test
    public void testDelete_invalidMac_returnsFalse() {
        when(dispositivoElectronicoServicio.delete("G7:H8:I9:J0")).thenReturn(false);
        boolean result = dispositivoElectronicoControlador.delete("G7:H8:I9:J0");
        assertFalse(result);
    }
}