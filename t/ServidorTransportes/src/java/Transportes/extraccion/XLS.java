/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transportes.extraccion;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 *
 * @author raquel
 */
public class XLS extends Formato{

    /**
    * Extrae los datos de un fichero XLS
    * @param nomFichero: nombre del fichero que contiene los datos
    * @param referencia: referencia del fichero
    * @param url: url donde se puede encontrar el fichero
    * @param ciudad: ciudad a donde pertenecen los datos
    * @param pais: pais a donde pertenecen los datos
    */
    @Override
    public void extraerDatos(String nomFichero, String referencia, String url, String ciudad, String pais) {

        try {
            if (referencia.contains("Transporte"))
                super.transporte = true;
            super.furl = url;
            super.ciudad = ciudad;
            super.pais = pais;
            super.referencia = referencia;
            super.fichero = fichero+pais+"/"+ciudad;
            folder = folder+pais+"/"+ciudad+"/"+nomFichero;
            //se crea el archivo excel
            Workbook archivoExcel = Workbook.getWorkbook(new File( folder)); 

            
            // Recorre cada hoja  
            for (int sheetNo = 0; sheetNo < archivoExcel.getNumberOfSheets(); sheetNo++) {                                                                                                                                                  
             
                Sheet hoja = archivoExcel.getSheet(sheetNo); 
                int numColumnas = hoja.getColumns(); 
                int numFilas = hoja.getRows(); 

                // Recorre cada fila de la hoja 
                for (int fila = 1; fila < numFilas; fila++) { 
                    // Recorre cada columna de la fila 
                    super.inicializar_variables();
                
                    for (int columna = 0; columna < numColumnas; columna++) { 
                        String cabecera = hoja.getCell(columna, 0).getContents();
                        String data = hoja.getCell(columna, fila).getContents();
                        super.obtenerDatos(cabecera, data);
                        
                    }
                    super.integrarDatos(fila-1, numFilas-2);

                }
            }
            archivoExcel.close();
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(XLS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XLS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(XLS.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }


    
}
