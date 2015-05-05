/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Datos.Interficie;

import Ficheros.LineaEstacion;
import java.util.ArrayList;

/**
 *
 * @author raquel
 */
public interface CtrlLineaEstacion {

    public ArrayList<LineaEstacion> getLineaEstacionTransporte(String ciudad, 
            String pais, String transporte);
    
}
