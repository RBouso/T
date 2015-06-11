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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
//import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import BaseDatos.BaseDeDatos;
import android.support.v4.app.ActionBarDrawerToggle;

import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends ActionBarActivity {
	SupportMapFragment fragment;
	
	private LocationManager locManager;
	private LocationListener locListener;
	private double latitude;
	private double longitude;
	private DrawerLayout NavDrawerLayout;
	private ListView NavList;
	private String[] titulos;
	 private ActionBarDrawerToggle toggle;
	public boolean mChangeContentFragment = false;
	public Object mNextContentFragment;
	private Object mContentFragment;
	private Bundle b;
	private String ciudad;
	private String pais;
	private MainActivity context;
	private String transporte;
	private ArrayList<Aparcamiento> apar = new ArrayList<Aparcamiento>();
	private ArrayList<Bicicletas> bici = new ArrayList<Bicicletas>();
	private ArrayList<EstructuraPublica> ep = new ArrayList<EstructuraPublica>();
	private String direccion;
	private ArrayList<String> transportesList = new ArrayList<String>();
	private EstructuraPublica estructura = new EstructuraPublica();
	private ArrayList<EstructuraPublica> estructuras = new ArrayList<EstructuraPublica>();

	private RatingBar rb;

	private String linea;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		android.app.ActionBar bar = getActionBar();
		//for color
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0033CD")));

		NavDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		//Lista
		NavList = (ListView) findViewById(R.id.lista);

		titulos = getResources().getStringArray(R.array.nav_options);
		
		NavDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		NavList.setAdapter(new ArrayAdapter<String>(this,
	                R.layout.activity_header,R.id.title_item, titulos));



		NavList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

		        

				 Intent i;
				 switch (position) {
				    
				    case 0:
				    	 i = new Intent(MainActivity.this, Listas.class);
				    	 i.putExtra("ciudad", ciudad);
					     startActivity(i);
					 
					     break;
				    case 1:
				    	buscaTransportes("Transportes");
				    	
					     break;
				    case 2:
				    	buscaTransportes("BusquedaParadas");
				        break;
				    case 3:
				    	i = new Intent(MainActivity.this, BusquedaCercana.class);
				    	 i.putExtra("ciudad", ciudad);
				    	 i.putExtra("pais", pais);
					     startActivity(i);
				    	break;
				    case 4:
				    	buscaTransportes("Horarios");
				    	break;
				    case 5:
				    	i = new Intent(MainActivity.this, Favoritos.class);
				    	 i.putExtra("ciudad", ciudad);
				    	 i.putExtra("pais", pais);
					     startActivity(i);
				    	break;
				}
				NavList.setItemChecked(position, true);
//				    NavDrawerLayout.closeDrawer(NavList);
				}


			
		});
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
		toggle = new ActionBarDrawerToggle(this, NavDrawerLayout, R.drawable.ic_drawer, 
                R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            public void onDrawerClosed(View view) {
                getActionBar().setTitle("hola");
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }
 
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle("ADIOS");
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };

        NavDrawerLayout.setDrawerListener(new DrawerListener());
        b = getIntent().getExtras();
        Log.d("Entro", "Estoy en main activity");
        if (b != null) {
			if (b.getString("Anterior").equals("ciudades")){
				ciudad = b.getString("ciudad");

			}
			if (b.getString("Anterior").equals("ListaHorario")){
				ciudad = b.getString("ciudad");
				pais = b.getString("pais");

			}
			else if (b.getString("Anterior").equals("transportes")){
				ciudad = b.getString("ciudad");
				transporte = b.getString("transporte");
				pais = b.getString("pais");
//				Toast.makeText(getApplicationContext(), transporte, Toast.LENGTH_SHORT).show();
			}
			else if (b.getString("Anterior").equals("cercana")){
				ciudad = b.getString("ciudad");
				pais = b.getString("pais");
				direccion = b.getString("direccion");
				latitude = b.getDouble("latitud");
				longitude = b.getDouble("longitud");
			}
			else if (b.get("Anterior").equals("busqueda")) {
				ciudad = b.getString("ciudad");
				transporte = b.getString("transporte");
				
				if (b.getString("descripcion") != null) {
					estructura.descripcion = b.getString("descripcion");
				}
				if (b.getString("direccion") != null) 
					estructura.direccion = b.getString("direccion");
//				if (b.getString("latitud") != null) 
					estructura.latitud = b.getDouble("latitud");
//				if (b.getString("longitud") != null) 
					estructura.longitud =b.getDouble("longitud");
				if (b.getString("paisTab") != null) 
					estructura.pais = b.getString("paisTab");
				if (b.getString("region") != null) 
					estructura.region = b.getString("region");
				if (b.getString("telefono") != null)
					estructura.telefono = b.getString("telefono");
				
			}
			else if (b.getString("Anterior").equals("Lineas")){
				Log.d("s", "anterior");
				ciudad = b.getString("ciudad");
				pais = b.getString("pais");
				transporte = b.getString("transporte");
				linea = b.getString("linea");
				Log.d("s", linea);
			}
			
			
		}
	    
       if (savedInstanceState == null) {
            selectItem(0);
        }

	}


	private void obtenerVistaCiudad() {
	// TODO Auto-generated method stub
		Geocoder g = new Geocoder(context);
		try {
			List<Address> fromLocationName = g.getFromLocationName(ciudad, 1);
			latitude = fromLocationName.get(0).getLatitude();
			longitude = fromLocationName.get(0).getLongitude();
			pais = fromLocationName.get(0).getCountryName();
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private void crearFragment() {
	// TODO Auto-generated method stub
		android.support.v4.app.Fragment fragment1 = new FragmentMapa();
		FragmentManager fragmentManager = getFragmentManager();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment1).commit();
	}


	private void selectItem(int i) {
	// TODO Auto-generated method stub
		if (i == 0){
			crearFragment();
			obtenerGeolocalizacion();
			
		}
        // update selected item and title, then close the drawer
        NavList.setItemChecked(i, true);

        NavDrawerLayout.closeDrawer(NavList);
}

	private void obtenerGeolocalizacion() {
		// TODO Auto-generated method stub
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locListener = new LocationListener()
        {
          

			

			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				latitude = location.getLatitude();
				longitude = location.getLongitude();
				Geocoder g = new Geocoder(context);
				try {
					
					if (ciudad == null) {
						
							List<Address> fromLocation = g.getFromLocation(latitude, longitude, 1);
							ciudad = fromLocation.get(0).getLocality();
							pais = fromLocation.get(0).getCountryName();
						
							añadirPunto();
							buscaTransportes("direccion");
						
					}
					else {
						
						obtenerVistaCiudad();
						if (transporte != null) {
							if (estructura.descripcion != null) {
								ep.add(estructura);
								latitude = estructura.latitud;
								longitude = estructura.longitud;
								añadirParadas();
							}
							else if (linea != null){
								
								buscaTransportes("linea");
							}
							else {
								buscaTransportes(transporte);
							}
						}
						else {
							
							if (direccion != null){
								
								
									List<Address> fromLocationName = g.getFromLocationName(direccion+", "+ciudad, 1);
									latitude = fromLocationName.get(0).getLatitude();
									longitude = fromLocationName.get(0).getLongitude();
									pais = fromLocationName.get(0).getCountryName();
									buscaTransportes("direccion");
							}
							
							else  {
								añadirPunto();
								buscaTransportes("direccion");
							}
								
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
					
					builder.setMessage("La WIFI de su teléfono móvil no esta activa. Por favor, active la WIFI e intente acceder de nuevo a la aplicación más tarde.")
					        .setTitle("Sin conexión a Intenet")
					        .setCancelable(false)
					        .setNeutralButton("Aceptar",
					                new DialogInterface.OnClickListener() {
					                    public void onClick(DialogInterface dialog, int id) {
					                        dialog.cancel();
					                        finish();
					                    }
					                });
					AlertDialog alert = builder.create();
					alert.show();
					//e.printStackTrace();
				}
				
				
			}







			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				Log.d("", "Estoy desactivado");
			}
             
        };
        
        if (locManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        	locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10,   locListener);
        else
            locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10,  locListener);
   
        
    }// end launchLocator.

	private void añadirParadas() {
		// TODO Auto-generated method stub
		int icono = 0;
		fragment.getMap().moveCamera(CameraUpdateFactory.newLatLngZoom((new LatLng(latitude, longitude)), 14));
		fragment.getMap().setMyLocationEnabled(true);

		Log.d("para", "Estoy en añadir paradas");
		if (transporte.equals("Aparcamiento")) {
			Log.d("para", "Soy aparcamiento y "+apar.size());
			Log.d("para", latitude+ " soy "+ longitude);
			icono = R.drawable.icono_parking;
			fragment.getMap().setMyLocationEnabled(true);
			for(int i = 0; i < 1; i++) {
//			for (int i = 0; i < apar.size(); i++) {
				Aparcamiento a = apar.get(i);
				double la = a.latitud;
				double lo = a.longitud;
				Geocoder g = new Geocoder(context);
				
				Log.d("lat", la+ " "+lo+" "+ a.direccion);
				try {
					List<Address> fromLocationName = g.getFromLocationName(a.direccion+", "+a.localidad, 1);
					if (!fromLocationName.isEmpty()) {
						la = fromLocationName.get(0).getLatitude();
					
						lo = fromLocationName.get(0).getLongitude();
						pais = fromLocationName.get(0).getCountryName();
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				LatLng l = new LatLng(la, lo);
				String acces = "No";
				if(a.accesibilidad == 1) acces = "Si";
				fragment.getMap().addMarker(new MarkerOptions().position(l)
						.title(a.direccion).snippet("Telefono: "+a.telefono+" Descripcion: "+
								a.descripcion+" Latitud: "+ latitude+" Longitud: "+ longitude+" Accesibilidad: "
								+acces+", Plazas totales: "+String.valueOf(a.plazasTotales)+", Plazas libres: "
								+String.valueOf(a.plazasLibres)).alpha(1F).anchor(0.2F,0.2F)
						.icon(BitmapDescriptorFactory.fromResource(icono)));
				
				fragment.getMap().setInfoWindowAdapter(new InfoWindowAdapter() {
					
					@Override
					public View getInfoWindow(Marker marker) {
						// TODO Auto-generated method stub
						return null;
					}
					
					@Override
					public View getInfoContents(Marker marker) {
						// TODO Auto-generated method stub
						View popup =  getLayoutInflater().inflate(R.layout.activity_popup, null);
						
						TextView tv=(TextView)popup.findViewById(R.id.title);
						tv.setText(marker.getTitle());
						tv=(TextView)popup.findViewById(R.id.snippet);
						tv.setText(marker.getSnippet().substring(marker.getSnippet().indexOf("Accesibilidad"), marker.getSnippet().lastIndexOf(",")+1));
						tv=(TextView)popup.findViewById(R.id.snippet1);
						tv.setText(marker.getSnippet().substring(marker.getSnippet().lastIndexOf(",")+1));
						rb = (RatingBar) popup.findViewById(R.id.ratingBar1);
						rb.setEnabled(true);
						Double lat = Double.valueOf(marker.getSnippet().substring(marker.getSnippet().indexOf("Latitud")+9,marker.getSnippet().indexOf("Longitud")));
						Double lon = Double.valueOf(marker.getSnippet().substring(marker.getSnippet().indexOf("Longitud")+10,marker.getSnippet().indexOf("Accesibilidad")));
						BaseDeDatos bd =
					            new BaseDeDatos(getApplicationContext(), "DBEstacion", null, 1);
					 
					        SQLiteDatabase db = bd.getWritableDatabase();
//					        bd.onUpgrade(db, 1, 2);
					        //Si hemos abierto correctamente la base de datos
					        if(db != null)
					        {
					        	Cursor cu = db.rawQuery("SELECT * FROM Estacion e WHERE e.latitud = "+lat+" and e.longitud = "+lon, null);
					            if (cu.moveToFirst()) {
					            	
					            		Log.d("select Main", cu.getString(0)+" "+cu.getString(1)+" "+cu.getFloat(2)
					    	        			+" "+cu.getFloat(3)+ " "+cu.getInt(4));
					            		rb.setRating(cu.getInt(4));
					            	
					            }
					        
					           cu.close();
					            //Cerramos la base de datos
					            db.close();
					        }
					        
//						infoRatingListener.setMarker(marker);
						
						return(popup);
					}
				});
			}
			
				
			
		}
		else if (transporte.equals("Bicicletas")) {
			icono = R.drawable.icono_bici;
			for (int i = 0; i < bici.size(); i++) {
				Bicicletas a = bici.get(i);
				double la = a.latitud;
				double lo = a.longitud;
				LatLng l = new LatLng(la, lo);
				fragment.getMap().addMarker(new MarkerOptions().position(l)
						.title(a.direccion).snippet("Telefono: "+a.telefono+" Descripcion: "+a.descripcion+" Latitud: "+ latitude+" Longitud: "+ longitude+" Nº Anclajes: "+String.valueOf(a.anclajes)+", Plazas libres"
								+String.valueOf(a.biciLibres)).alpha(1F).anchor(0.2F,0.2F)
						.icon(BitmapDescriptorFactory.fromResource(icono)));
				
				fragment.getMap().setInfoWindowAdapter(new InfoWindowAdapter() {
					
					@Override
					public View getInfoWindow(Marker marker) {
						// TODO Auto-generated method stub
						return null;
					}
					
					@Override
					public View getInfoContents(Marker marker) {
						// TODO Auto-generated method stub
						View popup = getLayoutInflater().inflate(R.layout.activity_popup, null);
						TextView tv=(TextView)popup.findViewById(R.id.title);
						tv.setText(marker.getTitle());
						tv=(TextView)popup.findViewById(R.id.snippet);
						tv.setText(marker.getSnippet().substring(marker.getSnippet().indexOf("Nº"), marker.getSnippet().lastIndexOf(",")+1));
						tv=(TextView)popup.findViewById(R.id.snippet1);
						tv.setText(marker.getSnippet().substring(marker.getSnippet().lastIndexOf(",")+1));
//						infoRatingListener.setMarker(marker);
//						if(rb.getRating() == 0) rb.setRating(1);
						
						rb = (RatingBar) popup.findViewById(R.id.ratingBar1);
						rb.setEnabled(true);
						Double lat = Double.valueOf(marker.getSnippet().substring(marker.getSnippet().indexOf("Latitud")+9,marker.getSnippet().indexOf("Longitud")));
						Double lon = Double.valueOf(marker.getSnippet().substring(marker.getSnippet().indexOf("Longitud")+10,marker.getSnippet().indexOf("Nº")));
						BaseDeDatos bd =
					            new BaseDeDatos(getApplicationContext(), "DBEstacion", null, 1);
					 
					        SQLiteDatabase db = bd.getWritableDatabase();
//					        bd.onUpgrade(db, 1, 2);
					        //Si hemos abierto correctamente la base de datos
					        if(db != null)
					        {
					        	Cursor cu = db.rawQuery("SELECT * FROM Estacion e WHERE e.latitud = "+lat+" and e.longitud = "+lon, null);
					            if (cu.moveToFirst()) {
					            	do {
					            		rb.setRating(cu.getInt(4));
					            	}while(cu.moveToNext());
					            }
					        
					           cu.close();
					            //Cerramos la base de datos
					            db.close();
					        }
						
						return(popup);
					}
				});
			}
			
		}
		else {
			if (transporte.equals("Taxi"))
				icono = R.drawable.icono_taxi;
			else if (transporte.equals("Autobus"))
				icono = R.drawable.icono_bus;
			else if (transporte.equals("Metro"))
				icono = R.drawable.icono_metro;
			else if (transporte.equals("Tranvia"))
				icono = R.drawable.icono_tranvia;
			else if (transporte.equals("Ferrocarriles"))
				icono = R.drawable.icono_cercanias;
			else if (transporte.equals("Funicular"))
				icono = R.drawable.icono_funicular;
			else if (transporte.equals("Teleferico"))
				icono = R.drawable.icono_teleferico;
			for (int i = 0; i < ep.size(); i++) {
				EstructuraPublica a = ep.get(i);
				double la = a.latitud;
				double lo = a.longitud;
				LatLng l = new LatLng(la, lo);
				fragment.getMap().addMarker(new MarkerOptions().position(l)
						.title(a.direccion).snippet("Telefono: "+a.telefono+" Descripcion: "+a.descripcion+" Latitud: "+ 
				latitude+" Longitud: "+ longitude).alpha(1F).anchor(0.2F,0.2F)
						.icon(BitmapDescriptorFactory.fromResource(icono)));
				
fragment.getMap().setInfoWindowAdapter(new InfoWindowAdapter() {
					
					@Override
					public View getInfoWindow(Marker marker) {
						// TODO Auto-generated method stub
						return null;
					}
					
					@Override
					public View getInfoContents(Marker marker) {
						// TODO Auto-generated method stub
						View popup = getLayoutInflater().inflate(R.layout.activity_popup, null);
						TextView tv=(TextView)popup.findViewById(R.id.title);
						tv.setText(marker.getTitle());
						tv=(TextView)popup.findViewById(R.id.snippet);
						tv.setText(marker.getSnippet().substring(marker.getSnippet().indexOf("Nº"), marker.getSnippet().lastIndexOf(",")+1));
						tv=(TextView)popup.findViewById(R.id.snippet1);
						tv.setText(marker.getSnippet().substring(marker.getSnippet().lastIndexOf(",")+1));
//						infoRatingListener.setMarker(marker);
//						if(rb.getRating() == 0) rb.setRating(1);
						
						rb = (RatingBar) popup.findViewById(R.id.ratingBar1);
						rb.setEnabled(true);
						Double lat = Double.valueOf(marker.getSnippet().substring(marker.getSnippet().indexOf("Latitud")+9,marker.getSnippet().indexOf("Longitud")));
						Double lon = Double.valueOf(marker.getSnippet().substring(marker.getSnippet().indexOf("Longitud")+10,marker.getSnippet().indexOf("Nº")));
						BaseDeDatos bd =
					            new BaseDeDatos(getApplicationContext(), "DBEstacion", null, 1);
					 
					        SQLiteDatabase db = bd.getWritableDatabase();
//					        bd.onUpgrade(db, 1, 2);
					        //Si hemos abierto correctamente la base de datos
					        if(db != null)
					        {
					        	Cursor cu = db.rawQuery("SELECT * FROM Estacion e WHERE e.latitud = "+lat+" and e.longitud = "+lon, null);
					            if (cu.moveToFirst()) {
					            	do {
					            		rb.setRating(cu.getInt(4));
					            	}while(cu.moveToNext());
					            }
					        
					           cu.close();
					            //Cerramos la base de datos
					            db.close();
					        }
						
						return(popup);
					}
				});
				
			}
			
		}
		
		fragment.getMap().setOnInfoWindowClickListener(new OnInfoWindowClickListener() {          
	        public void onInfoWindowClick(Marker marker) {
	        	Intent i = new Intent(MainActivity.this,Info.class);
	        	String direccion = marker.getTitle();
				String telefono = marker.getSnippet().substring(9,marker.getSnippet().indexOf("Descripcion"));
	        	String descripcion = marker.getSnippet().substring(marker.getSnippet().indexOf("Descripcion")+12,marker.getSnippet().indexOf("Latitud"));
	        	Double lat = Double.valueOf(marker.getSnippet().substring(marker.getSnippet().indexOf("Latitud")+9,marker.getSnippet().indexOf("Longitud")));
				
				i.putExtra("transporte", transporte);
				i.putExtra("ciudad", ciudad);
				i.putExtra("pais", pais);
				if (telefono != null)
					i.putExtra("telefono", telefono);
				else i.putExtra("telefono", "-1");
	            i.putExtra("direccion", direccion);
	            i.putExtra("latitud", lat);

				if (transporte.equals("Aparcamiento")) {
					Double lon = Double.valueOf(marker.getSnippet().substring(marker.getSnippet().indexOf("Longitud")+10,marker.getSnippet().indexOf("Accesibilidad")));
					i.putExtra("longitud", lon);
					String acces = marker.getSnippet().substring(marker.getSnippet().indexOf("Accesibilidad")+15, marker.getSnippet().indexOf(", Plazas totales"));
					int total = Integer.parseInt(marker.getSnippet().substring(marker.getSnippet().indexOf("totales")+9, marker.getSnippet().indexOf(", Plazas libres")));
		            int libres = Integer.parseInt(marker.getSnippet().substring(marker.getSnippet().indexOf("libres")+8));
	            
		            i.putExtra("acces", acces);
		            i.putExtra("total", total);
		            i.putExtra("libres", libres);
				}
				else if (transporte.equals("Bicicletas")) {
					Double lon = Double.valueOf(marker.getSnippet().substring(marker.getSnippet().indexOf("Longitud")+10,marker.getSnippet().indexOf("Nº")));
					i.putExtra("longitud", lon);
					int anclajes = Integer.parseInt(marker.getSnippet().substring(marker.getSnippet().indexOf("Anclajes")+9, marker.getSnippet().indexOf(", Plazas libres")));
		            int libres = Integer.parseInt(marker.getSnippet().substring(marker.getSnippet().indexOf("libres")+8));
	            
		            i.putExtra("anclajes", anclajes);
		            i.putExtra("bicis", libres);
				}
				else {
					Double lon = Double.valueOf(marker.getSnippet().substring(marker.getSnippet().indexOf("Longitud")+10));
					i.putExtra("longitud", lon);
				}
	            startActivity(i);
	        }
	    });
//		fragment.getMap().setOnInfoWindowClickListener((OnInfoWindowClickListener) this);
//		 Toast.makeText(getApplicationContext(), latitude+ " "+ longitude, Toast.LENGTH_LONG).show();
		fragment.getMap().moveCamera(CameraUpdateFactory.newLatLngZoom((new LatLng(latitude, longitude)), 14));
	}



	private void añadirPunto() {
		// TODO Auto-generated method stub
//		Log.d("Longitud", latitude+" "+ longitude);
////		if (locManager!= null)
//		if (longitude != 0.0 && latitude != 0.0) {
//			Log.d("Longitud", "entro");
		if(fragment.getMap() != null) {
			fragment.getMap().setMyLocationEnabled(true);
			fragment.getMap().moveCamera(CameraUpdateFactory.newLatLngZoom((new LatLng(latitude, longitude)), 13));

			fragment.getMap().addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));
		}
	}
	

	private class DrawerListener implements android.support.v4.widget.DrawerLayout.DrawerListener {

	    
		@Override
	    public void onDrawerClosed(View view) {
			
	     }
		@Override
		public void onDrawerOpened(View arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onDrawerSlide(View arg0, float arg1) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onDrawerStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}
	}
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }
    
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }
    


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		 if (toggle.onOptionsItemSelected(item)) {
			 return true;
	        }
		int id = item.getItemId();
		switch (id) {
			case( R.id.action_settings):
				return true;
			case (R.id.menu_busqueda):
				return true;
//			case (R.id.home):
//				if (NavDrawerLayout.isDrawerOpen(NavList)){
//					NavDrawerLayout.closeDrawers();
//				}else{
//					NavDrawerLayout.openDrawer(NavList);
//				}
//				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}





private  class FragmentMapa extends Fragment {
	private View rootView;
	
	public FragmentMapa(){}


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_fragment_mapa, container, false);
        
		fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		
        return rootView;
    }
	


}
	private void buscaTransportes(String nom) {
		// TODO Auto-generated method stub
		ProgressDialog progress = new ProgressDialog(this);
		progress.setMessage("Buscando, por favor espere...");
		new LoadParadaTask(progress, nom).execute();
	
	}
	
private class LoadParadaTask extends AsyncTask<Void, Void, String> {
		
		ProgressDialog progress;
		String nom;
		
		public LoadParadaTask(ProgressDialog progress, String nom) {
			// TODO Auto-generated constructor stub
			this.progress = progress;
			 this.nom = nom;
		}


		public void onPreExecute() {
			progress.show();
		}

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
				
				HttpClient cliente = new DefaultHttpClient();

				String url;
				if (nom.equals("Aparcamiento"))
					url = constantes.aparcamiento+"ciudad="+ciudad+"&pais="+pais;
				else if (nom.equals("Bicicletas"))
					url = constantes.bicicleta+"ciudad="+ciudad+"&pais="+pais;
				else if (nom.equals("Taxi"))
					url = constantes.taxi+"ciudad="+ciudad+"&pais="+pais;
				else if (nom.equals("direccion")) { 
//					if(direccion != null)
//						url = constantes.cercanas+"ciudad="+ciudad+"&pais="
//							+pais+"&direccion="+espacio(direccion);
//					else 
						url = constantes.cercanas+"ciudad="+ciudad+"&pais="
								+pais+"&latitud="+latitude+"&longitud="+longitude;
				}
				else if (nom.equals("linea"))
					url = constantes.paradas+"ciudad="+ciudad+"&pais="+pais+"&transporte="+transporte+"&linea="+espacio(linea);
				else if (nom.equals("Horarios")) 
					url = constantes.horario+"ciudad="+ciudad+"&pais="
							+pais;
				else
					url = constantes.transportes+ciudad;
				HttpGet peticion = new HttpGet(url);
				Log.d("Url 1", url);
				// ejecuta una petición get
				 InputStream is = null;
				 String result = "";
				try {

					HttpResponse respuesta = cliente.execute(peticion);
					Log.d("",respuesta.getStatusLine().getStatusCode()+"");
					int status = respuesta.getStatusLine().getStatusCode();
					if (status == 0) {
						result = "No ha conectado con el servidor";
					}
					else {
						is = respuesta.getEntity().getContent();
						if (is != null) {
							result = convertInputtoString(is);
							
							
							
							
						}
						else {
							result = "No ha funcionado";
						}
					}
					is.close();
					return result;
				
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					
					return "No ha conectado con el servidor";
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
				progress.cancel();
				progress.dismiss();
				if(response == null || response.equals("No ha conectado con el servidor")){
					AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
					
					builder.setMessage("En estos momentos el servidor no se encuentra disponible. Por favor, intente acceder a la aplicación más tarde.")
					        .setTitle("Conexión fallida")
					        .setCancelable(false)
					        .setNeutralButton("Aceptar",
					                new DialogInterface.OnClickListener() {
					                    public void onClick(DialogInterface dialog, int id) {
					                        dialog.cancel();
					                        finish();
					                    }
					                });
					AlertDialog alert = builder.create();
					alert.show();
				}
				else {	
					Log.d("f", response);
					JSONObject json = new JSONObject(response);
					
					if (nom.equals("direccion") || nom.equals("linea")) {
						JSONArray js = json.getJSONArray("Estaciones");
						
						for (int i = 0; i < js.length(); i++) {
							JSONObject j = js.getJSONObject(i);
							ep = new ArrayList<EstructuraPublica>();
								EstructuraPublica a = new EstructuraPublica();
								if(nom.equals("direccion"))a.transporte = j.getString("transporte");
								else a.transporte = transporte;
								if (!j.getString("descripcion").isEmpty())
									a.descripcion = j.getString("descripcion");
								if (!j.getString("latitud").isEmpty())
									a.latitud = Double.valueOf(j.getString("latitud"));
								if (!j.getString("longitud").isEmpty())
									a.longitud = Double.valueOf(j.getString("longitud"));
								if (!j.getString("pais").isEmpty())
									a.pais = j.getString("pais");
								if (!j.getString("localidad").isEmpty())
									a.localidad = j.getString("localidad");
								if (!j.getString("direccion").isEmpty())
									a.direccion = j.getString("direccion");
								if (!j.getString("telefono").isEmpty())
									a.telefono = j.getString("telefono");
								if (a.transporte.equals("Aparcamiento") ) {
									Aparcamiento ap = new Aparcamiento();
									if (!j.getString("accesibilidad").isEmpty())
										ap.accesibilidad = Integer.valueOf(j.getString("accesibilidad"));
									if (!j.getString("plazasLibres").isEmpty())
										ap.plazasLibres = Integer.valueOf(j.getString("plazasLibres"));
									if (!j.getString("plazasTotales").isEmpty())
										ap.plazasTotales = Integer.valueOf(j.getString("plazasTotales"));
									a.aparcamiento = ap;
								}
								else if (a.transporte.equals("Bicicleta")) {
									Bicicletas b = new Bicicletas();
									if (!j.getString("anclajes").isEmpty())
										b.anclajes = Integer.valueOf(j.getString("anclajes"));
									if (!j.getString("biciLibres").isEmpty())
										b.biciLibres = Integer.valueOf(j.getString("biciLibres"));
									a.bicis = b;
								}
								
								transporte = a.transporte;
								if (transporte.equals("Aparcamiento")) {
									apar = new ArrayList<Aparcamiento>();
									Aparcamiento ap = a.aparcamiento;
									ap.descripcion = a.descripcion;
									ap.direccion = a.direccion;
									ap.latitud = a.latitud;
									ap.longitud = a.longitud;
									ap.localidad = a.localidad;
									ap.pais = a.pais;
									ap.region = a.region;
									ap.telefono =a.telefono;
									apar.add(ap);
								}
								else if (transporte.equals("Bicicleta")) {
									apar = new ArrayList<Aparcamiento>();
									Bicicletas ap = a.bicis;
									ap.descripcion = a.descripcion;
									ap.direccion = a.direccion;
									ap.latitud = a.latitud;
									ap.longitud = a.longitud;
									ap.localidad = a.localidad;
									ap.pais = a.pais;
									ap.region = a.region;
									ap.telefono = a.telefono;
									bici.add(ap);
								}
								else {
								
									ep.add(a);
								}
								añadirParadas();
								
							}
					}
					else if (nom.equals("Aparcamiento")) {
						JSONArray js = json.getJSONArray("Aparcamientos");
						for (int i = 0; i < js.length(); i++) {
							JSONObject j = js.getJSONObject(i);
							Aparcamiento a = new Aparcamiento();
							if (!j.getString("descripcion").isEmpty())
								a.descripcion = j.getString("descripcion");
							if (!j.getString("latitud").isEmpty())
								a.latitud = Double.valueOf(j.getString("latitud"));
							if (!j.getString("longitud").isEmpty())
								a.longitud = Double.valueOf(j.getString("longitud"));
							if (!j.getString("pais").isEmpty())
								a.pais = j.getString("pais");
							if (!j.getString("localidad").isEmpty())
								a.localidad = j.getString("localidad");
							if (!j.getString("direccion").isEmpty())
								a.direccion = j.getString("direccion");
							if (!j.getString("telefono").isEmpty())
								a.telefono = j.getString("telefono");
							if (!j.getString("accesibilidad").isEmpty())
								a.accesibilidad = Integer.valueOf(j.getString("accesibilidad"));
							if (!j.getString("plazasLibres").isEmpty())
								a.plazasLibres = Integer.valueOf(j.getString("plazasLibres"));
							if (!j.getString("plazasTotales").isEmpty())
								a.plazasTotales = Integer.valueOf(j.getString("plazasTotales"));
							apar.add(a);
						}
						obtenerVistaCiudad();
						añadirParadas();
					}
					else if (nom.equals("Bicicletas")) {
						JSONArray js = json.getJSONArray("Bicicletas");
						for (int i = 0; i < js.length(); i++) {
							JSONObject j = js.getJSONObject(i);
							Bicicletas a = new Bicicletas();
							if (!j.getString("descripcion").isEmpty())
								a.descripcion = j.getString("descripcion");
							if (!j.getString("latitud").isEmpty())
								a.latitud = Double.valueOf(j.getString("latitud"));
							if (!j.getString("longitud").isEmpty())
								a.longitud = Double.valueOf(j.getString("longitud"));
							if (!j.getString("pais").isEmpty())
								a.pais = j.getString("pais");
							if (!j.getString("localidad").isEmpty())
								a.localidad = j.getString("localidad");
							if (!j.getString("direccion").isEmpty())
								a.direccion = j.getString("direccion");
							if (!j.getString("telefono").isEmpty())
								a.telefono = j.getString("telefono");
							
							if (!j.getString("anclajes").isEmpty())
								a.anclajes = Integer.valueOf(j.getString("anclajes"));
							if (!j.getString("biciLibres").isEmpty())
								a.biciLibres = Integer.valueOf(j.getString("biciLibres"));
							bici.add(a);
						}
						obtenerVistaCiudad();
						añadirParadas();
					}
					else if (nom.equals("Taxi")) {
						JSONArray js = json.getJSONArray("Taxi");
						for (int i = 0; i < js.length(); i++) {
							JSONObject j = js.getJSONObject(i);
							EstructuraPublica a = new EstructuraPublica();
							if (!j.getString("descripcion").isEmpty())
								a.descripcion = j.getString("descripcion");
							if (!j.getString("latitud").isEmpty())
								a.latitud = Double.valueOf(j.getString("latitud"));
							if (!j.getString("longitud").isEmpty())
								a.longitud = Double.valueOf(j.getString("longitud"));
							if (!j.getString("pais").isEmpty())
								a.pais = j.getString("pais");
							if (!j.getString("localidad").isEmpty())
								a.localidad = j.getString("localidad");
							if (!j.getString("direccion").isEmpty())
								a.direccion = j.getString("direccion");
							if (!j.getString("telefono").isEmpty())
								a.telefono = j.getString("telefono");
							ep.add(a);
						}
						obtenerVistaCiudad();
						añadirParadas();
					}
					else {
						JSONArray js = json.getJSONArray("nombres");
						transportesList = new ArrayList<String>();
						for (int i = 0; i < js.length(); i++) {
							JSONObject j = js.getJSONObject(i);
							transportesList.add(j.getString("nombre"));
							
						}
						
						if(nom.equals("BusquedaParadas")) {
					    	Intent i = new Intent(MainActivity.this, BusquedaParada.class);
					    	i.putExtra("ciudad", ciudad);
					    	i.putExtra("pais", pais);
					    	i.putExtra("transportes", transportesList);
						     startActivity(i);
						}
						else if(nom.equals("Horarios")) {
							if (transportesList.isEmpty()) {
								Intent i  = new Intent(MainActivity.this, Error.class);
								i.putExtra("ciudad", ciudad);
						    	i.putExtra("pais", pais);
						    	i.putExtra("Anterior", "MainActivity");
						    	i.putExtra("error", "No hay horarios disponibles para ningun transporte de esta ciudad.");
						    	startActivity(i);
							}
							else {	
						    	Intent i = new Intent(MainActivity.this, TabHorarios.class);
						    	i.putExtra("ciudad", ciudad);
						    	i.putExtra("pais", pais);
						    	i.putExtra("transportes", transportesList);
							     startActivity(i);
							}
						}
						else {
							Log.d("Main pais", pais);
							Intent i = new Intent(MainActivity.this, Transportes.class);
					    	i.putExtra("ciudad", ciudad);
					    	i.putExtra("pais", pais);
					    	i.putExtra("transportes", transportesList);
					    	startActivity(i);
					    	
						}
	
					}
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.e("Error nuestro", e.getMessage());	
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

