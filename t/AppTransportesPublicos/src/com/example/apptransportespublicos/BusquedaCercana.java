package com.example.apptransportespublicos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BusquedaCercana extends ActionBarActivity {

	private EditText calle; //nombre del campo de texto que obtiene la calle
	private EditText numero;//nombre del campo de texto que obtiene el número de la calle
	private String ciudad; //ciudad actual de la aplicación
	private String pais;//pais actual de la aplicación
	private Button buscar;//Boton para buscar la parada
	private String nom;//nombre de la calle obtenido del campo de texto
	private String num;//número de la calle obtenido del campo de texto
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_busqueda_cercana);
		//Cambiamos el color del actionBar
		android.app.ActionBar bar = getActionBar();
		
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0033CD")));
		//Obtenemos los parámetros enviados por la actividad anterior
		Bundle b = getIntent().getExtras();
		ciudad = b.getString("ciudad");
		pais = b.getString("pais");
		
		//declaramos los campos de texto y el botón
		calle = (EditText) findViewById(R.id.textDireccion);
		numero = (EditText) findViewById(R.id.textNumeroCalle);
		buscar = (Button) findViewById(R.id.buttonCercana);
		
		//Si clicamos al boton
		buscar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//conseguimos los valores de los campos de texto
				nom = calle.getText().toString();
				num = numero.getText().toString();
				//si alguno está vacio se muestra un mensaje
				if (nom.equals("") || num.equals("")) {
					Toast.makeText(getApplicationContext(), 
							"Por favor rellene todos los campos del formulario", 
							Toast.LENGTH_SHORT).show();
				}
				else {
					Geocoder g = new Geocoder(getApplicationContext());
					List<Address> ad = new ArrayList<Address>();
					try {
						ad = g.getFromLocationName(nom+", "+num+", "+ ciudad, 1);
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//Si la dirección no es válida se muestra un mensaje de error
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
					//Regresamos a la actividad principal para mostrar los datos
					else {
						Intent i =  new Intent(BusquedaCercana.this, MainActivity.class);
		                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		                i.putExtra("ciudad", ciudad);
		                i.putExtra("pais", pais);
		                i.putExtra("Anterior", "cercana");
		                i.putExtra("direccion", nom+", "+num);
		                i.putExtra("latitud", ad.get(0).getLatitude());
		                i.putExtra("longitud", ad.get(0).getLongitude());
		               
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
