package com.example.quileia.repository.interfaces;

import com.example.quileia.model.DispositivoElectronico;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface DispositivoElectronicoInterface extends CrudRepository<DispositivoElectronico, String> {
    Optional<DispositivoElectronico> findByMac(String mac);
    List<DispositivoElectronico> findByConexionActual_Tipo(Integer id);
}
