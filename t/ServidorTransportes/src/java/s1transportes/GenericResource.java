/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s1transportes;


import Controlador.Dominio.ControladorTransportes;
import Ficheros.Ciudad;
import Ficheros.EstacionAlquilerBicicletas;
import Ficheros.EstacionAparcamiento;
import Ficheros.EstacionCivica;
import com.google.common.collect.ArrayListMultimap;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;


/**
 * REST Web Service
 *
 * @author raquel
 */
@Path("generic")
public class GenericResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    /**
     * Retrieves representation of an instance of s1transportes.GenericResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/xml")
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }

    
    @GET
    @Path("/ciudades")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<String> getCiudades() {
        ControladorTransportes ct = new ControladorTransportes();
        
        return  ct.getCiudades();
//        return res;
    }
    
    @GET
    @Path("/transportes/{ciudad}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<String> getTransportes(@PathParam("ciudad") String ciudad) {
       ControladorTransportes ct = new ControladorTransportes();
       return ct.getTransportes(ciudad);
        
    }
    
    @GET
    @Path("/lineas/ciudad={ciudad}&pais={pais}&transporte={transporte}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<String> getLineas(@PathParam("ciudad") String ciudad, 
            @PathParam("pais") String pais, @PathParam("transporte") String transporte) {
        //TODO write your implementation code here:
        ControladorTransportes ct = new ControladorTransportes();
        return ct.getLineas(ciudad, pais, transporte);
    }
    
    @GET
    @Path("/aparcamiento/ciudad={ciudad}&pais={pais}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<EstacionAparcamiento> getAparcamientos(@PathParam("ciudad") String ciudad, 
            @PathParam("pais") String pais) {
        //TODO write your implementation code here:
        ControladorTransportes ct = new ControladorTransportes();
        return ct.getAparcamientos(ciudad, pais);
    }
      
    @GET
    @Path("/bicicletas/ciudad={ciudad}&pais={pais}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<EstacionAlquilerBicicletas> getBicicletas(@PathParam("ciudad") String ciudad, 
            @PathParam("pais") String pais) {
        //TODO write your implementation code here:
        ControladorTransportes ct = new ControladorTransportes();
        return ct.getBicicletas(ciudad, pais);
    }
    
    @GET
    @Path("/paradas/ciudad={ciudad}&pais={pais}&transporte={transporte}&linea={linea}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<EstacionCivica> getParadas(@PathParam("ciudad") String ciudad, 
            @PathParam("pais") String pais, @PathParam("transporte") String transporte, 
            @PathParam("linea") String linea) {
        //TODO write your implementation code here:
        ControladorTransportes ct = new ControladorTransportes();
        return ct.getParadas(ciudad, pais, transporte, linea);
    }
}
