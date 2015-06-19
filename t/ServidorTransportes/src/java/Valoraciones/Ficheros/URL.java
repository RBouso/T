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
public class URL {
    private String url;//url que contiene los datos de un transporte
    private String nombreReferencia; // referencia que identifica la url
    private String fechaActualizacion;//fecha de la última actualización de la url
    private String actualizacion;//periodo de actualización de los datos

    /**
     * Obtener la fecha de la última actualización de los datos
     * @return string que contiene la fecha con la última actualización
     */
    public String getFechaActualizacion() {
        return fechaActualizacion;
    }

     /**
     * Añadir la fecha de la última actualización de los datos
     * @param fechaActualizacion : fecha con la última actualización
     */
    public void setFechaActualizacion(String fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

     /**
     * Obtener el periodo de actualización de los datos
     * @return string que contiene el periodo de actualización
     */
    public String getActualizacion() {
        return actualizacion;
    }

      /**
     * Añadir el periodo de actualización de los datos
     * @param actualizacion : periodo de actualización
     */
    public void setActualizacion(String actualizacion) {
        this.actualizacion = actualizacion;
    }
    
    public URL(){}

    /**
     * Constructura de una instacia url
     * @param url: nombre de la url
     * @param nombreReferencia: nombre de referencia
     * @param fecha: fecha de la última actualización de los datos
     * @param actual : periodo de actualización de los datos
     */
    public URL(String url, String nombreReferencia, String fecha, String actual) {
        this.url = url;
        this.nombreReferencia = nombreReferencia;
        this.fechaActualizacion= fecha;
        this.actualizacion = actual;
    }

    /**
     * Obtener la url donde encontrar los datos
     * @return string que contiene la url para acceder a los datos
     */
    public String getUrl() {
        return url;
    }

    /**
     * Añadir una url
     * @param url : url donde encontrar los datos
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Obtener el nombre de referencia que identifica a una url (suele tener el
     * siguiente formato TransporteCiudadFormato)
     * @return string que contiene el nombre de referencia
     */
    public String getNombreReferencia() {
        return nombreReferencia;
    }

    /**
     * Añadir el nombre de referencia que identifica a una url (suele tener el
     * siguiente formato TransporteCiudadFormato)
     * @param nombreReferencia : nombre de referencia que identifica una url
     */
    public void setNombreReferencia(String nombreReferencia) {
        this.nombreReferencia = nombreReferencia;
    }
}
