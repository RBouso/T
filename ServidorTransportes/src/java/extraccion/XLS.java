/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extraccion;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
    private URLConnection con; 
    private String folder = "descargas/";
    private String nombre;
    
    public void extraerDatos(String nomFichero, String referencia, String url, String ciudad, String pais) {

        try {
            if (referencia.contains("Transporte"))
                super.transporte = true;
            super.furl = url;
            super.ciudad = ciudad;
            super.pais = pais;
            super.referencia = referencia;
            super.fichero = "ficheros/"+pais+"/"+ciudad;
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
                        //if (cabecera.equals("Anclajes")) System.out.println(bicicletas[1][0].compareToIgnoreCase(cabecera));
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
