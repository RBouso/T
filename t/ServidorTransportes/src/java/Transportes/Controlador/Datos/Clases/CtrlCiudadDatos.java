/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transportes.Controlador.Datos.Clases;

import Transportes.Controlador.Datos.Interficie.CtrlCiudad;
import FactoriaDatos.CtrlFactoriaDatos;
import Transportes.Ficheros.Ciudad;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author raquel
 */
public class CtrlCiudadDatos implements CtrlCiudad{
    //path donde se encuentran los ficheros que contienen los datos
    private String folder = "/home/raquel/NetBeansProjects/ServidorTransportes/ficheros/";
    
    /**
     * Añade una ciudad a la base de datos
     * @param nombre: nombre de la ciudad
     * @param pais : nombre del pais
     */
    @Override
    public void anadirCiudad(String nombre, String pais) {
        try {
            File pai = new File(folder+pais);
            if (!pai.exists()) pai.createNewFile();
            File f = new File(folder+pais+"/"+nombre);
            f.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(CtrlFactoriaDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Obtener todas las ciudades guardadas en la base de datos
     * @return lista de ciudades 
     */
    @Override
    public ArrayList<Ciudad> getCiudades() {
        ArrayList<Ciudad> ac = new ArrayList();
        File f = new File(folder);
        //obtiene los paises
        File[] paises = f.listFiles();
        //se recorre cada país para obtener la ciudades
        for (int i = 0; i < paises.length; i++) {
            File[] ciudades = paises[i].listFiles();
            String nomPais = paises[i].getName();
            for (int j = 0; j < ciudades.length; j++) {
                Ciudad c = new Ciudad(ciudades[j].getName(), nomPais);
                ac.add(c);
            }
        }
        return ac;
    }

    /**
     * Obtiene todos los transportes que tiene una ciudad
     * @param ciudad: nombre de la ciudad
     * @return lista de los transportes que contiene una ciudad
     */
    @Override
    public ArrayList<String> getTransportes(String ciudad) {
        ArrayList<String> result = new ArrayList<>();
        File f = new File(folder);
        File[] paises = f.listFiles();
        Boolean encontrada = false;
        //se recorren todos los paise buscando la ciudad para coger sus transportes
        for (int i = 0; i < paises.length && !encontrada; i++) {
            File[] ciudades = paises[i].listFiles();
            for (int j = 0; j < ciudades.length && !encontrada; j++) {
                if (ciudades[j].getName().equalsIgnoreCase(ciudad)) {
                    encontrada = true;
                    File[]  transp = ciudades[j].listFiles();
                    
                    for (int k = 0; k < transp.length; k++) {
                        String nombre = transp[k].getName();
                        if (nombre.contains("Aparcamiento"))
                            result.add("Aparcamiento");
                        else if (nombre.contains("Bicicleta"))
                            result.add("Bicicletas");
                        else if (nombre.contains(".html")) {
                            int ult =nombre.indexOf(".");
                            result.add(nombre.substring(0, ult));
                            
                        }
                    }
                }
                
            }
        }
        return result;
    }
    
    /**
     * Se obtienen los nombres de los transportes públicos de una ciudad,
     * que proporcionan sus horarios
     * @param ciudad: nombre de la ciudad
     * @param pais: nombre del pais
     * @return lista con los nombre públicos de los transportes de una ciudad
     */
    @Override
    public ArrayList<String> getTransportesHorarios(String ciudad, String pais) {
        ArrayList<String> result = new ArrayList<>();
        File f = new File(folder+"/"+pais+"/"+ciudad);
        if(f.exists()) {
            //se define el path al que acceder
            f = new File(folder+"/"+pais+"/"+ciudad+"/Horarios");
            if (f.exists()) {
                // se obtienen los ficheros que hay en el path
                File[] files = f.listFiles();
                //Se añaden los nombres de los ficheros a la lista
                for (int i = 0; i < files.length; i++) {
                    String nombre = files[i].getName();
                        if (nombre.contains("Aparcamiento"))
                            result.add("Aparcamiento");
                        else if (nombre.contains("Bicicleta"))
                            result.add("Bicicletas");
                        else if (nombre.contains(".html")) {
                            int ult =nombre.indexOf(".");
                            result.add(nombre.substring(0, ult));
                            
                        }
                }
            }
        }
        return result;
    }

    
}
