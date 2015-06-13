/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transportes.Controlador.Datos.Clases;

import Transportes.Ficheros.EstructurasPublicas;
import Transportes.Ficheros.HorarioApertura;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author raquel
 */
public class CtrlFerrocarrilesDatos extends CtrlEstacionCivicaDatos{
   
    public ArrayList<String> getLineas(String ciudad, String pais) {
        String fichero = folder+pais+"/"+ciudad+"/Ferrocarriles.html";
        return obtenerLineas(fichero);
    }

    @Override
    public ArrayList<EstructurasPublicas> getParadasCercanas(String ciudad, 
            String pais, String direccion, Double lat, Double lon) {
        try {
            String fichero = folder+pais+"/"+ciudad+"/Ferrocarriles.html";
            return obtenerParadasCercanas(fichero, direccion, lat, lon);
        } catch (Exception ex) {
            Logger.getLogger(CtrlFerrocarrilesDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<String> getLineasHorarios(String ciudad, String pais) {
         String fichero = folder+pais+"/"+ciudad+"/Horarios/Ferrocarriles.html";
        return obtenerLineas(fichero);
    }

    
     public ArrayList<String> getSentidoHorarios(String ciudad, String pais, String linea) {
        String fichero = folder+pais+"/"+ciudad+"/Horarios/Ferrocarriles.html";
        return obtenerSentidos(fichero, linea);
    }

     public ArrayList<HorarioApertura> getHorarios(String ciudad, String pais,  String linea, String sentido) {
        String fichero = folder+pais+"/"+ciudad+"/Horarios/Ferrocarriles.html";
        return obtenerHorarios(fichero, linea, sentido);
    }
     
}