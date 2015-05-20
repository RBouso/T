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
import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

import android.support.v4.app.ActionBarDrawerToggle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml.Encoding;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends ActionBarActivity {
	private MapView mapa = null;
	SupportMapFragment fragment;
	
	private LocationManager locManager;
	private LocationListener locListener;
	private double latitude;
	private double longitude;
	private DrawerLayout NavDrawerLayout;
	private ListView NavList;
	private String[] titulos;
	private ArrayList<String> NavItems;
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
	private String linea;
	private Object parada;
	private ArrayList<String> transportesList = new ArrayList<String>();
	
//	private NavigationAdapter NavAdapter;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		android.app.ActionBar bar = getActionBar();
		//for color
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0033CD")));
		

	     
//		getActionBar().setDisplayHomeAsUpEnabled(true);
		NavDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		//Lista
		NavList = (ListView) findViewById(R.id.lista);
//		View header =  getLayoutInflater().inflate(R.layout.activity_header, null);
//		NavList.addHeaderView(header);
		titulos = getResources().getStringArray(R.array.nav_options);
		
		NavDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		NavList.setAdapter(new ArrayAdapter<String>(this,
	                R.layout.activity_header,R.id.title_item, titulos));
//		NavList.setAdapter(new ArrayAdapter<String>(
//                getSupportActionBar().getThemedContext(),
//            	R.layout.activity_header, R.id.title_item, titulos));
		
//		getSupportActionBar().setHomeButtonEnabled(true);


		NavList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

		        
//				 Toast.makeText(getApplicationContext(), "holaaaaaaaa", Toast.LENGTH_SHORT).show();
				 Intent i;
				 switch (position) {
				    
				    case 0:
				    	 i = new Intent(MainActivity.this, Listas.class);
				    	 i.putExtra("ciudad", ciudad);
					     startActivity(i);
					     
					     finish();
					     break;
				    case 1:
				    	buscaTransportes("Transportes");
				    	
					     break;
				    case 2:
				    	buscaTransportes("BusquedaParadas");
//				    	Log.d("transportes", "tamaño " +transportesList.size());
//				    	i = new Intent(MainActivity.this, BusquedaParada.class);
//				    	i.putExtra("ciudad", ciudad);
//				    	i.putExtra("transportes", transportesList);
//					     startActivity(i);
				        Toast.makeText(getApplicationContext(), "Favoritos", Toast.LENGTH_SHORT).show();
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
        if (b != null) {
			if (b.getString("Anterior").equals("ciudades")){
				ciudad = b.getString("ciudad");

			}
			else if (b.getString("Anterior").equals("transportes")){
				ciudad = b.getString("ciudad");
				transporte = b.getString("transporte");
				pais = b.getString("pais");
				Toast.makeText(getApplicationContext(), transporte, Toast.LENGTH_SHORT).show();
			}
			else if (b.get("Anterior").equals("busqueda")) {
				ciudad = b.getString("ciudad");
				transporte = b.getString("transporte");
				linea = b.getString("linea");
				parada = b.get("parada");

			}
			
		}
	    
       if (savedInstanceState == null) {
            selectItem(0);
        }
//       obtenerGeolocalizacion();
//        //Obtenemos una referencia al control MapView
//        mapa = (MapView)findViewById(R.id.map);
// 
//        //Mostramos los controles de zoom sobre el mapa
//        mapa.setBuiltInZoomControls(true);
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
//			if (b != null)
//				obtenerVistaCiudad();
//			else 
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
					List<Address> fromLocation = g.getFromLocation(latitude, longitude, 1);
					if (ciudad == null) {
						ciudad = fromLocation.get(0).getLocality();
						pais = fromLocation.get(0).getCountryName();
						
						añadirPunto();
					}
					else {
						obtenerVistaCiudad();
						if (transporte != null) {
							if (linea != null && parada != null) {
								obtenerLatLong();
							}
							else {
								buscaTransportes(transporte);
							}
						}
						else {
							añadirPunto();
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
				
			}
             
        };
        if (locManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        	locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10,   locListener);
        else
            locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10,  locListener);
   
        
    }// end launchLocator.
	
	private void obtenerLatLong() {
		// TODO Auto-generated method stub
		//Transporte, linea y parada
	}
	
	
	
	private void añadirParadas() {
		// TODO Auto-generated method stub
		int icono = 0;
		Log.d("para", "Estoy en añadir paradas");
		if (transporte.equals("Aparcamiento")) {
			Log.d("para", "Soy aparcamiento y "+apar.size());
			icono = R.drawable.icono_parking;
			fragment.getMap().setMyLocationEnabled(true);
			for (int i = 0; i < apar.size(); i++) {
				Aparcamiento a = apar.get(i);
				double la = a.latitud;
				double lo = a.longitud;
				Geocoder g = new Geocoder(context);
				
				Log.d("lat", la+ " "+lo+" "+ a.direccion);
				try {
					List<Address> fromLocationName = g.getFromLocationName(a.direccion+", "+a.localidad, 1);
					la = fromLocationName.get(0).getLatitude();
					lo = fromLocationName.get(0).getLongitude();
					pais = fromLocationName.get(0).getCountryName();
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				LatLng l = new LatLng(la, lo);
				
				fragment.getMap().addMarker(new MarkerOptions().position(l)
						.title(a.direccion).snippet("Plazas totales: "+String.valueOf(a.plazasTotales)+"/n Plazas libres"
								+String.valueOf(a.plazasLibres))
						.snippet("Plazas libres: "+String.valueOf(a.plazasLibres)).alpha(1F).anchor(0.2F,0.2F)
						.icon(BitmapDescriptorFactory.fromResource(icono)));
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
						.title(a.direccion).snippet("Nº Anclajes: "+String.valueOf(a.anclajes)+"/n Plazas libres"
								+String.valueOf(a.biciLibres))
						.snippet("Plazas libres: "+String.valueOf(a.anclajes)).alpha(1F).anchor(0.2F,0.2F)
						.icon(BitmapDescriptorFactory.fromResource(icono)));
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
						.title(a.direccion).alpha(1F).anchor(0.2F,0.2F)
						.icon(BitmapDescriptorFactory.fromResource(icono)));
			}
			
		}
		
		fragment.getMap().moveCamera(CameraUpdateFactory.newLatLngZoom((new LatLng(latitude, longitude)), 14));
	}

	private void buscarParadas() {
		// TODO Auto-generated method stub
//		Aparcamiento a = new Aparcamiento();
//		a.latitud = latitude;
//		a.longitud = longitude;
//		a.plazasLibres = 3;
//		a.plazasTotales = 300;
//		a.direccion= "Av. Meridiana 596";
//		a.localidad = "Barcelona";
//		a.region = "Barcelona";
//		a.pais = "España";
//		a.identificador = "A78";
//				
//		apar.add(a);
	}

	private void añadirPunto() {
		// TODO Auto-generated method stub
//		Log.d("Longitud", latitude+" "+ longitude);
////		if (locManager!= null)
//		if (longitude != 0.0 && latitude != 0.0) {
//			Log.d("Longitud", "entro");
			fragment.getMap().setMyLocationEnabled(true);
			fragment.getMap().moveCamera(CameraUpdateFactory.newLatLngZoom((new LatLng(latitude, longitude)), 13));

			fragment.getMap().addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));
		
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


	
//	@Override
//	protected boolean isRouteDisplayed() {
//		// TODO Auto-generated method stub
//		return false;
//	}




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
				else
					url = constantes.transportes+ciudad;
				HttpGet peticion = new HttpGet(url);
				Log.d("Url 1", url);
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
					is.close();
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
				
				JSONObject json = new JSONObject(response);
				if (nom.equals("Aparcamiento")) {
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
					añadirParadas();
				}
				else {
	//				JSONObject jso = json.getJSONObject("data");
					JSONArray js = json.getJSONArray("nombres");
					for (int i = 0; i < js.length(); i++) {
						JSONObject j = js.getJSONObject(i);
						transportesList.add(j.getString("nombre"));
						
					}
					
					if(nom.equals("BusquedaParadas")) {
				    	Intent i = new Intent(MainActivity.this, BusquedaParada.class);
				    	i.putExtra("ciudad", ciudad);
				    	i.putExtra("transportes", transportesList);
					     startActivity(i);
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
//					
//					Parada p;
//					for (int i = 0; i < js.length(); ++ i) {
//						JSONObject j = js.getJSONObject(i);
//						p = new Parada();
//						p.setCorrespondencia(j.getString("connections"));
//						p.setId(j.getString("id"));
//						p.setLatitud(j.getString("lat"));
//						p.setLinea(j.getString("line"));
//						p.setLongitud(j.getString("lon"));
//						p.setNombre(j.getString("name"));
//						p.setTipo(j.getString("type"));
//						p.setZona(j.getString("zone"));
//						lista.add(p);
//					}
//		
//			        paradas.setAdapter(adapt);
//				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.e("Error nuestro", e.toString());	
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				Log.e("Error nuestro", e.toString());
			}
//			List<Parada> paradas = getParadas(h);
//			if (!paradas.isEmpty()) {
//				mostrarParada(paradas.get(0));
//			}
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

