/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Datos;

import Controlador.Datos.Interficie.CtrlEstacionAlquilerBicicletas;
import Ficheros.CoordinadasGeo;
import Ficheros.DireccionPostal;
import Ficheros.EstacionAlquilerBicicletas;
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
public class CtrlEstacionAlquilerBicicletasDatos implements CtrlEstacionAlquilerBicicletas{
    
    private String folder =  "/home/raquel/NetBeansProjects/ServidorTransportes/ficheros/";
    
    @Override
    public ArrayList<EstacionAlquilerBicicletas> getBicicletas(String ciudad, 
            String pais) {
                try {
            ArrayList<EstacionAlquilerBicicletas> result = new ArrayList<>();
            String fichero = folder+pais+"/"+ciudad+"/EstacionAlquilerBicicletas.html";
            Document htmlFile = Jsoup.parse(new File(fichero), "UTF-8");
            Elements elementos = htmlFile.body().children();
           
            for (int i = 0; i < elementos.size(); i++) {
                Element elemento = elementos.get(i);
                Elements elements = elemento.children();
                EstacionAlquilerBicicletas ea = new EstacionAlquilerBicicletas();
                boolean anclajes = false;
                boolean libres = false;
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
                    else if (atributo.contains("itemprop=\"slots\"")) {
                        ea.setAnclajes(Integer.parseInt(el.text()));
                        anclajes = true;
                    }
                    else if (atributo.contains("itemprop=\"bikes\"")) {
                        ea.setBiciLibres(Integer.parseInt(el.text()));
                        libres = true;
                    }
                }
                int num = -1;
                if (!anclajes) 
                    ea.setAnclajes(num);
                if (!libres) 
                    ea.setBiciLibres(num);
                result.add(ea);
            }

            return result;

        } catch (IOException ex) {
            Logger.getLogger(CtrlEstacionAparcamientoDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
