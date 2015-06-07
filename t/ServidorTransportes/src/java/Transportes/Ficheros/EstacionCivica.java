/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transportes.Ficheros;

import java.util.ArrayList;

/**
 *
 * @author raquel
 */
public class EstacionCivica extends EstructurasPublicas{
    private ArrayList<Linea> lineas;

    public ArrayList<Linea> getLineas() {
        return lineas;
    }

    public void setLineas(ArrayList<Linea> lineas) {
        this.lineas = lineas;
    }
    
    
}
