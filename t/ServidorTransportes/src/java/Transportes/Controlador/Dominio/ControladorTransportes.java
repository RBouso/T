/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transportes.Controlador.Dominio;

import FactoriaDatos.CtrlFactoriaDatos;
import Transportes.Controlador.Datos.Interficie.CtrlEstacionCivica;
import Transportes.Controlador.Datos.Interficie.CtrlCiudad;
import Transportes.Controlador.Datos.Interficie.CtrlEstacionAlquilerBicicletas;
import Transportes.Controlador.Datos.Interficie.CtrlEstacionAparcamiento;
import Transportes.Controlador.Datos.Interficie.CtrlEstacionTaxi;
import Transportes.Controlador.Datos.Interficie.CtrlEstructurasPublicas;
import Transportes.Controlador.Datos.Interficie.CtrlLineaEstacion;
import Transportes.Ficheros.AccionViajar;
import Transportes.Ficheros.Ciudad;
import Transportes.Ficheros.EstacionAlquilerBicicletas;
import Transportes.Ficheros.EstacionAparcamiento;
import Transportes.Ficheros.EstacionCivica;
import Transportes.Ficheros.EstacionTaxi;
import Transportes.Ficheros.EstructurasPublicas;
import Transportes.Ficheros.Hora;
import Transportes.Ficheros.HorarioApertura;
import Transportes.Ficheros.LineaEstacion;
import Transportes.Ficheros.Lugar;
import Transportes.Ficheros.TipoDia;
import java.awt.geom.Point2D;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import javax.persistence.Convert;
import maps.java.Geocoding;

/**
 *
 * @author raquel
 */
public class ControladorTransportes extends Controlador{

    private Double latitudOr;
    private Double longitudOr;
    private Double latitudDest;
    private Double longitudDest;
    //radio de la tierra en km
    private double radio = 6371;
    private ArrayList<Lineas> lineas = new ArrayList<>();
    
    public ControladorTransportes()
    {}
    
    @Override
    public void executar() {
       
    }

    /**
     * Obtener la lista de ciudades guardadas en el sistema
     * @return Lista de los nombres de las ciudades que están guardadas en el
     * sistema.
     */
    public ArrayList<String> getCiudades() {
         CtrlFactoriaDatos cfd = new CtrlFactoriaDatos();
        //Conseguir todas las ciudades que hay en el sistema
        CtrlCiudad cc = cfd.getCtrlCiudad();
        List<Ciudad> c = cc.getCiudades();
        ArrayList<String> lc = new ArrayList<>();
        
        for (int i = 0; i < c.size(); i++) {
            lc.add(c.get(i).getNombre());
        }
        return  lc;
    }

    /**
     * Obtener la lista de transportes que tiene una ciudad
     * @param ciudad: nombre de la ciudad de la cual se quiere obtener sus transportes
     * @return Lista de los transportes que contiene la ciudad
     */
    public ArrayList<String> getTransportes(String ciudad) {
        CtrlFactoriaDatos cfd = new CtrlFactoriaDatos();
        //Conseguir todas las ciudades que hay en el sistema
        CtrlCiudad cc = cfd.getCtrlCiudad();
       return cc.getTransportes(ciudad);
        
    }

     /**
     * Obtener la lista de transportes que proporcionan horarios en una ciudad
     * @param ciudad: nombre de la ciudad de la cual se quiere obtener sus transportes
     * @param pais: nombre del pais
     * @return Lista de los transportes que contiene la ciudad
     */
    public ArrayList<String> getTransportesHorarios(String ciudad, String pais) {
        CtrlFactoriaDatos cfd = new CtrlFactoriaDatos();
        //Conseguir todas las ciudades que hay en el sistema
        CtrlCiudad cc = cfd.getCtrlCiudad();
       return cc.getTransportesHorarios(ciudad, pais);
    }
    
    /**
     * Obtener la lista de líneas que tiene un determinado transporte de una ciudad
     * @param ciudad: Nombre de la ciudad 
     * @param transporte: Nombre del transporte
     * @return Lista de lineas que el transporte tiene en la ciudad ciudad
     */
    public ArrayList<String> getLineas(String ciudad, String pais, String transporte) {
        CtrlFactoriaDatos cfd = new CtrlFactoriaDatos();
        CtrlEstacionCivica cec =  cfd.getCtrlEstacionCivica(transporte) ;
        if (cec == null) return null;
        return cec.getLineas(ciudad, pais);
    }

    public ArrayList<String> getLineasHorarios(String ciudad, String pais, String transporte) {
        CtrlFactoriaDatos cfd = new CtrlFactoriaDatos();
        CtrlEstacionCivica cec =  cfd.getCtrlEstacionCivica(transporte) ;
        if (cec == null) return null;
        return cec.getLineasHorarios(ciudad, pais);
    }
    
    public ArrayList<String> getSentidoHorarios(String ciudad, String pais,
            String transporte, String linea) {
        CtrlFactoriaDatos cfd = new CtrlFactoriaDatos();
        CtrlEstacionCivica cec =  cfd.getCtrlEstacionCivica(transporte) ;
        if (cec == null) return null;
        return cec.getSentidoHorarios(ciudad, pais, linea);
    }
    
    private boolean esValida(String fecha) {
        if (fecha.length() == 10) {
            if (fecha.indexOf("-") == 4) {

                String dato = fecha.substring(fecha.indexOf("-")+1);
                if(dato.indexOf("-") == 2) {
                    if(fecha.lastIndexOf("-") == 7)
                        return true;
                    return false;
                }
                return false;
            }
            return false;
        }
        return false;
    }
    
    public ArrayList<String> getHorarios(String ciudad, String pais, String transporte,
            String linea, String sentido, String fecha) {
        CtrlFactoriaDatos cfd = new CtrlFactoriaDatos();
        CtrlEstacionCivica cec =  cfd.getCtrlEstacionCivica(transporte) ;
        if (cec == null) return null;
        ArrayList<String> result = new ArrayList<String>();
        if(!esValida(fecha)) {
            result.add("La fecha no tiene un formato valido, deberia tener un formato yyyy-MM-dd.");
            return result;
        }
        Calendar c = new GregorianCalendar(Integer.valueOf(fecha.substring(0, 3)), 
                Integer.valueOf(fecha.substring(5,6)), Integer.valueOf(fecha.substring(8,9)));
        ArrayList<HorarioApertura> ha = cec.getHorarios(ciudad, pais, linea, sentido);
        int dia = c.get(Calendar.DAY_OF_WEEK);
        int mes = c.get(Calendar.MONTH);
        for (int i = 0; i < ha.size(); i++) {
            HorarioApertura h = ha.get(i);
            TipoDia t = new TipoDia();
            Hora hora = h.getHoraSalida();
            System.out.println(hora.getHora()+" "+ hora.getMinuto());
            if(h.getTipo() != null) {
                t = h.getTipo();
                System.out.println(t.getNombre()+" "+dia);
                if (dia == Calendar.SUNDAY) {
                    if ( t.getNombre().contains("Festivo")) {
                        String res = String.valueOf(hora.getHora())+":"+String.valueOf(hora.getMinuto());
                        result.add(res);
                    }
                }
                else if (dia == Calendar.SATURDAY)  {
                    if ( t.getNombre().contains("Sábado")) {
                        String res = String.valueOf(hora.getHora())+":"+String.valueOf(hora.getMinuto());
                        result.add(res);
                    }
                }
               else  {
                    if (t.getNombre().contains("Laborables")) {
                    String res = String.valueOf(hora.getHora())+":"+String.valueOf(hora.getMinuto());
                        result.add(res);
                    }
                }
            }
            else  {
                    String res = String.valueOf(hora.getHora())+":"+String.valueOf(hora.getMinuto());
                        result.add(res);
            }
            
           
            
        }
        return result;
    }
    
    public ArrayList<EstacionCivica > getParadas(String ciudad, String pais, String transporte, 
            String linea) {
        CtrlFactoriaDatos cfd = new CtrlFactoriaDatos();
        CtrlLineaEstacion cle =  cfd.getCtrlLineaEstacion() ;
        ArrayList<LineaEstacion> lista = cle.getLineaEstacionTransporte(ciudad, 
                pais, transporte, linea);
        ArrayList<EstacionCivica> l = new ArrayList<>();
        for (int i = 0; i < lista.size(); i++) {
            LineaEstacion estacion = lista.get(i);
            EstacionCivica par = estacion.getEstacion();
            l.add(par);
               
        }
       
        return l;
    }

    public ArrayList<EstacionAparcamiento> getAparcamientos(String ciudad, 
            String pais) {
        CtrlFactoriaDatos cfd = new CtrlFactoriaDatos();
        CtrlEstacionAparcamiento cea = cfd.getCtrlEstacionAparcamiento();
        return cea.getAparcamiento(ciudad, pais);
    }

    public ArrayList<EstacionAlquilerBicicletas> getBicicletas(String ciudad, 
            String pais) {
        CtrlFactoriaDatos cfd = new CtrlFactoriaDatos();
        CtrlEstacionAlquilerBicicletas ceab = cfd.getCtrlEstacionAlquilerBicicletas();
        return ceab.getBicicletas(ciudad, pais);
    }

    public ArrayList<EstacionTaxi> getTaxi(String ciudad, String pais) {
        CtrlFactoriaDatos cfd = new CtrlFactoriaDatos();
        CtrlEstacionTaxi cet = cfd.getCtrlEstacionTaxi();
        return cet.getTaxis(ciudad, pais);
    }

    public ArrayList<EstructurasPublicas> getParadasCercanas(String ciudad, String pais, 
            String direccion, double latitud, double longitud) {
        CtrlFactoriaDatos cfd = new CtrlFactoriaDatos();
        ArrayList<EstructurasPublicas> ea = new ArrayList<>();
        CtrlCiudad cc = cfd.getCtrlCiudad();
        ArrayList<String> lc = cc.getTransportes(ciudad);
        for (int i = 0; i < lc.size(); i++) {
            CtrlEstructurasPublicas cep = cfd.getCtrlEstructurasPublicas(lc.get(i));
            if (cep != null) {
                ArrayList<EstructurasPublicas> ec = new ArrayList<>();
                ec = cep.getParadasCercanas(ciudad, pais, 
                        direccion,latitud,longitud);
                for (int j = 0; j < ec.size(); j++)
                    ea.add(ec.get(j));
            }
        }
        return ea;
    }

    private ArrayList<Estructuras> getEstacionesCercana(String ciudad, String pais, 
            String dir, Double lat, Double lon) {
                CtrlFactoriaDatos cfd = new CtrlFactoriaDatos();
        ArrayList<Estructuras> ea = new ArrayList<>();
        CtrlCiudad cc = cfd.getCtrlCiudad();
        ArrayList<String> lc = cc.getTransportes(ciudad);
        for (int i = 0; i < lc.size(); i++) {
            if (!lc.get(i).equals("Taxi") && !lc.get(i).equals("Aparcamiento") &&
                    !lc.get(i).equals("Bicicletas") ) {
                CtrlEstructurasPublicas cep = cfd.getCtrlEstructurasPublicas(lc.get(i));
                if (cep != null) {
                    Estructuras est = new Estructuras();
                    est.estaciones = cep.getParadasCercanas(ciudad, pais, 
                            dir,lat,lon);
                    
                    est.transporte = lc.get(i);
                    ea.add(est);
                }
            }
        }
        return ea;
    }
   
    public ArrayList<AccionViajar> getTrayectos(String ciudad, String pais, 
            double latOr, double longOr, double latDest, double longDest) {

            CtrlFactoriaDatos cfd = new CtrlFactoriaDatos();
            ArrayList<EstacionCivica> ec = new ArrayList<>();
            ArrayList<AccionViajar> av = new ArrayList<>();
            AccionViajar mejor = new AccionViajar();
            ArrayList<Estructuras> epOrigen = getEstacionesCercana(ciudad, pais, "",latOr,longOr);
            ArrayList<Estructuras> epDestino = getEstacionesCercana(ciudad, pais, "",latDest,longDest);
            
            latitudOr = latOr;
            longitudOr = longOr;
            latitudDest = latDest;
            longitudDest = longOr;
            
            Comparator<AccionViajar> comparator = new EstructurasPublicasComparator();

            //abiertos.insertar(Estado inicial)
            ArrayList<EstacionCivica> lista = new ArrayList<>();
            PriorityQueue<AccionViajar> abiertos = new PriorityQueue<>(comparator);
            for (int i = 0; i < epOrigen.size(); i++) {
                for (int j = 0; j < epOrigen.get(i).estaciones.size(); j++) {
                    
                    AccionViajar copiar = new AccionViajar();
                    copiar.setOrigen(epOrigen.get(i).estaciones.get(j));
                    copiar.setDestino(epOrigen.get(i).estaciones.get(j));
                    if (epOrigen.get(i).estaciones.get(j).getEc() != null && epOrigen.get(i).estaciones.get(j).getEc().getLineas() != null)
                        for (int k = 0; k < epOrigen.get(i).estaciones.get(j).getEc().getLineas().size(); k++) {
                            if (contieneLinea(lineas,epOrigen.get(i).estaciones.get(j).getEc().getLineas().get(k).getNumLinea()))
                                getParadas(ciudad, pais, epOrigen.get(i).estaciones.get(j).transporte,
                                    epOrigen.get(i).estaciones.get(j).getEc().getLineas().get(k).getNumLinea());
                        }

                    lista.add((EstacionCivica) epOrigen.get(i).estaciones.get(j));
                    copiar.setDistancia(0.0);
    //                System.out.println("");
                    abiertos.add(copiar);
                }
            }
            ArrayList<AccionViajar> cerrados = new ArrayList<>();
            //mientras no es final Actual y no Est_abiertos.vacía?( hacer
            AccionViajar actual = abiertos.peek();
            int soy = 0;
            boolean fin = esFinal(actual, epDestino);
            while (!abiertos.isEmpty() && !fin) {
                
                soy++;
                //Est_abiertos.borrar_primero()
                abiertos.poll();
                //Est_cerrados.insertar(Actual)
                cerrados.add(actual);
                
                //hijos←generar_sucesores(Actual)
                ArrayList<Estructuras> hijos = new ArrayList<Estructuras>();
                //hijos.add(getProximaParada(actual));
                hijos.addAll(getEstacionesCercana(ciudad, 
                        pais, null,actual.getDestino().getGeo().getLatitud(),actual.getDestino().getGeo().getLongitud()));
                //hijos←tratar_repetidos(Hijos, Est_cerrados, Est_abiertos)
               hijos = tratarRepetidos(hijos, cerrados, abiertos);
               //Est_abiertos.insertar(Hijos)
               for (int i = 0; i < hijos.size(); i++) {
                   for(int j = 0; j < hijos.get(i).estaciones.size(); j++) {
                        AccionViajar copiar = new AccionViajar();
                        copiar.setOrigen(actual.getOrigen());
                        copiar.setDestino(hijos.get(i).estaciones.get(j));
                        ArrayList<EstacionCivica> cl = actual.getEstacionCivica();
                        cl.add((EstacionCivica) hijos.get(i).estaciones.get(j));
                        copiar.setEstacionCivica(cl);
                        copiar.setDistancia(distancia(copiar,latitudOr,longitudOr));
                        abiertos.add(copiar);
                   }
               }
               //Actual←Est_abiertos.primero
                if(!abiertos.isEmpty())actual = abiertos.peek();
                fin = esFinal(actual, epDestino);
                System.out.println(actual.getOrigen().getGeo().getLatitud()+" "+actual.getDestino().getGeo().getLatitud());
            }
            if(!fin) System.out.println("Soy final ");
            av.add(actual);
            return av;
        
    }

    private ArrayList<Estructuras> tratarRepetidos(ArrayList<Estructuras> hijos, 
            ArrayList<AccionViajar> cerrados, PriorityQueue<AccionViajar> abiertos) {
        ArrayList<Estructuras> result = new ArrayList<>();
        for (int i = 0; i < hijos.size(); i++) {
            Estructuras est = new Estructuras();
            est.transporte = hijos.get(i).transporte;
            for (int l = 0; l < hijos.get(i).estaciones.size(); l++) {
                int encontrado = 0;
                for (int j = 0; j < cerrados.size() && encontrado < 1; j++) {
                    AccionViajar av = cerrados.get(j);
                    for (int k = 0; k < av.getEstacionCivica().size() && encontrado < 1; k++) {

                        if(av.getEstacionCivica().get(k).getGeo().equals(hijos.get(i).estaciones.get(l).getGeo()))
                            encontrado++;
                    }
                }
          
                Iterator<AccionViajar> it = abiertos.iterator();

                while(it.hasNext() && encontrado < 1) {
                    AccionViajar av = it.next();
                    for (int k = 0; k < av.getEstacionCivica().size() && encontrado < 2; k++) {
                        if(av.getEstacionCivica().get(k).getGeo().equals(hijos.get(i).estaciones.get(l).getGeo()))
                            encontrado++;
                    }
                }
                if (encontrado == 0) est.estaciones.add( hijos.get(i).estaciones.get(l));
            }
            result.add(est);
        }
        return result;
    }

    private boolean esFinal(AccionViajar actual, ArrayList<Estructuras> epDestino) {
        for (int i = 0; i < epDestino.size(); i++) {
            for (int j = 0; j < epDestino.get(i).estaciones.size(); j++)
            if(epDestino.get(i).estaciones.get(j).getGeo().getLatitud() == actual.getDestino().getGeo().getLatitud() &&
                   epDestino.get(i).estaciones.get(j).getGeo().getLongitud()== actual.getDestino().getGeo().getLongitud())
                return true;
        }
        return false;
    }

    
    private double distancia(AccionViajar o1, double lat1, double lon1 ) {
            //Δlat = lat2− lat1
            double lat = EnRadianes(o1.getDestino().getGeo().getLatitud() - lat1);
            
            //Δlong = long2− long1
            double lon = EnRadianes(o1.getDestino().getGeo().getLongitud() - lon1);
            //a = sin²(Δlat/2) + cos(lat1) · cos(lat2) · sin²(Δlong/2)
            double a = Math.pow(Math.sin(lat/2),2) + Math.cos(EnRadianes(lat1))+
                    Math.cos(EnRadianes(o1.getDestino().getGeo().getLatitud())+
                            Math.pow(Math.sin(lon/2),2));
            //c = 2 · atan2(√a, √(1−a))
            double raiz1 = Math.sqrt(a); 
            double raiz2 = Math.sqrt(1.0 - a);
            double c = (Math.atan2(raiz1, raiz2));
            c *= 2;
            //d = R · c
            return radio * c;
        }

    private double EnRadianes(double valor)
        {
            return Math.toRadians(valor);
          }

    private boolean contieneLinea(ArrayList<Lineas> lineas, String numLinea) {
        
        for (int i = 0; i < lineas.size(); i++) {
            if (lineas.get(i).Linea.equals(numLinea))
                return true;
        }
        return false;
    }

    private Estructuras getProximaParada(AccionViajar actual) {
        Estructuras est = new Estructuras();
        CtrlFactoriaDatos cfd = new CtrlFactoriaDatos();
        CtrlEstructurasPublicas cep = cfd.getCtrlEstructurasPublicas(actual.getDestino().transporte);
       // cep.getSiguienteParada(actual.getDestino());
        return est;
    }


    
    private class EstructurasPublicasComparator implements Comparator<AccionViajar> {

        @Override
       
        public int compare(AccionViajar o1, AccionViajar o2) {
            double distO1= distancia(o1,latitudOr,longitudOr);
            double distO2 = distancia(o2,latitudOr,longitudOr);
            //distancia a destino
            double distD1= distancia(o1,latitudDest,longitudDest);
            double distD2 = distancia(o2,latitudDest,longitudDest);
            if (distO1 <  distO2) {
                if(distD1 <= distD2) return -1;
                else return 1;
            }
            if (distO1 > distO2) {
                if(distD1 <= distD2) return -1;
                else return 1;
            }
            return 0;
            
        }

        
        

       
    }
    
    
    private class Lineas {
        String Linea;
        ArrayList<EstacionCivica> estaciones = new ArrayList<>();
    }
    
    private class Estructuras {
        String transporte;
        ArrayList<EstructurasPublicas> estaciones = new ArrayList<>();
    }
    
}
