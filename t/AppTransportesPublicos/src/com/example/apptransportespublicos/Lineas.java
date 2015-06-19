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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.conexion.constantes;


import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Lineas  extends Activity implements OnQueryTextListener{
	ListView lista; //listview donde se añaden todas las líneas encontradas
	private ArrayAdapter<String> adapt; //adaptador de listas de string
	private ArrayList<String> list;//lista de lineas
	private String ciudad;// nombre de la ciudad actual en el sistema
	private String transporte; //nombre del transporte para el cual se quieren saber las líneas
	private String pais; //nombre del país
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listas);
		//se cambia el color de la action bar
		android.app.ActionBar bar = getActionBar();
		
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0033CD")));
		lista = (ListView)findViewById(R.id.listView1);
		list = new ArrayList<String>();
		//se cogen los parámetros que ha pasado la actividad anterior
		Bundle b = getIntent().getExtras();
		ciudad = b.getString("ciudad");
		transporte = b.getString("transporte");
		pais = b.getString("pais");
		
		
	
		
		  // Si se clica algún elemento de la lista
        lista.setOnItemClickListener(new OnItemClickListener() {

              @Override
              public void onItemClick(AdapterView<?> parent, View view,
                 int position, long id) {
            	  
            	  
	               // ListView Clicked item index
	               int itemPosition     = position;
	               
	               // ListView Clicked item value
	               String  itemValue    = (String) lista.getItemAtPosition(position);
	                  
 
	               //Se abre la mainActivity para mostrar todas las estaciones que contiene la línea
	                Intent i = new Intent(Lineas.this, MainActivity.class);
	                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
	                i.putExtra("ciudad", ciudad);
	                i.putExtra("pais", pais);
	                i.putExtra("transporte", transporte);
	                i.putExtra("linea", itemValue);
	                i.putExtra("Anterior", "Lineas");
	                startActivity(i);
	                finish();
            	
              }

         }); 
        //buscar las líneas
        buscarLinea();
	}
	
	/**
	 * Busca las líneas en el servidor del transporte que pertenece la ciudad
	 */
	void buscarLinea() {

		ProgressDialog progress = new ProgressDialog(this);
		progress.setMessage("Buscando, por favor espere...");
		new LoadParadaTask(progress).execute();
	
}

	//se conecta con el servidor para obtener las líneas
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
			String url = constantes.lineas+"ciudad="+ciudad+"&pais="+pais+
					"&transporte="+transporte;
			
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
			
//			return HttpRequest.get(params[0]).accept("application/json").body();
		
	}



	protected void onPostExecute(String response) {
		try {
			//linea.setText(response);
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
		     adapt = new ArrayAdapter<String>(Lineas.this,
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


	

@Override
public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.listas, menu);
	//se introduce un botón de busqueda para encontrar antes una línea
	SearchManager searchManager = (SearchManager)
            getSystemService(Context.SEARCH_SERVICE);
	MenuItem searchMenuItem = menu.findItem(R.id.menu_busqueda_list);
	SearchView searchView = (SearchView) searchMenuItem.getActionView();
	
	searchView.setSearchableInfo(searchManager.
	                getSearchableInfo(getComponentName()));
	searchView.setOnQueryTextListener(this);

	return true;
}

public boolean onOptionsItemSelected(MenuItem item) {

	int id = item.getItemId();
	switch (id) {
		case( R.id.action_settings):
			return true;
		case (R.id.menu_busqueda_list):
			
			return true;

		default:
			return super.onOptionsItemSelected(item);
	}
}

//funciones que encuentran en la lista de líneas las que contienen la palabra que se está escribiendo en el campo de búsqueda
	@Override
	public boolean onQueryTextChange(String arg0) {
		// TODO Auto-generated method stub
		 Lineas.this.adapt.getFilter().filter(arg0);
		return true;
	}
	
	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		Lineas.this.adapt.getFilter().filter(arg0);
		return true;
	}
}
