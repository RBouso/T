/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transportes.Ficheros;

import Valoraciones.Ficheros.URL;

/**
 *
 * @author raquel
 */
public abstract class Cosa {
    private String nombre; // nombre que identifica una cosa
    private String descripcion; // descripción que identifica una cosa
    private URL url; //url donde poder encontrar una cosa

    /**
     * Obtener el nombre
     * @return string que contiene el nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Añadir el nombre
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

        /**
     * Obtener la descripción
     * @return string que contiene la descripción
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Añadir la descripción
     * @param descripcion 
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

        /**
     * Obtener la URL
     * @return url
     */
    public URL getUrl() {
        return url;
    }

    /**
     * Añadir la url
     * @param url 
     */
    public void setUrl(URL url) {
        this.url = url;
    }
    
    
}
