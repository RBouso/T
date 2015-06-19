/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transportes.Controlador.Datos.Clases;

import Transportes.Controlador.Datos.Interficie.CtrlEstacionCivica;
import Transportes.Ficheros.EstructurasPublicas;
import Transportes.Ficheros.Hora;
import Transportes.Ficheros.HorarioApertura;
import Transportes.Ficheros.Temporada;
import Transportes.Ficheros.TipoDia;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author raquel
 */
public abstract class CtrlEstacionCivicaDatos extends CtrlEstructurasPublicasDatos implements CtrlEstacionCivica{
    //path que contiene los ficheros
    protected String folder = "/home/raquel/NetBeansProjects/ServidorTransportes/ficheros/";
    
    //obtiene todas la lineas de un determinado transporte
    @Override
    public abstract ArrayList<String> getLineas(String ciudad, String pais);
    //obtiene todas las paradas cercanas de un determinado transporte a una ubicación
    @Override
    public abstract ArrayList<EstructurasPublicas> getParadasCercanas(String ciudad, String pais, String direccion,  Double lat, Double lon);
    //obtiene todas la lineas de un determinado transporte que proporciona sus horarios
    @Override
    public abstract ArrayList<String> getLineasHorarios(String ciudad, String pais);
    //obtiene todos los sentidos de una línea de un determinado transporte
    @Override
    public abstract ArrayList<String> getSentidoHorarios(String ciudad, String pais, String linea);
    //obtiene todos los horarios de un determinado transporte
    @Override
    public abstract ArrayList<HorarioApertura> getHorarios(String ciudad, String pais, String linea, String sentido);
    
    /**
     * Obtiene todas las líneas de un determinado transporte
     * @param fichero: paht donde encontrar todo los datos de un transporte
     * @return Lista con el nombre de las líneas de un determinado transporte
     */
    protected ArrayList<String> obtenerLineas(String fichero) {
        try {
            
            ArrayList<String> result = new ArrayList<>();
           //Creación del documento a parsear
            Document htmlFile = Jsoup.parse(new File(fichero), "UTF-8");

           //Se obtienen todas las líneas de todas las estaciones de transporte
            Elements select = htmlFile.select("span[itemprop=line]");
            //para cada una de las líneas seleccionada
            for (int i = 0; i < select.size(); i++) {
                //Se obtiene el valor de la línea
                String linea = select.get(i).text();
               //se añade la linea a la lista
                if (!linea.equals("") && !result.contains(linea))
                    result.add(linea);
            }
            return result;
        } catch (IOException ex) {
            Logger.getLogger(CtrlEstacionCivicaDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }
    
    /**
     * Obtener los sentidos que puede tener una línea
     * @param fichero: path donde se encuentra los datos del fichero
     * @param linea: nombre de la línea 
     * @return lista con todos los sentidos que tiene una línea
     */
    public ArrayList<String> obtenerSentidos(String fichero, String linea){
        try {
            
            ArrayList<String> result = new ArrayList<>();
           
            Document htmlFile = Jsoup.parse(new File(fichero), "UTF-8");
           //Se seleccionan todas las lineas 
            Elements select = htmlFile.select("span[itemprop=line]");
            // para cada una de los elementos seleccionados
            for (int i = 0; i < select.size(); i++) {
                //en el caso  de que el valor del elemento i sea igual al nombre de la línea se
                //obtienen el sentido de la linea
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
           //obtiene los elementos que contiene los sentidos
            Elements select = htmlFile.body().select("span[itemprop=sense]");
            //para cada unos de los sentidos se mira si es igual al sentido proporcionado
            // se obtienen los horarios
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
