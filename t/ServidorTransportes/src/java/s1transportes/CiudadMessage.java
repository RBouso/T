/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s1transportes;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
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
@Produces({"text/plain", "application/json"})
public class CiudadMessage implements MessageBodyWriter<List<String>> {
 
    public boolean isWriteable(Class<?> type, Type type1, Annotation[] antns, MediaType mt) {
        return true;
    }
 
   
   
 
    public void writeTo(List<String> t, Class<?> type, Type type1, Annotation[] antns, MediaType mt, MultivaluedMap<String, Object> mm, OutputStream out) throws IOException, WebApplicationException {
        if (mt.getType().equals("application") && mt.getSubtype().equals("json")) {
            StringBuffer buffer = new StringBuffer();
            buffer = buffer.append("{\"nombres\":[");
            for (int i = 0; i < t.size(); i++) {
                buffer = buffer.append("{");
                buffer = buffer.append("\"nombre\":\"").append(t.get(i));
                buffer = buffer.append("\"}");
                if(i < t.size()-1) buffer = buffer.append(",");
            }
            buffer = buffer.append("]}");
            try (PrintStream printStream = new PrintStream(out)) {
                printStream.print(buffer.toString());
            }
            return;
        } 
        throw new UnsupportedOperationException("Not supported MediaType: " + mt);
    }

   
    @Override
    public long getSize(List<String> t, Class<?> type, Type type1, Annotation[] antns, MediaType mt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   

    
}
