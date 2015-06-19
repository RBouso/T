/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transportes.Controlador.Datos.Interficie;


import Transportes.Ficheros.Ciudad;
import java.util.ArrayList;

/**
 *
 * @author raquel
 */
public interface CtrlCiudad {

    //añadir una ciudad en el sistema
    public void anadirCiudad(String nombre, String pais) ;

    //obtener las ciudades que hay guardadas en el sistema
    public ArrayList<Ciudad> getCiudades() ;

    //obtener los transportes que tiene una determinada ciudad
    public ArrayList<String> getTransportes(String ciudad);

    //obtener los transportes que ofrecen los horarios de una determinada ciudad
    public ArrayList<String> getTransportesHorarios(String ciudad, String pais);
    
}
