/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transportes.extraccion;

import Valoraciones.Controlador.Datos.Clases.CtrlUrlDatos;
import FactoriaDatos.CtrlFactoriaDatos;
import Transportes.Controlador.Dominio.Controlador;
import Transportes.Controlador.Dominio.ControladorExtraccion;
import Transportes.Ficheros.AccionViajar;
import Transportes.Ficheros.EstacionAparcamiento;
import Transportes.Ficheros.EstacionCivica;
import Valoraciones.Controlador.Datos.Clases.CtrlEstacionValoradaDatos;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
import static javax.measure.unit.SI.METER;
import org.jscience.geography.coordinates.LatLong;
import org.jscience.geography.coordinates.UTM;
import org.jscience.geography.coordinates.crs.CoordinatesConverter;
import s1transportes.GenericResource;


/**
 *
 * @author raquel
 */
public class Main {
        public static void main(String[] args) {
            //extractor e = new extractor();
            GenericResource t = new GenericResource();

//            AccionViajar l = t.getParadasCercanas1();
//            System.out.println(l.getDestino().getGeo().getLatitud());
//            t.getParadasCercanas("Barcelona", "España","Av. Meridiana, 596, Barcelona");
//            List<Pair<Double, Double> > l = t.getParadas("Barcelona", "España", "Autobus", "118");
//            CtrlEstacionValoradaDatos s = new CtrlEstacionValoradaDatos();
//            s.anadirPuntuacion(4, 33.23, 2.3, "Lugo", "Galicia");
            
            
//            ArrayList<EstacionAparcamiento> l  = t.getAparcamientos("Barcelona", "España");
//         ArrayList<EstacionCivica> l = t.getParadas("Barcelona", "España", "Tranvia", "T1");
//            for (int i = 0; i < l.size(); i++) {
//                System.out.println(l.get(i).getDireccion().getPais());
//            }    
//          
            
                    
            
            

//            CtrlUrlDatos cfd = new CtrlUrlDatos();
//
//            cfd.anadirURL("Bilbao", "España", "BicicletasBilbaoXML","http://www.bilbao.net/WebServicesBilbao/WSBilbao?s=ODPRESBICI&u=OPENDATA&p0=A&p1=A",
//            "2015/06/08","mensual");
//            cfd.anadirURL("Madrid", "España", "AutobusesMadridRDF","http://datos.madrid.es/datosabiertos/BDC/POIS_TRANSPORTE/EMT/2015/EMT_2015_04.rdf",
//            "2015/06/08","trimestral");
//
//            cfd.anadirURL("Madrid", "España", "MetroMadridRDF","http://datos.madrid.es/datosabiertos/BDC/POIS_TRANSPORTE/Metro/2015/Metro_2015_04.rdf","2015/06/08","trimestral");
//            cfd.anadirURL("Madrid", "España", "TaxiMadridRDF","http://datos.madrid.es/datosabiertos/BDC/POIS_TRANSPORTE/Paradas_Taxi/2015/Paradas_Taxi_2015_04.rdf","2015/06/08","trimestral");
//            cfd.anadirURL("Barcelona", "España", "TaxiBarcelonaCSV","http://bismartopendata.blob.core.windows.net/opendata/opendata/paradestaxi_0_opendata_paradestaxi.csv","2015/06/08","mensual");
//            cfd.anadirURL("Barcelona", "España", "AutobusesBarcelonaCSV","http://bismartopendata.blob.core.windows.net/opendata/opendata/estacionsbus_0_opendata_estacionsbus.csv","2015/06/08","mensual");
//            cfd.anadirURL("Barcelona", "España", "TransportesBarcelonaCSV","http://bismartopendata.blob.core.windows.net/opendata/opendata/transports_0_opendata_transports.csv","2015/06/08","mensual");
//            
//            cfd.anadirURL("Barcelona", "España", "AparcamientosBarcelonaCSV", "http://bismartopendata.blob.core.windows.net/opendata/opendata/aparcaments_0_opendata_aparcaments.csv","2015/06/08","mensual");
//            cfd.anadirURL("Madrid", "España", "CercaniasMadridRDF", "http://datos.madrid.es/datosabiertos/BDC/POIS_TRANSPORTE/Cercanias/2015/Cercanias_2015_04.rdf","2015/06/08","trimestral");
//            cfd.anadirURL("Madrid", "España", "AparcamientosMadridRDF", "http://datos.madrid.es/egob/catalogo/202625-0-aparcamientos-publicos.rdf","2015/06/08","anual");
//            
// cfd.anadirURL("Madrid", "España", "AparcamientosMadridCSV","http://datos.madrid.es/egob/catalogo/202625-0-aparcamientos-publicos.csv");
            Controlador c = new ControladorExtraccion();
            c.executar();
//            Formato f = new RDF()
//;            f.extraerDatos("Metro_2015_04.rdf", 
//        "MetroMadrid", "", 
//            "Madrid", "España");
//            Formato f  = new CSV()
//;f.extraerDatos("transports_0_opendata_transports.csv", "TransportesBarcelona", 
//        "http://bismartopendata.blob.core.windows.net/opendata/opendata/transports_0_opendata_transports.csv", "Barcelona", "España");
            //e.extraerDatos("http://wservice.viabicing.cat/v1/getstations.php?v=1", "XML");
            //e.extraerDatos("http://bismartopendata.blob.core.windows.net/opendata/opendata/aparcaments_0_opendata_aparcaments.csv", "CSV");
            //datos madrid bicimad
            //e.extraerDatos("http://datos.madrid.es/egob/catalogo/208327-0-transporte-bicicletas-bicimad.xls", "XLS");
            //datos aparcamientos madrid
            //e.extraerDatos("http://datos.madrid.es/egob/catalogo/202625-0-aparcamientos-publicos.rdf", "RDF");
    }

}
