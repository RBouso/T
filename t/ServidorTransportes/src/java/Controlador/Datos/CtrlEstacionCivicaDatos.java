/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Datos;

import Controlador.Datos.Interficie.CtrlEstacionCivica;
import Ficheros.EstacionCivica;
import Ficheros.LineaEstacion;
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
public abstract class CtrlEstacionCivicaDatos implements CtrlEstacionCivica{
     
    @Override
    public abstract List<String> getLineas(String ciudad, String pais);
    
    protected List<String> obtenerLineas(String fichero) {
        try {
            
            ArrayList<String> result = new ArrayList<>();
           
            Document htmlFile = Jsoup.parse(new File(fichero), "ISO-8859-1");
//            List<Node> childNodes = htmlFile.body().childNodes();
            
            Elements select = htmlFile.select("span[itemprop=line]");
            
            for (int i = 0; i < select.size(); i++) {
                String linea = select.get(i).text();
                
                if (!linea.equals("") && !result.contains(linea))
                    result.add(linea);
            }
            return result;
        } catch (IOException ex) {
            Logger.getLogger(CtrlEstacionCivicaDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    


}
