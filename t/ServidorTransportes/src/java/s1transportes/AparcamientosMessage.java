/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s1transportes;

import Transportes.Ficheros.EstacionAlquilerBicicletas;
import Transportes.Ficheros.EstacionAparcamiento;
import Transportes.Ficheros.EstacionCivica;
import Transportes.Ficheros.EstacionTaxi;
import Transportes.Ficheros.EstructurasPublicas;
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
            if(type1.getTypeName().contains("ArrayList<Transportes.Ficheros.EstacionAparcamiento>")) {
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
            else if(type1.getTypeName().contains("ArrayList<Transportes.Ficheros.EstacionAlquilerBicicletas>")) {
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
            else if(type1.getTypeName().contains("ArrayList<Transportes.Ficheros.EstacionCivica>") ||
                    type1.getTypeName().contains("ArrayList<Transportes.Ficheros.EstructurasPublicas>")) {
                buffer = buffer.append(" {\"Estaciones\":[");
                int ultimo = 0;
                for (int i = 0; i < t.size(); i++) {
                    EstructurasPublicas apar = new EstructurasPublicas();
                    EstacionCivica apar1 = new EstacionCivica();
                    if (type1.getTypeName().contains("EstacionCivica")) 
                        apar = (EstacionCivica) t.get(i);
                    else apar = (EstructurasPublicas) t.get(i);
                    if(apar != null) { 
                      boolean pintar =false;  
                    if (ultimo != 0)  buffer = buffer.append(",");
                    ultimo++;
                    buffer = buffer.append("{");
                    
                    if( type1.getTypeName().contains("ArrayList<Transportes.Ficheros.EstructurasPublicas>")){
                        buffer = buffer.append("\"transporte\":\"").append(apar.transporte);
                        buffer = buffer.append("\",");
                    }
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
                        buffer = buffer.append(apar.getTelefono());
                    }
                    
                    
                   if( type1.getTypeName().contains("ArrayList<Transportes.Ficheros.EstructurasPublicas>")){
                       
                       if (apar.transporte.equals("Aparcamiento")) {
                           buffer = buffer.append("\",");
                           System.out.println(apar.toString());
                           EstacionAparcamiento a = apar.getEa();
                            buffer = buffer.append("\"accesibilidad\":\"");
                            if((a != null)) {
                                if (a.getAccesibilidad() == 0 || a.getAccesibilidad() == 1) {
                                   buffer = buffer.append(a.getAccesibilidad());
                                }
                                buffer = buffer.append("\",");
                                buffer = buffer.append("\"plazasLibres\":\"");
                                if (a.getPlazasLibres() >= 0) {
                                    buffer = buffer.append(a.getPlazasLibres());
                                }
                                buffer = buffer.append("\",");
                                buffer = buffer.append("\"plazasTotales\":\"");
                                if (a.getPlazasTotales() >= 0) {
                                    buffer = buffer.append(a.getPlazasTotales());
                                }
                            }
                       }
                       else if (apar.transporte.equals("Bicicleta")) {
                           buffer = buffer.append("\",");
                           EstacionAlquilerBicicletas b = apar.getEab();
                           buffer = buffer.append("\"anclajes\":\"");
                        if (b.getAnclajes() >= 0) {
                           buffer = buffer.append(b.getAnclajes());
                        }
                        buffer = buffer.append("\",");
                        buffer = buffer.append("\"biciLibres\":\"");
                        if (b.getBiciLibres() >= 0) {
                            buffer = buffer.append(b.getBiciLibres());
                        }
                       }
                       else {
                            buffer = buffer.append("\", ");
                            buffer = buffer.append("\"lineas\":\"");
                            if(apar.getEc() != null) {
                                if(apar.getEc().getLineas() != null) {
                                    if(apar.getEc().getLineas()!= null) {
                                        buffer = buffer.append("[\"");
                                        for (int j = 0; j < apar.getEc().getLineas().size(); j++) {
                                            if (j != 0) buffer = buffer.append(", \"");
                                            buffer = buffer.append(apar.getEc().getLineas().get(j).getNumLinea());
                                            buffer = buffer.append("\"");
                                        }
                                    buffer = buffer.append("]");
                                    }
                                }
                            }
                       }
                    }
                   else {
                        buffer = buffer.append("\",");
                        buffer = buffer.append("\"lineas\":");
                        apar1 = (EstacionCivica) t.get(i);
                        
                        if (apar1 != null) {
                            if(apar1.getLineas()!= null) {
                                buffer = buffer.append("[\"");
                                for (int j = 0; j < apar1.getLineas().size(); j++) {
                                    if (j != 0) buffer = buffer.append(", \"");
                                    buffer = buffer.append(apar1.getLineas().get(j).getNumLinea());
                                    buffer = buffer.append("\"");
                                }
                                buffer = buffer.append("]");
                                pintar = true;
                            }
                        }
//                            
                   }
                    
                   if(!pintar) buffer = buffer.append("\"}");
                   else buffer = buffer.append("}");
                    
                }
                }
                buffer = buffer.append("]}");
            }
            
            else if(type1.getTypeName().contains("ArrayList<Transportes.Ficheros.EstacionTaxi>")) {
                buffer = buffer.append(" {\"Taxi\":[");
                for (int i = 0; i < t.size(); i++) {

                    EstacionTaxi apar = (EstacionTaxi) t.get(i);
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

