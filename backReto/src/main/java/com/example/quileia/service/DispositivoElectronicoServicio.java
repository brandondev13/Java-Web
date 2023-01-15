package com.example.quileia.service;

import com.example.quileia.model.DispositivoElectronico;
import com.example.quileia.repository.DispositivoElectronicoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DispositivoElectronicoServicio {

    @Autowired
    private DispositivoElectronicoRepositorio dispositivoElectronicoRepositorio;

    public List<DispositivoElectronico> getAll() {
        return dispositivoElectronicoRepositorio.getAll();
    }

    public Optional<DispositivoElectronico> getDispositivo(String mac) {
        return dispositivoElectronicoRepositorio.getDispositivo(mac);
    }

    public Optional<DispositivoElectronico> save(DispositivoElectronico dispositivoElectronico) {
        if (dispositivoElectronico.getMac() == null || dispositivoElectronico.getTipo() == null || dispositivoElectronico.getIpAsignada() == null || dispositivoElectronico.getConexionActual() == null) {
            return Optional.empty();
        } else {
            Optional<DispositivoElectronico> buscarDispositivo = dispositivoElectronicoRepositorio.getDispositivo(dispositivoElectronico.getMac());
            if (buscarDispositivo.isEmpty()) {
                int maximoDeRegistros = 0;
                List<DispositivoElectronico> buscarIdConexion = dispositivoElectronicoRepositorio.filtroConexion(dispositivoElectronico.getConexionActual().getTipo());
                for (DispositivoElectronico indexDispositivo : buscarIdConexion) {
                    if (indexDispositivo.getConexionActual().getTipo().equals(dispositivoElectronico.getConexionActual().getTipo())) {
                        maximoDeRegistros++;
                    }
                }
                if (maximoDeRegistros < 3) {
                    return Optional.ofNullable(dispositivoElectronicoRepositorio.save(dispositivoElectronico));
                } else {
                    return
                            Optional.empty();
                }
            }
            else {
                return Optional.empty();
            }
        }
    }

    public Optional<DispositivoElectronico> update(DispositivoElectronico dispositivoElectronico) {

        if (dispositivoElectronico.getMac() != null) {
            Optional<DispositivoElectronico> obtener = dispositivoElectronicoRepositorio.getDispositivo(dispositivoElectronico.getMac());
            if (obtener.isPresent()) {
                if (dispositivoElectronico.getTipo() != null) {
                    obtener.get().setTipo(dispositivoElectronico.getTipo());
                }
                if (dispositivoElectronico.getConectadoActualmente() != null) {
                    obtener.get().setConectadoActualmente(dispositivoElectronico.getConectadoActualmente());
                }
                if (dispositivoElectronico.getConexionActual() != null) {
                    int maximoDeRegistros = 0;
                    List<DispositivoElectronico> buscarIdConexion = dispositivoElectronicoRepositorio.filtroConexion(dispositivoElectronico.getConexionActual().getTipo());
                    for (DispositivoElectronico indexDispositivo : buscarIdConexion) {
                        if (indexDispositivo.getConexionActual().getTipo().equals(dispositivoElectronico.getConexionActual().getTipo())) {
                            maximoDeRegistros++;
                        }
                    }
                    if (maximoDeRegistros < 3) {
                        obtener.get().setConexionActual(dispositivoElectronico.getConexionActual());
                    }

                }
                if (dispositivoElectronico.getIpAsignada() != null) {
                    obtener.get().setIpAsignada(dispositivoElectronico.getIpAsignada());
                }
                dispositivoElectronicoRepositorio.save(obtener.get());
                return Optional.of(dispositivoElectronico);
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    public boolean delete(String mac) {
        Optional<DispositivoElectronico> obtener = getDispositivo(mac);
        if (obtener.isPresent()) {
            dispositivoElectronicoRepositorio.delete(mac);
            return true;
        }
        return false;
    }

    public List<DispositivoElectronico> filtro(Integer id){
        return dispositivoElectronicoRepositorio.filtroConexion(id);
    }
}