/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Valoraciones.Controlador.Datos.Interficie;

import FactoriaDatos.CtrlFactoriaDatos;
import Valoraciones.Ficheros.URL;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author raquel
 */
public interface CtrlURL {
    public ArrayList<URL> getURLs(String ciudad, String pais) ;
    public void anadirURL(String ciudad, String pais, String nomReferencia, String url);
}
