/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transportes.extraccion;

import com.csvreader.CsvReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author raquel
 */
public class CSV extends Formato{

    
       /**
    * Extrae los datos de un fichero CSV
    * @param nomFichero: nombre del fichero que contiene los datos
    * @param referencia: referencia del fichero
    * @param url: url donde se puede encontrar el fichero
    * @param ciudad: ciudad a donde pertenecen los datos
    * @param pais: pais a donde pertenecen los datos
    */
    @Override
    public void extraerDatos(String nomFichero, String referencia, String url, 
            String ciudad, String pais) {
        try {
            if (referencia.contains("Transporte"))
                super.transporte = true;
            super.furl = url;
            super.ciudad = ciudad;
            super.pais = pais;
            super.referencia = referencia;
            super.fichero = super.fichero+pais+"/"+ciudad;
            folder = folder+pais+"/"+ciudad+"/"+nomFichero;
            CsvReader f = new CsvReader(folder, ';');
            //leer archivo
            f.readHeaders();

            int ultimo = f.getHeaderCount();
            int j = 0;
            while (f.readRecord()){
                
                super.inicializar_variables();
                
                for (int i = 0; i < ultimo; i++) {
                    String header = f.getHeader(i);
                    String valor = f.get(i);
                    super.obtenerDatos(header, valor);
                }
                if (referencia.contains("Tiempo"))
                    ;//super.integrarTiempos();
                else 
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
