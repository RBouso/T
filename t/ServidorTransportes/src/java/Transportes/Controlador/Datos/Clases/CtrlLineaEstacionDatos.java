/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transportes.Controlador.Datos.Clases;

import Transportes.Controlador.Datos.Interficie.CtrlLineaEstacion;
import Transportes.Ficheros.Autobus;
import Transportes.Ficheros.CoordinadasGeo;
import Transportes.Ficheros.DireccionPostal;
import Transportes.Ficheros.EstacionCivica;
import Transportes.Ficheros.Ferrocarriles;
import Transportes.Ficheros.Funicular;
import Transportes.Ficheros.Linea;
import Transportes.Ficheros.LineaEstacion;
import Transportes.Ficheros.Metro;
import Transportes.Ficheros.Teleferico;
import Transportes.Ficheros.Tranvia;
import Valoraciones.Ficheros.URL;
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
public class CtrlLineaEstacionDatos implements CtrlLineaEstacion{
    //path que contiene donde encontrar los ficheros
    private String folder = "/home/raquel/NetBeansProjects/ServidorTransportes/ficheros/";
    
    /**
     * Obtiene las paradas que de una determinada linea y un transporte
     * @param ciudad: nombre de la ciudad
     * @param pais: nombre del pais
     * @param transporte: nombre del transporte
     * @param nomLinea: nombre de la línea
     * @return lista de las estaciones de una línea y un determinado transporte
     */
    @Override
    public ArrayList<LineaEstacion> getLineaEstacionTransporte(String ciudad, 
        String pais, String transporte, String nomLinea) {
        ArrayList<LineaEstacion> ec = new ArrayList<>();
        try {
            String fichero = folder+pais+"/"+ciudad+"/"+transporte+".html";

            Document htmlFile = Jsoup.parse(new File(fichero), "utf-8");
            //Si hay alguna estación que contenga una linea
            if (htmlFile.body().select("span[itemprop=line]").size() != 0) {
                // se obtienen los elementos del tag body
                Elements e = htmlFile.body().children();
                for (int i = 0; i < e.size(); i++) {
                    //se obtienen los datos del elemento i
                    Element elem = e.get(i);
                    //Se obtienen los hijos del elemento i
                    Elements he = elem.children();
                    EstacionCivica estacion = construir(transporte);
                    LineaEstacion el = new LineaEstacion();
                    ArrayList<Linea> lin = new ArrayList<>();
                    Linea linea = new Linea();
                    boolean esta = false;
                    estacion.transporte = transporte;
                    //para cada uno de los elementos de los hijos se obtienen los datos
                    //para añadir las estaciones que tengan una línea igual a la de la
                    //linea proporcionada
                    for (int j = 0; j < he.size(); j++) {
                        Element helem = he.get(j);

                        if(helem.toString().contains("itemprop=\"name\""))
                            estacion.setNombre(helem.text());
                        else if(helem.toString().contains("itemprop=\"description\""))
                            estacion.setDescripcion(helem.text());    
                        else if(helem.toString().contains("itemprop=\"url\"")) {
                            URL u = new URL();
                            u.setUrl(helem.text());
                            estacion.setUrl(u);   
                        }
                        else if(helem.toString().contains("itemprop=\"geo\"")) {
                            CoordinadasGeo geo = new CoordinadasGeo();
                            Elements geos = helem.children();
                            geo.setLatitud(Double.valueOf(geos.get(0).attr("content")));
                            geo.setLongitud(Double.valueOf(geos.get(1).attr("content")));
                            estacion.setGeo(geo);
                        }
                        else if(helem.toString().contains("itemprop=\"address\"")) {
                            DireccionPostal dp = new DireccionPostal();
                            Elements dir = helem.children();
                            dp.setPais(dir.get(0).text());
                            dp.setLocalidad(dir.get(1).text());
                            dp.setRegion(dir.get(2).text());
                            dp.setDireccion(dir.get(3).text());
                            estacion.setDireccion(dp);
                        }
                        else if(helem.toString().contains("itemprop=\"telephone\""))
                            estacion.setTelefono(helem.text());
                        else if(helem.toString().contains("itemprop=\"line\"") &&
                                !helem.text().equals("")) {
                            Linea l = new Linea();
                            l.setNumLinea(helem.text());
                            lin.add(l);
                            if (helem.text().equals(nomLinea)) {
                                
                                esta = true;
                                linea.setNumLinea(helem.text());
                               

                            }
                                
                        }
                        


                    }
                    if( esta) {
                            el.setLinea(linea);
                            estacion.setLineas(lin);
                            el.setEstacion(estacion);
                             ec.add(el);
                        }
                        
                       
                }
                
                
            }
        } catch (IOException ex) {
            Logger.getLogger(CtrlLineaEstacionDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ec;
    }

    private EstacionCivica construir(String transporte) {
        EstacionCivica est = null;
        if(transporte.equalsIgnoreCase("autobus")) 
            est = new Autobus();
        else if (transporte.equalsIgnoreCase("metro"))
            est = new Metro();
        else if (transporte.equalsIgnoreCase("tranvia"))
            est = new Tranvia();
        else if (transporte.equalsIgnoreCase("ferrocarriles"))
            est = new Ferrocarriles();
        else if (transporte.equalsIgnoreCase("funicular"))
            est = new Funicular();
        else if (transporte.equalsIgnoreCase("teleferico"))
            est = new Teleferico();
        return est;
    }
    

            
}
