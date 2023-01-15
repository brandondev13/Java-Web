package com.example.quileia.controller;

import com.example.quileia.model.ConexionDeRed;
import com.example.quileia.service.ConexionDeRedServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin("*")
@RequestMapping("/conexiondered")
public class ConexionDeRedControlador {

    @Autowired
    private ConexionDeRedServicio conexionDeRedServicio;

    @GetMapping("/all")
    public List<ConexionDeRed> getAll() {
        return conexionDeRedServicio.getAll();
    }

    @GetMapping("/{tipo}")
    public Optional<ConexionDeRed> getConexion(@PathVariable int tipo) {
        return conexionDeRedServicio.getConexion(tipo);
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ConexionDeRed save(@RequestBody ConexionDeRed conexionDeRed) {
        return conexionDeRedServicio.save(conexionDeRed);
    }
    @PutMapping("/update")
    @ResponseStatus(HttpStatus.CREATED)
    public ConexionDeRed update(@RequestBody ConexionDeRed conexionDeRed) {
        return conexionDeRedServicio.update(conexionDeRed);
    }

    @DeleteMapping("/{tipo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean delete(@PathVariable int tipo) {
        return conexionDeRedServicio.delete(tipo);
    }
}




