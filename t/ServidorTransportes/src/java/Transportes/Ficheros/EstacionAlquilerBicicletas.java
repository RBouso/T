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
public class EstacionAlquilerBicicletas extends EstructurasPublicas{
    private String identificador; //identificador de una estación de alquiler de bicicletas
    private int anclajes; //número total de anclajes
    private int biciLibres; //número de bicicletas libres

    /**
     * Obtener el identificador
     * @return string que contiene el identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Añadir el identificador
     * @param identificador: identificador de una parada de alquiler de bicicletas
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Obtener el número de anclajes
     * @return número de anclajes 
     */
    public int getAnclajes() {
        return anclajes;
    }

    /**
     * Añadir el número de anclajes que contiene una estación
     * @param anclajes : número total de anclajes
     */
    public void setAnclajes(int anclajes) {
        this.anclajes = anclajes;
    }

    /**
     * Obtener el número de bicicletas libres
     * @return número de bicicletas libres
     */
    public int getBiciLibres() {
        return biciLibres;
    }

    /**
     * Añadir el número de bicicletas libres que contiene una estación
     * @param biciLibres : número de bicicletas libres
     */
    public void setBiciLibres(int biciLibres) {
        this.biciLibres = biciLibres;
    }
    
    
}
