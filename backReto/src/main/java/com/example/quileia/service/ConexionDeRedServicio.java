package com.example.quileia.service;

import com.example.quileia.model.ConexionDeRed;
import com.example.quileia.repository.ConexionDeRedRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConexionDeRedServicio {

    @Autowired
    private ConexionDeRedRepositorio conexionDeRedRepositorio;

    public List<ConexionDeRed> getAll() {
        return conexionDeRedRepositorio.getAll();
    }

    public Optional<ConexionDeRed> getConexion(Integer tipo) {
        if (tipo == null) return Optional.empty();
        return conexionDeRedRepositorio.getConexion(tipo);
    }

    public ConexionDeRed save(ConexionDeRed conexionDeRed) {
        if (conexionDeRed.getTipo() == null) {
            if (conexionDeRed.getNombre() == null || conexionDeRed.getTipoDeCifrado() == null || conexionDeRed.getUsuarioConexion() == null || conexionDeRed.getContrasenaDeConexion() == null) {
                return conexionDeRed;
            } else {
                return conexionDeRedRepositorio.save(conexionDeRed);
            }
        } else {
            Optional<ConexionDeRed> buscarConexion = conexionDeRedRepositorio.getConexion(conexionDeRed.getTipo());
            if (buscarConexion.isEmpty()) {
                if (conexionDeRed.getNombre() == null || conexionDeRed.getTipoDeCifrado() == null || conexionDeRed.getUsuarioConexion() == null || conexionDeRed.getContrasenaDeConexion() == null) {
                    return conexionDeRed;
                } else {
                    return conexionDeRedRepositorio.save(conexionDeRed);
                }
            } else {
                return conexionDeRed;
            }
        }
    }


    public ConexionDeRed update(ConexionDeRed conexionDeRed) {
        if (conexionDeRed.getTipo() != null) {
            Optional<ConexionDeRed> obtener = conexionDeRedRepositorio.getConexion(conexionDeRed.getTipo());
            if (obtener.isPresent()) {
                if (conexionDeRed.getNombre() != null) {
                    obtener.get().setNombre(conexionDeRed.getNombre());
                }
                if (conexionDeRed.getTipoDeCifrado() != null) {
                    obtener.get().setTipoDeCifrado(conexionDeRed.getTipoDeCifrado());
                }
                if (conexionDeRed.getUsuarioConexion() != null) {
                    obtener.get().setUsuarioConexion(conexionDeRed.getUsuarioConexion());
                }
                if (conexionDeRed.getContrasenaDeConexion() != null) {
                    obtener.get().setContrasenaDeConexion(conexionDeRed.getContrasenaDeConexion());
                }
                return conexionDeRedRepositorio.save(obtener.get());
            } else {
                return conexionDeRed;
            }
        } else {
            return conexionDeRed;
        }
    }

    public boolean delete(Integer tipo) {
        if (tipo == null) return false;
        Optional<ConexionDeRed> obtener = getConexion(tipo);
        if (obtener.isPresent()) {
            conexionDeRedRepositorio.delete(obtener.get());
            return true;
        }
        return false;
    }
}