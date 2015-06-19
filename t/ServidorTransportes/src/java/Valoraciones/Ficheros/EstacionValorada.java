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
    private int valoracionTotal; //valoración total de la estación realizada por los usuarios
    private int totalUsuarios; // número de usuarios total que han valorado la estación

    /**
     * Obtener la valoración total que los usuarios han realizado de la estación
     * @return valoración total de los usuarios han realizado de la estación
     */
    public int getValoracionTotal() {
        return valoracionTotal;
    }

    /**
     * Añadir la valoración total que han hecho los usuarios
     * @param valoracionTotal: número con la valoración total realizada por los usuarios
     * que han valorado la estación
     */
    public void setValoracionTotal(int valoracionTotal) {
        this.valoracionTotal = valoracionTotal;
    }

    /**
     * Obtener el número total de usuarios que han valorado la estación
     * @return  número total de usuarios que han valorado la estación 
     */
    public int getTotalUsuarios() {
        return totalUsuarios;
    }

    
    /**
     * Añadir el número total de usuarios que han valorado la estación
     * @param totalUsuarios :  número total de usuarios que han valorado la estación 
     */
    public void setTotalUsuarios(int totalUsuarios) {
        this.totalUsuarios = totalUsuarios;
    }
    
    /**
     * Obtener la media entre las valoraciones hechas por todos los usuarios
     * @return  media de las valoraciones realizadas por los usuarios
     */
    public double getMedia() {
        return (valoracionTotal+0.0)/(0.0+totalUsuarios);
    }
}
