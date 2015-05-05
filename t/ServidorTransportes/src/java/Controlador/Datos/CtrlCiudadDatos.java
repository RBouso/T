/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Datos;

import Controlador.Datos.Interficie.CtrlCiudad;
import Controlador.Dominio.CtrlFactoriaDatos;
import Ficheros.Ciudad;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author raquel
 */
public class CtrlCiudadDatos implements CtrlCiudad{
        private String folder = "ficheros/";
        
     public void anadirCiudad(String nombre, String pais) {
        try {
            File f = new File(folder+pais+"/"+nombre);
            f.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(CtrlFactoriaDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Ciudad> getCiudades() {
        ArrayList<Ciudad> ac = new ArrayList();
        File f = new File(folder);
        File[] paises = f.listFiles();
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

    @Override
    public List<String> getTransportes(String ciudad) {
        ArrayList<String> result = new ArrayList<>();
        File f = new File(folder);
        File[] paises = f.listFiles();
        Boolean encontrada = false;

        for (int i = 0; i < paises.length && !encontrada; i++) {
            File[] ciudades = paises[i].listFiles();

            for (int j = 0; j < ciudades.length && !encontrada; j++) {
                if (ciudades[j].getName().equalsIgnoreCase(ciudad)) {
                    encontrada = true;
                    File[]  transp = ciudades[i].listFiles();
                    for (int k = 0; k < transp.length; k++) {
                        String nombre = transp[k].getName();
                        if (nombre.contains(".html")) {
                            int ult =nombre.indexOf(".");
                            result.add(nombre.substring(0, ult));
                        }
                    }
                }
                
            }
        }
        return result;
    }

    
}
