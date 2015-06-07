package com.example.apptransportespublicos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.conexion.constantes;

import BaseDatos.BaseDeDatos;
import android.support.v7.app.ActionBarActivity;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

public class Info extends Activity {
	private String transporte;
	private String direccion;
	private Double lat;
	private Double lon;
	private String acces;
	private int total;
	private int libres;
	private int anclajes;
	private int bicis;
	private String telefono;
	private String lineas;
	private String ciudad;
	private String pais;
	private float favorito;
	private TextView val;
	private Button valorar;
	private int puntuacionA;
	private int puntuacionN;
	private RatingBar rb;
	private RatingBar valoracion;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		android.app.ActionBar bar = getActionBar();
		//for color
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0033CD")));
		
		Bundle b = getIntent().getExtras();
		transporte = b.getString("transporte");
		ciudad = b.getString("ciudad");
		pais = b.getString("pais");
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
		Log.d("rb", rb.isActivated()+ " "+ rb.isClickable()+ " "+ rb.isEnabled()+" "+ rb.isFocusable());
		
		lat = b.getDouble("latitud");
		lon = b.getDouble("longitud");
		BaseDeDatos bd = new BaseDeDatos(getApplicationContext(), "DBEstacion", null, 1);
		SQLiteDatabase db = bd.getWritableDatabase();
        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {
			Cursor cu = db.rawQuery("SELECT * FROM Estacion e WHERE e.ciudad = '"+
	    			ciudad+"' AND e.pais = '" +pais+"' AND e.latitud = "+
	    			lat+" AND e.longitud = "+lon, null);
	        if (cu.moveToFirst()) {
	        	Log.d("select", cu.getString(0)+" "+cu.getString(1)+" "+cu.getFloat(2)
	        			+" "+cu.getFloat(3)+ " "+cu.getInt(4));
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
		telefono = b.getString("telefono");
		if(!telefono.equals("-1") || telefono != null) {
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
		
		if (transporte.equals("Aparcamiento")) {
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
			total = b.getInt("total");
			tot = (TextView) findViewById(R.id.totales);
			tot.setText(""+total);
			tot = (TextView) findViewById(R.id.LibresText);
			tot.setText("Plazas libres: ");
			libres = b.getInt("libres");
			tot = (TextView) findViewById(R.id.libres);
			tot.setText(""+libres);
		}
		else if (transporte.equals("Bicicletas")) {
			TextView tot = (TextView) findViewById(R.id.accesibilidadText);
			tot.setText("Nº de anclajes: ");
			anclajes = b.getInt("anclajes");
			tot = (TextView) findViewById(R.id.accesibilidad);
			tot.setText(""+anclajes);
			tot = (TextView) findViewById(R.id.totalesText);
			tot.setText("Bicicletas disponibles: ");
			bicis = b.getInt("bicis");
			tot = (TextView) findViewById(R.id.totales);
			tot.setText(""+bicis);
			TextView desaparecer = (TextView) findViewById(R.id.LibresText);
			TextView desaparece = (TextView) findViewById(R.id.libres);
			desaparecer.setVisibility(View.GONE);
			desaparece.setVisibility(View.GONE);
		}
		else if(transporte.equals("Taxi")) {
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
        rb.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				// TODO Auto-generated method stub
				BaseDeDatos bd = new BaseDeDatos(getApplicationContext(), "DBEstacion", null, 1);
				SQLiteDatabase db = bd.getWritableDatabase();
				if(favorito == 0) {
					favorito = 1;
					Log.d("fav", "Pongo a 1");
					
			        //Si hemos abierto correctamente la base de datos
			        if(db != null)
			        {
						Cursor cu = db.rawQuery("SELECT * FROM Estacion e WHERE e.ciudad = '"+
				    			ciudad+"' AND e.pais = '" +pais+"' AND e.latitud = "+
				    			lat+" AND e.longitud = "+lon, null);
						 if (cu.moveToFirst()) {
				            	db.execSQL("UPDATE Estacion SET esFavorita = 1 WHERE e.ciudad = '"+
				        			ciudad+"' AND e.pais = '" +pais+"' AND e.latitud = "+
				        			lat+" AND e.longitud = "+lon);
				            }
				        	
				            else {
				            	db.execSQL("INSERT INTO Estacion (ciudad, pais, latitud, longitud, esFavorita, puntuacion) " +
				                           "VALUES ('" + ciudad + "', '" + pais +"', "+lat+", "+lon+", 1, -1)");
				            }
			        	
				       cu.close();
				        
			        }
				}
				else {
					favorito = 0;
					Log.d("fav", "Pongo a 0");
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
		        Log.d("fav", "Soy favorito "+ favorito);
				rb.setRating(favorito);
			}
		});
       
        val = (TextView) findViewById(R.id.valoracionText);
        val.setText("Valoracion: ");
        val = (TextView) findViewById(R.id.valoracion);
		//poner valoracion
        Log.d("rb", "soy indicador"+ rb.isIndicator()+ " "+ rb.isInEditMode()+ " "+ rb.isEnabled()+" "+ rb.isInTouchMode());
//        valoracion.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
//			
//			@Override
//			public void onRatingChanged(RatingBar ratingBar, float rating,
//					boolean fromUser) {
				// TODO Auto-generated method stub
        valoracion.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
	

				return true;
			}
		});
        valorar = (Button) findViewById(R.id.buttonValorar);
        valorar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				puntuacionN = (int) valoracion.getRating();
				BaseDeDatos bd = new BaseDeDatos(getApplicationContext(), "DBEstacion", null, 1);
				SQLiteDatabase db = bd.getWritableDatabase();

				if(puntuacionA != -1) {
					if(db != null)
			        {
						Cursor cu = db.rawQuery("SELECT * FROM Estacion e WHERE e.ciudad = '"+
				    			ciudad+"' AND e.pais = '" +pais+"' AND e.latitud = "+
				    			lat+" AND e.longitud = "+lon, null);
						 if (cu.moveToFirst()) {
				            	db.execSQL("UPDATE Estacion SET puntuacion = "+puntuacionN+" WHERE ciudad = '"+
				        			ciudad+"' AND pais = '" +pais+"' AND latitud = "+
				        			lat+" AND longitud = "+lon);
				            }
						 cu.close();
			        }
					buscarValoracion("modificar");
				}
				else {
					db.execSQL("INSERT INTO Estacion (ciudad, pais, latitud, longitud, esFavorita, puntuacion) " +
	                           "VALUES ('" + ciudad + "', '" + pais +"', "+lat+", "+lon+", 0, "+puntuacionN+")");
					buscarValoracion("añadir");
				}
				db.close();
			}
		});
        
        
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
				else url = constantes.valoraciones+"ciudad="+ciudad+"&pais="+pais+"&latitud="+lat+"&longitud="+lon+"&puntuacionNueva"+puntuacionN+"&puntuacionAntigua"+puntuacionA;
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
				
//				return HttpRequest.get(params[0]).accept("application/json").body();
			
		}



		protected void onPostExecute(String response) {
			try {
				//linea.setText(response);
				progress.dismiss();
				Log.d("String", response);
				
				if(!response.equals("-1.0")) {
					val.setText(response);
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

