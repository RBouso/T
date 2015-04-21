/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Dominio;

import Controlador.Datos.Interficie.CtrlCiudad;
import Controlador.Datos.Interficie.CtrlURL;
import Ficheros.Ciudad;
import Ficheros.URL;
import ServicioDatosAbiertos.ServicioAdapter;
import extraccion.extractorFacade;
import java.util.ArrayList;

/**
 *
 * @author raquel
 */
public class ControladorExtraccion extends Controlador{
    
    /**
     * Se realiza todo el proceso de obtener las urls que se encuentran en el 
     * sistema para descargar los ficheros, y posteriormente extraer los datos de 
     * esos ficheros para integrarlos en un modelo unico.
     */
    public void executar(){
        CtrlFactoriaDatos cfd = new CtrlFactoriaDatos();
        //Conseguir todas las ciudades que hay en el sistema
        CtrlCiudad cc = cfd.getCtrlCiudad();
        CtrlURL cu = cfd.getCtrlURL();
        extractorFacade e = new extractorFacade();
        ArrayList<Ciudad> ciudades =  cc.getCiudades();
        //Para cada ciudad que hay en el sistema extraemos los datos de sus ficheros
        for (int i = 0; i < ciudades.size(); i++) {
            String nombre = ciudades.get(i).getNombre();
            String pais = ciudades.get(i).getPais(); 
            //Conseguir todas las urls que tiene la ciudad y pais
            ArrayList<URL> au = cu.getURLs(nombre, pais);
            ServicioAdapter sa = new ServicioAdapter();
            for (int j = 0; j < au.size(); j++) {
                //Descargamos el fichero
                String nomFichero = sa.getFichero(au.get(j).getUrl(),nombre, pais);
                //Si el String no esta vacio extraemos los datos del fichero
                if (!nomFichero.isEmpty()) {
                    //System.out.println(nomFichero);
                    
                    e.extraerDatos(nomFichero,au.get(j).getUrl(), au.get(j).getNombreReferencia(), nombre, pais);
                }
            }
        //for (int i = 0; i < )
        }
    }
}
