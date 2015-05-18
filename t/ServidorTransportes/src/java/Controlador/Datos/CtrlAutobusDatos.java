/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Datos;

import Controlador.Datos.Interficie.CtrlAutobus;
import Ficheros.EstacionCivica;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author raquel
 */
public class CtrlAutobusDatos extends CtrlEstacionCivicaDatos {
    
    public ArrayList<String> getLineas(String ciudad, String pais) {
       
            String fichero = folder+pais+"/"+ciudad+"/Autobus.html";
            return obtenerLineas(fichero);

    }

  
}
