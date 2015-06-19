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
public class Temporada {
    private String nombre; //nombre de la temporada

    /**
     * Obtener el nombre de una temporada
     * @return nombre de la temporada
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Añadir una temporada
     * @param nombre: nombre de una temporada
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
