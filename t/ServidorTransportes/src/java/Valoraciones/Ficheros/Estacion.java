/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Valoraciones.Ficheros;

/**
 *
 * @author raquel
 */
public class Estacion {
    private Double latitud; //latitud de la ubicación de una estación
    private Double longitud; //longitud de la ubicación de una estación

    /**
     * Obtener la latitud de la ubicación de una estación
     * @return latitud de la ubicación de una estación
     */
    public Double getLatitud() {
        return latitud;
    }

    /**
     * Añadir la latitud de la ubicación de una estación
     * @param latitud: latitud de la ubicación de una estación
     */
    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    /**
     * Obtener la longitud de la ubicación de una estación
     * @return longitud de la ubicación de una estación
     */
    public Double getLongitud() {
        return longitud;
    }

    /**
     * Añadir la longitud de la ubicación de una estación
     * @param longitud: longitud de la ubicación de una estación
     */
    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }
    
    

}
