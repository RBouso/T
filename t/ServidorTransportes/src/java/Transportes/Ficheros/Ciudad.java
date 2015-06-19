/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transportes.Ficheros;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author raquel
 */
@XmlRootElement
public class Ciudad {
    private String nombre; //nombre de la ciudad
    private String pais;//nombre del pais

    /**
     * Creadora de una instancia de ciudad
     * @param nombre: nombre de la ciudad
     * @param pais : nombre del pais
     */
    public Ciudad(String nombre, String pais) {
        this.nombre = nombre;
        this.pais = pais;
    }

    public Ciudad() {
    }

    /**
     * Obtener el nombre de una ciudad
     * @return string que contiene el nombre de la ciudad
     */
    @XmlElement(name = "nombre_persona")
    public String getNombre() {
        return nombre;
    }

    /**
     * Añadir el nombre de una ciudad
     * @param nombre : nombre de la ciudad
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

     /**
     * Obtener el nombre del pais
     * @return string que contiene el nombre del pais
     */
    public String getPais() {
        return pais;
    }

    /**
     * Añadir el nombre del pais
     * @param pais : nombre del pais
     */
    public void setPais(String pais) {
        this.pais = pais;
    }
    
}
