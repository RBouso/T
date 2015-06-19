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
public class Hora {
    private int hora; //número que representa las hora de una determinda hora
    private int minuto; //número que representa los minutos de una determinda hora

    /**
     * Obtener la hora
     * @return entero que representa la hora
     */
    public int getHora() {
        return hora;
    }

    /**
     * Añade una hora
     * @param hora: 
     */
    public void setHora(int hora) {
        this.hora = hora;
    }

     /**
     * Obtener los minutos
     * @return entero que representa los minutos
     */
    public int getMinuto() {
        return minuto;
    }

    /**
     * Añade los minutos de una hora
     * @param minuto 
     */
    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }
    
}
