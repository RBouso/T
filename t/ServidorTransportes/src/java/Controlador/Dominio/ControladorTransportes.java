/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Dominio;

import Controlador.Datos.Interficie.CtrlAutobus;
import Controlador.Datos.Interficie.CtrlEstacionCivica;
import Controlador.Datos.Interficie.CtrlCiudad;
import Controlador.Datos.Interficie.CtrlEstacionAlquilerBicicletas;
import Controlador.Datos.Interficie.CtrlEstacionAparcamiento;
import Controlador.Datos.Interficie.CtrlLineaEstacion;
import Ficheros.Ciudad;
import Ficheros.EstacionAlquilerBicicletas;
import Ficheros.EstacionAparcamiento;
import Ficheros.EstacionCivica;
import Ficheros.LineaEstacion;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author raquel
 */
public class ControladorTransportes extends Controlador{

    public ControladorTransportes()
    {}
    
    @Override
    public void executar() {
       
    }

    /**
     * Obtener la lista de ciudades guardadas en el sistema
     * @return Lista de los nombres de las ciudades que están guardadas en el
     * sistema.
     */
    public ArrayList<String> getCiudades() {
         CtrlFactoriaDatos cfd = new CtrlFactoriaDatos();
        //Conseguir todas las ciudades que hay en el sistema
        CtrlCiudad cc = cfd.getCtrlCiudad();
        List<Ciudad> c = cc.getCiudades();
        ArrayList<String> lc = new ArrayList<>();
        
        for (int i = 0; i < c.size(); i++) {
            lc.add(c.get(i).getNombre());
        }
        return  lc;
    }

    /**
     * Obtener la lista de transportes que tiene una ciudad
     * @param ciudad: nombre de la ciudad de la cual se quiere obtener sus transportes
     * @return Lista de los transportes que contiene la ciudad
     */
    public ArrayList<String> getTransportes(String ciudad) {
        CtrlFactoriaDatos cfd = new CtrlFactoriaDatos();
        //Conseguir todas las ciudades que hay en el sistema
        CtrlCiudad cc = cfd.getCtrlCiudad();
       return cc.getTransportes(ciudad);
        
    }

    /**
     * Obtener la lista de líneas que tiene un determinado transporte de una ciudad
     * @param ciudad: Nombre de la ciudad 
     * @param transporte: Nombre del transporte
     * @return Lista de lineas que el transporte tiene en la ciudad ciudad
     */
    public ArrayList<String> getLineas(String ciudad, String pais, String transporte) {
        CtrlFactoriaDatos cfd = new CtrlFactoriaDatos();
        CtrlEstacionCivica cec =  cfd.getCtrlEstacionCivica(transporte) ;
        if (cec == null) return null;
        return cec.getLineas(ciudad, pais);
    }

    public ArrayList<EstacionCivica > getParadas(String ciudad, String pais, String transporte, 
            String linea) {
        CtrlFactoriaDatos cfd = new CtrlFactoriaDatos();
        CtrlLineaEstacion cle =  cfd.getCtrlLineaEstacion() ;
        ArrayList<LineaEstacion> lista = cle.getLineaEstacionTransporte(ciudad, pais, transporte);
        ArrayList<EstacionCivica> l = new ArrayList<>();
        for (int i = 0; i < lista.size(); i++) {
            LineaEstacion estacion = lista.get(i);
            Boolean eslinea = estacion.esLinea(linea);
            if (eslinea) {
                EstacionCivica par = estacion.getEstacion();
                l.add(par);
            }      
        }
       
        return l;
    }

    public ArrayList<EstacionAparcamiento> getAparcamientos(String ciudad, 
            String pais) {
        CtrlFactoriaDatos cfd = new CtrlFactoriaDatos();
        CtrlEstacionAparcamiento cea = cfd.getCtrlEstacionAparcamiento();
        return cea.getAparcamiento(ciudad, pais);
    }

    public ArrayList<EstacionAlquilerBicicletas> getBicicletas(String ciudad, 
            String pais) {
        CtrlFactoriaDatos cfd = new CtrlFactoriaDatos();
        CtrlEstacionAlquilerBicicletas ceab = cfd.getCtrlEstacionAlquilerBicicletas();
        return ceab.getBicicletas(ciudad, pais);
    }

   
    
    
}
