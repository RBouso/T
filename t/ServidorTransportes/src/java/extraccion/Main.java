/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extraccion;

import Controlador.Datos.CtrlUrlDatos;
import Controlador.Dominio.CtrlFactoriaDatos;
import Controlador.Dominio.Controlador;
import Controlador.Dominio.ControladorExtraccion;
import java.util.List;
import javafx.util.Pair;
import static javax.measure.unit.SI.METER;
import org.jscience.geography.coordinates.LatLong;
import org.jscience.geography.coordinates.UTM;
import org.jscience.geography.coordinates.crs.CoordinatesConverter;
import stransportes.Transportes;

/**
 *
 * @author raquel
 */
public class Main {
        public static void main(String[] args) {
            //extractor e = new extractor();
//            Transportes t = new Transportes();
//            List<Pair<Double, Double> > l = t.getParadas("Barcelona", "España", "Autobus", "118");
//            for (int i = 0; i < l.size(); i++) {
//                System.out.println("latitud: "+l.get(i).getKey()+" longitud: "+l.get(i).getValue());
//            }    
          
            
                    
            
            
////
//            CtrlUrlDatos cfd = new CtrlUrlDatos();
//            cfd.anadirURL("Bilbao", "España", "TiempoEsperaBilbaoCSV","http://www.bilbao.net/autobuses/jsp/od_horarios.jsp?idioma=c&formato=csv&tipo=espera");

//            cfd.anadirURL("Bilbao", "España", "BicicletasBilbaoXML","http://www.bilbao.net/WebServicesBilbao/WSBilbao?s=ODPRESBICI&u=OPENDATA&p0=A&p1=A");
//            cfd.anadirURL("Madrid", "España", "AutobusesMadridRDF","http://datos.madrid.es/datosabiertos/BDC/POIS_TRANSPORTE/EMT/2015/EMT_2015_04.rdf");
//            cfd.anadirURL("Madrid", "España", "MetroMadridRDF","http://datos.madrid.es/datosabiertos/BDC/POIS_TRANSPORTE/Metro/2015/Metro_2015_04.rdf");
//            cfd.anadirURL("Madrid", "España", "TaxiMadridRDF","http://datos.madrid.es/datosabiertos/BDC/POIS_TRANSPORTE/Paradas_Taxi/2015/Paradas_Taxi_2015_04.rdf");
//            cfd.anadirURL("Barcelona", "España", "TaxiBarcelonaCSV","http://bismartopendata.blob.core.windows.net/opendata/opendata/paradestaxi_0_opendata_paradestaxi.csv");
//            cfd.anadirURL("Barcelona", "España", "AutobusesBarcelonaCSV","http://bismartopendata.blob.core.windows.net/opendata/opendata/estacionsbus_0_opendata_estacionsbus.csv");
//            cfd.anadirURL("Barcelona", "España", "TransportesBarcelonaCSV","http://bismartopendata.blob.core.windows.net/opendata/opendata/transports_0_opendata_transports.csv");
            
//            cfd.anadirURL("Barcelona", "España", "AparcamientosBarcelonaCSV", "http://bismartopendata.blob.core.windows.net/opendata/opendata/aparcaments_0_opendata_aparcaments.csv");
//            cfd.anadirURL("Madrid", "España", "CercaniasMadridRDF", "http://datos.madrid.es/datosabiertos/BDC/POIS_TRANSPORTE/Cercanias/2015/Cercanias_2015_04.rdf");
//            cfd.anadirURL("Madrid", "España", "AparcamientosMadridRDF", "http://datos.madrid.es/egob/catalogo/202625-0-aparcamientos-publicos.rdf");
//            
 //cfd.anadirURL("Madrid", "España", "AparcamientosMadridCSV","http://datos.madrid.es/egob/catalogo/202625-0-aparcamientos-publicos.csv");
//            Controlador c = new ControladorExtraccion();
//            c.executar();
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
