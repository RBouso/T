package com.example.apptransportespublicos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.example.conexion.constantes;

import BaseDatos.BaseDeDatos;
import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

public class Info extends Activity {
	private String transporte;//nombre del transporte
	private String direccion;//dirección de la estación
	private Double lat;//latitud de la estación
	private Double lon;//longitud de la estación
	private String acces;//indica si es accesible un aparcamineto
	private String total;//plazas totales de un aparcamiento
	private String libres;//plazas libres de un aparcamiento
	private String anclajes;//anclajes que hay en una estacion de alquiler de bicicletas
	private String bicis;//bicis libres que hay en una estacion de alquiler de bicicletas
	private String telefono;//número de teléfono de una estación
	private String lineas;//Nombre de línea que pasan por una estación
	private String ciudad;//nombre de la ciudad
	private String pais;//nombre del pais
	private float favorito; //indica si es o no favorito
	private TextView val;// Texto donde se pondrá la valoración obtenida por el servidor
	private Button valorar;//Botón de valorar
	private int puntuacionA;//Puntuación antigua
	private int puntuacionN;//Puntuación mueva
	private RatingBar rb;//rating Bar de favoritos
	private RatingBar valoracion; //rating Bar de valoraciones
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		android.app.ActionBar bar = getActionBar();
		//for color
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0033CD")));
		//obtenemos los parametros que nos pasa la actividad anterior
		Bundle b = getIntent().getExtras();
		transporte = b.getString("transporte");
		ciudad = b.getString("ciudad");
		pais = b.getString("pais");
		//añadimos la información en los textview
		TextView par = (TextView) findViewById(R.id.parada);
		par.setText("Parada de " + transporte);
		rb = (RatingBar) findViewById(R.id.ratingBarInfo);
		valoracion = (RatingBar) findViewById(R.id.ratingBarValorar);
		rb.setClickable(true);
		rb.setSelected(true);
		rb.setActivated(true);
		valoracion.setClickable(true);
		valoracion.setSelected(true);
		valoracion.setActivated(true);
		
		
		lat = b.getDouble("latitud");
		lon = b.getDouble("longitud");
		//Consultamos en la base de datos de la aplicación si esta o no valorada y es o no favorita
		BaseDeDatos bd = new BaseDeDatos(getApplicationContext(), "DBEstacion", null, 1);
		SQLiteDatabase db = bd.getWritableDatabase();
		
        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {
			Cursor cu = db.rawQuery("SELECT * FROM Estacion e WHERE e.ciudad = '"+
	    			ciudad+"' AND e.pais = '" +pais+"' AND e.latitud = "+
	    			lat+" AND e.longitud = "+lon, null);
	        if (cu.moveToFirst()) {
	        
	        	rb.setRating(cu.getInt(4));
	        	if(cu.getInt(5) != -1) {
	        		valoracion.setRating(cu.getInt(5));
	        		puntuacionA = cu.getInt(5);
	        	}
	        	else {
	        		valoracion.setRating(0);
	        		puntuacionA = -1;
	        	}
	        }
	    	
	        else {
	        	rb.setRating(0);
	        	valoracion.setRating(0);
	        	puntuacionA = -1;
	        }
	       cu.close();
	        //Cerramos la base de datos
	        db.close();
        }
		TextView dir = (TextView) findViewById(R.id.direccion);
		direccion = b.getString("direccion");
		dir.setText(direccion);
		direccion.replace("'", " " );
		telefono = b.getString("telefono");
		if(!telefono.equals("-1") && telefono != null && !telefono.contains("null")) {
			dir = (TextView) findViewById(R.id.telefonoText);
			dir.setText("Nº de telefeno: ");
			dir = (TextView) findViewById(R.id.telefono);
			dir.setText(telefono);
		
		}
		else {
			dir = (TextView) findViewById(R.id.telefonoText);
			dir.setVisibility(View.GONE);
			dir = (TextView) findViewById(R.id.telefono);
			dir.setVisibility(View.GONE);
		}
		
		if (transporte.contains("Aparcamiento")) {
			TextView ac = (TextView) findViewById(R.id.accesibilidadText);
			ac.setText("Accesibilidad: ");
			acces = b.getString("acces");
			TextView ac1 = (TextView) findViewById(R.id.accesibilidad);
			if (acces.equals("0")) 
				ac1.setText("No");
			else
				ac1.setText("Si");
			TextView tot = (TextView) findViewById(R.id.totalesText);
			tot.setText("Plazas totales: ");
			total = b.getString("total");
			tot = (TextView) findViewById(R.id.totales);
			tot.setText(""+total);
			tot = (TextView) findViewById(R.id.LibresText);
			tot.setText("Plazas libres: ");
			libres = b.getString("libres");
			tot = (TextView) findViewById(R.id.libres);
			tot.setText(""+libres);
		}
		else if (transporte.contains("Bicicleta")) {
			TextView tot = (TextView) findViewById(R.id.accesibilidadText);
			tot.setText("Nº de anclajes: ");
			anclajes = b.getString("anclajes");
			tot = (TextView) findViewById(R.id.accesibilidad);
			tot.setText(""+anclajes);
			tot = (TextView) findViewById(R.id.totalesText);
			tot.setText("Bicicletas disponibles: ");
			bicis = b.getString("bicis");
			tot = (TextView) findViewById(R.id.totales);
			tot.setText(""+bicis);
			TextView desaparecer = (TextView) findViewById(R.id.LibresText);
			TextView desaparece = (TextView) findViewById(R.id.libres);
			desaparecer.setVisibility(View.GONE);
			desaparece.setVisibility(View.GONE);
		}
		else if(transporte.contains("Taxi")) {
			TextView tot = (TextView) findViewById(R.id.accesibilidadText);
			tot.setVisibility(View.GONE);
			tot = (TextView) findViewById(R.id.accesibilidad);
			tot.setVisibility(View.GONE);
			tot = (TextView) findViewById(R.id.totalesText);
			tot.setVisibility(View.GONE);
			tot = (TextView) findViewById(R.id.totales);
			tot.setVisibility(View.GONE);
			tot = (TextView) findViewById(R.id.LibresText);
			tot.setVisibility(View.GONE);
			tot = (TextView) findViewById(R.id.libres);
			tot.setVisibility(View.GONE);
		}
		else {
			TextView tot = (TextView) findViewById(R.id.accesibilidadText);
			tot.setText("Nº de linea: ");
			tot = (TextView) findViewById(R.id.accesibilidad);
			lineas = b.getString("lineas");
			tot.setText(lineas);
			tot = (TextView) findViewById(R.id.totalesText);
			tot.setVisibility(View.GONE);
			tot = (TextView) findViewById(R.id.totales);
			tot.setVisibility(View.GONE);
			tot = (TextView) findViewById(R.id.LibresText);
			tot.setVisibility(View.GONE);
			tot = (TextView) findViewById(R.id.libres);
			tot.setVisibility(View.GONE);
		}
		favorito = rb.getRating();
		//Cambiar de el estado de favorito
        rb.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				// TODO Auto-generated method stub
				BaseDeDatos bd = new BaseDeDatos(getApplicationContext(), "DBEstacion", null, 1);
				SQLiteDatabase db = bd.getWritableDatabase();
				//Si no era favorito lo ponemos a favorito y lo guardamos en la base de datos
				if(favorito == 0) {
					favorito = 1;
					
			        //Si hemos abierto correctamente la base de datos
			        if(db != null)
			        {
						Cursor cu = db.rawQuery("SELECT * FROM Estacion e WHERE e.ciudad = '"+
				    			ciudad+"' AND e.pais = '" +pais+"' AND e.latitud = "+
				    			lat+" AND e.longitud = "+lon, null);
						 if (cu.moveToFirst()) {
				            	db.execSQL("UPDATE Estacion SET esFavorita = 1 WHERE ciudad = '"+
				        			ciudad+"' AND pais = '" +pais+"' AND latitud = "+
				        			lat+" AND longitud = "+lon);
				            }
				        	
				            else {
				            	db.execSQL("INSERT INTO Estacion (ciudad, pais, latitud, longitud, esFavorita, puntuacion, direccion) " +
				                           "VALUES ('" + ciudad + "', '" + pais +"', "+lat+", "+lon+", 1, -1, '"+direccion+"')");
				            }
			        	
				       cu.close();
				        
			        }
				}
				//Si era favorito lo ponemos a no favorito y lo guardamos en la base de datos
				else {
					favorito = 0;
					//Si hemos abierto correctamente la base de datos
			        if(db != null)
			        {
						Cursor cu = db.rawQuery("SELECT * FROM Estacion e WHERE e.ciudad = '"+
				    			ciudad+"' AND e.pais = '" +pais+"' AND e.latitud = "+
				    			lat+" AND e.longitud = "+lon, null);
						if (cu.moveToFirst()) {
			            	if (cu.getInt(5) != -1)  {
							db.execSQL("UPDATE Estacion SET esFavorita = 0 WHERE ciudad = '"+
			        			ciudad+"' AND pais = '" +pais+"' AND latitud = "+
			        			lat+" AND longitud = "+lon);
			            	}
			            	else {
			            		db.execSQL("DELETE FROM Estacion WHERE ciudad = '"+
			        			ciudad+"' AND pais = '" +pais+"' AND latitud = "+
			        			lat+" AND longitud = "+lon);
			            	}
			            }
						cu.close();
			        }
				}
				//Cerramos la base de datos
		        db.close();
				rb.setRating(favorito);
			}
		});
       
        val = (TextView) findViewById(R.id.valoracionText);
        val.setText("Valoracion: ");
        val = (TextView) findViewById(R.id.valoracion);
		//poner valoracion
        
        valoracion.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				// TODO Auto-generated method stub
				BaseDeDatos bd = new BaseDeDatos(getApplicationContext(), "DBEstacion", null, 1);
				SQLiteDatabase db = bd.getWritableDatabase();
				//obtenemos la puntuación nueva
				puntuacionN = (int) rating;
				
				//obtenemos la puntuación  antigua 
			        //Si hemos abierto correctamente la base de datos
			        if(db != null)
			        {
						Cursor cu = db.rawQuery("SELECT * FROM Estacion e WHERE e.ciudad = '"+
				    			ciudad+"' AND e.pais = '" +pais+"' AND e.latitud = "+
				    			lat+" AND e.longitud = "+lon, null);
						 if (cu.moveToFirst()) {
							 puntuacionA = cu.getInt(5);
				            	
				            }
						 cu.close();
			        }       	
		        db.close();
				rb.setRating(puntuacionN);
				
			}
		});
       
        valorar = (Button) findViewById(R.id.buttonValorar);
        //valorar una estación
        valorar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				BaseDeDatos bd = new BaseDeDatos(getApplicationContext(), "DBEstacion", null, 1);
				SQLiteDatabase db = bd.getWritableDatabase();

				//guardamos la valoración en la base de datos
					if(db != null)
			        {
						boolean existe = false;
						Cursor cu = db.rawQuery("SELECT * FROM Estacion e WHERE e.ciudad = '"+
				    			ciudad+"' AND e.pais = '" +pais+"' AND e.latitud = "+
				    			lat+" AND e.longitud = "+lon, null);
						 if (cu.moveToFirst()) {
							 existe = true;
				            	db.execSQL("UPDATE Estacion SET puntuacion = "+puntuacionN+" WHERE ciudad = '"+
				        			ciudad+"' AND pais = '" +pais+"' AND latitud = "+
				        			lat+" AND longitud = "+lon);
				            }
						 else {
							 
							 db.execSQL("INSERT INTO Estacion (ciudad, pais, latitud, longitud, esFavorita, puntuacion, direccion) " +
			                           "VALUES ('" + ciudad + "', '" + pais +"', "+lat+", "+lon+", 0, "+puntuacionN+", '"+direccion+"')");
						 }
						 cu.close();
						 //guardamos la puntuación en el servidor
						 if(existe) buscarValoracion("modificar");
						 else buscarValoracion("añadir");
			        }
				
				
				db.close();
			}
		});
        
        //obtenemos la media de la valoración de una estación
		buscarValoracion("media");
	}

	private void buscarValoracion(String selec) {
		ProgressDialog progress = new ProgressDialog(this);
		progress.setMessage("Buscando, por favor espere...");
		new LoadParadaTask(progress, selec).execute();
	}
	

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.info, menu);
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
	
	/**
	 * Accede al servidor para guardar la valoración y obtener la media
	 *
	 */
	private class LoadParadaTask extends AsyncTask<Void, Void, String> {
		
		private ProgressDialog progress;
		private String selec;
		

		
		public LoadParadaTask(ProgressDialog progress, String selec) {
			// TODO Auto-generated constructor stub
			this.progress = progress;
			this.selec = selec;
		}


		public void onPreExecute() {
			progress.show();
			
		}

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
				
				HttpClient cliente = new DefaultHttpClient();
				String url;
				if (selec.equals("media"))url = constantes.media+"ciudad="+ciudad+"&pais="+pais+"&latitud="+lat+"&longitud="+lon;
				else if (selec.equals("añadir"))url = constantes.valoraciones+"ciudad="+ciudad+"&pais="+pais+"&latitud="+lat+"&longitud="+lon+"&puntuacion="+puntuacionN;
				else url = constantes.valoraciones+"ciudad="+ciudad+"&pais="+pais+"&latitud="+lat+"&longitud="+lon+"&puntuacionNueva="+puntuacionN+"&puntuacionAntigua="+puntuacionA;
				
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
				
//				return HttpRequest.get(params[0]).accept("application/json").body();
			
		}



		protected void onPostExecute(String response) {
			try {
				
				progress.dismiss();
				
				
				if(!response.equals("-1.0")) {
					double media = Math.round(Double.valueOf(response)*100)/100.0;
					val.setText(String.valueOf(media));
				}
				else 
					val.setText("Esta parada no ha sido valorada");
				
			} 
			catch (Exception e) {
				// TODO Auto-generated catch block
				Log.e("Error nuestro", e.toString());
			}

		}
	}

	//convertir la respuesta del servidor a string
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
	
	private String espacio(String np) {
		// TODO Auto-generated method stub
		String res = np.replaceAll(" ", "%20");

		return res;
	}
		
}

