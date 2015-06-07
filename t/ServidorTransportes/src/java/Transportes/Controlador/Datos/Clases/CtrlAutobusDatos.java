/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transportes.Controlador.Datos.Clases;

import Transportes.Controlador.Datos.Interficie.CtrlAutobus;
import Transportes.Ficheros.EstacionCivica;
import Transportes.Ficheros.EstructurasPublicas;
import Transportes.Ficheros.HorarioApertura;
import Transportes.Ficheros.LineaEstacion;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author raquel
 */

public class CtrlAutobusDatos extends CtrlEstacionCivicaDatos {
    
    public ArrayList<String> getLineas(String ciudad, String pais) {
       
            String fichero = folder+pais+"/"+ciudad+"/Autobus.html";
            return obtenerLineas(fichero);

    }
    
    public ArrayList<String> getLineasHorarios(String ciudad, String pais) {
        String fichero = folder+pais+"/"+ciudad+"/Horarios/Autobus.html";
        return obtenerLineas(fichero);
    }

    public ArrayList<String> getSentidoHorarios(String ciudad, String pais, String linea) {
        String fichero = folder+pais+"/"+ciudad+"/Horarios/Autobus.html";
        return obtenerSentidos(fichero, linea);
    }
    
    public ArrayList<HorarioApertura> getHorarios(String ciudad, String pais,  String linea, String sentido) {
        String fichero = folder+pais+"/"+ciudad+"/Horarios/Autobus.html";
        return obtenerHorarios(fichero, linea, sentido);
    }

    @Override
    public ArrayList<EstructurasPublicas> getParadasCercanas(String ciudad, String pais, String direccion, 
            Double lat, Double lon) {
        try {
            String fichero = folder+pais+"/"+ciudad+"/Autobus.html";
            return obtenerParadasCercanas(fichero, direccion,lat,lon);
        } catch (Exception ex) {
            Logger.getLogger(CtrlAutobusDatos.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

  
}
