/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s1transportes;

import Ficheros.EstacionAlquilerBicicletas;
import Ficheros.EstacionAparcamiento;
import Ficheros.EstacionCivica;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author raquel
 */

@Provider
@Produces("application/json")
public class AparcamientosMessage implements MessageBodyWriter<ArrayList<Object>> {
 
    @Override
    public boolean isWriteable(Class<?> type, Type type1, Annotation[] antns, MediaType mt) {
        return true;
    }
 
   
   
 
    @Override
    public void writeTo(ArrayList<Object> t, Class<?> type,
            Type type1, Annotation[] antns, MediaType mt, MultivaluedMap<String, 
                    Object> mm, OutputStream out) throws IOException, 
            WebApplicationException {
        if (mt.getType().equals("application") && mt.getSubtype().equals("json")) {
            StringBuffer buffer = new StringBuffer();
//            buffer = buffer.append(type1.getTypeName());
            if(type1.getTypeName().contains("ArrayList<Ficheros.EstacionAparcamiento>")) {
                buffer = buffer.append(" {\"Aparcamientos\":[");
                for (int i = 0; i < t.size(); i++) {

                    EstacionAparcamiento apar = (EstacionAparcamiento) t.get(i);
                    buffer = buffer.append("{");
                    buffer = buffer.append("\"nombre\":\"");
                    if (apar.getNombre() != null) {
                        buffer = buffer.append(apar.getNombre());   
                    }
                    buffer = buffer.append("\",");
                    buffer = buffer.append("\"descripcion\":\"");
                   
                    if (apar.getDescripcion() != null) {
                        buffer = buffer.append(apar.getDescripcion());
                        
                    }
                    
                    if (apar.getGeo() != null) {
                        buffer = buffer.append("\",");
                        buffer = buffer.append("\"latitud\":\"").append(apar.getGeo().getLatitud());
                        buffer = buffer.append("\",");
                        buffer = buffer.append("\"longitud\":\"").append(apar.getGeo().getLongitud());
                    }
                    else {
                        buffer = buffer.append("\",");
                        buffer = buffer.append("\"latitud\":\"");
                         buffer = buffer.append("\",");
                        buffer = buffer.append("\"longitud\":\"");
                    }
                    if (apar.getDireccion()!= null) {
                        buffer = buffer.append("\",");
                        buffer = buffer.append("\"pais\":\"").append(apar.getDireccion().getPais());
                        buffer = buffer.append("\",");
                        buffer = buffer.append("\"localidad\":\"").append(apar.getDireccion().getLocalidad());
                        buffer = buffer.append("\",");
                        buffer = buffer.append("\"direccion\":\"").append(apar.getDireccion().getDireccion());
                    }
                    else {
                         buffer = buffer.append("\",");
                        buffer = buffer.append("\"pais\":\"");
                         buffer = buffer.append("\",");
                        buffer = buffer.append("\"localidad\":\"");
                        buffer = buffer.append("\",");
                        buffer = buffer.append("\"direccion\":\"");
                        
                    }
                    buffer = buffer.append("\",");
                    buffer = buffer.append("\"telefono\":\"");
                    if(apar.getTelefono() != null) {
                        
                    }
                    buffer = buffer.append("\",");
                    buffer = buffer.append("\"accesibilidad\":\"");
                    if (apar.getAccesibilidad() == 0 || apar.getAccesibilidad() == 1) {
                       buffer = buffer.append(apar.getAccesibilidad());
                    }
                    buffer = buffer.append("\",");
                    buffer = buffer.append("\"plazasLibres\":\"");
                    if (apar.getPlazasLibres() >= 0) {
                        buffer = buffer.append(apar.getPlazasLibres());
                    }
                    buffer = buffer.append("\",");
                    buffer = buffer.append("\"plazasTotales\":\"");
                    if (apar.getPlazasTotales() >= 0) {
                        buffer = buffer.append(apar.getPlazasTotales());
                    }
                    buffer = buffer.append("\"}");

                    if(i < t.size()-1) buffer = buffer.append(",");
                }
                buffer = buffer.append("]}");
            }
            else if(type1.getTypeName().contains("ArrayList<Ficheros.EstacionAlquilerBicicletas>")) {
                buffer = buffer.append(" {\"Bicicletas\":[");
                for (int i = 0; i < t.size(); i++) {

                    EstacionAlquilerBicicletas apar = (EstacionAlquilerBicicletas) t.get(i);
                    buffer = buffer.append("{");
                    buffer = buffer.append("\"nombre\":\"");
                    if (apar.getNombre() != null) {
                        buffer = buffer.append(apar.getNombre());   
                    }
                    buffer = buffer.append("\",");
                    buffer = buffer.append("\"descripcion\":\"");
                   
                    if (apar.getDescripcion() != null) {
                        buffer = buffer.append(apar.getDescripcion());
                        
                    }
                    
                    if (apar.getGeo() != null) {
                        buffer = buffer.append("\",");
                        buffer = buffer.append("\"latitud\":\"").append(apar.getGeo().getLatitud());
                        buffer = buffer.append("\",");
                        buffer = buffer.append("\"longitud\":\"").append(apar.getGeo().getLongitud());
                    }
                    else {
                        buffer = buffer.append("\",");
                        buffer = buffer.append("\"latitud\":\"");
                         buffer = buffer.append("\",");
                        buffer = buffer.append("\"longitud\":\"");
                    }
                    if (apar.getDireccion()!= null) {
                        buffer = buffer.append("\",");
                        buffer = buffer.append("\"pais\":\"").append(apar.getDireccion().getPais());
                        buffer = buffer.append("\",");
                        buffer = buffer.append("\"localidad\":\"").append(apar.getDireccion().getLocalidad());
                        buffer = buffer.append("\",");
                        buffer = buffer.append("\"direccion\":\"").append(apar.getDireccion().getDireccion());
                    }
                    else {
                         buffer = buffer.append("\",");
                        buffer = buffer.append("\"pais\":\"");
                         buffer = buffer.append("\",");
                        buffer = buffer.append("\"localidad\":\"");
                        buffer = buffer.append("\",");
                        buffer = buffer.append("\"direccion\":\"");
                    }
                    buffer = buffer.append("\",");
                    buffer = buffer.append("\"telefono\":\"");
                    if(apar.getTelefono() != null) {
                        
                    }
                    buffer = buffer.append("\",");
                    buffer = buffer.append("\"anclajes\":\"");
                    if (apar.getAnclajes() >= 0) {
                       buffer = buffer.append(apar.getAnclajes());
                    }
                    buffer = buffer.append("\",");
                    buffer = buffer.append("\"biciLibres\":\"");
                    if (apar.getBiciLibres() >= 0) {
                        buffer = buffer.append(apar.getBiciLibres());
                    }
               
                    buffer = buffer.append("\"}");

                    if(i < t.size()-1) buffer = buffer.append(",");
                }
                buffer = buffer.append("]}");
            }
            else if(type1.getTypeName().contains("ArrayList<Ficheros.EstacionCivica>")) {
                buffer = buffer.append(" {\"Estaciones\":[");
                for (int i = 0; i < t.size(); i++) {

                    EstacionCivica apar = (EstacionCivica) t.get(i);
                    buffer = buffer.append("{");
                    buffer = buffer.append("\"nombre\":\"");
                    if (apar.getNombre() != null) {
                        buffer = buffer.append(apar.getNombre());   
                    }
                    buffer = buffer.append("\",");
                    buffer = buffer.append("\"descripcion\":\"");
                   
                    if (apar.getDescripcion() != null) {
                        buffer = buffer.append(apar.getDescripcion());
                        
                    }
                    
                    if (apar.getGeo() != null) {
                        buffer = buffer.append("\",");
                        buffer = buffer.append("\"latitud\":\"").append(apar.getGeo().getLatitud());
                        buffer = buffer.append("\",");
                        buffer = buffer.append("\"longitud\":\"").append(apar.getGeo().getLongitud());
                    }
                    else {
                        buffer = buffer.append("\",");
                        buffer = buffer.append("\"latitud\":\"");
                         buffer = buffer.append("\",");
                        buffer = buffer.append("\"longitud\":\"");
                    }
                    if (apar.getDireccion()!= null) {
                        buffer = buffer.append("\",");
                        buffer = buffer.append("\"pais\":\"").append(apar.getDireccion().getPais());
                        buffer = buffer.append("\",");
                        buffer = buffer.append("\"localidad\":\"").append(apar.getDireccion().getLocalidad());
                        buffer = buffer.append("\",");
                        buffer = buffer.append("\"direccion\":\"").append(apar.getDireccion().getDireccion());
                    }
                    else {
                        buffer = buffer.append("\",");
                        buffer = buffer.append("\"pais\":\"");
                        buffer = buffer.append("\",");
                        buffer = buffer.append("\"localidad\":\"");
                        buffer = buffer.append("\",");
                        buffer = buffer.append("\"direccion\":\"");
                    }
                    buffer = buffer.append("\",");
                    buffer = buffer.append("\"telefono\":\"");
                    if(apar.getTelefono() != null) {
                        
                    }
                   
                    
                    buffer = buffer.append("\"}");

                    if(i < t.size()-1) buffer = buffer.append(",");
                }
                buffer = buffer.append("]}");
            }
            
            else if (type1.getTypeName().contains("ArrayList<java.lang.String>")) {
                buffer = buffer.append("{\"nombres\":[");
                for (int i = 0; i < t.size(); i++) {
                    buffer = buffer.append("{");
                    buffer = buffer.append("\"nombre\":\"").append(String.valueOf(t.get(i)));
                    buffer = buffer.append("\"}");
                    if(i < t.size()-1) buffer = buffer.append(",");
                }
                buffer = buffer.append("]}");
            }
            try (PrintStream printStream = new PrintStream(out,true, "UTF-8")) {
                printStream.print(buffer.toString());
            }
            return;
        } 
        throw new UnsupportedOperationException("Not supported MediaType: " + mt);
    }

   


    @Override
    public long getSize(ArrayList<Object> t, Class<?> type, Type type1, Annotation[] antns, MediaType mt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   

   

    
}

