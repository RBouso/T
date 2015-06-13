package BaseDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDeDatos extends SQLiteOpenHelper{
	String sqlCreate = "CREATE TABLE Estacion (ciudad TEXT, pais TEXT, latitud REAL, longitud REAL, esFavorita BOOLEAN, puntuacion INTEGER, direccion TEXT)";
	
	public BaseDeDatos(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(sqlCreate);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS Estacion");
		 
        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCreate);
	}

}
