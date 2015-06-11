/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transportes.Controlador.Datos.Clases;

import Transportes.Controlador.Datos.Interficie.CtrlEstacionCivica;
import Transportes.Ficheros.Autobus;
import Transportes.Ficheros.CoordinadasGeo;
import Transportes.Ficheros.DireccionPostal;
import Transportes.Ficheros.EstacionCivica;
import Transportes.Ficheros.EstructurasPublicas;
import Transportes.Ficheros.Ferrocarriles;
import Transportes.Ficheros.Funicular;
import Transportes.Ficheros.Hora;
import Transportes.Ficheros.HorarioApertura;
import Transportes.Ficheros.Linea;
import Transportes.Ficheros.Metro;
import Transportes.Ficheros.Teleferico;
import Transportes.Ficheros.Temporada;
import Transportes.Ficheros.TipoDia;
import Transportes.Ficheros.Tranvia;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import maps.java.Geocoding;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author raquel
 */
public abstract class CtrlEstacionCivicaDatos extends CtrlEstructurasPublicasDatos implements CtrlEstacionCivica{
    
    protected String folder = "/home/raquel/NetBeansProjects/ServidorTransportes/ficheros/";
    
    @Override
    public abstract ArrayList<String> getLineas(String ciudad, String pais);
    public abstract ArrayList<EstructurasPublicas> getParadasCercanas(String ciudad, String pais, String direccion,  Double lat, Double lon);
    public abstract ArrayList<String> getLineasHorarios(String ciudad, String pais);
    public abstract ArrayList<String> getSentidoHorarios(String ciudad, String pais, String linea);
    public abstract ArrayList<HorarioApertura> getHorarios(String ciudad, String pais, String linea, String sentido);
    
    
    protected ArrayList<String> obtenerLineas(String fichero) {
        try {
            
            ArrayList<String> result = new ArrayList<>();
           
            Document htmlFile = Jsoup.parse(new File(fichero), "UTF-8");

           
            Elements select = htmlFile.select("span[itemprop=line]");
            
            for (int i = 0; i < select.size(); i++) {
                String linea = select.get(i).text();
               
                if (!linea.equals("") && !result.contains(linea))
                    result.add(linea);
            }
            return result;
        } catch (IOException ex) {
            Logger.getLogger(CtrlEstacionCivicaDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }
    
    public ArrayList<String> obtenerSentidos(String fichero, String linea){
        try {
            
            ArrayList<String> result = new ArrayList<>();
           
            Document htmlFile = Jsoup.parse(new File(fichero), "UTF-8");
//            List<Node> childNodes = htmlFile.body().childNodes();
           
            Elements select = htmlFile.select("span[itemprop=line]");
            
            for (int i = 0; i < select.size(); i++) {
                if(linea.equals(select.get(i).text())) {
                    Element elem = htmlFile.body().child(i);
                    String lin = elem.select("span[itemprop=sense]").get(0).text();
                    if (!lin.equals("") && !result.contains(lin))
                        result.add(lin);
                }
            }
            return result;
        } catch (IOException ex) {
            Logger.getLogger(CtrlEstacionCivicaDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }
    
    
    /**
     * Obtiene los horarios de la una estacion civica de transportes por la que 
     * pasa la linea linea en un determinado sentido.
     * @param fichero: path donde se encuentra el fichero
     * @param linea: nombre de la linea
     * @param sentido: sentido de la linea
     * @return lista con todos los horarios disponible para esa línea y sentido.
     */
    public ArrayList<HorarioApertura> obtenerHorarios(String fichero, String linea, 
            String sentido) {
                try {
            
            ArrayList<HorarioApertura> result = new ArrayList<>();
                 
            Document htmlFile = Jsoup.parse(new File(fichero), "UTF-8");
//            List<Node> childNodes = htmlFile.body().childNodes();
           
            Elements select = htmlFile.body().select("span[itemprop=sense]");
            for (int i = 0; i < select.size(); i++) {
                String sent = select.get(i).text();
                
                if (sent.equals(sentido)) {
                    
                    Element el = htmlFile.body().child(i);
                    
                    Elements temp = el.select("span[itemprop=season]");
                    Temporada t = new Temporada();
                    if( temp.size() > 0) {
                        t.setNombre(temp.get(0).text());
                    }
                    Elements td = el.select("span[itemprop=type]");
                    TipoDia tip = new TipoDia();
                    if( td.size() > 0) {
                        tip.setNombre(td.get(0).text());
                    }
                    Elements time = el.select("div[itemprop=times]");
                    
                    for( int j = 0; j <time.size(); j++) {
                        String tiempo = time.get(j).child(0).text();
              
                       String horas;
                        while (tiempo.contains(",")) {
                            HorarioApertura ha = new HorarioApertura();
                            horas = tiempo.substring(0, tiempo.indexOf(","));
                            tiempo = tiempo.substring(tiempo.indexOf(",")+1);
                            Hora hor = new Hora();
                            int hora = Integer.valueOf(horas.substring(0, horas.indexOf(":")));
                            int min = Integer.valueOf(horas.substring( horas.indexOf(":")+1));
                            hor.setHora(hora);
                            hor.setMinuto(min);
                            ha.setHoraSalida(hor);
                            ha.setTemporada(t);
                            ha.setTipo(tip);
                            result.add(ha);
                        }
                        if(!tiempo.isEmpty()) {
                            horas = tiempo;
                            HorarioApertura ha = new HorarioApertura();
                             int hora = Integer.valueOf(horas.substring(0, horas.indexOf(":")));
                            int min = Integer.valueOf(horas.substring( horas.indexOf(":")+1));
                            Hora hor = new Hora();
                            hor.setHora(hora);
                            hor.setMinuto(min);
                            ha.setHoraSalida(hor);
                            ha.setTemporada(t);
                            ha.setTipo(tip);
                            result.add(ha);
                        }
                    }
                }
               
            }
            return result;
        } catch (IOException ex) {
            Logger.getLogger(CtrlEstacionCivicaDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<HorarioApertura>();
    }
    

}
