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
public class CoordinadasGeo extends Intangible{
    private double latitud;//latitud de una localización
    private double longitud; //longitud de una localización

    /**
     * Obtener la latitud
     * @return real que contiene la latitud
     */
    public double getLatitud() {
        return latitud;
    }

    /**
     * Añadir la latitud
     * @param latitud : latitud de una localización
     */
    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

        /**
     * Obtener la longitud
     * @return real que contiene la longitud
     */
    public double getLongitud() {
        return longitud;
    }

     /**
     * Añadir la longitud
     * @param longitud : longitud de una localización
     */
    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
    
}
