/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Datos.Interficie;

import Ficheros.EstacionAparcamiento;
import java.util.ArrayList;

/**
 *
 * @author raquel
 */
public interface CtrlEstacionAparcamiento {
    public ArrayList<EstacionAparcamiento> getAparcamiento(String ciudad, String pais);
}
