package com.example.quileia.repository;

import com.example.quileia.repository.interfaces.ConexionDeRedInterface;
import com.example.quileia.model.ConexionDeRed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class ConexionDeRedRepositorio {
    @Autowired
    private ConexionDeRedInterface conexionDeRedInterface;
    public List<ConexionDeRed> getAll() {
        return (List<ConexionDeRed>) conexionDeRedInterface.findAll();
    }

    public Optional<ConexionDeRed> getConexion(Integer tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de conexión no puede ser nulo");
        }
        return conexionDeRedInterface.findById(tipo);
    }


    public ConexionDeRed save(ConexionDeRed conexionDeRed) {
        return conexionDeRedInterface.save(conexionDeRed);
    }

    public void delete(ConexionDeRed conexionDeRed) {
        conexionDeRedInterface.delete(conexionDeRed);
    }
}