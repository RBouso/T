/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Valoraciones.Controlador.Datos.Clases;

import Transportes.Ficheros.Constantes;
import Valoraciones.Controlador.Datos.Interficie.CtrlEstacionValorada;
import Valoraciones.Ficheros.EstacionValorada;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
public class CtrlEstacionValoradaDatos implements CtrlEstacionValorada{
    private Constantes cons = new Constantes();
    private int numElemento;
    private EstacionValorada estacion = new EstacionValorada();
    
    @Override
    public EstacionValorada anadirPuntuacion(int puntuacion, double latitud, 
            double longitud, String ciudad, String pais, boolean modificar) {
        EstacionValorada ev = new EstacionValorada();
        try {
            File f = new File(cons.valoraciones);
            if (!f.exists())  {
                f.mkdir();
                //wr.write("nomReferencia: "+nomReferencia+ " url: "+ url+"\n");
            }
            f = new File(cons.valoraciones+pais);
            if (!f.exists())  {
                f.mkdir();
                //wr.write("nomReferencia: "+nomReferencia+ " url: "+ url+"\n");
            }
            f = new File(cons.valoraciones+pais+"/"+ciudad);
            if (!f.exists())
                f.mkdir();
            
            File fichero = new File (cons.valoraciones+pais+"/"+ciudad+"/EstacionValorada.html");
            Boolean b = true;
            if (!(b=fichero.exists()))
                fichero.createNewFile();
            
            //FileWriter w = new FileWriter(fichero);
           
            // PrintWriter wr = new PrintWriter(w);
            int numUsuarios = 1;
            
            Document htmlFile = Jsoup.parse(fichero, "UTF-8");
            if (!b){
                 BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fichero,false), "utf-8"));
                bw.write("<!DOCTYPE html> \n");
                bw.write("<html> \n");
                bw.write("<body> \n");
                String html = "<div itemscope itemtype=\"http://schema.org/Place/Station\"> \n"+
                        "<div itemprop=\"geo\" itemscope itemtype=\"http://schema.org/GeoCoordinates\" > \n"+
                        "<meta itemprop=\"latitude\" content=\""+latitud+"\" /> \n"+"<meta itemprop=\"longitude\" content=\""+longitud+"\" />\n"+"</div> \n"+
                        "<span itemprop=\"usersTotal\"> "+numUsuarios+"</span>\n"+
                        "<div itemprop=\"aggregateRating\" itemscope itemtype=\"http://schema.org/AggregateRating\">\n" +
                         "<span itemprop=\"ratingValue\">"+puntuacion+"</span> \n"+
                        "</div>\n";
                bw.write(html);
                bw.write("</body>\n");
                bw.write("</html> \n");
                bw.close();
            }
           

            else if (!existeValoracion(latitud, longitud, ciudad, pais) ){
                
                
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fichero,false), "utf-8"));
                String html = "<div itemscope itemtype=\"http://schema.org/Place/Station\"> \n"+
                        "<div itemprop=\"geo\" itemscope itemtype=\"http://schema.org/GeoCoordinates\" > \n"+
                        "<meta itemprop=\"latitude\" content=\""+latitud+"\" /> \n"+"<meta itemprop=\"longitude\" content=\""+longitud+"\" />\n"+"</div> \n"+
                        "<span itemprop=\"usersTotal\"> "+numUsuarios+"</span>\n"+
                        "<div itemprop=\"aggregateRating\" itemscope itemtype=\"http://schema.org/AggregateRating\">\n" +
                         "<span itemprop=\"ratingValue\">"+puntuacion+"</span> \n"+
                        "</div>\n";
                        Element body = htmlFile.body();
                        body.append(html);
                        
                        bw.write(htmlFile.toString());
                        bw.close();
            }       
            else {
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fichero,false), "utf-8"));
               
                Element child = htmlFile.body().child(numElemento);
                Element get = child.children().get(1);
                if(modificar == true) numUsuarios = Integer.parseInt(get.text());
                else numUsuarios = Integer.parseInt(get.text())+1;
                get.text(String.valueOf(numUsuarios));
                Element punt = child.children().get(2).children().first();
                puntuacion = Integer.parseInt(punt.text()) + puntuacion;
                punt.text(String.valueOf(puntuacion));
                bw.write(htmlFile.toString());
                bw.close();
            }
            ev.setLatitud(latitud);
            ev.setLongitud(longitud);
            ev.setTotalUsuarios(numUsuarios);
            ev.setValoracionTotal(puntuacion);
            return ev;
        } catch (IOException ex) {
            Logger.getLogger(CtrlEstacionValoradaDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ev;
    }

    public boolean existeValoracion(double latitud, double longitud, String ciudad, String pais) {
        try {
            File fichero = new File (cons.valoraciones+pais+"/"+ciudad+"/EstacionValorada.html");
            Document htmlFile = Jsoup.parse(fichero, "UTF-8");
            Elements children = htmlFile.body().children();
            for (int i = 0; i < children.size(); i++) {
                Elements children1 = children.get(i).children();
                
                if (Double.parseDouble(children1.get(0).child(0).attributes().get("content")) == latitud 
                        && Double.parseDouble(children1.get(0).child(1).attributes().get("content")) == longitud) {
                    estacion.setTotalUsuarios(Integer.valueOf(children1.get(1).text()));
                    estacion.setValoracionTotal(Integer.valueOf(children1.get(2).child(0).text()));
                    numElemento = i;
                    return true;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(CtrlEstacionValoradaDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public EstacionValorada getEstacionValorada(Double latitud, double longitud, 
            String ciudad, String pais)  {
        if(existeValoracion(latitud, longitud, ciudad, pais)) {
            estacion.setLatitud(latitud);
            estacion.setLongitud(longitud);
        }
        else{
            estacion.setValoracionTotal(-1);
            estacion.setTotalUsuarios(1);
        }
        return estacion;
    }
}
