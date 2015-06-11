/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s1transportes;


import Transportes.Controlador.Dominio.ControladorTransportes;
import Transportes.Ficheros.AccionViajar;
import Transportes.Ficheros.EstacionAlquilerBicicletas;
import Transportes.Ficheros.EstacionAparcamiento;
import Transportes.Ficheros.EstacionCivica;
import Transportes.Ficheros.EstacionTaxi;
import Transportes.Ficheros.EstructurasPublicas;
import Valoraciones.Controlador.Dominio.ControladorValoraciones;
import java.util.ArrayList;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
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

    /**
     * Obtener todas la ciudades almacenadas en el sistema
     * @return lista con los nombres de las ciudades.
     */
    @GET
    @Path("/ciudades")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<String> getCiudades() {
        ControladorTransportes ct = new ControladorTransportes();
        
        return  ct.getCiudades();
    }
    
    /**
     * Obtener los transportes que tiene una ciudad
     * @param ciudad: nombre de la ciudad
     * @return lista con los nombres de los transportes que tiene la ciudad
     */
    @GET
    @Path("/transportes/{ciudad}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<String> getTransportes(@PathParam("ciudad") String ciudad) {
       ControladorTransportes ct = new ControladorTransportes();
       return ct.getTransportes(ciudad);
        
    }
    
    
    /**
     * Obtiene la lineas que tiene un transporte público
     * @param ciudad: nombre de la ciudad
     * @param pais: nombre del pais
     * @param transporte: nombre del transporte
     * @return lista con los nombres de las líneas de un transporte de la ciudad ciudad.
     */
    @GET
    @Path("/lineas/ciudad={ciudad}&pais={pais}&transporte={transporte}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<String> getLineas(@PathParam("ciudad") String ciudad, 
            @PathParam("pais") String pais, @PathParam("transporte") String transporte) {
        //TODO write your implementation code here:
        ControladorTransportes ct = new ControladorTransportes();
        return ct.getLineas(ciudad, pais, transporte);
    }
    
    /**
     * Obtiene los aparcamientos que tiene una ciudad
     * @param ciudad: nombre de la ciudad
     * @param pais: nombre del pais
     * @return lista con todos los atributos necesarios para definir las estaciones
     * de aparcamientos
     */
    @GET
    @Path("/aparcamiento/ciudad={ciudad}&pais={pais}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<EstacionAparcamiento> getAparcamientos(@PathParam("ciudad") String ciudad, 
            @PathParam("pais") String pais) {
        //TODO write your implementation code here:
        ControladorTransportes ct = new ControladorTransportes();
        return ct.getAparcamientos(ciudad, pais);
    }
      
    /**
     * Obtiene las estaciones de alquiler de bicicletas que tiene una ciudad
     * @param ciudad: nombre de la ciudad
     * @param pais: nombre del pais
     * @return lista con todos los atributos necesarios para definir las estaciones
     * de alquiler de bicicletas
     */
    @GET
    @Path("/bicicletas/ciudad={ciudad}&pais={pais}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<EstacionAlquilerBicicletas> getBicicletas(@PathParam("ciudad") String ciudad, 
            @PathParam("pais") String pais) {
        //TODO write your implementation code here:
        ControladorTransportes ct = new ControladorTransportes();
        return ct.getBicicletas(ciudad, pais);
    }
    
   
    /**
     * Obtiene las paradas de taxi que tiene una ciudad
     * @param ciudad: nombre de la ciudad
     * @param pais: nombre del pais
     * @return lista con todos los atributos necesarios para definir las paradas
     * de taxi.
     */
    @GET
    @Path("/taxi/ciudad={ciudad}&pais={pais}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<EstacionTaxi> getTaxi(@PathParam("ciudad") String ciudad, 
            @PathParam("pais") String pais) {
        //TODO write your implementation code here:
        ControladorTransportes ct = new ControladorTransportes();
        return ct.getTaxi(ciudad, pais);
    }
    
    
    /**
     * Obtiene los paradas de un transporte publico que tiene una ciudad
     * @param ciudad: nombre de la ciudad
     * @param pais: nombre del pais
     * @param transporte: nombre del transporte
     * @param linea: nombre de la línea
     * @return lista con todos los atributos necesarios para definir las estaciones
     * de aparcamientos
     */
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
    
    /**
     * Obtiene las paradas cercanas a una localizacion
     * @param ciudad: nombre de la ciudad
     * @param pais: nombre del pais
     * @param direccion: nombre de la direccion sobre la cual se quieren obtener
     * las paradas cercanas
     * @return lista con todas las estaciones cercanas a la dirección proporcionada
     */
    @GET
    @Path("/paradasCercanas/ciudad={ciudad}&pais={pais}&direccion={direccion}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<EstructurasPublicas> getParadasCercanas(@PathParam("ciudad") String ciudad, 
            @PathParam("pais") String pais, @PathParam("direccion") String direccion) {
        //TODO write your implementation code here:
        ControladorTransportes ct = new ControladorTransportes();
        return ct.getParadasCercanas(ciudad, pais, direccion,0.0,0.0);
    }
    
    /**
     * Obtiene todas las estaciones cercanas de una determinada latitud y longitud
     * @param ciudad: nombre de la ciudad
     * @param pais: nombre del pais
     * @param latitud: latitud de una localización
     * @param longitud: longitud de una localización
     * @return lista con todas las direcciones cercanas a la dirección propocionada
     * con la latitud y longitud
     */
        @GET
    @Path("/paradasCercanas/ciudad={ciudad}&pais={pais}&latitud={latitud}&longitud={longitud}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<EstructurasPublicas> getParadasCercanasCoordenadas(@PathParam("ciudad") String ciudad, 
            @PathParam("pais") String pais,  @PathParam("latitud") Double latitud,
            @PathParam("longitud") double longitud) {
        //TODO write your implementation code here:
        ControladorTransportes ct = new ControladorTransportes();
        return ct.getParadasCercanas(ciudad, pais, "", latitud, longitud);
    }
    
    /**
     * Obtener la media de la valoración de una estación
     * @param ciudad: nombre de la ciudad
     * @param pais: nombre del pais
     * @param latitud: latitud de la ubicación de una estación
     * @param longitud: longitud de la ubicación de una estación
     * @return media de la valoración total que tiene una estación entre los usuarios
     * que la valoraron
     */
     @GET
    @Path("/valoracion/media/ciudad={ciudad}&pais={pais}&latitud={latitud}&longitud={longitud}")
    @Produces(MediaType.APPLICATION_JSON)
    public Double getMedia(@PathParam("ciudad") String ciudad, 
            @PathParam("pais") String pais, @PathParam("latitud") Double latitud,
            @PathParam("longitud") double longitud) {
        //TODO write your implementation code here:

        ControladorValoraciones cv = new ControladorValoraciones();
        return cv.getMedia(latitud, longitud, ciudad, pais);
    }
    
    
    /**
     * Añadir una nueva valoración al sistema
     * @param ciudad: nombre de la ciudad
     * @param pais: nombre del pais
     * @param latitud: latitud de la ubicación de una estación
     * @param longitud: longitud de la ubicación de una estación
     * @param puntuacion: puntuacion que se da a una estación
     * @return media de la valoración total que tiene una estación entre los usuarios
     * que la valoraron
     */
     @GET
    @Path("/valoracion/ciudad={ciudad}&pais={pais}&latitud={latitud}&longitud={longitud}&puntuacion={puntuacion}")
    @Produces(MediaType.APPLICATION_JSON)
    public Double anadirValoracion(@PathParam("ciudad") String ciudad, 
            @PathParam("pais") String pais, @PathParam("latitud") Double latitud,
            @PathParam("longitud") double longitud, @PathParam("puntuacion") int puntuacion) {
        //TODO write your implementation code here:

        ControladorValoraciones cv = new ControladorValoraciones();
        return cv.añadirPuntuacion(puntuacion, latitud, longitud, ciudad, pais);
    }
    
    
    /**
     * Modificar una valoración que ha sido realizada anteriormente
     * @param ciudad: nombre de la ciudad
     * @param pais: nombre del pais
     * @param latitud: latitud de la ubicación de una estación
     * @param longitud: longitud de la ubicación de una estación
     * @param puntuacionNueva: nueva puntuación para la estación
     * @param puntuacionAntigua: puntuación que se habia dado anteriormente a la 
     * estacion
     * @return media de la valoración total que tiene una estación entre los usuarios
     * que la valoraron
     */
     @GET
    @Path("/valoracion/ciudad={ciudad}&pais={pais}&latitud={latitud}&longitud={longitud}&puntuacionNueva={puntuacionNueva}&puntuacionAntigua={puntuacionAntigua}")
    @Produces(MediaType.APPLICATION_JSON)
    public Double modificarValoracion(@PathParam("ciudad") String ciudad, 
            @PathParam("pais") String pais, @PathParam("latitud") Double latitud,
            @PathParam("longitud") double longitud,
            @PathParam("puntuacionNueva") int puntuacionNueva, @PathParam("puntuacionNueva") int puntuacionAntigua) {
        //TODO write your implementation code here:

        ControladorValoraciones cv = new ControladorValoraciones();
        return cv.modificarPuntuacion(puntuacionNueva, puntuacionAntigua,latitud, longitud, ciudad, pais);
    }
    
    /**
     * Obtiene los nombres de los transportes que proporcionan sus horarios
     * @param ciudad: nombre de la ciudad
     * @param pais: nombre del pais
     * @return lista con los nombres de los transportes que proporcionan horarios
     */
     @GET
    @Path("/transportes/horarios/ciudad={ciudad}&pais={pais}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<String> getTransportesHorarios(@PathParam("ciudad") String ciudad, 
            @PathParam("pais") String pais) {
        //TODO write your implementation code here:

        ControladorTransportes ct = new ControladorTransportes();
        return ct.getTransportesHorarios( ciudad, pais);
    }
    
    /**
     * Obtiene las líneas para las cuales se propocionan los horarios del transporte
     * que se ha propocionado
     * @param ciudad: nombre de la ciudad
     * @param pais: nombre del pais
     * @param transporte: nombre del transporte
     * @return lista con todas las líneas del transporte seleccionado que proporcionan
     * su horario
     */
    @GET
    @Path("/transportes/horarios/ciudad={ciudad}&pais={pais}&transporte={transporte}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<String> getLineasHorarios(@PathParam("ciudad") String ciudad, 
            @PathParam("pais") String pais, @PathParam("transporte") String transporte) {
        //TODO write your implementation code here:

        ControladorTransportes ct = new ControladorTransportes();
        return ct.getLineasHorarios(ciudad, pais,transporte);
    }
    
    /**
     * Obtener el sentido de la línea para la cual se proporciona el horario
     * @param ciudad: nombre de la ciudad
     * @param pais: nombre del pais
     * @param transporte: nombre del transporte
     * @param linea: nombre de la línea
     * @return lista con todos los sentidos que que tiene una línea
     */
    @GET
    @Path("/transportes/horarios/ciudad={ciudad}&pais={pais}&transporte={transporte}&linea={linea}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<String> getSentidoHorarios(@PathParam("ciudad") String ciudad, 
            @PathParam("pais") String pais, @PathParam("transporte") String transporte,
            @PathParam("linea") String linea) {
        //TODO write your implementation code here:

        ControladorTransportes ct = new ControladorTransportes();
        return ct.getSentidoHorarios(ciudad, pais,transporte, linea);
    }
    
    /**
     * Obtener todos los horarios que tiene una línea en una sentido, en una determinada fecha 
     * @param ciudad: nombre de la ciudad
     * @param pais: nombre del pais
     * @param transporte: nombre del transporte
     * @param linea: nombre de la línea
     * @param sentido: nombre del sentido
     * @param fecha: fecha para la cual se quieren saber los horarios
     * @return lista de todas horas a las que saldra un transporte de su estación, 
     * en la fecha seleccionada 
     */
     @GET
    @Path("/transportes/horarios/ciudad={ciudad}&pais={pais}&transporte={transporte}&linea={linea}&sentido={sentido}&fecha={fecha}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<String> getHorarios(@PathParam("ciudad") String ciudad, 
            @PathParam("pais") String pais, @PathParam("transporte") String transporte,
            @PathParam("linea") String linea, @PathParam("sentido") String sentido,
            @PathParam("fecha") String fecha) {
        //TODO write your implementation code here:

        ControladorTransportes ct = new ControladorTransportes();
        return ct.getHorarios( ciudad, pais, transporte, linea, sentido, fecha);
    }
    


    @GET
    @Path("/prueba1")
    @Produces(MediaType.APPLICATION_JSON)
    public AccionViajar getParadasCercanas1() {
        //TODO write your implementation code here:
        String ciudad = "Barcelona";
        String pais = "España";
        String direccion = "Av. Meridiana, 596, Barcelona";
        Double latOr = 41.44331;
        Double longOr = 2.187180000000012;
        String destino = "Plaza cataluña, Barcelona";
        Double latDest = 41.379947;
        Double longDest = 2.1783167999999478;
        ControladorTransportes ct = new ControladorTransportes();
        return ct.getTrayectos(ciudad, pais, latOr, longOr, latDest, longDest).get(0);
    }
}
