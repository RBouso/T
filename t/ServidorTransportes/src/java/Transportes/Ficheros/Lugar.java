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
public class Lugar extends Cosa{
    public String transporte; //nombre del transporte al que representa un lugar, en el caso de que sea un estación de un transporte
    private CoordinadasGeo geo; //coordenadas que indican la ubicación de un lugar
    private DireccionPostal direccion; //dirección que indican la ubicación de un lugar
    private String telefono;//número de teléfono
    private EstacionCivica ec; //estación civica

    public EstacionCivica getEc() {
        return ec;
    }

    public void setEc(EstacionCivica ec) {
        this.ec = ec;
    }

    /**
     * Obtener la coordenadas geográficas
     * @return coordenadas geográficas de una estación
     */
    public CoordinadasGeo getGeo() {
        return geo;
    }

    /**
     * Añade la coordenadas geográficas
     * @param geo: coordenadas geograficas de una estación
     */
    public void setGeo(CoordinadasGeo geo) {
        this.geo = geo;
    }

    /**
     * Obtiene la dirección de una ubicación
     * @return dirección de una ubicación
     */
    public DireccionPostal getDireccion() {
        return direccion;
    }

    /**
     * Añade la dirección de una ubicación
     * @param direccion: dirección de una ubicación
     */
    public void setDireccion(DireccionPostal direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene el teléfono
     * @return número de teléfono de un lugar
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Añade el número de teléfono
     * @param telefono: número de teléfono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    
}
