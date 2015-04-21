/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stransportes;

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
    public String getTransportes() {
        //TODO write your implementation code here:
        return null;
    }
}
