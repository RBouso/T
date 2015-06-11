/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transportes.extraccion;

import Valoraciones.Controlador.Dominio.ControladorValoraciones;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.measure.unit.SI.METER;
import maps.java.Geocoding;
import org.jscience.geography.coordinates.LatLong;
import org.jscience.geography.coordinates.UTM;
import org.jscience.geography.coordinates.crs.CoordinatesConverter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

/**
 *
 * @author raquel
 */
public abstract class Formato {
    protected String fichero = "/home/raquel/NetBeansProjects/ServidorTransportes/ficheros/";
    //url del fichero donde se han de guardar los datos
    protected String folder = "/home/raquel/NetBeansProjects/ServidorTransportes/descargas/";
    protected String furl = ""; //url del fichero donde se encuentran los datos extraidos
    protected String ciudad = ""; //nombre de la ciudad a la que hacen referencia los datos
    protected String pais = ""; //nombre del pais al que hacen referencia los datos
    protected String referencia =""; //referencia de la fichero a extraer
    protected String nom = ""; //nombre de una Cosa
    protected String descripcion = ""; //descripción de una Cosa
    protected String coordx = ""; //coordenada x de una estación
    protected String lat = ""; //latitud de una estación
    protected String coordy = ""; //coordenada y de una estación
    protected String longitud = ""; //longitud de una estación
    protected String calle = ""; //nombre de la calle donde se encuentra una estación
    protected String numCalle = ""; //numero de la calle donde se encuentra la estación 
    protected String telefono = ""; //telefono del lugar 
    protected String identificador = ""; //identificador de una estacion de Aparcamiento o Alquiler Bicicletas
    protected String accesibilidad = ""; //indica si el aparcaminento tiene plazas pa minusvalidos
    protected String ptotales = ""; //numero de plazas totales del aparcamiento
    protected String plibres  = ""; //numero de plazas libres de un aparcamiento
    protected String anclajes = ""; //número total de anclajes que tiene una estación de bicicletas
    protected String alibres = "";
    protected String aocupados = "";
    protected String bdisp = ""; //número de bicicletas disponibles en una estación
    protected String urlSchema = ""; //url que 
    protected Boolean transporte = false; 
    protected ArrayList<String> linea = new ArrayList<>();
    protected String dlinea = "";
    protected String codigo = "";
    protected String correspondencia = "";
    protected String espera = "";
    protected String temporada = "";
    protected String tipoDia = "";
    protected ArrayList<String> horaSalida = new ArrayList<>();
    protected String sentido  = "";
    protected int zonaNumero = 0;
    protected char zonaLetra;
    
    /**
     * Lista de nombres que pueden aparecer en un archivo con respecto al lugar 
     * en el que se encuentran. La primera lista hace referencia al nombre, la 
     * segunda a la descrición, después la de la latitud, longitud, calle, 
     * número de calle y finalment telefono.
     */
    protected String lugar[][] = {{"nom", "nombre", "nom_capa_cast", "name"},
        {"descripcion-entidad", "descripcion", "equipament", "descripcion linea"}, 
        {"coordenada-x", "lat", "latitud", "latitude", "coord_x", "coord-x","gis x ", 
            "location"}, 
        {"coordenada-y", "long", "longitud", "longitude", "coord_y", "coord-y",
            "gis y"}, 
        {"adreca", "direccion", "nombre-via", "street", "calle","parada"}, 
        {"streetnumber","num","nº", "n�"}, 
        {"telefono", "telefon"}};
    
    protected String lin[][] = {{"codigo linea", "Codigo linea"},  
        {"correspondencias"}};
    /**
     * Lista de nombres que pueden aparecer en un archivo si es un Aparcamiento. 
     * La primera lista hace referencia al identificador, la segunda a la 
     * accesibilidad, después a las plazas totales y plazas libres.
     */
    protected String Aparcamiento[][] = {{"pk", "codigo", "identificador","codi_capa"},
        {"accesibilidad"},
        {"plazas-totales"}, 
        {"plazas-libres"}};
    
        /**
     * Lista de nombres que pueden aparecer en un archivo si es una estación de
     * Alquiler de Bicicletas. 
     * La primera lista hace referencia al identificador, la segunda al número 
     * de anclajes, y por último al numero de bicicletas disponibles.
     */
    protected String bicicletas[][] = {{"id", "identificador", "estaci�n"},  
        {"anclajes", "slots","alibres","ausados"}, 
        {"bikes", "blibres"}};
    
    protected String paradas[][] = {{"id", "identificador", "Codigo parada"},  
        {"Tiempo espera"}};
    
    protected String horarios[][] = {{"temporada"},  
        {"tipo dia"}, {"sentido"}, {"horas de salida"}};
   
    ArrayList<String> transports = new ArrayList();
   


  
    /**
     * Función abstracta que extrae los datos de una estacion dependiendo del 
     * formato en el que se ofrezcan los datos.
     * @param nomFichero
     * @param referencia
     * @param url
     * @param ciudad
     * @param pais 
     */
    public abstract void extraerDatos(String nomFichero, String referencia, String url, 
            String ciudad, String pais);
    
    
    protected void integrarDatos(int i, int ultimo) {
        BufferedWriter bw = null;
        try {
            if (!nom.isEmpty()||!descripcion.isEmpty()||!coordx.isEmpty()||!lat.isEmpty()
                ||!coordy.isEmpty()||!longitud.isEmpty()||!calle.isEmpty()||!numCalle.isEmpty()
                    ||!telefono.isEmpty()||!accesibilidad.isEmpty()||!identificador.isEmpty()
                    ||!ptotales.isEmpty()||!plibres.isEmpty()||!anclajes.isEmpty()
                    ||!alibres.isEmpty()||!aocupados.isEmpty()||!bdisp.isEmpty()) {

                           
            //String sFichero = "ficheros/"+pais+"/"+ciudad+"/EstacionAlquilerBicicletas.html";
            crear_ficheros("ficheros/",pais,ciudad);
            String nomFichero = "", uri = "";
            //
            obtenerReferencia();
            
                if (!transports.contains(referencia)) {
                    transports.add(referencia);
                    if (i == ultimo) 
                        ultimo = 0;
                    i = 0;
                }
            if (referencia.contains("Horarios")) {
                if (!fichero.contains("Horarios"))
                    fichero += "/Horarios";
            }
            if (referencia.contains("Bicicleta")) {
                nomFichero = "/EstacionAlquilerBicicletas.html";
                uri = "/CivicStructure/BikesStation";
            }    
            else if (referencia.contains("Aparcamiento")) {
                nomFichero = "/EstacionAparcamientos.html";
                uri = "/CivicStructure/ParkingFacility";
                
            }
            else if (referencia.contains("Ferrocarriles")) {
                nomFichero = "/Ferrocarriles.html";
                uri = "/CivicStructure/RailwaysStation";
                obtenerLineas();
            }
            else if (referencia.contains("Metro")) {
                nomFichero = "/Metro.html";
                uri = "/CivicStructure/SubwayStation";
                obtenerLineas();
            }
            else if (referencia.contains("Autobus")) {
                nomFichero = "/Autobus.html";
                uri = "/CivicStructure/BusStop";
                obtenerLineas();
                
            }
            else if (referencia.contains("Taxi")) {
                nomFichero = "/Taxi.html";
                uri = "/CivicStructure/TaxiStand";
            }
            else if (referencia.contains("Tranvia")) {
                nomFichero = "/Tranvia.html";
                uri = "/CivicStructure/TramStation";
                obtenerLineas();
            }
            else if (referencia.contains("Funicular")) {
                nomFichero = "/Funicular.html";
                uri = "/CivicStructure/FunicularStation";
                obtenerLineas();
            }
            else if (referencia.contains("Teleferico")) {
                nomFichero = "/Teleferico.html";
                uri = "/CivicStructure/CableCarsStation";
                obtenerLineas();
            }
           
            File f = new File(fichero+nomFichero);
           // System.out.println(fichero+nomFichero);
           
            
            if (i == 0 ) {
                
                if (f.exists())
                    f.delete();
                f.createNewFile();}
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fichero+nomFichero,true), "UTF-8"));
            if (i == 0 ) {
                
                bw.write("<!DOCTYPE html> \n");
                bw.write("<html> \n");
                bw.write("<body> \n");
               
            }
            bw.append("<div itemscope itemtype=\"http://schema.org/Place"+uri+"\"> \n");
           
            if (!nom.isEmpty())
                bw.append("Nombre: <span itemprop=\"name\"> "+nom+".</span>\n");
//            else 
//                 bw.append("Nombre: <span itemprop=\"name\"> "+nom+".</span>\n");
            if (!descripcion.isEmpty())
                bw.append("Descripcion: <span itemprop=\"description\">"+descripcion+".</span>\n");
            bw.append("URL: <span itemprop=\"url\"> "+furl+"</span>\n");
            if (lat.isEmpty() && !coordx.isEmpty() && longitud.isEmpty() && 
                !coordy.isEmpty()) {
                //System.out.println("entro" )
                getZonaCoordenadas();
                
                coordx = coordx.replace(',', '.');
                coordy = coordy.replace(',', '.');
                UTM utm = UTM.valueOf(zonaNumero, zonaLetra, Double.parseDouble(coordx), Double.parseDouble(coordy), METER);

                CoordinatesConverter<UTM, LatLong> utmToLatLong = UTM.CRS
                        .getConverterTo(LatLong.CRS);

                LatLong latLong = utmToLatLong.convert(utm);
                lat = String.valueOf(latLong.getCoordinates()[0]);
                longitud = String.valueOf(latLong.getCoordinates()[1]);
            }
            if (lat.isEmpty() && longitud.isEmpty()) {
                if(!referencia.contains("Horarios"))
                    getLatLong();
            }
            if (!lat.isEmpty() && !longitud.isEmpty()) {
                lat = lat.replace(',', '.');
                longitud = longitud.replace(',', '.');
               
                bw.append("geo: <div itemprop=\"geo\" itemscope itemtype=\"http://schema.org/GeoCoordinates\" > \n");
                bw.append("latitud: <meta itemprop=\"latitude\" content=\""+lat+"\" /> \n");
                bw.append("longitud: <meta itemprop=\"longitude\" content=\""+longitud+"\" />\n");
                bw.append("</div> \n");
            }
            if (!calle.isEmpty()) {
                bw.append("direccion: <div itemprop=\"address\" itemscope itemtype=\"http://schema.org/PostalAddress\"> \n");
                bw.append("pais: <span itemprop=\"addressCountry\">"+pais+"</span>\n");
                bw.append("localidad: <span itemprop=\"addressLocality\">"+ciudad+"</span>\n");
                bw.append("region: <span itemprop=\"addressRegion\">"+ciudad+"</span>\n");
                bw.append("direccion: <span itemprop=\"streetAddress\">"+calle);
                if (!numCalle.isEmpty())
                    bw.append(", "+numCalle);
                bw.append("</span> \n");
                bw.append("</div> \n");
            }
            if (!telefono.isEmpty())
                bw.append("telefono: <span itemprop=\"telephone\">"+telefono+"</span>\n");
            //civicStructure/Aparcamiento
            if (!identificador.isEmpty())
                bw.append("identificador: <span itemprop=\"identifier\">"+identificador+"</span>\n");
            if (!accesibilidad.isEmpty())
                bw.append("accesibilidad: <span itemprop=\"accessibility\">"+accesibilidad+"</span>\n");
            if(!ptotales.isEmpty())
                bw.append("plazastotales: <span itemprop=\"total\">"+ptotales+"</span>\n");
            if(!plibres.isEmpty())
                bw.append("plazaslibres: <span itemprop=\"free\">"+plibres+"</span>\n");
            if (!alibres.isEmpty() && !aocupados.isEmpty()) {
                int ancl = Integer.parseInt(alibres)+Integer.parseInt(aocupados);
                System.out.println();
                anclajes = String.valueOf(ancl);
            }
            if(!anclajes.isEmpty())
                bw.append("anclajes: <span itemprop=\"slots\">"+anclajes+"</span>\n");
            if(!bdisp.isEmpty())
                bw.append("biciLibres: <span itemprop=\"bikes\">"+bdisp+"</span>\n");
            
            int line = 0;
            while (line < linea.size()) {
                    bw.append("Linea: <span itemprop=\"line\">"+linea.get(line)+"</span>\n");
                    File l = new File(fichero+"/Linea");
                    if (!l.exists()) 
                        l.createNewFile();
                    BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fichero+"/Linea",true), "utf-8"));
                    if (!codigo.isEmpty()) {
                        if (!existeLinea(codigo+" "+dlinea))
                            bw1.append(codigo+ " " +dlinea+"\n");
                    
                    }
                    else if (!existeLinea(linea.get(line)))bw1.append(linea.get(line)+"\n");
                    bw1.close();
                    ++line;
                
            }
            if(!espera.isEmpty()) {
                bw.append("tiempo: <div itemprop=\"wait\"  > \n");
                bw.append("Linea: <span itemprop=\"line\">"+linea.get(line)+"</span>\n");
                bw.append("espera: <span itemprop=\"time\">"+espera+"</span>\n");
                bw.append("</div>");
            }
            if(!temporada.isEmpty()) 
                bw.append("temporada: <span itemprop=\"season\">"+temporada+"</span>\n");
            if(!tipoDia.isEmpty()) 
                bw.append("tipoDia: <span itemprop=\"type\">"+tipoDia+"</span>\n");
            if(!sentido.isEmpty()) 
                bw.append("sentido: <span itemprop=\"sense\">"+sentido+"</span>\n");
            int taman = 0;
            while(taman < horaSalida.size()) {
                if (taman  == 0) bw.append("horas: <div itemprop=\"times\"  > \n");
                bw.append("hora: <span itemprop=\"time\">"+horaSalida.get(taman)+"</span>\n");
                
                if (taman  == horaSalida.size()-1) bw.append("</div>");
                taman++;
            }  
            
            bw.append("</div> \n");
            bw.append("\n");
            if (i == ultimo) {
                bw.append("</body>\n");
                bw.append("</html> \n");
            }
                bw.close();
            }

        } catch (IOException ex) {
            Logger.getLogger(Formato.class.getName()).log(Level.SEVERE, null, ex);
        } 
                }
                
    

private void crear_ficheros(String folder, String pais, String ciudad) {
        
                File f = new File(folder+pais); 
                if (!f.exists())  {
                    f.mkdir();
                }
                f = new File(folder+pais+"/"+ciudad);
                 if (!f.exists())  
                    f.mkdir();
                 if (referencia.contains("Horarios")) {
                     f = new File(folder+pais+"/"+ciudad+"/Horarios");
                    if (!f.exists())  
                        f.mkdir();
                 }
    }


    protected boolean contiene(String nn, String transporte, int pos) {
        int i = 0;
        //System.out.println(transporte);
        String transp[][] = null;
      
        if (transporte.equals("lugar"))
            transp = lugar;
//            for (i = 0; i < lugar[pos].length; i++) {
//                if (lugar[pos][i].equalsIgnoreCase(nn)) 
//                    return true;}
        else if (transporte.equals("linea"))
            transp = lin;
        else if (transporte.equals("aparcamientos")) 
                transp = Aparcamiento;
//            for (i = 0; i < Aparcamiento[pos].length; i++) 
//                if (Aparcamiento[pos][i].equalsIgnoreCase(nn)) {
//                    return true;}
        else if (transporte.equals("bicicletas")) 
            transp = bicicletas;
        else if (transporte.equals("parada")) 
            transp = paradas;
        else if (transporte.equals("horarios")) 
                transp = horarios;
        for (i = 0; i < transp[pos].length; i++) {
//              if( referencia.contains("HorariosAutobusesBilbao")&& transporte.equals("horarios"))
//                  System.out.println(nn+" "+ transp[pos][i]+" "+transp[pos][i].compareToIgnoreCase(nn));
            if (transp[pos][i].equalsIgnoreCase(nn)) 
                    return true;}
        
        return false;
    }


    protected void obtenerDatos(String nn, String nv) {

        //Lugar

//        if (referencia.contains("RDF"))System.out.println(nn );
        if (contiene(nn, "lugar", 0))
            nom = nv;
        else if (contiene(nn, "lugar", 1)){
            if (nn.contains("linea")) 
                dlinea = nv;
            descripcion = nv;
        }
            
        else if (contiene(nn, "lugar", 2)) {
            if(nn.equals("location")) {
                int espacio = nv.indexOf(" ");
                lat = nv.substring(0, espacio);
                longitud = nv.substring(espacio+1);
            }
            else if (nn.contains("x") || nn.contains("X")){
                //System.out.println("entro1");
                coordx = nv;}
            else
                lat = nv;
        }
        else if (contiene(nn, "lugar", 3)) {
           // System.out.println("entro1");
            if (nn.contains("y") || nn.contains("Y"))
                coordy = nv;
            else
                longitud = nv;
        }
        else if (contiene(nn, "lugar", 4)) {
            calle = nv;
            
        }
        else if (contiene(nn, "lugar", 5))
            numCalle = nv;
        else if (contiene(nn, "lugar", 6))
            telefono = nv;
        else if (contiene(nn, "linea", 0))
            codigo = nv;
        else if (contiene(nn, "linea", 1))
            correspondencia = nv;
        
        
        //aparcamientos
        else if (referencia.contains("Aparcamiento")) {
            if (contiene(nn,"aparcamientos",0))
                identificador = nv;
            else if (contiene(nn,"aparcamientos",1))
                accesibilidad = nv;
            else if (contiene(nn,"aparcamientos",2))
                ptotales = nv;
            else if (contiene(nn,"aparcamientos",3))
                plibres = nv;
        }
        //bicicletas
        else if (referencia.contains("Bicicleta")) {
            if (contiene(nn, "bicicletas", 0)){
                identificador = nv;}
            else if (contiene(nn, "bicicletas", 1)) {
                if (nn.contains("LIBRES")) {
                    alibres = nv;
                }
                else if (nn.contains("USADOS")) {
                    aocupados = nv;
                }
                else 
                    anclajes = nv;
            }
            else if (contiene(nn, "bicicletas", 2))
                bdisp = nv;
        }
        else if (contiene(nn, "parada", 0))
            identificador = nv;
        else if (contiene(nn, "parada", 1))
            espera = nv;
        else if (referencia.contains("Horarios")) {
            if (contiene(nn, "horarios", 0))
                temporada = nv;
            else if (contiene(nn, "horarios", 1))
                tipoDia = nv;
            else if (contiene(nn, "horarios", 2))
                sentido = nv;
            else if (contiene(nn, "horarios", 3))
                horaSalida.add(nv);
        }
    }

    void inicializar_variables() {
        nom = "";
        descripcion = "";
        coordx = "";
        lat = "";
        coordy = "";
        longitud = "";
        calle = "";
        numCalle = "";
        telefono = "";
        accesibilidad = "";
        identificador ="";
        ptotales = "";
        plibres  = "";
        anclajes = "";
        alibres = "";
        aocupados = "";
        bdisp = "";
        linea = new ArrayList<>();
        codigo = "";
        correspondencia = "";
        espera = "";
        temporada = "";
        tipoDia = "";
        horaSalida = new ArrayList<>();
        sentido = "";
    }

    private void obtenerLineas() {
        int u;
        String result;
        if (!correspondencia.isEmpty() || !codigo.isEmpty()) {
            
            result = codigo;
            if(!correspondencia.isEmpty()) result += ","+correspondencia;
            obtenerLinea(result);
        }
        else if (!descripcion.isEmpty()) {
            //System.out.println(descripcion);
            if (referencia.contains("AutobusesBarcelona")) {
                result = descripcion.substring(descripcion.indexOf("-")+1);
                obtenerLinea(result);
            }
            else if (!descripcion.contains(")"))
                linea.add(descripcion);
            else { 
                
                int ini = descripcion.indexOf("(");
                u = descripcion.indexOf(")");
                result = descripcion.substring(ini+1, u);
//                System.out.println(result);
                obtenerLinea(result);
            }
        }
//        System.out.println(calle);
        
        else  {
            u = nom.indexOf(" ");
            
            linea.add(nom.substring(0, u));
    
         }
    }

    private boolean existeLinea(String l) {
        FileReader fr = null;
        try {
            File f = new File(fichero+"/Linea");
            fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String texto = "";
            while((texto=br.readLine())!=null) {
                if (texto.contains(l)) return true;
            }
            br.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Formato.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Formato.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
                
            } catch (IOException ex) {
                Logger.getLogger(Formato.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    private void obtenerLinea(String result) {
        int ini = 0;
        int fin = result.length();

        while(ini < fin) {
            result = result.substring(ini);
            if (result.contains(","))
                fin = result.indexOf(",");
            else if (result.contains("-"))
                fin = result.indexOf("-");
            else 
                fin = result.length();
//            System.out.println(result+" "+ini+" "+fin);
            linea.add(result.substring(0, fin));
            ini = fin+1;
            fin = result.length();
            //i++;
        }
    }

    private void obtenerReferencia() {
       if (transporte) {
                
                if(nom.contains("Metro") || nom.contains("subway"))
                    referencia = "Metro";
                else if (nom.contains("Ferrocarriles") || nom.contains("railways") 
                        || nom.contains("Tren") ||nom.contains("train"))
                    referencia = "Ferrocarriles";
                else if (nom.contains("Funicular")) {
                    referencia = "Funicular";
                }
                else if (nom.contains("Telef"))
                    referencia = "Teleferico";
                else if (nom.contains("Tran") || nom.contains("Tram") )
                    referencia = "Tranvia";
       }
    }

    private void getLatLong() {
        try {
            Geocoding ObjGeocod=new Geocoding();
            Point2D.Double resultadoCD=ObjGeocod.getCoordinates(calle+" "+numCalle+", "+ciudad+", "+pais);
            if(resultadoCD != null) {
                //System.out.println( resultadoCD.x + "," + resultadoCD.y+" "+codigo+" "+correspondencia);
                lat = String.valueOf(resultadoCD.x);
                longitud = String.valueOf(resultadoCD.y);
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Formato.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Formato.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void integrarTiempos() {
        try {
            Document htmlFile = Jsoup.parse(new File(fichero+"/Autobus.html"), "ISO-8859-1");
//            List<Node> childNodes = htmlFile.body().childNodes();
            Elements select = htmlFile.select("span[itemprop=streetAddress]");
            boolean encontrado = false;
            obtenerLineas();
            System.out.println(select.size());
            for (int i = 0; i < select.size() && !encontrado; i++) {
                Element ca = select.get(i);
                if (ca.text().equals(calle)){
                    Element el = htmlFile.body().child(i);
                    Elements sel = htmlFile.select("span[itemprop=wait]");
                    int j = 0; 
                    while (j < sel.size() && !encontrado) {
                        for (int k = 0; k < linea.size() && !encontrado; k++)
                            if(sel.get(j).child(0).equals(linea.get(k))){
                                sel.get(j).child(1).text(espera);
                                encontrado = true;
                            }
                        j++;
                    }
                    if (!encontrado) {
                        
                        String texto = "tiempo: <div itemprop=\"wait\"  > \n"+
                        "Linea: <span itemprop=\"line\">"+linea.get(0)+"</span>\n"+
                        "espera: <span itemprop=\"time\">"+espera+"</span>\n";
                        el.append(texto);
                        System.out.println(ca.text()+" "+texto);
                        encontrado = true;
                    }
                    File f = new File(fichero+"/Autobus.html");
                    f.delete();
                    f.createNewFile();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fichero+"/Autobus.html",false), "utf-8"));
                    bw.write(htmlFile.toString());
                    bw.close();
                }   
            }

           
//                Node nodo = childNodes.get(i);
//                List<Node> childNodes1 = nodo.childNodes();
//                for(int j = 0; j < childNodes1.size();j++) {
//                    Node get = childNodes1.get(j);
//                    System.out.println();
//                }
//                
//            }
        } catch (IOException ex) {
            Logger.getLogger(Formato.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getZonaCoordenadas() {
        if( ciudad.equals("Barcelona")){
                    zonaNumero = 31;
                    zonaLetra = 'T';
                }
                else if( ciudad.equals("Madrid")&& ciudad.equals("Bilbaod")){
                    zonaNumero = 30;
                    zonaLetra = 'T';
                }
    }
}
