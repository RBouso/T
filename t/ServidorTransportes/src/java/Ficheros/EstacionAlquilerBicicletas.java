/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ficheros;

/**
 *
 * @author raquel
 */
public class EstacionAlquilerBicicletas extends EstructurasPublicas{
    private String identificador;
    private int anclajes;
    private int biciLibres;

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public int getAnclajes() {
        return anclajes;
    }

    public void setAnclajes(int anclajes) {
        this.anclajes = anclajes;
    }

    public int getBiciLibres() {
        return biciLibres;
    }

    public void setBiciLibres(int biciLibres) {
        this.biciLibres = biciLibres;
    }
    
    
}
