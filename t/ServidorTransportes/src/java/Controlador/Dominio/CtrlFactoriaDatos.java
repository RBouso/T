/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Dominio;


import Controlador.Datos.CtrlAutobusDatos;
import Controlador.Datos.CtrlCiudadDatos;
import Controlador.Datos.CtrlEstacionAlquilerBicicletasDatos;
import Controlador.Datos.CtrlFerrocarrilesDatos;
import Controlador.Datos.CtrlFunicularDatos;
import Controlador.Datos.CtrlLineaEstacionDatos;
import Controlador.Datos.CtrlMetroDatos;
import Controlador.Datos.CtrlTelefericoDatos;
import Controlador.Datos.CtrlTranviaDatos;
import Controlador.Datos.CtrlUrlDatos;
import Controlador.Datos.CtrlEstacionAparcamientoDatos;
import Controlador.Datos.Interficie.CtrlCiudad;
import Controlador.Datos.Interficie.CtrlEstacionAlquilerBicicletas;
import Controlador.Datos.Interficie.CtrlEstacionAparcamiento;
import Controlador.Datos.Interficie.CtrlEstacionCivica;
import Controlador.Datos.Interficie.CtrlLineaEstacion;
import Controlador.Datos.Interficie.CtrlURL;

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

    CtrlEstacionAlquilerBicicletas getCtrlEstacionAlquilerBicicletas() {
        if (ceab == null) ceab = new CtrlEstacionAlquilerBicicletasDatos();
        return ceab;
    }
}