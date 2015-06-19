/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transportes.Controlador.Datos.Interficie;

import Transportes.Ficheros.EstacionAlquilerBicicletas;
import java.util.ArrayList;

/**
 *
 * @author raquel
 */
public interface CtrlEstacionAlquilerBicicletas {
    //obtener las estaciones de alquiler de bicicletas de una ciudad
    public ArrayList<EstacionAlquilerBicicletas> getBicicletas(String ciudad, 
            String pais);
}
