/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extraccion;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author raquel
 */
public class XML extends Formato{
    private String descargas = "descargas/";
    private String tags[] = {"station", "DETALLE"};
    
    @Override
    public void extraerDatos(String nomFichero, String referencia, String url, String ciudad, String pais) {
        try {
            if (referencia.contains("Transporte"))
                super.transporte = true;
            super.furl = url;
            super.ciudad = ciudad;
            super.pais = pais;
            super.referencia = referencia;
            super.fichero = "ficheros/"+pais+"/"+ciudad;
            descargas = descargas+pais+"/"+ciudad+"/"+nomFichero;
            DocumentBuilderFactory fabricaCreadorDocumento = DocumentBuilderFactory.newInstance();
            DocumentBuilder creadorDocumento;
            creadorDocumento = fabricaCreadorDocumento.newDocumentBuilder();
            Document documento = creadorDocumento.parse(url);
            
            Element raiz = documento.getDocumentElement();
            NodeList lista = null; 
//            for (int i = 0; i < tags.length && lista == null; i++) 
//                lista = raiz.getElementsByTagName(tags[i]);
           lista = raiz.getChildNodes();
           
            //System.out.println( lista.getLength());
            for (int i = 0; i < lista.getLength(); i++) {
                //inicializo variables
                super.inicializar_variables();
                Boolean h = false;

                
                Node es = lista.item(i);
                //System.out.println("Estacion "+i);
                NodeList datos = es.getChildNodes();
               
                for (int j = 0; j < datos.getLength(); j++) {
                    Node dato = datos.item(j);
                    NodeList hijos = dato.getChildNodes();
                    
                    if(hijos.getLength() > 1 ) {
                       inicializar_variables();
                       h = true;
                        for (int k = 0; k < hijos.getLength(); k++) {
                        Node dat = hijos.item(k);
                        if (dat.getNodeType() == Node.ELEMENT_NODE){ 
                            String nn = dat.getNodeName();
                        Node dc = dat.getFirstChild();
                        if (dc != null /*&& dc.getNodeType() == Node.TEXT_NODE*/) {
                            String nv = dc.getNodeValue();
                            super.obtenerDatos(nn, nv);
                        
                        }
                    }
                    }
                       super.integrarDatos(j, hijos.getLength()-1);
                    }
                    else {
                        if (dato.getNodeType() == Node.ELEMENT_NODE) {
                         String nn1 = dato.getNodeName();
                         Node dc = dato.getFirstChild();
                         if (dc != null /*&& dc.getNodeType() == Node.TEXT_NODE*/) {
                             String nv = dc.getNodeValue();
                             super.obtenerDatos(nn1, nv);

                         }

                     }
                        
                    
                    }
                }
                
                    if (!h)super.integrarDatos(i, lista.getLength()-1);
                
                
            }   } catch (ParserConfigurationException ex) {
            Logger.getLogger(XML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(XML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XML.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    
}
