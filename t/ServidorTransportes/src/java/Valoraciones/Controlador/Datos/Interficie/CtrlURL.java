/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Valoraciones.Controlador.Datos.Interficie;


import Valoraciones.Ficheros.URL;
import java.util.ArrayList;


/**
 *
 * @author raquel
 */
public interface CtrlURL {
    
    //obtener todas las url de una ciudad que están guardadas en el sistema
    public ArrayList<URL> getURLs(String ciudad, String pais) ;
    
    //Añadir una url de los datos de una ciudad. en el sistema
    public void anadirURL(String ciudad, String pais, String nomReferencia, 
            String url, String fecha, String actual);
}
