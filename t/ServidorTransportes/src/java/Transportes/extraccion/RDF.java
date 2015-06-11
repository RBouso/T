/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transportes.extraccion;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author raquel
 */
public class RDF extends Formato{


    
   /**
    * Extrae los datos de un fichero RDF
    * @param nomFichero: nombre del fichero que contiene los datos
    * @param referencia: referencia del fichero
    * @param url: url donde se puede encontrar el fichero
    * @param ciudad: ciudad a donde pertenecen los datos
    * @param pais: pais a donde pertenecen los datos
    */
    @Override
    public void extraerDatos(String nomFichero, String referencia, String url, String ciudad, String pais) {
   
            
        InputStream ips = null;
        try {
            if (referencia.contains("Transporte"))
                super.transporte = true;
            //inicializamos variables
            super.furl = url;
            super.ciudad = ciudad;
            super.pais = pais;
            super.referencia = referencia;
            super.fichero = "ficheros/"+pais+"/"+ciudad;
            folder = folder+pais+"/"+ciudad+"/"+nomFichero;
            //creamos el modelo para leer el archivo rdf
            Model model = ModelFactory.createDefaultModel();
            ips = new FileInputStream(folder); 
           InputStreamReader ipsr=new InputStreamReader(ips);
            
            if (ips == null) {
                throw new IllegalArgumentException( "Archivo: " + folder + " no encontrado");
            }
            // leemos el archivo rdf
             model.read(ipsr, "");

               
             // Cojemos cada uno de los hijos que contiene el modelo
            ResIterator listSubjects = model.listSubjects();


            int i = 0;
            int ultimo = (int) model.listSubjects().toList().size();
            
            while(listSubjects.hasNext()) {
                inicializar_variables();
                //cogemos el hijo
                Resource res = listSubjects.nextResource();
                //cogemos las propiedades
                StmtIterator listProperties = res.listProperties();
                //para cada propidad, obtenemos el nombre de la variable y el valor
                while (listProperties.hasNext()) {
                    Statement state = listProperties.nextStatement();
                    
                    super.obtenerDatos(state.getPredicate().getLocalName(), state.getObject().toString());
                }
                //guardamos los datos
 
               
                    super.integrarDatos(i, ultimo-1);
                i++;
            }

           model.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RDF.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ips.close();
               
            } catch (IOException ex) {
                Logger.getLogger(RDF.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

           
    
    } 



}
