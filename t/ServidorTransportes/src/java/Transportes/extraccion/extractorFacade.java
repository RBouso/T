/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transportes.extraccion;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author raquel
 */
public class extractorFacade {
    private String tipo[] = {"application/vnd.ms-excel","application/rdf+xml","application/xml","text/csv"};
    
    public void extraerDatos(String nomFichero, String url, String referencia, String ciudad, String pais) {
        try {

            String folder = "descargas/"+pais+"/"+ciudad+"/"+ nomFichero;
            Path source = Paths.get(folder);
            String formato =  Files.probeContentType(source);
            System.out.println("formato "+ formato);
            Formato f = null;
           if (tipo[0].equals(formato)){
                f = new XLS();
            } 
            else if (tipo[1].equals(formato)){
                f = new RDF();
            }
            else if (tipo[2].equals(formato)){
                f = new XML();
            }
            else if (tipo[3].equals(formato)){
                f = new CSV();
               // csv.extraerDatos(nomFichero, referencia, url, ciudad, pais); 
            }
           f.extraerDatos(nomFichero, referencia, url, ciudad, pais); 
        } catch (IOException ex) {
            Logger.getLogger(extractorFacade.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
