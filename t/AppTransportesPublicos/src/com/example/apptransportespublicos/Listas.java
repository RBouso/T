package com.example.apptransportespublicos;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.conexion.constantes;

import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Listas extends ActionBarActivity {

	private ListView lista;//listview que contiene la lista de las ciudades
	private ArrayAdapter<String> adapt; //adaptador de listas de string
	private ArrayList<String> list; // lista de ciudades
	private AsyncTask<Void, Void, String> par;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listas);
		//cambiar el color del actionbar
		android.app.ActionBar bar = getActionBar();
		
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0033CD")));
		lista = (ListView)findViewById(R.id.listView1);
		list = new ArrayList<String>();
		//acceder  a los valores pasados por la actividad anterior
		Bundle b = getIntent().getExtras();
		String ciudad = b.getString("ciudad");
		
	
		
		  // Si se clica en una ciudad regresamos a la actividad principal
        lista.setOnItemClickListener(new OnItemClickListener() {

              @Override
              public void onItemClick(AdapterView<?> parent, View view,
                 int position, long id) {
            	 
	               // ListView Clicked item index
	               int itemPosition     = position;
	               
	               // ListView Clicked item value
	               String  itemValue    = (String) lista.getItemAtPosition(position);
	                  
	                Intent i = new Intent(Listas.this, MainActivity.class);
	                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                i.putExtra("ciudad", itemValue);
	                i.putExtra("Anterior", "ciudades");
	                startActivity(i);
	                finish();
            	
              }

         }); 
        buscarCiudad();
	}
	

	

	/**
	 * Buscar las ciudades guardadas en el sistema
	 */
	void buscarCiudad() {

		ProgressDialog progress = new ProgressDialog(this);
		progress.setMessage("Buscando, por favor espere...");
		par = new LoadParadaTask(progress).execute();
	
}

	//accede al servidor para buscar las ciudades
private class LoadParadaTask extends AsyncTask<Void, Void, String> {
	
	ProgressDialog progress;

	
	public LoadParadaTask(ProgressDialog progress) {
		// TODO Auto-generated constructor stub
		this.progress = progress;
	}


	public void onPreExecute() {
		progress.show();
	}

	@Override
	protected String doInBackground(Void... params) {
		// TODO Auto-generated method stub
			
			HttpClient cliente = new DefaultHttpClient();
			String url = constantes.ciudades;
			
			HttpGet peticion = new HttpGet(url);
			// ejecuta una petición get
			 InputStream is = null;
			 String result = "";
			try {

				HttpResponse respuesta = cliente.execute(peticion);

				is = respuesta.getEntity().getContent();
				if (is != null) {
					result = convertInputtoString(is);
					
					
					
				}
				else {
					result = "No ha funcionado";
				}
				return result;
			
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			
		
	}



	protected void onPostExecute(String response) {
		try {
		
			progress.dismiss();
			
			JSONObject json = new JSONObject(response);
			JSONArray js = json.getJSONArray("nombres");
			
			for (int i = 0; i < js.length(); ++ i) {
				JSONObject j = js.getJSONObject(i);
				list.add(j.getString("nombre")); 
				
			}
	
			String[] valores = new String[list.size()];
			valores = list.toArray(valores);
       		Arrays.sort(valores);
		     adapt = new ArrayAdapter<String>(Listas.this,
		        	   R.layout.activity_nombre_listas, R.id.nom, valores) ;
		    lista.setAdapter(adapt);
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e("Error nuestro", e.toString());	
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("Error nuestro", e.toString());
		}

	}
}

private static String convertInputtoString(InputStream is) {
	// TODO Auto-generated method stub
	BufferedReader b = null;
	try {
		b = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
	} catch (UnsupportedEncodingException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	String line = "";
	String result = "";
	try {
		while ((line = b.readLine()) != null) {
			result += line;
		}
	
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally {
		try {
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	return result;
}
	
	
}
