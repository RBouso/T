/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transportes.Controlador.Datos.Interficie;

import FactoriaDatos.CtrlFactoriaDatos;
import Transportes.Ficheros.Ciudad;
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
public interface CtrlCiudad {

    public void anadirCiudad(String nombre, String pais) ;

    public ArrayList<Ciudad> getCiudades() ;

    public ArrayList<String> getTransportes(String ciudad);

    public ArrayList<String> getTransportesHorarios(String ciudad, String pais);
    
}
