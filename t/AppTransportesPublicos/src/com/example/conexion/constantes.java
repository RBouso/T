package com.example.conexion;




/**
 * 
 * @author raquel
 *Esta clase contiene las constantes que hacen referencia a las urls a las que 
 *acceder para conectarse con el servidor
 */
public class constantes {
	public static String url = "http://192.168.1.133:8084/ServidorTransportes/webresources/generic/";
	public static String ciudades = url+"ciudades";
	public static String transportes = url+"transportes/";
	public static String lineas = url+"lineas/";
	public static String aparcamiento = url+"aparcamiento/";
	public static String paradas = url+"paradas/";
	public static String bicicleta = url+"bicicletas/";
	public static String taxi = url+"taxi/";
	public static String cercanas = url+"paradasCercanas/";
	public static String valoraciones = url+"valoracion/";
	public static String media = valoraciones+"media/";
	public static String horario = transportes+"horarios/";
	
}
