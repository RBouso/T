/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Valoraciones.Controlador.Datos.Clases;

import Valoraciones.Controlador.Datos.Interficie.CtrlURL;
import FactoriaDatos.CtrlFactoriaDatos;
import Transportes.Ficheros.Constantes;
import Valoraciones.Ficheros.URL;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
public class CtrlUrlDatos implements CtrlURL{
    
    private Constantes cons = new Constantes();
    int numElemento= 0;
   
    
    public ArrayList<URL> getURLs(String ciudad, String pais) {
        ArrayList<URL> au = new ArrayList<URL>();
        try {
            File fichero = new File (cons.valoraciones+pais+"/"+ciudad+"/URL.html");
            Document htmlFile = Jsoup.parse(fichero, "UTF-8");
            Elements children = htmlFile.body().children();
            for (int i = 0; i < children.size(); i++) {
                Elements children1 = children.get(i).children();
                URL ur = new URL();
                ur.setNombreReferencia(children1.get(0).text());
                ur.setUrl(children1.get(1).text());
                ur.setFechaActualizacion(children1.get(2).text());
                ur.setActualizacion(children1.get(3).text());
                au.add(ur);
            }
        } catch (IOException ex) {
            Logger.getLogger(CtrlEstacionValoradaDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return au;
        
    }
    
   public void anadirURL(String ciudad, String pais, String nomReferencia, String url, String fecha, String actual) {
            try {
                int ultimo =url.lastIndexOf("/");
                String nombre = url.substring(ultimo+1);

                File f = new File(cons.valoraciones+pais); 
                if (!f.exists())  {
                    f.mkdir();
                    //wr.write("nomReferencia: "+nomReferencia+ " url: "+ url+"\n");
                }
                f = new File(cons.valoraciones+pais+"/"+ciudad);
                 if (!f.exists())  
                    f.mkdir();

                 

                File fichero = new File (cons.valoraciones+pais+"/"+ciudad+"/URL.html");
                Boolean b = true;
                if (!(b=fichero.exists())) {
                        fichero.createNewFile();
                }
                Document htmlFile = Jsoup.parse(fichero, "UTF-8");
                if (!b) {
                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fichero,false), "utf-8"));
                        bw.write("<!DOCTYPE html> \n");
                        bw.write("<html> \n");
                        bw.write("<body> \n");
                        String html = "<div itemscope itemtype=\"http://schema.org/URL\"> \n"+
                                "<span itemprop=\"reference\"> "+nomReferencia+"</span>\n"+
                                "<span itemprop=\"url\"> "+url+"</span>\n"+
                                "<span itemprop=\"date\"> "+fecha+"</span>\n"+
                                "<span itemprop=\"period\"> "+actual+"</span>\n"+
                                 "</div>\n";
                        bw.write(html);
                        bw.write("</body>\n");
                        bw.write("</html> \n");
                        bw.close();
                }

                 else if (!existeURL(nomReferencia, ciudad, pais )){
                
                
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fichero,false), "utf-8"));
                        String html = "<div itemscope itemtype=\"http://schema.org/URL\"> \n"+
                                "<span itemprop=\"reference\"> "+nomReferencia+"</span>\n"+
                                "<span itemprop=\"url\"> "+url+"</span>\n"+
                                "<span itemprop=\"date\"> "+fecha+"</span>\n"+
                                "<span itemprop=\"period\"> "+actual+"</span>\n"+
                                 "</div>\n";

                        Element body = htmlFile.body();
                        body.append(html);
                        
                        bw.write(htmlFile.toString());
                        bw.close();
            }       
            else {
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fichero,false), "utf-8"));
                    
               
                Element child = htmlFile.body().child(numElemento);
                Element get = child.children().get(2);
                get.text(fecha);
                
                Element punt = child.children().get(3);
                punt.text(actual);
                
                bw.write(htmlFile.toString());
                bw.close();
            }
           
     
           
            } catch (IOException ex) {
                    Logger.getLogger(CtrlFactoriaDatos.class.getName()).log(Level.SEVERE, null, ex);
        
        }
    }

    private boolean existeURL(String nomReferencia, String ciudad, String pais) {
       try {
            File fichero = new File (cons.valoraciones+pais+"/"+ciudad+"/URL.html");
            Document htmlFile = Jsoup.parse(fichero, "UTF-8");
            Elements children = htmlFile.body().children();
            for (int i = 0; i < children.size(); i++) {
                Elements children1 = children.get(i).children();
                
                if (children1.get(0).text().equals(nomReferencia)) {
                    
                    numElemento = i;
                    return true;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(CtrlEstacionValoradaDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }  

  
}
