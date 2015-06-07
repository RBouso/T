/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transportes.Controlador.Datos.Interficie;

import Transportes.Ficheros.EstacionCivica;
import Transportes.Ficheros.HorarioApertura;
import java.util.ArrayList;

/**
 *
 * @author raquel
 */
public interface CtrlEstacionCivica {

    public ArrayList<String> getLineas(String ciudad, String pais);

    public ArrayList<String> getLineasHorarios(String ciudad, String pais);

    public ArrayList<String> getSentidoHorarios(String ciudad, String pais, String linea);

    public ArrayList<HorarioApertura> getHorarios(String ciudad, String pais, String linea, String sentido);

    

    
}
