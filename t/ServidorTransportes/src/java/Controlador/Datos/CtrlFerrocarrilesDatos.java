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
public class CtrlFerrocarrilesDatos extends CtrlEstacionCivicaDatos{
   
    public ArrayList<String> getLineas(String ciudad, String pais) {
        String fichero = folder+pais+"/"+ciudad+"/Ferrocarriles.html";
        return obtenerLineas(fichero);
    }


}
