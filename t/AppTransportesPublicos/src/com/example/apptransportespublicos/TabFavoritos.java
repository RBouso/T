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
	private View v; //vista del fragmento
	private String ciudad; //nombre de la ciudad
	private String pais; //nombre del país
	private ArrayAdapter<String> adapt; //Adaptador de listas de string
	private ListView lista; //listview que contiene la lista de estaciones favoritas

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,

	        Bundle savedInstanceState) {
	       
		 v = inflater.inflate(R.layout.activity_listas, container, false);
		 //se consiguen los elementos que pasa la actividad anterior
		 ciudad = this.getArguments().get("ciudad").toString();
		 pais = this.getArguments().get("pais").toString();
		 lista = (ListView)v.findViewById(R.id.listView1);
		 //Se conecta a la base de datos para obtener todas las estaciones que son favoritas en la ciudad definida
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
		
		 //ordenamos y añadimos los valores a la lista
		 	String[] valores = new String[list.size()];
			valores = list.toArray(valores);
    		Arrays.sort(valores);
		     adapt = new ArrayAdapter<String>(getActivity(),
		        	   R.layout.activity_nombre_listas, R.id.nom, valores) ;
		    lista.setAdapter(adapt);
		    return v;
	 }
}
