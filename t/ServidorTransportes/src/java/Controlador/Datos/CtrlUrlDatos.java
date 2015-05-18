/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Datos;

import Controlador.Datos.Interficie.CtrlURL;
import Controlador.Dominio.CtrlFactoriaDatos;
import Ficheros.URL;
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

/**
 *
 * @author raquel
 */
public class CtrlUrlDatos implements CtrlURL{
    private String folder = "/home/raquel/NetBeansProjects/ServidorTransportes/ficheros/";

   
    
    public ArrayList<URL> getURLs(String ciudad, String pais) {
        FileReader fr = null;
        ArrayList<URL> au = new ArrayList();
        try {
            File f = new File(folder+pais+"/"+ciudad+"/URL.txt");
            fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String texto = "";
            while((texto=br.readLine())!=null) {
                int nref = texto.indexOf(":");
                int npurl = texto.indexOf("url: ");
                int nfurl = texto.lastIndexOf(": ");
                String ref = texto.substring(nref+2,npurl);
                String urlstring = texto.substring(nfurl+2);
                
                URL ur = new URL(urlstring, ref);
                au.add(ur);
            }
            br.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CtrlFactoriaDatos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CtrlFactoriaDatos.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(CtrlFactoriaDatos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return au;
        
    }
    
   public void anadirURL(String ciudad, String pais, String nomReferencia, String url) {
            try {
                int ultimo =url.lastIndexOf("/");
                String nombre = url.substring(ultimo+1);

                File f = new File(folder+pais); 
                if (!f.exists())  {
                    f.mkdir();
                    //wr.write("nomReferencia: "+nomReferencia+ " url: "+ url+"\n");
                }
                f = new File(folder+pais+"/"+ciudad);
                 if (!f.exists())  
                    f.mkdir();

                File fichero = new File (folder+pais+"/"+ciudad+"/URL.txt");
                Boolean b = true;
                if (!(b=fichero.exists())) 
                    fichero.createNewFile();
                
                //FileWriter w = new FileWriter(fichero);
               BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fichero,true), "utf-8"));
               // PrintWriter wr = new PrintWriter(w);
               
                if (!b){
                    bw.write("nomReferencia: "+
                            nomReferencia+ " url: "+ url+"\n");
                    
                }
                else {
                    if(!existeURL(nomReferencia, ciudad, pais)) bw.append("nomReferencia: "+
                            nomReferencia+ " url: "+ url+"\n");
                    
                }
                bw.close();
                
                //wr.close();

           
            } catch (IOException ex) {
                    Logger.getLogger(CtrlFactoriaDatos.class.getName()).log(Level.SEVERE, null, ex);
        
        }
    }

    private boolean existeURL(String nomReferencia, String ciudad, String pais) {
        FileReader fr = null;
        try {
            File f = new File(folder+pais+"/"+ciudad+"/URL.txt");
            fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String texto = "";
            while((texto=br.readLine())!=null) {
                if (texto.contains(nomReferencia)) return true;
            }
            br.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CtrlFactoriaDatos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CtrlFactoriaDatos.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
                
            } catch (IOException ex) {
                Logger.getLogger(CtrlFactoriaDatos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }  
}
