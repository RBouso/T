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
import android.support.v7.app.ActionBarActivity;
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
	ListView lista;
	private ArrayAdapter<String> adapt;
	private ArrayList<String> list;
	private String ciudad;
	private String transporte;
	private String pais;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listas);
		android.app.ActionBar bar = getActionBar();
		//for color
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0033CD")));
		lista = (ListView)findViewById(R.id.listView1);
		list = new ArrayList<String>();
		
		Bundle b = getIntent().getExtras();
		ciudad = b.getString("ciudad");
		transporte = b.getString("transporte");
		pais = b.getString("pais");
		
		
	
		
		  // ListView Item Click Listener
        lista.setOnItemClickListener(new OnItemClickListener() {

              @Override
              public void onItemClick(AdapterView<?> parent, View view,
                 int position, long id) {
            	  
            	  
	               // ListView Clicked item index
	               int itemPosition     = position;
	               
	               // ListView Clicked item value
	               String  itemValue    = (String) lista.getItemAtPosition(position);
	                  
 
	               
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
        buscarLinea();
	}
	
	void buscarLinea() {

		ProgressDialog progress = new ProgressDialog(this);
		progress.setMessage("Buscando, por favor espere...");
		new LoadParadaTask(progress).execute();
	//}
}

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
			Log.d("url", url);
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
			Log.d("String", response);
			JSONObject json = new JSONObject(response);
			JSONArray js = json.getJSONArray("nombres");
			
			for (int i = 0; i < js.length(); ++ i) {
				JSONObject j = js.getJSONObject(i);
				list.add(j.getString("nombre")); 
				Log.d("lineas", list.get(i));
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
//	Toast.makeText(getApplicationContext(), "estoy en el menu", Toast.LENGTH_LONG).show();
	getMenuInflater().inflate(R.menu.listas, menu);
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
