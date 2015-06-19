package com.example.apptransportespublicos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
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

public class Trayectos extends Activity {

	private String ciudad;
	private String pais;
	private Button buscar;
	protected String nomOrigen;
	private EditText calleOrigen;
	private EditText calleDestino;
	protected String nomDestino;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trayectos);
		android.app.ActionBar bar = getActionBar();
		
		//for color
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0033CD")));
		Bundle b = getIntent().getExtras();
		ciudad = b.getString("ciudad");
		pais = b.getString("pais");
		
		calleOrigen = (EditText) findViewById(R.id.textOrigen);
		calleDestino = (EditText) findViewById(R.id.textDestino);
		buscar = (Button) findViewById(R.id.buttonTrayectos);
		
		buscar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				nomOrigen = calleOrigen.getText().toString();
				nomDestino = calleDestino.getText().toString();
				if (nomOrigen.equals("") || nomDestino.equals("")) {
					Toast.makeText(getApplicationContext(), 
							"Por favor rellene todos los campos del formulario", 
							Toast.LENGTH_SHORT).show();
				}
				else {
//					ProgressDialog progress = new ProgressDialog(BusquedaCercana.this);
//					progress.setMessage("Buscando, por favor espere...");
					Geocoder g = new Geocoder(getApplicationContext());
					List<Address> ao = new ArrayList<Address>();
					List<Address> ad = new ArrayList<Address>();
					try {
						ao = g.getFromLocationName(nomOrigen+", "+ ciudad, 1);
						ad = g.getFromLocationName(nomDestino+", "+ ciudad, 1);
						
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(ad.isEmpty() || ao.isEmpty()) {
							AlertDialog.Builder builder = new AlertDialog.Builder(Trayectos.this);
						
						builder.setMessage("Alguna de las direcciones introduccidas no es válida. Por favor, introduzca una dirección válida.")
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
						Intent i =  new Intent(Trayectos.this, MainActivity.class);
		                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		                i.putExtra("ciudad", ciudad);
		                i.putExtra("pais", pais);
		                i.putExtra("Anterior", "cercana");
		                i.putExtra("direccionOrigen", nomOrigen+", "+ciudad);
		                i.putExtra("direccionDestino", nomDestino+", "+ciudad);
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
		getMenuInflater().inflate(R.menu.trayectos, menu);
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
