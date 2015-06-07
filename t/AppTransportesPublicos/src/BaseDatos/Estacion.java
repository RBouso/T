package BaseDatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Estacion {
	private String ciudad;
	private String pais;
	private Double latitud;
	private Double longitud;
	private boolean esFavorita;
	private int puntuacion;
	
	public void anadirEstacionFavorita(Context c) {
		BaseDeDatos bd =
	            new BaseDeDatos(c, "DBEstacion", null, 1);
	 
	        SQLiteDatabase db = bd.getWritableDatabase();
	        //Si hemos abierto correctamente la base de datos
	        if(db != null)
	        {
	        	Cursor cu = db.rawQuery("SELECT * FROM Estacion e WHERE e.ciudad = '"+
	        			ciudad+"' AND e.pais = '" +pais+"' AND e.latitud = "+
	        			latitud+" AND e.longitud = "+longitud+" AND e.esFavorita = 0", null);
	            if (cu.moveToFirst()) {
	            	db.execSQL("UPDATE Estacion SET esFavorita = 1 WHERE e.ciudad = '"+
	        			ciudad+"' AND e.pais = '" +pais+"' AND e.latitud = "+
	        			latitud+" AND e.longitud = "+longitud);
	            }
	        	//Insertamos 5 usuarios de ejemplo
	            else {
	            	db.execSQL("INSERT INTO Estacion (ciudad, pais, latitud, longitud, esFavorita, puntuacion) " +
	                           "VALUES ('" + ciudad + "', '" + pais +"', "+latitud+", "+longitud+", 1, -1)");
	            }
	           cu.close();
	            //Cerramos la base de datos
	            db.close();
	        }
	}
	
	public void anadirPuntuacion(Context c, int puntuacion) {
		BaseDeDatos bd =
	            new BaseDeDatos(c, "DBEstacion", null, 1);
		
	        SQLiteDatabase db = bd.getWritableDatabase();
	        
	        
	        //Si hemos abierto correctamente la base de datos
	        if(db != null)
	        {
	        	Cursor cu = db.rawQuery("SELECT * FROM Estacion e WHERE e.ciudad = '"+
	        			ciudad+"' AND e.pais = '" +pais+"' AND e.latitud = "+
	        			latitud+" AND e.longitud = "+longitud+" AND e.puntuacion = -1", null);
	            if (cu.moveToFirst()) {
	            	Log.d("BD", "Encuentro esta");
	            	db.execSQL("UPDATE Estacion SET puntacion = "+puntuacion+" WHERE e.ciudad = '"+
	        			ciudad+"' AND e.pais = '" +pais+"' AND e.latitud = "+
	        			latitud+" AND e.longitud = "+longitud);
	            }
	        	//Insertamos 5 usuarios de ejemplo
	            else {
	            	Log.d("BD", "No encuentro esta");
	            	db.execSQL("INSERT INTO Estacion (ciudad, pais, latitud, longitud, esFavorita, puntuacion) " +
	                           "VALUES ('" + ciudad + "', '" + pais +"', "+latitud+", "+longitud+", 0, "+puntuacion+")");
	            }
	           cu.close();
	            //Cerramos la base de datos
	            db.close();
	        }
	}
	
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public Double getLatitud() {
		return latitud;
	}
	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}
	public Double getLongitud() {
		return longitud;
	}
	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}
	public boolean isEsFavorita() {
		return esFavorita;
	}
	public void setEsFavorita(boolean esFavorita) {
		this.esFavorita = esFavorita;
	}
	public int getPuntuacion() {
		return puntuacion;
	}
	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}
	
	
}
