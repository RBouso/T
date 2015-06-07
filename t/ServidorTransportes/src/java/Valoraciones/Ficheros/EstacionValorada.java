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
public class EstacionValorada extends Estacion{
    private int valoracionTotal;
    private int totalUsuarios;

    public int getValoracionTotal() {
        return valoracionTotal;
    }

    public void setValoracionTotal(int valoracionTotal) {
        this.valoracionTotal = valoracionTotal;
    }

    public int getTotalUsuarios() {
        return totalUsuarios;
    }

    public void setTotalUsuarios(int totalUsuarios) {
        this.totalUsuarios = totalUsuarios;
    }
    
    
    public double getMedia() {
        return (valoracionTotal+0.0)/(0.0+totalUsuarios);
    }
}
