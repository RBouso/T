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
public class DireccionPostal extends PuntoDeContacto{
    private String pais; // nombre del país
    private String localidad; //nombre de la localidad
    private String region; //nombre de la región
    private String direccion; // dirección de una localización

    /**
     * Obtener el país
     * @return string que contiene el nombre del país
     */
    public String getPais() {
        return pais;
    }

    /**
     * Añadir el nombre del país
     * @param pais : nombre del país
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

        /**
     * Obtener la localidad
     * @return string que contiene el nombre de la localidad
     */
    public String getLocalidad() {
        return localidad;
    }

    /**
     * Añadir el nombre de la localidad
     * @param localidad: nombre de la localidad
     */
    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

        /**
     * Obtener la región
     * @return string que contiene el nombre de la región
     */
    public String getRegion() {
        return region;
    }

    /**
     * Añadir el nombre de la región
     * @param region: nombre de la región
     */
    public void setRegion(String region) {
        this.region = region;
    }

        /**
     * Obtener la dirección
     * @return string que contiene la dirección de una localización
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Añadir la dirección de un localización
     * @param direccion: dirección de un localización
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    
}
