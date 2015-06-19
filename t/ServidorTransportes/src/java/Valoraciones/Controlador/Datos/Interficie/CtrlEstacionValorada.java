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

    //añadir una valoración a la base de datos
    public EstacionValorada anadirPuntuacion(int puntuacion, double latitud, 
            double longitud, String ciudad, String pais, boolean modificar);

    //obtener la valoración de una estación
    public EstacionValorada getEstacionValorada(Double latitud, double longitud, String ciudad, String pais);

    //comprobar si una estación ha sido valorada anteriormente por algún usuario
    public boolean existeValoracion(double latitud, double longitud, String ciudad, String pais);
    
}
