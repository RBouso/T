/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ficheros;

import java.util.ArrayList;
import javafx.util.Pair;

/**
 *
 * @author raquel
 */
public class LineaEstacion {
    private EstacionCivica estacion;
    private Linea linea;

    public EstacionCivica getEstacion() {
        return estacion;
    }

    public void setEstacion(EstacionCivica estacion) {
        this.estacion = estacion;
    }

    public Linea getLinea() {
        return linea;
    }

    public void setLinea(Linea linea) {
        this.linea = linea;
    }


    public Boolean esLinea(String linea) {
        return this.linea.getNumLinea().equals(linea);
    }
    
}
