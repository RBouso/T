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
public class TipoDia {
    private String nombre; //nombre que identifica el tipo de día

    /**
     * Obtiene el nombre del tipo de día
     * @return nombre del tipo de día
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Añade el nombre del tipo de día
     * @param nombre: nombre del tipo de día
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
