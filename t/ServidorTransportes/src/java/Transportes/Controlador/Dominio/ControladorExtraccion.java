/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transportes.Controlador.Dominio;

import FactoriaDatos.CtrlFactoriaDatos;
import Transportes.Controlador.Datos.Interficie.CtrlCiudad;
import Valoraciones.Controlador.Datos.Interficie.CtrlURL;
import Transportes.Ficheros.Ciudad;
import Valoraciones.Ficheros.URL;
import Transportes.ServicioDatosAbiertos.ServicioAdapter;
import Transportes.extraccion.extractorFacade;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author raquel
 */
public class ControladorExtraccion extends Controlador{
    
    /**
     * Se realiza todo el proceso de obtener las urls que se encuentran en el 
     * sistema para descargar los ficheros, y posteriormente extraer los datos de 
     * esos ficheros para integrarlos en un modelo unico.
     */
    public void executar(){
        CtrlFactoriaDatos cfd = new CtrlFactoriaDatos();
        //Conseguir todas las ciudades que hay en el sistema
        CtrlCiudad cc = cfd.getCtrlCiudad();
        CtrlURL cu = cfd.getCtrlURL();
        extractorFacade e = new extractorFacade();
        ArrayList<Ciudad> ciudades =  cc.getCiudades();
        //Para cada ciudad que hay en el sistema extraemos los datos de sus ficheros
        for (int i = 0; i < ciudades.size(); i++) {
            String nombre = ciudades.get(i).getNombre();
            String pais = ciudades.get(i).getPais(); 
            //Conseguir todas las urls que tiene la ciudad y pais
            ArrayList<URL> au = cu.getURLs(nombre, pais);
            ServicioAdapter sa = new ServicioAdapter();
            for (int j = 0; j < au.size(); j++) {
                //Descargamos el fichero

                Calendar fecha = new GregorianCalendar();
                //Obtenemos el valor del año, mes, día,
                //hora, minuto y segundo del sistema
                //usando el método get y el parámetro correspondiente
                int año = fecha.get(Calendar.YEAR);
                int mes = fecha.get(Calendar.MONTH);
                int dia = fecha.get(Calendar.DAY_OF_MONTH);
                int hora = fecha.get(Calendar.HOUR_OF_DAY);
                int minuto = fecha.get(Calendar.MINUTE);
                int segundo = fecha.get(Calendar.SECOND);
                String fech = año+"/";
                if (mes > 10) fech+= mes+"/";
                else fech+= "0"+mes+"/";
                if (dia > 10) fech+= dia;
                else fech+= "0"+dia;
                
                String fechaAct = au.get(j).getFechaActualizacion();
                String actualizacion = au.get(j).getActualizacion();
                boolean actualizar = true;
//                System.out.println(au.get(i).getNombreReferencia()+" "+ fechaAct+" "+actualizacion);
                if (!actualizacion.equals("continuamente")) {
                    fechaAct = incrementarFecha(fechaAct, actualizacion);
                    if(fechaAct.compareTo(fech) >  0) actualizar = false;
                    
                }
                if( hora > 3) actualizar = false;
                if (actualizar) {
                    String nomFichero = sa.getFichero(au.get(j).getUrl(),nombre, pais);
                    //Si el String no esta vacio extraemos los datos del fichero
                    if (!nomFichero.isEmpty()) {

                        e.extraerDatos(nomFichero,au.get(j).getUrl(), au.get(j).getNombreReferencia(), nombre, pais);

                    }
                    cu.anadirURL(nombre, pais, au.get(j).getNombreReferencia(), au.get(j).getUrl(),fech, au.get(j).getActualizacion());
                }
            }
           
        }
    }

    /**
     * Incrementar una fecha con un valor correspondiente a su periodo actualización
     * @param fechaAct: fecha a incrementar
     * @param actualizacion: periodo de actualización
     * @return string que contiene la fecha incrementada con el valor de la actualización
     */
    private String incrementarFecha(String fechaAct, String actualizacion) {
        int ano = Integer.valueOf(fechaAct.substring(0, 4));
        int mes = Integer.valueOf(fechaAct.substring(6, 7));
        int dia = Integer.valueOf(fechaAct.substring(9, 10));
        int inc = 0;
        //si el periodo es mensual se incrementa el mes en 1
        if (actualizacion.equals("mensual")) {
            inc = 1;
        }
        //si el periodo es trimestral se incrementa el mes en 3
        else if (actualizacion.equals("trimestral")) {
            inc = 3;
        }
        //si el periodo es semestral se incrementa el mes en 6
        else if (actualizacion.equals("semestral")) {
            inc = 6;
        }
        //si el periodo es anual se incrementa el mes en 12
        else if (actualizacion.equals("anual")) {
            inc = 12;
        }
        mes = mes + inc;
        if(mes > 12) {
                mes -= 12;
                ano++;
            }
        String result = ano+"/";
        if (mes > 10) result+= mes+"/";
        else result+= "0"+mes+"/";
        if (dia > 10) result+= dia;
        else result+= "0"+dia;
        return result;
    }
}
