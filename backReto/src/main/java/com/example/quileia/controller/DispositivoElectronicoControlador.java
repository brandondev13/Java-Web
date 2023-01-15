package com.example.quileia.controller;

import com.example.quileia.model.DispositivoElectronico;
import com.example.quileia.service.DispositivoElectronicoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin("*")
@RequestMapping("/dispositivoelectronico")
public class DispositivoElectronicoControlador {

    @Autowired
    private DispositivoElectronicoServicio dispositivoElectronicoServicio;


    @GetMapping("/all")
    public List<DispositivoElectronico> getAll() {
        return dispositivoElectronicoServicio.getAll();
    }

    @GetMapping("/{mac}")
    public Optional<DispositivoElectronico> getDispositivo(@PathVariable String mac) {
        return dispositivoElectronicoServicio.getDispositivo(mac);
    }

    @GetMapping("/filter/{id}")
    public List<DispositivoElectronico> filtro(@PathVariable("id") Integer id) {
        return dispositivoElectronicoServicio.filtro(id);
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Optional<DispositivoElectronico> save(@RequestBody DispositivoElectronico dispositivoElectronico) {
        return dispositivoElectronicoServicio.save(dispositivoElectronico);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.CREATED)
    public Optional<DispositivoElectronico> update(@RequestBody DispositivoElectronico dispositivoElectronico) {
        return dispositivoElectronicoServicio.update(dispositivoElectronico);
    }


    @DeleteMapping("/{mac}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean delete(@PathVariable String mac) {
        return dispositivoElectronicoServicio.delete(mac);
    }
}

















