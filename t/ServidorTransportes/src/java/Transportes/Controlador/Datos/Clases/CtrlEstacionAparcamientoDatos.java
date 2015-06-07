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
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

/**
 *
 * @author raquel
 */
public class CtrlEstacionAparcamientoDatos extends CtrlEstructurasPublicasDatos 
implements CtrlEstacionAparcamiento {

    private String folder =  "/home/raquel/NetBeansProjects/ServidorTransportes/ficheros/";
    
    public CtrlEstacionAparcamientoDatos() {
    }

    @Override
    public ArrayList<EstacionAparcamiento> getAparcamiento(String ciudad, String pais) {
        try {
            ArrayList<EstacionAparcamiento> result = new ArrayList<>();
            String fichero = folder+pais+"/"+ciudad+"/EstacionAparcamientos.html";
            Document htmlFile = Jsoup.parse(new File(fichero), "UTF-8");
            Elements elementos = htmlFile.body().children();
           
            for (int i = 0; i < elementos.size(); i++) {
                Element elemento = elementos.get(i);
                Elements elements = elemento.children();
                EstacionAparcamiento ea = new EstacionAparcamiento();
                boolean acces = false;
                boolean libres = false;
                boolean total = false;
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
    
    @Override
    public ArrayList<EstructurasPublicas> getParadasCercanas(String ciudad, 
            String pais, String direccion, Double lat, Double lon) {
        try {
            String fichero = folder+pais+"/"+ciudad+"/EstacionAparcamientos.html";
            return obtenerParadasCercanas(fichero, direccion, lat, lon);
        } catch (Exception ex) {
            Logger.getLogger(CtrlEstacionAparcamientoDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
