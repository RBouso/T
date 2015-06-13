package com.example.apptransportespublicos;

import java.util.ArrayList;
import java.util.Arrays;

import BaseDatos.BaseDeDatos;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TabFavoritos extends Fragment{
	private View v;
	private String ciudad;
	private String pais;
	private ArrayAdapter<String> adapt;
	private ListView lista;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,

	        Bundle savedInstanceState) {
	       
		 v = inflater.inflate(R.layout.activity_listas, container, false);
		 ciudad = this.getArguments().get("ciudad").toString();
		 pais = this.getArguments().get("pais").toString();
		 lista = (ListView)v.findViewById(R.id.listView1);
		 BaseDeDatos bd = new BaseDeDatos(getActivity(), "DBEstacion", null, 1);
		 
		 SQLiteDatabase db = bd.getWritableDatabase();
		        //Si hemos abierto correctamente la base de datos
		 ArrayList<String> list = new ArrayList<String>();
		 if(db != null)
		  {
		        	Cursor cu = db.rawQuery("SELECT * FROM Estacion e WHERE e.ciudad = '"+ciudad +"' AND e.esFavorita = 1", null);
		            if (cu.moveToFirst()) {
		            	do {
		            		list.add(cu.getString(6));
		            		Log.d("bd", cu.getDouble(2)+" "+ cu.getDouble(3));
		            	}while(cu.moveToNext());
		            }
		            else {
		   			 list.add("No hay ninguna estación en favoritos");
		   			 lista.setClickable(false);
		   		 }
		           cu.close();
		            //Cerramos la base de datos
		            db.close();
		        }
		
		 	String[] valores = new String[list.size()];
			valores = list.toArray(valores);
    		Arrays.sort(valores);
		     adapt = new ArrayAdapter<String>(getActivity(),
		        	   R.layout.activity_nombre_listas, R.id.nom, valores) ;
		    lista.setAdapter(adapt);
		    return v;
	 }
}
