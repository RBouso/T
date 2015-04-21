/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extraccion;

import com.csvreader.CsvReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author raquel
 */
public class CSV extends Formato{
    private URLConnection con; 
    private String folder = "descargas/";
    private String nombre;
    
    
    public void extraerDatos(String nomFichero, String referencia, String url, 
            String ciudad, String pais) {
        try {
            if (referencia.contains("Transporte"))
                super.transporte = true;
            super.furl = url;
            super.ciudad = ciudad;
            super.pais = pais;
            super.referencia = referencia;
            super.fichero = "ficheros/"+pais+"/"+ciudad;
            folder = folder+pais+"/"+ciudad+"/"+nomFichero;
            CsvReader f = new CsvReader(folder);
            //leer archivo
            f.readHeaders();

            int ultimo = f.getHeaderCount();
            int j = 0;
            while (f.readRecord()){
                
                super.inicializar_variables();
                
                //System.out.println(fichero.getHeaderCount());
                for (int i = 0; i < ultimo; i++) {
                    String header = f.getHeader(i);
                    String valor = f.get(i);
                    super.obtenerDatos(header, valor);
                    
                }
                 super.integrarDatos(j, ultimo-1);
                 j++;
            }
           
            f.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CSV.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CSV.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
}
