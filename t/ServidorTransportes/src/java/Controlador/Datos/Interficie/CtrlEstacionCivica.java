/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Datos.Interficie;

import Ficheros.EstacionCivica;
import Ficheros.LineaEstacion;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author raquel
 */
public interface CtrlEstacionCivica {

    public List<String> getLineas(String ciudad, String pais);

    
}
