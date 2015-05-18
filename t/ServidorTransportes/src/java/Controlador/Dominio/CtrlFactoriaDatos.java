/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Dominio;

import Controlador.Datos.CtrlAutobusDatos;
import Controlador.Datos.CtrlCiudadDatos;
import Controlador.Datos.CtrlFerrocarrilesDatos;
import Controlador.Datos.CtrlFunicularDatos;
import Controlador.Datos.CtrlLineaEstacionDatos;
import Controlador.Datos.CtrlMetroDatos;
import Controlador.Datos.CtrlTelefericoDatos;
import Controlador.Datos.CtrlTranviaDatos;
import Controlador.Datos.CtrlUrlDatos;
import Controlador.Datos.Interficie.CtrlCiudad;
import Controlador.Datos.Interficie.CtrlEstacionCivica;
import Controlador.Datos.Interficie.CtrlLineaEstacion;
import Controlador.Datos.Interficie.CtrlURL;
import Ficheros.Ciudad;
import Ficheros.URL;
import extraccion.extractorFacade;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author raquel
 */
public class CtrlFactoriaDatos {
    private static CtrlCiudad cc = null; 
    private static CtrlURL cu = null;
    private static CtrlEstacionCivica cec = null;
    private static CtrlLineaEstacion cle = null;
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

    CtrlURL getCtrlURL() {
         if (cu == null)cu = new CtrlUrlDatos();
        return cu;
    }

    CtrlEstacionCivica getCtrlEstacionCivica(String transporte) {
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

    CtrlLineaEstacion getCtrlLineaEstacion() {
        if (cle == null) cle = new CtrlLineaEstacionDatos();
        return cle;
    }
}