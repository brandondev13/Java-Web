package com.example.quileia.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dispositivoelectronico")
@Entity
public class DispositivoElectronico {
    @Id
    private String mac;
    private String tipo;
    private Boolean conectadoActualmente= true;
    private String ipAsignada;
    @ManyToOne()
    @JoinColumn(name = "conexionmac")
    @JsonIgnoreProperties("dispositivoElectronicos")
    private ConexionDeRed conexionActual;

    public DispositivoElectronico(String mac, String tipo, String ipAsignada, ConexionDeRed conexionActual) {
        this.mac = mac;
        this.tipo = tipo;
        this.ipAsignada = ipAsignada;
        this.conexionActual = conexionActual;
    }

    public DispositivoElectronico(String mac, String tipo, String ipAsignada, ConexionDeRed conexionActual, boolean conectadoActualmente) {
        this.mac = mac;
        this.tipo = tipo;
        this.ipAsignada = ipAsignada;
        this.conexionActual = conexionActual;
        this.conectadoActualmente = conectadoActualmente;
    }

}



