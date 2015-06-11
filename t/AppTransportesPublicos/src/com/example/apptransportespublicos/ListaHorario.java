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
import org.w3c.dom.Text;

import com.example.conexion.constantes;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ListaHorario extends Activity {

	private ListView lista;
	private ArrayList<String> list;
	private String ciudad;
	private String pais;
	private String transporte;
	private String linea;
	private String sentido;
	private String fecha;
	private ArrayAdapter<String> adapt;
	private Button principal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_horario);
		android.app.ActionBar bar = getActionBar();
		//for color
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0033CD")));
		lista = (ListView)findViewById(R.id.listViewHorarios);
		list = new ArrayList<String>();
		
		Bundle b = getIntent().getExtras();
		ciudad = b.getString("ciudad");
		pais = b.getString("pais");
		transporte = b.getString("transporte");
		linea = b.getString("linea");
		sentido = b.getString("sentido");
		fecha = b.getString("fecha");

		TextView valor = (TextView) findViewById(R.id.titleTextLinea);
		valor.setText(linea);
		valor = (TextView) findViewById(R.id.titleTextSentido);
		valor.setText(sentido);
		valor = (TextView) findViewById(R.id.titleHorario);
		valor.setText("Horarios para la fecha "+fecha);
		  // ListView Item Click Listener
        lista.setClickable(false);
        principal = (Button) findViewById(R.id.buttonPrincipal);
        principal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ListaHorario.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("ciudad", ciudad);
                i.putExtra("Anterior", "ListaHorario");
                i.putExtra("pais", pais);
                
                startActivity(i);
                finish();
			}
		});
        buscarHorarios();
	}
	


	void buscarHorarios() {

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
			String url = constantes.horario+"ciudad="+ciudad+"&pais="+pais+
					"&transporte="+transporte+"&linea="+linea+"&sentido="+espacio(sentido)+"&fecha="+fecha.replace("/", "-");
			HttpGet peticion = new HttpGet(url);
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
			//linea.setText(response);
			progress.dismiss();
			Log.d("String", response);
			JSONObject json = new JSONObject(response);
			JSONArray js = json.getJSONArray("nombres");
			String datos ="";
			list = new ArrayList<String>();
			for (int i = 0; i < js.length(); ++ i) {
				JSONObject j = js.getJSONObject(i);
				String dat = j.getString("nombre");
				
				if(i == 0) datos = dat;
				else if(dat.substring(0,dat.indexOf(":")).equals(datos.substring(0, datos.indexOf(":")))) {
					datos = datos+", "+dat;
				}
					
				else {
					list.add(datos); 
					datos = dat;
				}

			}
	
			String[] valores = new String[list.size()];
			valores = list.toArray(valores);
       		Arrays.sort(valores);
		     adapt = new ArrayAdapter<String>(ListaHorario.this,
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
//		List<Parada> paradas = getParadas(h);
//		if (!paradas.isEmpty()) {
//			mostrarParada(paradas.get(0));
//		}
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
		getMenuInflater().inflate(R.menu.lista_horario, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private String espacio(String np) {
		// TODO Auto-generated method stub
		String res = np.replaceAll(" ", "%20");

		return res;
	}
}
