/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transportes.Ficheros;

import java.util.ArrayList;
import javafx.util.Pair;

/**
 *
 * @author raquel
 */
public class LineaEstacion {
    private EstacionCivica estacion;//estación de transporte público
    private Linea linea; //línea que pasa por una estación de transporte público

    /**
     * Obtener la estación
     * @return estación de transporte público
     */
    public EstacionCivica getEstacion() {
        return estacion;
    }

    /**
     * Añadir una estación
     * @param estacion : estación de transporte público
     */
    public void setEstacion(EstacionCivica estacion) {
        this.estacion = estacion;
    }

    /**
     * Obtener la línea
     * @return línea que pasa por una estación
     */
    public Linea getLinea() {
        return linea;
    }

    /**
     * Añade una línea
     * @param linea : línea que pasa por una estación 
     */
    public void setLinea(Linea linea) {
        this.linea = linea;
    }


    /**
     * Comprobar si el número de línea proporciopado es igual al número de línea
     * del la instancia lineaEstacion
     * @param linea: número de una línea
     * @return boolean que indica si los números de línea coinciden
     */
    public Boolean esLinea(String linea) {
        return this.linea.getNumLinea().equals(linea);
    }
    
}
