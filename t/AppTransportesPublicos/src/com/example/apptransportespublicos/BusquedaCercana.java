package com.example.apptransportespublicos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BusquedaCercana extends ActionBarActivity {

	private EditText calle;
	private EditText numero;
	private String ciudad;
	private String pais;
	private Button buscar;
	private String nom;
	private String num;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_busqueda_cercana);
		android.app.ActionBar bar = getActionBar();
		//for color
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0033CD")));
		Bundle b = getIntent().getExtras();
		ciudad = b.getString("ciudad");
		pais = b.getString("pais");
		
		calle = (EditText) findViewById(R.id.textDireccion);
		numero = (EditText) findViewById(R.id.textNumeroCalle);
		buscar = (Button) findViewById(R.id.buttonCercana);
		
		buscar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				nom = calle.getText().toString();
				num = numero.getText().toString();
				if (nom.equals("") || num.equals("")) {
					Toast.makeText(getApplicationContext(), 
							"Por favor rellene todos los campos del formulario", 
							Toast.LENGTH_SHORT).show();
				}
				else {
//					ProgressDialog progress = new ProgressDialog(BusquedaCercana.this);
//					progress.setMessage("Buscando, por favor espere...");
					Geocoder g = new Geocoder(getApplicationContext());
					List<Address> ad = new ArrayList<Address>();
					try {
						ad = g.getFromLocationName(nom+", "+num+", "+ ciudad, 1);
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(ad.isEmpty()) {
							AlertDialog.Builder builder = new AlertDialog.Builder(BusquedaCercana.this);
						
						builder.setMessage("Por favor, introduzca una dirección válida.")
						        .setTitle("Dirección no valida")
						        .setCancelable(false)
						        .setNeutralButton("Aceptar",
						                new DialogInterface.OnClickListener() {
						                    public void onClick(DialogInterface dialog, int id) {
						                        dialog.cancel();
						                    }
						                });
						AlertDialog alert = builder.create();
						alert.show();
					}
					else {
						Intent i =  new Intent(BusquedaCercana.this, MainActivity.class);
		                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		                i.putExtra("ciudad", ciudad);
		                i.putExtra("pais", pais);
		                i.putExtra("Anterior", "cercana");
		                i.putExtra("direccion", nom+", "+num);
		               
		                startActivity(i);
		                finish();
					}
					
					
				}
			}
		});
	}
	
	
	
		

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.busqueda_cercana, menu);
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
}
