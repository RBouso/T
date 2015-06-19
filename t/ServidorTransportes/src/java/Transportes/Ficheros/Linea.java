/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transportes.Ficheros;

/**
 *
 * @author raquel
 */
public class Linea {
    private String numLinea; //número de una línea
    private String descripcion; //descripción de una línea

      /**
     * Obtiene el número de una línea
     * @return string que contiene el número de una línea
     */
    public String getNumLinea() {
        return numLinea;
    }

    /**
     * Añade el número de una línea
     * @param numLinea : número de una línea
     */
    public void setNumLinea(String numLinea) {
        this.numLinea = numLinea;
    }

    /**
     * Obtiene la descripción de una línea
     * @return string que contiene la descripción de una línea
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Añade la descripción de una línea
     * @param descripcion: descripción de una línea
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
}
