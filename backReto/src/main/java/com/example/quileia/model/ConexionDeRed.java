package com.example.quileia.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "conexiondered")
public class ConexionDeRed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tipo;
    private String nombre;
    private String tipoDeCifrado;
    private String usuarioConexion;
    private String contrasenaDeConexion;



    @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "conexionActual")
    @JsonIgnoreProperties("conexionActual")
    List<DispositivoElectronico> dispositivoElectronicos;

    public ConexionDeRed(Integer tipo, String nombre, String tipoDeCifrado, String usuarioConexion, String contrasenaDeConexion) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.tipoDeCifrado = tipoDeCifrado;
        this.usuarioConexion = usuarioConexion;
        this.contrasenaDeConexion = contrasenaDeConexion;
    }


    public ConexionDeRed(String nombre, String tipoDeCifrado, String usuarioConexion, String contrasenaDeConexion) {
        this.nombre = nombre;
        this.tipoDeCifrado = tipoDeCifrado;
        this.usuarioConexion = usuarioConexion;
        this.contrasenaDeConexion = contrasenaDeConexion;
    }

    public ConexionDeRed(Integer tipo, String nombre) {
        this.tipo = tipo;
        this.nombre = nombre;
    }


    public ConexionDeRed(String nombre, String tipoDeCifrado) {
        this.nombre = nombre;
        this.tipoDeCifrado = tipoDeCifrado;
    }
}



