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
public class CtrlFunicularDatos extends CtrlEstacionCivicaDatos{
    
        /**
     * Obtiene las líneas de Funicular
     * @param ciudad: nombre de la ciudad
     * @param pais: nombre del pais
     * @return lista con el nombre de las líneas que forman parte de todas las 
     * estaciones de funicular
     */
    @Override
    public ArrayList<String> getLineas(String ciudad, String pais) {
        String fichero = folder+pais+"/"+ciudad+"/Funicular.html";
            return obtenerLineas(fichero);
    }
    
        /**
     * Obtiene las líneas de funicular que proporcionan sus horarios
     * @param ciudad: nombre de la ciudad
     * @param pais: nombre del pais
     * @return lista con el nombre de las líneas que forman parte de todas las 
     * estaciones de funicular y que proporcionan sus horarios
     */
        @Override
    public ArrayList<String> getLineasHorarios(String ciudad, String pais) {
         String fichero = folder+pais+"/"+ciudad+"/Horarios/Funicular.html";
        return obtenerLineas(fichero);
    }
    
     /**
     * Obtiene los sentidos que tiene una línea de funicular
     * @param ciudad: nombre de la ciudad
     * @param pais: nombre del pais
     * @param linea: nombre de la linea
     * @return lista con el nombre de los sentidos que tiene una línea de funicular
     */
    @Override
     public ArrayList<String> getSentidoHorarios(String ciudad, String pais, String linea) {
        String fichero = folder+pais+"/"+ciudad+"/Horarios/Funicular.html";
        return obtenerSentidos(fichero, linea);
    }
     
     /**
     * Obtener los horarios que tiene una línea de transporte en un determinado sentido
     * @param ciudad: nombre de la ciudad
     * @param pais: nombre del pais
     * @param linea: nombre de la línea
     * @param sentido: nombre del sentido
     * @return lista de los horarios que tiene una línea de funicular en el sentido indicado
     */
    @Override
     public ArrayList<HorarioApertura> getHorarios(String ciudad, String pais,  String linea, String sentido) {
        String fichero = folder+pais+"/"+ciudad+"/Horarios/Funicular.html";
        return obtenerHorarios(fichero, linea, sentido);
    }

     /**
     * Obtener las paradas cercanas de estaciones de funicular a una dirección o ubicación
     * @param ciudad: nombre de la ciudad
     * @param pais: nombre del pais
     * @param direccion: dirección sobre la que se desean buscar las paradas cercanas
     * @param lat: latitud de una ubicación
     * @param lon: longitud de una ubicación
     * @return lista que contiene las paradas de funicular cercanas a la dirección 
     * o a la latitud y la longitud indicadas.
     */
    @Override
    public ArrayList<EstructurasPublicas> getParadasCercanas(String ciudad, 
            String pais, String direccion, Double lat, Double lon) {
        try {
            String fichero = folder+pais+"/"+ciudad+"/Funicular.html";
            return obtenerParadasCercanas(fichero, direccion, lat, lon);
        } catch (Exception ex) {
            Logger.getLogger(CtrlFunicularDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


}
