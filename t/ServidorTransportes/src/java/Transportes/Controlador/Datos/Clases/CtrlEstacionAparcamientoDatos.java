/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transportes.Controlador.Datos.Clases;

import Transportes.Controlador.Datos.Interficie.CtrlEstacionAparcamiento;
import Transportes.Ficheros.CoordinadasGeo;
import Transportes.Ficheros.DireccionPostal;
import Transportes.Ficheros.EstacionAparcamiento;
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
public class CtrlEstacionAparcamientoDatos extends CtrlEstructurasPublicasDatos 
implements CtrlEstacionAparcamiento {
    //path donde encontrar los datos de los aparcamientos
    private String folder =  "/home/raquel/NetBeansProjects/ServidorTransportes/ficheros/";
    
    public CtrlEstacionAparcamientoDatos() {
    }


    /**
     * Obtener las estaciones de aparcamiento que hay en una ciudad
     * @param ciudad: nombre de la ciudad
     * @param pais: nombre del pais
     * @return lista de las estaciones de aparcamiento que hay en la ciudad definida
     */
    @Override
    public ArrayList<EstacionAparcamiento> getAparcamiento(String ciudad, String pais) {
        try {
            ArrayList<EstacionAparcamiento> result = new ArrayList<>();
            //Path donde encontrar las estaciones de aparcamiento de una determinada ciudad
            String fichero = folder+pais+"/"+ciudad+"/EstacionAparcamientos.html";
            //Se define el documento para parsear los datos
            Document htmlFile = Jsoup.parse(new File(fichero), "UTF-8");
            //Obtener los elementos del tag body
            Elements elementos = htmlFile.body().children();
           //para cada uno de los elementos
            for (int i = 0; i < elementos.size(); i++) {
                //se obtienen los datos del elemento
                Element elemento = elementos.get(i);
                //Se obtienen los hijos del elemento analizado
                Elements elements = elemento.children();
                EstacionAparcamiento ea = new EstacionAparcamiento();
                boolean acces = false;
                boolean libres = false;
                boolean total = false;
                // para cada uno de los hijos
                 for (int j = 0; j < elements.size(); j++) {
                     //se obtienen los datos del hijo
                    Element el = elements.get(j);
                    //se obtiene todo el hijo en un string
                    String atributo = el.attributes().toString();
                    //dependiendo de los datos que contiene el hijos se coge el valor del dato
                    // y se guarda en la clase EstacionAparcamiento
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
                    else if (atributo.contains("itemprop=\"accessibility\"")) {
                        ea.setAccesibilidad(Integer.parseInt(el.text()));
                        acces = true;
                    }
                    else if (atributo.contains("itemprop=\"total\"")) {
                        ea.setPlazasTotales(Integer.parseInt(el.text()));
                        libres = true;
                    }
                    else if (atributo.contains("itemprop=\"free\"")) {
                        ea.setPlazasLibres(Integer.parseInt(el.text()));
                        total = true;
                    }
 
                 }
                 //Si no se propociona la accesibilidad, las plazas libres y las 
                 //plazas totales  se pone un -1 en las variables. 
                 int num = -1;
                 if (!acces) 
                     ea.setAccesibilidad(num);
                 if (!libres) 
                     ea.setPlazasLibres(num);
                 if (!total) 
                     ea.setPlazasTotales(num);
                 
                 result.add(ea);
            }

            return result;

        } catch (IOException ex) {
            Logger.getLogger(CtrlEstacionAparcamientoDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
                
    }
    
    /**
     * Obtener las paradas de Aparcamientos cercanas a una ubicación
     * @param ciudad: nombre de la ciudad
     * @param pais: nombre del pais
     * @param direccion: dirección 
     * @param lat: latitud de una ubicación
     * @param lon: longitud de una ubicación
     * @return lista con los aparcamientos cercanos a una ubicación
     */
    @Override
    public ArrayList<EstructurasPublicas> getParadasCercanas(String ciudad, 
            String pais, String direccion, Double lat, Double lon) {
        ArrayList<EstructurasPublicas> result = new ArrayList<>();
        try {
            String fichero = folder+pais+"/"+ciudad+"/EstacionAparcamientos.html";
            return obtenerParadasCercanas(fichero, direccion, lat, lon);
        } catch (Exception ex) {
            Logger.getLogger(CtrlEstacionAparcamientoDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
