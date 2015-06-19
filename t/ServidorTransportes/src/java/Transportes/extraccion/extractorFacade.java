/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transportes.extraccion;


import java.io.IOException;
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
    private String tipo[] = {"application/vnd.ms-excel","application/rdf+xml","application/xml","text/csv","text/xml"};
    
    /**
     * Extrae los datos en el formato correspondiente al archivo que contiene los datos
     * @param nomFichero: nombre del fichero
     * @param url: url donde se puede encontrar el fichero
     * @param referencia: referencia del fichero
     * @param ciudad: nombre de la ciudad a la que pertenece el fichero
     * @param pais : nombre del país al que pertenece el fichero
     */
    public void extraerDatos(String nomFichero, String url, String referencia, String ciudad, String pais) {
        try {

            String folder = "descargas/"+pais+"/"+ciudad+"/"+ nomFichero;
            Path source = Paths.get(folder);
            //Se obtiene el formato del archivo
            String formato =  Files.probeContentType(source);
            Formato f = null;
           if (tipo[0].equals(formato)){
                f = new XLS();
            } 
            else if (tipo[1].equals(formato)){
                f = new RDF();
            }
            else if (tipo[2].equals(formato) || tipo[4].equals(formato)){
                f = new XML();
            }
            else if (tipo[3].equals(formato)){
                f = new CSV(); 
            }
           if (f!= null)
            f.extraerDatos(nomFichero, referencia, url, ciudad, pais); 
        } catch (IOException ex) {
            Logger.getLogger(extractorFacade.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
