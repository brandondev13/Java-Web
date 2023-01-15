package com.example.quileia.repository;


import com.example.quileia.repository.interfaces.DispositivoElectronicoInterface;
import com.example.quileia.model.DispositivoElectronico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DispositivoElectronicoRepositorio {

    @Autowired
    private DispositivoElectronicoInterface dispositivoElectronicoInterface;

    public List<DispositivoElectronico> getAll() {
        return (List<DispositivoElectronico>) dispositivoElectronicoInterface.findAll();
    }

    public Optional<DispositivoElectronico> getDispositivo(String mac) {
        return dispositivoElectronicoInterface.findByMac(mac);
    }

    public DispositivoElectronico save(DispositivoElectronico dispositivoElectronico) {
        return dispositivoElectronicoInterface.save(dispositivoElectronico);
    }

    public void delete(String mac) {
        dispositivoElectronicoInterface.deleteById(mac);
    }

    public List<DispositivoElectronico> filtroConexion(Integer id){
        return dispositivoElectronicoInterface.findByConexionActual_Tipo(id);
    }

}