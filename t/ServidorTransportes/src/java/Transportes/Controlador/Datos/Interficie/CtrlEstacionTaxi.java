/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transportes.Controlador.Datos.Interficie;

import Transportes.Ficheros.EstacionTaxi;
import java.util.ArrayList;

/**
 *
 * @author raquel
 */
public interface CtrlEstacionTaxi {

    //obtener las paradas de taxis de una determinada ciudad
    public ArrayList<EstacionTaxi> getTaxis(String ciudad, String pais);
    
}
