/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Datos;

import Ficheros.EstacionCivica;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author raquel
 */
public class CtrlFunicularDatos extends CtrlEstacionCivicaDatos{
    public List<String> getLineas(String ciudad, String pais) {
        String fichero = "ficheros/"+pais+"/"+ciudad+"/Funicular.html";
            return obtenerLineas(fichero);
    }


}
