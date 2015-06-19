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
    private static CtrlCiudad cc = null; //Interfaz del contralador de datos de ciudad
    private static CtrlURL cu = null;//Interfaz del contralador de datos de url
    private static CtrlEstacionCivica cec = null; //Interfaz del contralador de datos de Estacion civica
    private static CtrlLineaEstacion cle = null; //Interfaz del contralador de datos de LineaEstacion
    private static CtrlEstacionAparcamiento cea = null;//Interfaz del contralador de datos de aparcamiento
    private static CtrlEstacionAlquilerBicicletas ceab = null;//Interfaz del contralador de datos de bicicletas
    private static CtrlEstacionTaxi cet = null;//Interfaz del contralador de datos de taxi
    private static CtrlEstructurasPublicas cep = null;//Interfaz del contralador de datos de estructuras publicas
    private static CtrlEstacionValorada cev = null;//Interfaz del contralador de datos de estacion valorada
    private static CtrlFactoriaDatos INSTANCE = null;//Interfaz del contralador de esta clase
    

    public CtrlFactoriaDatos() {
    }

    /**
     * Crear una estancia de la clase CtrlFactoriaDatos
     */
    private synchronized static void createInstance() {
        if (INSTANCE == null) { 
            INSTANCE = new CtrlFactoriaDatos();
        }
    }
 
    /**
     * Obtener la instacia de la clase CtrlFactoriaDatos
     * @return instancia de CtrlFactoriaDatos
     */
    public static CtrlFactoriaDatos getInstance() {
        if (INSTANCE == null) createInstance();
        return INSTANCE;
    }

    /**
     * Obtener la instacia de la interfaz CtrlCiudad
     * @return instancia de CtrlCiudad
     */
    public CtrlCiudad getCtrlCiudad() {
         if (cc == null)cc = new CtrlCiudadDatos();
         
        return cc;
    }

    /**
     * Obtener la instacia de la interfaz CtrlURL
     * @return instancia de CtrlURL
     */
    public CtrlURL getCtrlURL() {
         if (cu == null)cu = new CtrlUrlDatos();
        return cu;
    }

    /**
     * Obtener la instacia de la interfaz CtrlEstacionCivica  para acceder a uno de los
     * transportes que hereda de la clase CtrlEstacionCivicaDatos
     * @param transporte : nombre del transporte
     * @return instancia de CtrlEstacionCivica
     */
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

    /**
     * Obtener la instacia de la interfaz CtrlLineaEstacion
     * @return instancia de CtrlLineaEstacion
     */
    public CtrlLineaEstacion getCtrlLineaEstacion() {
        if (cle == null) cle = new CtrlLineaEstacionDatos();
        return cle;
    }
    
    /**
     * Obtener la instacia de la interfaz CtrlEstacionAparcamiento
     * @return instancia de CtrlEstacionAparcamiento
     */
    public CtrlEstacionAparcamiento getCtrlEstacionAparcamiento() {
        if (cea == null) cea = new CtrlEstacionAparcamientoDatos();
        return cea;
    }

    /**
     * Obtener la instacia de la interfaz CtrlEstacionAlquilerBicicletas
     * @return instancia de CtrlEstacionAlquilerBicicletas
     */
    public CtrlEstacionAlquilerBicicletas getCtrlEstacionAlquilerBicicletas() {
        if (ceab == null) ceab = new CtrlEstacionAlquilerBicicletasDatos();
        return ceab;
    }

    /**
     * Obtener la instacia de la interfaz CtrlEstacionTaxi
     * @return instancia de CtrlEstacionTaxi
     */
    public CtrlEstacionTaxi getCtrlEstacionTaxi() {
        if(cet == null) cet = new CtrlEstacionTaxiDatos();
        return cet;
    }
    
     /**
     * Obtener la instacia de la interfaz CtrlEstructurasPublicas  para acceder a uno de los
     * transportes que hereda de la clase CtrlEstructurasPublicasDatos
     * @param transporte : nombre del transporte
     * @return instancia de CtrlEstructurasPublicas
     */
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

    /**
     * Obtener la instacia de la interfaz CtrlEstacionValorada
     * @return instancia de CtrlEstacionValorada
     */
    public CtrlEstacionValorada getEstacionValorada() {
        if (cev == null)
            cev = new CtrlEstacionValoradaDatos();
        return cev;
    }
}