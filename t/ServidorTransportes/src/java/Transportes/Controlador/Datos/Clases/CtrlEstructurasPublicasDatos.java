/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transportes.Controlador.Datos.Clases;

import Transportes.Controlador.Datos.Interficie.CtrlEstructurasPublicas;
import Transportes.Ficheros.Autobus;
import Transportes.Ficheros.CoordinadasGeo;
import Transportes.Ficheros.DireccionPostal;
import Transportes.Ficheros.EstacionAlquilerBicicletas;
import Transportes.Ficheros.EstacionAparcamiento;
import Transportes.Ficheros.EstacionCivica;
import Transportes.Ficheros.EstacionTaxi;
import Transportes.Ficheros.EstructurasPublicas;
import Transportes.Ficheros.Ferrocarriles;
import Transportes.Ficheros.Funicular;
import Transportes.Ficheros.Linea;
import Transportes.Ficheros.Metro;
import Transportes.Ficheros.Teleferico;
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
public abstract class CtrlEstructurasPublicasDatos implements CtrlEstructurasPublicas{
    
    private String transporte = ""; //nombre del transporte
    private double medida = 0.0045; //radio para obtener las proximidades de un punto
    
    //Obtener las paradas cercanas de un determinado transporte
    @Override
    public abstract ArrayList<EstructurasPublicas> getParadasCercanas(String ciudad, 
            String pais, String direccion, Double lat, Double lon);
    
     /**
     * Obtener las paradas de un determinado transporte, cercanas a una localización
     * @param fichero: path donde encontrar los datos de un determinado transporte
     * @param direccion: dirección sobre la que buscar las estaciones cercanas
     * @param lat: latitud de una ubicación
     * @param lon: longitud de una ubicación
     * @return lista de las estaciones de un determinado transporte, cercanas a la dirección
     * o a la latitud y longitud proporcionadas
     */
    protected ArrayList<EstructurasPublicas> obtenerParadasCercanas(String fichero, 
            String direccion, Double lat, Double lon) throws Exception {
        try {
            Geocoding ge = new Geocoding();
           
            // si no se proporciona la latitud y la longitud se encuentran a traves de la direccion proporcionada
            if(lat == 0.0 && lon == 0.0) {

                Point2D.Double coordinates = ge.getCoordinates(direccion);

                if (coordinates.equals(new Point2D.Double(0.0,0.0))) {
                        Exception e = new Exception("la dirección no es valida.");
                        throw e;
                }
                else {
                    lat = coordinates.x;
                    lon = coordinates.y;
                }
            }
           
            ArrayList<EstructurasPublicas> result = new ArrayList<>();
            Document htmlFile = Jsoup.parse(new File(fichero), "UTF-8");
            //Se obtienen los elementos del tag body
            Elements elementos = htmlFile.body().children();
            
            for (int i = 0; i < elementos.size(); i++) {
               //se obtiene el elemento i
                Element elemento = elementos.get(i);
                //Se selecciona los datos de la latitud y longitud de cada elemento
                Elements select = elemento.select("div[itemprop=geo]");
                // si se encuentra dentro del radio cercano a una ubicación se añaden todos los datos a 
                //la lista de estaciones
                if( select.size() > 0) {
                    Double la = Double.parseDouble(select.get(0).children().first().attributes().get("content"));
                    Double lo = Double.parseDouble(select.get(0).children().last().attributes().get("content"));
                    if (la >= (lat - medida) && la <= (lat + medida) && lo >= (lon - medida) 
                            && lo <= (lon +medida)) {
                        Elements elements = elemento.children();
                        EstructurasPublicas ea = construir(fichero);
                        ea.transporte = transporte;
                        Boolean acces = false;
                        Boolean libres = false;
                        Boolean total = false;
                        Boolean libresb = false;
                        Boolean anclajes = false;
                        EstacionAparcamiento ap = new EstacionAparcamiento();
                        EstacionAlquilerBicicletas b = new EstacionAlquilerBicicletas();
                        EstacionCivica ec = new EstacionCivica();
                        ArrayList<Linea> lin = new ArrayList<>();
                        for (int j = 0; j < elements.size(); j++) {
                            Element el = elements.get(j);
                            String atributo = el.attributes().toString();
                            if (atributo.contains("itemprop=\"name\"")) {
                                ea.setNombre(el.text());
                                ec.setNombre(el.text());
                            }
                            else if (atributo.contains("itemprop=\"description\"")) {
                                ea.setDescripcion(el.text());
                                ec.setDescripcion(el.text());
                            }
                            else if (atributo.contains("itemprop=\"geo\"")){
                                Elements geo = el.children();
                                CoordinadasGeo g = new CoordinadasGeo();
                                g.setLatitud(Double.parseDouble(geo.get(0).attributes().get("content")));
                                g.setLongitud(Double.parseDouble(geo.get(1).attributes().get("content")));
                                ea.setGeo(g);
                                ec.setGeo(g);
                            }
                            else if (atributo.contains("itemprop=\"address\"")){
                                Elements dir = el.children();
                                DireccionPostal dp = new DireccionPostal();
                                dp.setPais(dir.get(0).text());
                                dp.setLocalidad(dir.get(1).text());
                                dp.setRegion(dir.get(2).text());
                                dp.setDireccion(dir.get(3).text());
                                ea.setDireccion(dp);
                                ec.setDireccion(dp);
                            }
                            else if (atributo.contains("itemprop=\"telephone\"")) {
                                ea.setTelefono(el.text());
                                ec.setTelefono(el.text());
                            }
                            else if (atributo.contains("itemprop=\"accessibility\"")) {
                                ap.setAccesibilidad(Integer.parseInt(el.text()));
                                acces = true;
                            }
                            else if (atributo.contains("itemprop=\"total\"")) {
                                ap.setPlazasTotales(Integer.parseInt(el.text()));
                                libres = true;
                            }
                            else if (atributo.contains("itemprop=\"free\"")) {
                                ap.setPlazasLibres(Integer.parseInt(el.text()));
                                total = true;
                            }
                            else if (atributo.contains("itemprop=\"slots\"")) {
                                b.setAnclajes(Integer.parseInt(el.text()));
                                anclajes = true;
                            }
                            else if (atributo.contains("itemprop=\"bikes\"")) {
                                b.setBiciLibres(Integer.parseInt(el.text()));
                                libresb = true;
                            }
                             else if (atributo.contains("itemprop=\"line\"")) {
                                Linea l = new Linea();
                                l.setNumLinea(el.text());
                                lin.add(l);
                            }
                        }
                        int num = -1;
                        if (!acces) 
                         ap.setAccesibilidad(num);
                        if (!libres) 
                         ap.setPlazasLibres(num);
                        if (!total) 
                         ap.setPlazasTotales(num);
                        if (!libresb) 
                         b.setBiciLibres(num);
                        if (!anclajes) 
                         b.setAnclajes(num);
                        if (acces || libres || total) {
                            ea.setEa(ap);
                        }
                         if (anclajes || libresb) {
                            ea.setEab(b);
                        }
                         if(!lin.isEmpty()) {
                            ec.setLineas(lin);
                            ea.setEc(ec);
                         }
                        result.add(ea);
                    }
                }
                
            }
            return result;
        } catch (IOException ex) {
            Logger.getLogger(CtrlEstacionCivicaDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
   
    /**
     * Obtener la constructora de un determinado transporte
     * @param transporte: nombre del transporte
     * @return 
     */
private EstructurasPublicas construir(String transporte) {
        EstructurasPublicas est = null;
        if(transporte.contains("Autobus")) {
            est = new Autobus();
            this.transporte = "Autobus";
        }
        else if (transporte.contains("Metro")) {
            est = new Metro();
            this.transporte = "Metro";
        }
        else if (transporte.contains("Tranvia")) {
            est = new Tranvia();
            this.transporte = "Tranvia";
        }
        else if (transporte.contains("Ferrocarriles")) {
            est = new Ferrocarriles();
            this.transporte = "Ferrocarriles";
        }
        else if (transporte.contains("Funicular")) {
            est = new Funicular();
            this.transporte = "Funicular";
        }
        else if (transporte.contains("Teleferico")) {
            est = new Teleferico();
            this.transporte = "Teleferico";
        }
        else if (transporte.contains("Taxi")) {
            est = new EstacionTaxi();
            this.transporte = "Taxi";
        }
        else if (transporte.contains("Aparcamiento")) {
            est = new EstacionAparcamiento();
            this.transporte = "Aparcamiento";
        }
        else if (transporte.contains("Bicicleta")) {
            est = new EstacionAlquilerBicicletas();
            this.transporte = "Bicicleta";
        }
        return est;
    }
}
