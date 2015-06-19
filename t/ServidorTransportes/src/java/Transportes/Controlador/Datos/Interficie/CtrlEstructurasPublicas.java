/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transportes.Controlador.Datos.Interficie;

import Transportes.Ficheros.EstructurasPublicas;
import java.util.ArrayList;

/**
 *
 * @author raquel
 */
public interface CtrlEstructurasPublicas {
    //obtiene las paradas cercanas a una ubicación
    public ArrayList<EstructurasPublicas> getParadasCercanas(String ciudad, 
            String pais, String direccion,  Double lat, Double lon);

}
