/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transportes.Ficheros;

import java.util.ArrayList;

/**
 *
 * @author raquel
 */
public class AccionMoverse {
    private Lugar origen;
    private Lugar destino;
    private ArrayList<EstacionCivica> estacionCivica = new ArrayList<>();
    

    public ArrayList<EstacionCivica> getEstacionCivica() {
        return estacionCivica;
    }

    public void setEstacionCivica(ArrayList<EstacionCivica> estacionCivica) {
        this.estacionCivica = estacionCivica;
    }
    

    public Lugar getOrigen() {
        return origen;
    }

    public void setOrigen(Lugar origen) {
        this.origen = origen;
    }

    public Lugar getDestino() {
        return destino;
    }

    public void setDestino(Lugar destino) {
        this.destino = destino;
    }
    
    
}
