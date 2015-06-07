/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FactoriaDatos;


import Transportes.Controlador.Datos.Clases.CtrlAutobusDatos;
import Transportes.Controlador.Datos.Clases.CtrlCiudadDatos;
import Transportes.Controlador.Datos.Clases.CtrlEstacionAlquilerBicicletasDatos;
import Transportes.Controlador.Datos.Clases.CtrlFerrocarrilesDatos;
import Transportes.Controlador.Datos.Clases.CtrlFunicularDatos;
import Transportes.Controlador.Datos.Clases.CtrlLineaEstacionDatos;
import Transportes.Controlador.Datos.Clases.CtrlMetroDatos;
import Transportes.Controlador.Datos.Clases.CtrlTelefericoDatos;
import Transportes.Controlador.Datos.Clases.CtrlTranviaDatos;
import Valoraciones.Controlador.Datos.Clases.CtrlUrlDatos;
import Transportes.Controlador.Datos.Clases.CtrlEstacionAparcamientoDatos;
import Transportes.Controlador.Datos.Clases.CtrlEstacionTaxiDatos;
import Transportes.Controlador.Datos.Interficie.CtrlCiudad;
import Transportes.Controlador.Datos.Interficie.CtrlEstacionAlquilerBicicletas;
import Transportes.Controlador.Datos.Interficie.CtrlEstacionAparcamiento;
import Transportes.Controlador.Datos.Interficie.CtrlEstacionCivica;
import Transportes.Controlador.Datos.Interficie.CtrlEstacionTaxi;
import Transportes.Controlador.Datos.Interficie.CtrlEstructurasPublicas;
import Transportes.Controlador.Datos.Interficie.CtrlLineaEstacion;
import Valoraciones.Controlador.Datos.Interficie.CtrlURL;
import Valoraciones.Controlador.Datos.Clases.CtrlEstacionValoradaDatos;
import Valoraciones.Controlador.Datos.Interficie.CtrlEstacionValorada;

/**
 *
 * @author raquel
 */
public class CtrlFactoriaDatos {
    private static CtrlCiudad cc = null; 
    private static CtrlURL cu = null;
    private static CtrlEstacionCivica cec = null;
    private static CtrlLineaEstacion cle = null;
    private static CtrlEstacionAparcamiento cea = null;
    private static CtrlEstacionAlquilerBicicletas ceab = null;
    private static CtrlEstacionTaxi cet = null;
    private static CtrlEstructurasPublicas cep = null;
    private static CtrlEstacionValorada cev = null;
    private static CtrlFactoriaDatos INSTANCE = null;
    

    public CtrlFactoriaDatos() {
    }

    private synchronized static void createInstance() {
        if (INSTANCE == null) { 
            INSTANCE = new CtrlFactoriaDatos();
        }
    }
 
    public static CtrlFactoriaDatos getInstance() {
        if (INSTANCE == null) createInstance();
        return INSTANCE;
    }

    public CtrlCiudad getCtrlCiudad() {
         if (cc == null)cc = new CtrlCiudadDatos();
         
        return cc;
    }

    public CtrlURL getCtrlURL() {
         if (cu == null)cu = new CtrlUrlDatos();
        return cu;
    }

    public CtrlEstacionCivica getCtrlEstacionCivica(String transporte) {
        cec = null;
        if (transporte.equalsIgnoreCase("autobus")) 
            cec = new CtrlAutobusDatos();
        else if (transporte.equalsIgnoreCase("metro"))
            cec = new CtrlMetroDatos();
        else if (transporte.equalsIgnoreCase("tranvia"))
            cec = new CtrlTranviaDatos();
        else if (transporte.equalsIgnoreCase("ferrocarriles"))
            cec = new CtrlFerrocarrilesDatos();
        else if (transporte.equalsIgnoreCase("funicular"))
            cec = new CtrlFunicularDatos();
        else if (transporte.equalsIgnoreCase("teleferico"))
            cec = new CtrlTelefericoDatos();
        return cec;
    }

    public CtrlLineaEstacion getCtrlLineaEstacion() {
        if (cle == null) cle = new CtrlLineaEstacionDatos();
        return cle;
    }
    
    public CtrlEstacionAparcamiento getCtrlEstacionAparcamiento() {
        if (cea == null) cea = new CtrlEstacionAparcamientoDatos();
        return cea;
    }

    public CtrlEstacionAlquilerBicicletas getCtrlEstacionAlquilerBicicletas() {
        if (ceab == null) ceab = new CtrlEstacionAlquilerBicicletasDatos();
        return ceab;
    }

    public CtrlEstacionTaxi getCtrlEstacionTaxi() {
        if(cet == null) cet = new CtrlEstacionTaxiDatos();
        return cet;
    }
    
    public CtrlEstructurasPublicas getCtrlEstructurasPublicas(String transporte) {
        cep = null;
        if (transporte.equalsIgnoreCase("autobus")) 
            cep = new CtrlAutobusDatos();
        else if (transporte.equalsIgnoreCase("metro"))
            cep = new CtrlMetroDatos();
        else if (transporte.equalsIgnoreCase("tranvia"))
            cep = new CtrlTranviaDatos();
        else if (transporte.equalsIgnoreCase("ferrocarriles"))
            cep = new CtrlFerrocarrilesDatos();
        else if (transporte.equalsIgnoreCase("funicular"))
            cep = new CtrlFunicularDatos();
        else if (transporte.equalsIgnoreCase("teleferico"))
            cep = new CtrlTelefericoDatos();
        else if (transporte.equalsIgnoreCase("taxi"))
            cep = new CtrlEstacionTaxiDatos();
        else if (transporte.equalsIgnoreCase("aparcamiento"))
            cep = new CtrlEstacionAparcamientoDatos();
        else if (transporte.equalsIgnoreCase("bicicletas"))
            cep = new CtrlEstacionAlquilerBicicletasDatos();
        return cep;
    }

    public CtrlEstacionValorada getEstacionValorada() {
        if (cev == null)
            cev = new CtrlEstacionValoradaDatos();
        return cev;
    }
}