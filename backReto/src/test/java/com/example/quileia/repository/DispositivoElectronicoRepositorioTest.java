package com.example.quileia.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import java.util.*;
import com.example.quileia.model.ConexionDeRed;
import com.example.quileia.model.DispositivoElectronico;
import com.example.quileia.repository.interfaces.DispositivoElectronicoInterface;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DispositivoElectronicoRepositorioTest {

    @Mock
    private DispositivoElectronicoInterface dispositivoElectronicoInterface;

    @InjectMocks
    private DispositivoElectronicoRepositorio dispositivoElectronicoRepositorio;

    private List<DispositivoElectronico> dispositivosElectronicos;
    public ConexionDeRed conexionDeRed;

    @Before
    public void setUp() {
        // Initialize test data
        conexionDeRed = new ConexionDeRed(1, "Wi-Fi");
        dispositivosElectronicos = Arrays.asList(
                new DispositivoElectronico("AA:BB:CC:DD:EE:FF", "Smartphone", "192.168.1.101", conexionDeRed),
                new DispositivoElectronico("11:22:33:44:55:66", "Tablet", "192.168.1.102", conexionDeRed)
        );
    }

    @Test
    public void testGetAll_validData_success() {
        // Arrange
        when(dispositivoElectronicoInterface.findAll()).thenReturn(dispositivosElectronicos);

        // Act
        List<DispositivoElectronico> result = dispositivoElectronicoRepositorio.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(dispositivosElectronicos, result);
    }

    @Test
    public void testGetDispositivo_validData_success() {
        // Arrange
        when(dispositivoElectronicoInterface.findByMac("AA:BB:CC:DD:EE:FF")).thenReturn(Optional.of(dispositivosElectronicos.get(0)));

        // Act
        Optional<DispositivoElectronico> result = dispositivoElectronicoRepositorio.getDispositivo("AA:BB:CC:DD:EE:FF");

        // Assert
        assertTrue(result.isPresent());
        assertNotNull(result.get());
        assertEquals(dispositivosElectronicos.get(0), result.get());
    }

    @Test
    public void testGetDispositivo_invalidData_failure() {
        // Arrange
        when(dispositivoElectronicoInterface.findByMac("AA:BB:CC:DD:EE:FF")).thenReturn(Optional.empty());

        // Act
        Optional<DispositivoElectronico> result = dispositivoElectronicoRepositorio.getDispositivo("AA:BB:CC:DD:EE:FF");

        // Assert
        assertFalse(result.isPresent());
    }
}



