package com.example.quileia.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.quileia.model.ConexionDeRed;
import com.example.quileia.model.DispositivoElectronico;
import com.example.quileia.repository.DispositivoElectronicoRepositorio;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DispositivoElectronicoServicioTest {

    @Mock
    private DispositivoElectronicoRepositorio dispositivoElectronicoRepositorio;

    @InjectMocks
    private DispositivoElectronicoServicio dispositivoElectronicoServicio;

    private List<DispositivoElectronico> dispositivosElectronicos;

    @Before
    public void setUp() {
        dispositivosElectronicos = Arrays.asList(new DispositivoElectronico("00:11:22:33:44:55", "Smartphone", "192.168.1.100", new ConexionDeRed(1, "Wi-Fi", "WPA2", "admin", "password"), true), new DispositivoElectronico("11:22:33:44:55:66", "Laptop", "192.168.1.101", new ConexionDeRed(2, "Ethernet", "None", "", ""), false));
    }

    @Test
    public void testGetAll_success() {
        when(dispositivoElectronicoRepositorio.getAll()).thenReturn(dispositivosElectronicos);
        List<DispositivoElectronico> result = dispositivoElectronicoServicio.getAll();
        assertEquals(dispositivosElectronicos, result);
    }

    @Test
    public void testGetDispositivo_validMac_success() {
        when(dispositivoElectronicoRepositorio.getDispositivo("00:11:22:33:44:55")).thenReturn(Optional.of(dispositivosElectronicos.get(0)));
        Optional<DispositivoElectronico> result = dispositivoElectronicoServicio.getDispositivo("00:11:22:33:44:55");
        assertTrue(result.isPresent());
        assertEquals(dispositivosElectronicos.get(0), result.get());
    }

    @Test
    public void testGetDispositivo_invalidMac_returnsEmpty() {
        when(dispositivoElectronicoRepositorio.getDispositivo("99:88:77:66:55:44")).thenReturn(Optional.empty());
        Optional<DispositivoElectronico> result = dispositivoElectronicoServicio.getDispositivo("99:88:77:66:55:44");
        assertFalse(result.isPresent());
    }

    @Test
    public void testSave_validDispositivo_success() {
        DispositivoElectronico dispositivoElectronico = new DispositivoElectronico("99:88:77:66:55:44", "Tablet", "192.168.1.102", new ConexionDeRed(1, "Wi-Fi", "WPA2", "admin", "password"), true);
        when(dispositivoElectronicoRepositorio.getDispositivo("99:88:77:66:55:44")).thenReturn(Optional.empty());
        when(dispositivoElectronicoRepositorio.filtroConexion(1)).thenReturn(Arrays.asList(dispositivosElectronicos.get(0)));
        when(dispositivoElectronicoRepositorio.save(dispositivoElectronico)).thenReturn(dispositivoElectronico);
        Optional<DispositivoElectronico> result = dispositivoElectronicoServicio.save(dispositivoElectronico);
        assertTrue(result.isPresent());
        assertEquals(dispositivoElectronico, result.get());
    }

    @Test
    public void testSave_dispositivoAlreadyExists_returnsEmpty() {
        DispositivoElectronico dispositivoElectronico = new DispositivoElectronico("00:11:22:33:44:55", "Smartphone", "192.168.1.100", new ConexionDeRed(1, "Wi-Fi", "WPA2", "admin", "password"), true);
        when(dispositivoElectronicoRepositorio.getDispositivo("00:11:22:33:44:55")).thenReturn(Optional.of(dispositivosElectronicos.get(0)));
        Optional<DispositivoElectronico> result = dispositivoElectronicoServicio.save(dispositivoElectronico);
        assertFalse(result.isPresent());
    }

    @Test
    public void testSave_maxDevicesPerConnectionReached_returnsEmpty() {
        DispositivoElectronico dispositivoElectronico = new DispositivoElectronico("99:88:77:66:55:44", "Tablet", "192.168.1.102", new ConexionDeRed(1, "Wi-Fi", "WPA2", "admin", "password"), true);
        when(dispositivoElectronicoRepositorio.getDispositivo("99:88:77:66:55:44")).thenReturn(Optional.empty());
        when(dispositivoElectronicoRepositorio.filtroConexion(1)).thenReturn(dispositivosElectronicos);
        Optional<DispositivoElectronico> result = dispositivoElectronicoServicio.save(dispositivoElectronico);
        assertFalse(result.isPresent());
    }


    @Test
    public void testUpdate_invalidDispositivo_returnsEmpty() {
        DispositivoElectronico dispositivoElectronico = new DispositivoElectronico("99:88:77:66:55:44", "Tablet", "192.168.1.102", new ConexionDeRed(1, "Wi-Fi", "WPA2", "admin", "password"), true);
        when(dispositivoElectronicoRepositorio.getDispositivo("99:88:77:66:55:44")).thenReturn(Optional.empty());
        Optional<DispositivoElectronico> result = dispositivoElectronicoServicio.update(dispositivoElectronico);
        assertFalse(result.isPresent());
    }

    @Test
    public void testUpdate_maxDevicesPerConnectionReached_returnsEmpty() {
        DispositivoElectronico dispositivoElectronico = new DispositivoElectronico("00:11:22:33:44:55", "Smartphone", "192.168.1.100", new ConexionDeRed(1, "Wi-Fi", "WPA2", "admin", "password"), true);
        List<DispositivoElectronico> dispositivosConConexion1 = Arrays.asList(
                new DispositivoElectronico("00:11:22:33:44:56", "Smartphone", "192.168.1.101", new ConexionDeRed(1, "Wi-Fi", "WPA2", "admin", "password"), true),
                new DispositivoElectronico("00:11:22:33:44:57", "Smartphone", "192.168.1.102", new ConexionDeRed(1, "Wi-Fi", "WPA2", "admin", "password"), true),
                new DispositivoElectronico("00:11:22:33:44:58", "Smartphone", "192.168.1.103", new ConexionDeRed(1, "Wi-Fi", "WPA2", "admin", "password"), true)
        );
        when(dispositivoElectronicoRepositorio.getDispositivo("00:11:22:33:44:55")).thenReturn(Optional.of(dispositivoElectronico));
        when(dispositivoElectronicoRepositorio.filtroConexion(1)).thenReturn(dispositivosConConexion1);
        Optional<DispositivoElectronico> result = dispositivoElectronicoServicio.update(dispositivoElectronico);
        assertTrue(result.isPresent());
    }


    @Test
    public void testDelete_validMac_success() {
        when(dispositivoElectronicoRepositorio.getDispositivo("00:11:22:33:44:55")).thenReturn(Optional.of(dispositivosElectronicos.get(0)));
        dispositivoElectronicoServicio.delete("00:11:22:33:44:55");
        verify(dispositivoElectronicoRepositorio, times(1)).delete("00:11:22:33:44:55");
    }

    @Test
    public void testDelete_invalidMac_returnsFalse() {
        when(dispositivoElectronicoRepositorio.getDispositivo("99:88:77:66:55:44")).thenReturn(Optional.empty());
        boolean result = dispositivoElectronicoServicio.delete("99:88:77:66:55:44");
        assertFalse(result);
        verify(dispositivoElectronicoRepositorio, never()).delete("99:88:77:66:55:44");
    }


}
