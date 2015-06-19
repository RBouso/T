/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transportes.Controlador.Datos.Interficie;

import Transportes.Ficheros.HorarioApertura;
import java.util.ArrayList;

/**
 *
 * @author raquel
 */
public interface CtrlEstacionCivica {

    //obtener las lineas de un transporte de una determinada ciudad
    public ArrayList<String> getLineas(String ciudad, String pais);

    //obtener las lineas que ofrecen sus horarios de un transporte de una determinada ciudad
    public ArrayList<String> getLineasHorarios(String ciudad, String pais);

    //obtener los sentidos de una linea, que ofrece sus horarios, de un transporte de una determinada ciudad
    public ArrayList<String> getSentidoHorarios(String ciudad, String pais, String linea);

    //obtener las horarios de una linea de transporte, que ofrece sus horarios, de una determinada ciudad
    public ArrayList<HorarioApertura> getHorarios(String ciudad, String pais, String linea, String sentido);

    

    
}
