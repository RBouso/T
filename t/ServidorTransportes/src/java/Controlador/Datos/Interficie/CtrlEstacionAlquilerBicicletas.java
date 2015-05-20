/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Datos.Interficie;

import Ficheros.EstacionAlquilerBicicletas;
import java.util.ArrayList;

/**
 *
 * @author raquel
 */
public interface CtrlEstacionAlquilerBicicletas {
    public ArrayList<EstacionAlquilerBicicletas> getBicicletas(String ciudad, 
            String pais);
}
