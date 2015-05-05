/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stransportes;

import Controlador.Dominio.ControladorTransportes;
import java.util.List;
import javafx.util.Pair;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author raquel
 */
@WebService(serviceName = "Transportes")
public class Transportes {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getTransportes")
    public List<String> getTransportes(@WebParam(name = "ciudad") String ciudad) {
        //TODO write your implementation code here:
        ControladorTransportes ct = new ControladorTransportes();
        return ct.getTransportes(ciudad);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getCiudades")
    public List<String> getCiudades() {
        //TODO write your implementation code here:
        ControladorTransportes ct = new ControladorTransportes();
        return ct.getCiudades();
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getLineas")
    public List<String> getLineas(@WebParam(name = "ciudad") String ciudad, 
            @WebParam(name = "pais") String pais, @WebParam(name = "transporte") String transporte) {
        //TODO write your implementation code here:
        ControladorTransportes ct = new ControladorTransportes();
        return ct.getLineas(ciudad, pais, transporte);
    }

    /**
     * Web service operation
     * @return 
     */
    @WebMethod(operationName = "getParadas")
    public List<String > getParadas(@WebParam(name = "ciudad") String ciudad, @WebParam(name = "pais") String pais, @WebParam(name = "transporte") String transporte, @WebParam(name = "linea") String linea) {
        //TODO write your implementation code here:
        ControladorTransportes ct = new ControladorTransportes();
        ct.getParadas(ciudad, pais, transporte, linea);
        return null;
                
    }
}
