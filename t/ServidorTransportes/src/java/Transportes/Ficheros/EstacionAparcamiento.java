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
public class EstacionAparcamiento extends EstructurasPublicas{
    private String identificador; //identificador de un aparcamiento
    private int accesibilidad; //accesibilidad de un aparcamiento
    private int plazasTotales; // número de plazas totales de un aparcamiento
    private int plazasLibres; // número de plazas libres que tiene un aparcamiento

    /**
     * Obtiene el identificador de un aparcamiento
     * @return string que contiene el identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Añadir el identificador de una aparcamiento
     * @param identificador: identificador de una aparcamiento
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Obtiene la accesibilidad de un aparcamiento
     * @return accesibilidad de un aparcamiento
     */
    public int getAccesibilidad() {
        return accesibilidad;
    }

    /**
     * Añade la accesibilidad de un aparcamiento
     * @param accesibilidad: entero que indica si un aparcamiento es accesible o no 
     */
    public void setAccesibilidad(int accesibilidad) {
        this.accesibilidad = accesibilidad;
    }

    /**
     * Obtiene las plazas totales de un aparcamiento
     * @return plazas totales de un aparcamiento
     */
    public int getPlazasTotales() {
        return plazasTotales;
    }

    /**
     * Añade las plazas totales
     * @param plazasTotales : número de plazas totales
     */
    public void setPlazasTotales(int plazasTotales) {
        this.plazasTotales = plazasTotales;
    }

    /**
     * Obtiene las plazas libres de un aparcamiento
     * @return plazas libres de un aparcamiento
     */
    public int getPlazasLibres() {
        return plazasLibres;
    }

    /**
     * Añade las plazas libres de un aparcamiento
     * @param plazasLibres : número de plazas libres
     */
    public void setPlazasLibres(int plazasLibres) {
        this.plazasLibres = plazasLibres;
    }
    
    
}
