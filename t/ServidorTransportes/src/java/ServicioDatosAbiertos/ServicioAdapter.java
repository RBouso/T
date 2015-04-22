/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicioDatosAbiertos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author raquel
 */
public class ServicioAdapter {
    private String folder = "descargas/";
    
    /**
     * Descarga el fichero que se encuentra en la url y lo copia en la carpeta 
     * descargas/pais/ciudad/
     * @param url: es la url del servicio donde se encuentra el fichero
     * @param ciudad: es la ciudad a la que pertenecen los datos de la url
     * @param pais: es el pais al que pertenecen los datos de la url 
     */
    public String getFichero(String url, String ciudad, String pais) {
        String nombre = "";
        try {
            URL ur = new URL(url);
            URLConnection con = ur.openConnection();
            InputStream is = con.getInputStream();

            File f = new File(folder+pais); 
            //crear carpetas pais y ciudad si no existen
            if (!f.exists())  {
                f.mkdir();
                  
            }
            f = new File(folder+pais+"/"+ciudad);
            if (!f.exists())  
                f.mkdir();
            
            int ultimo =url.lastIndexOf("/");
            
            nombre = url.substring(ultimo+1);
            if (con.getContentType().contains("csv") && !nombre.contains(".csv"))
                nombre = nombre+".csv";
            f = new File(folder+pais+"/"+ciudad+"/"+ nombre);
            // Fichero en el que queremos guardar el contenido
            FileOutputStream fos = new FileOutputStream(f);
 
            // buffer para ir leyendo.
              byte [] array = new byte[1000];
               System.out.println("Servicio "+con.getContentType());
            // Primera lectura y bucle hasta el final
            int leido = is.read(array);
            while (leido > 0) {
                fos.write(array,0,leido);
                leido=is.read(array);
                
            }
 
            // Cierre de conexion y fichero.
            is.close();
            fos.close();
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(ServicioAdapter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServicioAdapter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nombre;
    }
    
}
