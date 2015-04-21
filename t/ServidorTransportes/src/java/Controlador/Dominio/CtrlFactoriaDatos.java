/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Dominio;

import Controlador.Datos.CtrlCiudadDatos;
import Controlador.Datos.CtrlUrlDatos;
import Controlador.Datos.Interficie.CtrlCiudad;
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
}