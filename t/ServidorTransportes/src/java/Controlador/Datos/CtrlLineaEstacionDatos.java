/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Datos;

import Controlador.Datos.Interficie.CtrlLineaEstacion;
import Ficheros.Autobus;
import Ficheros.CoordinadasGeo;
import Ficheros.DireccionPostal;
import Ficheros.EstacionCivica;
import Ficheros.Ferrocarriles;
import Ficheros.Funicular;
import Ficheros.Linea;
import Ficheros.LineaEstacion;
import Ficheros.Metro;
import Ficheros.Teleferico;
import Ficheros.Tranvia;
import Ficheros.URL;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    private String folder = "/home/raquel/NetBeansProjects/ServidorTransportes/ficheros/";
    
    public ArrayList<LineaEstacion> getLineaEstacionTransporte(String ciudad, 
        String pais, String transporte) {
        ArrayList<LineaEstacion> ec = new ArrayList<>();
        try {
            String fichero = folder+pais+"/"+ciudad+"/"+transporte+".html";
            Document htmlFile = Jsoup.parse(new File(fichero), "ISO-8859-1");
            if (htmlFile.select("span[itemprop=line]").size() != 0) {
                Elements e = htmlFile.body().children();
                for (int i = 0; i < e.size(); i++) {
                    Element elem = e.get(i);
                    Elements he = elem.children();
                    EstacionCivica estacion = construir(transporte);
                    LineaEstacion el = new LineaEstacion();
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
                        else if(helem.toString().contains("itemprop=\"line\"")) {
                            if (!helem.text().equals("")) {
                                Linea linea = new Linea();
                                linea.setNumLinea(helem.text());
                                el.setLinea(linea);
                                el.setEstacion(estacion);
                                ec.add(el);
                            }
                        }


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
