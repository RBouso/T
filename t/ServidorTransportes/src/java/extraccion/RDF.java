/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extraccion;
import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.query.QueryHandler;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.mem.ModelMem;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.NsIterator;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFList;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.RDFReader;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.rdf.model.impl.ModelCom;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.RDFS;
import static com.hp.hpl.jena.vocabulary.RDFS.Resource;
import com.hp.hpl.jena.vocabulary.VCARD;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author raquel
 */
public class RDF extends Formato{

    private URLConnection con; 
    private String folder = "descargas/";
    private String nombre;
    
   
    public void extraerDatos(String nomFichero, String referencia, String url, String ciudad, String pais) {
   
            
        InputStream ips = null;
        try {
            if (referencia.contains("Transporte"))
                super.transporte = true;
            super.furl = url;
            super.ciudad = ciudad;
            super.pais = pais;
            super.referencia = referencia;
            super.fichero = "ficheros/"+pais+"/"+ciudad;
            System.out.println(referencia);
            folder = folder+pais+"/"+ciudad+"/"+nomFichero;
            Model model = ModelFactory.createDefaultModel();
            ips = new FileInputStream(folder); 
            InputStreamReader ipsr=new InputStreamReader(ips);
            OntModel ontology = ModelFactory.createOntologyModel();
            if (ips == null) {
                throw new IllegalArgumentException( "Archivo: " + folder + " no encontrado");
            }
            
             model.read(ipsr, "");

                
               // Object next = listObjects.next();

            //System.out.println(model.listObjects().toString());
            StmtIterator iter = model.listStatements();
            String param = "";
// print out the predicate, subject and object of each statement
            int i = 0, j = 0;
            int ultimo = (int) model.size();
            while (iter.hasNext()) {
                
                Statement stmt      = iter.nextStatement();  // get next statement
                Resource  subject   = stmt.getSubject();     // get the subject
                Property  predicate = stmt.getPredicate();   // get the predicate
                RDFNode   object    = stmt.getObject();      // get the object
                    //System.out.print(predicate.getNameSpace()); nomReferencia: AparcamientosMadridRDF url: http://datos.madrid.es/egob/catalogo/202625-0-aparcamientos-publicos.rdf
                String predicado = predicate.getLocalName();
                String objeto = object.toString();
                if (param.equals(predicado)) {
                    super.integrarDatos(i, ultimo-1 );
                    i++;
                }
                    super.obtenerDatos(predicado,objeto);
                    if (j == 0){
                        param = predicado;
                        ++j;
                    }


                
                    
            }
            super.integrarDatos(ultimo-1, ultimo-1 );
            
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
