/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transportes.Controlador.Datos.Clases;

import Transportes.Controlador.Datos.Interficie.CtrlEstacionTaxi;
import Transportes.Ficheros.CoordinadasGeo;
import Transportes.Ficheros.DireccionPostal;
import Transportes.Ficheros.EstacionTaxi;
import Transportes.Ficheros.EstructurasPublicas;
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
public class CtrlEstacionTaxiDatos  extends CtrlEstructurasPublicasDatos 
implements CtrlEstacionTaxi{

    private String folder = "/home/raquel/NetBeansProjects/ServidorTransportes/ficheros/";
    
     /**
     * Obtener todas la estaciones de taxi que hay en una ciudad
     * @param ciudad: nombre de la ciudad
     * @param pais: nombre del pais
     * @return lista de todas las estaciones que hay en la ciudad ciudad.
     */
    @Override
    public ArrayList<EstacionTaxi> getTaxis(String ciudad, String pais) {
        try {
            ArrayList<EstacionTaxi> result = new ArrayList<>();
            String fichero = folder+pais+"/"+ciudad+"/Taxi.html";
            Document htmlFile = Jsoup.parse(new File(fichero), "UTF-8");
            Elements elementos = htmlFile.body().children();
           
            for (int i = 0; i < elementos.size(); i++) {
                Element elemento = elementos.get(i);
                Elements elements = elemento.children();
                EstacionTaxi ea = new EstacionTaxi();
                 for (int j = 0; j < elements.size(); j++) {
                    Element el = elements.get(j);
                    String atributo = el.attributes().toString();
                    if (atributo.contains("itemprop=\"name\""))
                        ea.setNombre(el.text());
                    else if (atributo.contains("itemprop=\"description\""))
                        ea.setDescripcion(el.text());
                    else if (atributo.contains("itemprop=\"geo\"")){
                        Elements geo = el.children();
                        CoordinadasGeo g = new CoordinadasGeo();
                        g.setLatitud(Double.parseDouble(geo.get(0).attributes().get("content")));
                        g.setLongitud(Double.parseDouble(geo.get(1).attributes().get("content")));
                        ea.setGeo(g);
                    }
                    else if (atributo.contains("itemprop=\"address\"")){
                        Elements dir = el.children();
                        DireccionPostal dp = new DireccionPostal();
                        dp.setPais(dir.get(0).text());
                        dp.setLocalidad(dir.get(1).text());
                        dp.setRegion(dir.get(2).text());
                        dp.setDireccion(dir.get(3).text());
                        ea.setDireccion(dp);
                    }
                    else if (atributo.contains("itemprop=\"telephone\""))
                        ea.setTelefono(el.text());
                    
 
                 }
                 
                 result.add(ea);
            }

            return result;

        } catch (IOException ex) {
            Logger.getLogger(CtrlEstacionAparcamientoDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Obtener las paradas de taxis cercanas a una localización
     * @param ciudad: nombre de la ciudad
     * @param pais: nombre del pais
     * @param direccion: dirección sobre la que buscar las estaciones cercanas
     * @param lat: latitud de una ubicación
     * @param lon: longitud de una ubicación
     * @return lista de las estaciones de taxis cercanas a la dirección
     * o a la latitud y longitud proporcionadas
     */
    @Override
    public ArrayList<EstructurasPublicas> getParadasCercanas(String ciudad, 
            String pais, String direccion, Double lat, Double lon) {
        ArrayList<EstructurasPublicas> result = new ArrayList<>();
        try {
            String fichero = folder+pais+"/"+ciudad+"/Taxi.html";
            return obtenerParadasCercanas(fichero, direccion, lat, lon);
        } catch (Exception ex) {
            Logger.getLogger(CtrlEstacionTaxiDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
