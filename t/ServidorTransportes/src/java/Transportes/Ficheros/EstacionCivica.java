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
public class EstacionCivica extends EstructurasPublicas{
    private ArrayList<Linea> lineas;//Lineas que pasan por la estación

    /**
     * Obtener las líneas que pasan por la estación
     * @return lista que contiene las líneas que pasan por la estación
     */
    public ArrayList<Linea> getLineas() {
        return lineas;
    }

    /**
     * Añade una lista con las líneas que pasan por la estación
     * @param lineas :lista con las líneas que pasan por la estación
     */
    public void setLineas(ArrayList<Linea> lineas) {
        this.lineas = lineas;
    }
    
    
}
