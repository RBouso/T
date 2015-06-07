/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Valoraciones.Controlador.Datos.Interficie;

import Valoraciones.Ficheros.EstacionValorada;

/**
 *
 * @author raquel
 */
public interface CtrlEstacionValorada {

    public EstacionValorada anadirPuntuacion(int puntuacion, double latitud, 
            double longitud, String ciudad, String pais, boolean modificar);

    public EstacionValorada getEstacionValorada(Double latitud, double longitud, String ciudad, String pais);


    
}
