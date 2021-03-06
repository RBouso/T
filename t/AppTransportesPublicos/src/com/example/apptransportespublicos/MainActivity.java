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
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
//import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import BaseDatos.BaseDeDatos;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity {
	//Fragmento del mapa
	SupportMapFragment fragment;
	
	private LocationManager locManager;//Localización del servicio
	private LocationListener locListener;//Escucha el servicio
	private double latitude; //latitud de una localización
	private double longitude;  //latitud de una localización
	private DrawerLayout NavDrawerLayout; //Panel lateral NavigationDrawer
	private ListView NavList; //lista del panel lateral
	private String[] titulos; //opciones de la lista NavList
	 private ActionBarDrawerToggle toggle;//NavigationDrawer
	public boolean mChangeContentFragment = false;
	public Object mNextContentFragment;
	private Bundle b;//paso de parametros que propociona otra actividad
	private String ciudad; //ciudad actual de la aplicación
	private String pais;//pais actual de la aplicación
	private MainActivity context; //clase MainActivity
	private String transporte;//transporte con el que trabajamos
	private ArrayList<Aparcamiento> apar = new ArrayList<Aparcamiento>(); //lista de todas las estaciones de aparcamiento
	private ArrayList<Bicicletas> bici = new ArrayList<Bicicletas>();//lista de todas las estaciones de bicicletas
	private ArrayList<EstructuraPublica> ep = new ArrayList<EstructuraPublica>(); //lista de todas las estaciones de taxi y estaciones de transporte público
	private String direccion; //dirección de una estación
	private ArrayList<String> transportesList = new ArrayList<String>();//lista de transportes de una ciudad
	private EstructuraPublica estructura = new EstructuraPublica(); //estación de transporte
	
	
	private RatingBar rb;//rating bar
	protected boolean geolocalizacion = false; //indica cuando se activa el boton de geolocalización

	private String linea; //linea de una estación de transporte público
	private String dirpunto; //dirección de un punto
	private String anterior = ""; //actividad de la que se viene

	private float zoom = 10; //zoom que incrementa o disminuye la visión que tengamos del mapa
	
	
	/**
	 * Crea la actividad
	 */
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		//cambiamos el color del ActionBar
		android.app.ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0033CD")));

		//definimos el NavigationDrawer
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
				    	//caso de buscar ciudades
				    	 i = new Intent(MainActivity.this, Listas.class);
				    	 i.putExtra("ciudad", ciudad);
					     startActivity(i);
					 
					     break;
				    case 1:
				    	//caso de buscar transportes
				    	buscaTransportes("Transportes");
				    	
					     break;
				    case 2:
				    	//caso de buscar paradas
				    	buscaTransportes("BusquedaParadas");
				        break;
				    case 3:
				    	//caso de buscar paradas cercanas a una ubicación
				    	i = new Intent(MainActivity.this, BusquedaCercana.class);
				    	 i.putExtra("ciudad", ciudad);
				    	 i.putExtra("pais", pais);
					     startActivity(i);
				    	break;
				    case 4:
				    	//caso de buscar horarios
				    	buscaTransportes("Horarios");
				    	break;
				    case 5:
				    	//caso de buscar las estaciones favoritas
				    	i = new Intent(MainActivity.this, Favoritos.class);
				    	 i.putExtra("ciudad", ciudad);
				    	 i.putExtra("pais", pais);
					     startActivity(i);
				    	break;
				    	
				    case 6:
				    	//caso de obtener información sobre la aplicación
				    	i = new Intent(MainActivity.this, SobreNosotros.class);
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
                getActionBar().setTitle("AppTransportesPublicos");
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }
 
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle("AppTransportesPublicos");
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };

        NavDrawerLayout.setDrawerListener(new DrawerListener());
        //Obtenemos los parametros que no envían actividades anteriores
        b = getIntent().getExtras();
        
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
				estructura.transporte = transporte;
				anterior = "busqueda";
				if (b.getString("descripcion") != null) {
					estructura.descripcion = b.getString("descripcion");
				}
				if (b.getString("direccion") != null) 
					estructura.direccion = b.getString("direccion");
					estructura.latitud = b.getDouble("latitud");
					estructura.longitud =b.getDouble("longitud");
				if (b.getString("paisTab") != null) 
					estructura.pais = b.getString("paisTab");
				if (b.getString("region") != null) 
					estructura.region = b.getString("region");
				if (b.getString("telefono") != null)
					estructura.telefono = b.getString("telefono");
				
			}
			else if (b.getString("Anterior").equals("Lineas")){
				
				ciudad = b.getString("ciudad");
				pais = b.getString("pais");
				transporte = b.getString("transporte");
				linea = b.getString("linea");
				
			}
			
			
		}
       
        //al no haber ningun item del navigation drawer se procede a obtener el mapa
       if (savedInstanceState == null) {
            selectItem(0);
        }
       
	}


	/**
	 * Obtiene el punto céntrico de una ciudad
	 */
	private void obtenerVistaCiudad() {
	// TODO Auto-generated method stub
		Geocoder g = new Geocoder(context);
		try {
			List<Address> fromLocationName = g.getFromLocationName(ciudad, 1);
			latitude = fromLocationName.get(0).getLatitude();
			longitude = fromLocationName.get(0).getLongitude();
			pais = fromLocationName.get(0).getCountryName();
			dirpunto = fromLocationName.get(0).getAddressLine(0);
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * Se crea el fragmento del mapa
	 */
	private void crearFragment() {
	// TODO Auto-generated method stub
		android.support.v4.app.Fragment fragment1 = new FragmentMapa();
		FragmentManager fragmentManager = getFragmentManager();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment1).commit();
	}


	/**
	 * Se crea el mapa y se pinta todo lo necesario en el.
	 * @param i 
	 */
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

	/**
	 * Se obtiene la localización de las estaciones de transporte y de la localización del dispositivo
	 */
	private void obtenerGeolocalizacion() {
		// TODO Auto-generated method stub
		//se obtiene la localización del servicio
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //si hay un servicio disponible se obtiene la localización exacta del dispositivo
        locListener = new LocationListener()
        {
          

			

			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				//localización exacta del dispositivo
				latitude = location.getLatitude();
				longitude = location.getLongitude();
				//Si se tiene activada la geolocalización se pone un punto en la ubicación donde se encuentra el dispositivo y se obtienen las paradas cercanas
				if (geolocalizacion == true) {
					Geocoder g = new Geocoder(context);
								
								try {
									List<Address> fromLocation= fromLocation = g.getFromLocation(latitude, longitude, 1);
									ciudad = fromLocation.get(0).getLocality();
									pais = fromLocation.get(0).getCountryName();
									dirpunto = fromLocation.get(0).getAddressLine(0);
									añadirPunto();
									buscaTransportes("direccion");
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								geolocalizacion = false;
				}
				else {
				
					Geocoder g = new Geocoder(context);
					
					
					try {
						//si la ciudad es nula, se obtiene mediante la longitud y latitud que se ha obtenido de la localización del dispositivo
						if (ciudad == null) {
							
								List<Address> fromLocation = g.getFromLocation(latitude, longitude, 1);
								ciudad = fromLocation.get(0).getLocality();
								pais = fromLocation.get(0).getCountryName();
								dirpunto = fromLocation.get(0).getAddressLine(0);
								buscaTransportes("direccion");
							
						}
						else {
							//se obtiene la vista de la ciudad
							obtenerVistaCiudad();
							//Si la actividad anterior nos proporciona el transporte
							if (transporte != null) {
								//y provenimos de la actividad búsqueda de paradas entonces mostramos la parada
								if (anterior.equals("busqueda")) {
									ep.add(estructura);
									latitude = estructura.latitud;
									longitude = estructura.longitud;
									zoom = 15;
									añadirParadas();
								}
								//si la linea no es nula, buscamos las paradas de esa linea
								else if (linea != null){
									
									buscaTransportes("linea");
								}
								//sino buscamos las paradas del transporte que se desea
								else {
									buscaTransportes(transporte);
								}
							}
							else {
								//si venimos de la actividad búsqueda de paradas cercanas, buscamos las paradas cercanas a la dirección
								if (direccion != null){
									
									
										List<Address> fromLocationName = g.getFromLocationName(direccion+", "+ciudad, 1);
										latitude = fromLocationName.get(0).getLatitude();
										longitude = fromLocationName.get(0).getLongitude();
										pais = fromLocationName.get(0).getCountryName();
										buscaTransportes("direccion");
								}
								//sino añadimos el punto en la latitud y longitud obtenida y obtenemos las paradas cercana a esa localización
								else  {
									añadirPunto();
									buscaTransportes("direccion");
								}
									
							}
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						//si la wifi no esta activa, se muestra el mensaje de que no se puede utilizar la aplicación
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
						
					}
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
        //si el proveedor gps esta activo cogemos la localización mediante el servicio gps y sino mediante el wifi
        if (locManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        	locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10,   locListener);
        else
            locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10,  locListener);
   
        
    }

	/**
	 * Se añade las paradas de los diferentes transportes público
	 */
	private void añadirParadas() {
		// TODO Auto-generated method stub
		int icono = 0;//variable que contiene el icono que se debe mostrar en un punto dependiendo del transporte que se pinte
		//se mueve la camara a la latitud y longitud deseada
		fragment.getMap().moveCamera(CameraUpdateFactory.newLatLngZoom((new LatLng(latitude, longitude)), zoom));
		fragment.getMap().setMyLocationEnabled(true);
		//añadimos los aparcamientos
		if (transporte.equals("Aparcamiento")) {

			icono = R.drawable.icono_parking;
			fragment.getMap().setMyLocationEnabled(true);

			for (int i = 0; i < apar.size(); i++) {
				Aparcamiento a = apar.get(i);
				double la = a.latitud;
				double lo = a.longitud;
				if(a.direccion == null || a.direccion == "") {
					Geocoder g = new Geocoder(context);
	
					try {
						
						List<Address> fromLocationName = g.getFromLocation(la, lo, 1);
						if (!fromLocationName.isEmpty()) {
							a.direccion = fromLocationName.get(0).getAddressLine(0);
						
							a.pais = fromLocationName.get(0).getCountryName();
						}
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				LatLng l = new LatLng(la, lo);
				String acces = "No";
				if(a.accesibilidad == 1) acces = "Si";
				String pt = String.valueOf(a.plazasTotales);
				if (pt.equals("0")) pt = "-";
				String pl = String.valueOf(a.plazasLibres);
				if (pl.equals("0")) pl = "-";
				//Se añade el punto
				fragment.getMap().addMarker(new MarkerOptions().position(l)
						.title(a.direccion).snippet("Transporte: Aparcamiento Telefono: "+a.telefono+" Descripcion: "+
								a.descripcion+" Latitud: "+ la+" Longitud: "+ lo+" Accesibilidad: "
								+acces+", Plazas totales: "+pt+", Plazas libres: "
								+pl).alpha(1F).anchor(0.2F,0.2F)
						.icon(BitmapDescriptorFactory.fromResource(icono)));
				//Configuramos la información que se muestra en el popup

			}
			
				
			
		}
		//Se añade las bicicletas
		else if (transporte.equals("Bicicletas")) {
			icono = R.drawable.icono_bici;
			for (int i = 0; i < bici.size(); i++) {
				Bicicletas a = bici.get(i);
				double la = a.latitud;
				double lo = a.longitud;
				if(a.direccion == null || a.direccion == "") {
					Geocoder g = new Geocoder(context);
	
					try {
						
						List<Address> fromLocationName = g.getFromLocation(la, lo, 1);
						if (!fromLocationName.isEmpty()) {
							a.direccion = fromLocationName.get(0).getAddressLine(0);
						
							a.pais = fromLocationName.get(0).getCountryName();
						}
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				LatLng l = new LatLng(la, lo);
				String an = String.valueOf(a.anclajes);
				String bl = String.valueOf(a.biciLibres);
				if (an.equals("0")&& bl.equals("0")) {
					an = "-";
					bl = "-";
				}

				fragment.getMap().addMarker(new MarkerOptions().position(l)
						.title(a.direccion).snippet("Transporte: Bicicletas Telefono: "+a.telefono+" Descripcion: "+
				a.descripcion+" Latitud: "+ la+" Longitud: "+ lo+
				" Nº Anclajes: "+an+", Plazas libres: "
								+bl).alpha(1F).anchor(0.2F,0.2F)
						.icon(BitmapDescriptorFactory.fromResource(icono)));
				

			}
			
		}
		//Se añaden los demás transporte eligiendo el icono que toca dependiendo del transporte
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
				if(a.direccion == null || a.direccion == "") {
					Geocoder g = new Geocoder(context);
					try {
						
						List<Address> fromLocationName = g.getFromLocation(la, lo, 1);
						
						if (!fromLocationName.isEmpty()) {
							a.direccion = fromLocationName.get(0).getAddressLine(0);
						
							a.pais = fromLocationName.get(0).getCountryName();
						}
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				LatLng l = new LatLng(la, lo);
				String lin = "";
				for (int k = 0; k < a.lineas.size(); k++) {
					if (k != 0) lin += ", ";
					if (!a.lineas.equals(""))
						lin += a.lineas.get(k);
				}
				fragment.getMap().addMarker(new MarkerOptions().position(l)
						.title(a.direccion).snippet("Transporte: "+a.transporte+" Telefono: "+a.telefono+" Descripcion: "+a.descripcion+" Latitud: "+ 
				la+" Longitud: "+ lo + " Lineas: "+lin).alpha(1F).anchor(0.2F,0.2F)
						.icon(BitmapDescriptorFactory.fromResource(icono)));
				

				
			}
			
		}
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
				if(marker.getSnippet() != null) {
					String trans = marker.getSnippet().substring(12,marker.getSnippet().indexOf("Telefono"));
		        	Double lat = Double.valueOf(marker.getSnippet().substring(marker.getSnippet().indexOf("Latitud")+9,marker.getSnippet().indexOf("Longitud")));
					Log.d("sniper", marker.getSnippet());
		        	Double lon;
					if (trans.contains("Aparcamiento")) {
						lon = Double.valueOf(marker.getSnippet().substring(marker.getSnippet().indexOf("Longitud")+10,marker.getSnippet().indexOf("Accesibilidad")));
						tv=(TextView)popup.findViewById(R.id.snippet);
						tv.setText(marker.getSnippet().substring(marker.getSnippet().indexOf("Accesibilidad"), marker.getSnippet().lastIndexOf(",")+1));
						tv=(TextView)popup.findViewById(R.id.snippet1);
						tv.setText(marker.getSnippet().substring(marker.getSnippet().lastIndexOf(",")+1));
					}
					else if (trans.contains("Bicicletas")) {
						lon = Double.valueOf(marker.getSnippet().substring(marker.getSnippet().indexOf("Longitud")+10,marker.getSnippet().indexOf("Nº")));
						tv=(TextView)popup.findViewById(R.id.snippet);
						tv.setText(marker.getSnippet().substring(marker.getSnippet().indexOf("Nº"), marker.getSnippet().lastIndexOf(",")+1));
						tv=(TextView)popup.findViewById(R.id.snippet1);
						tv.setText(marker.getSnippet().substring(marker.getSnippet().lastIndexOf(",")+1));
					}
					else {
						tv.setText(marker.getSnippet().substring(marker.getSnippet().indexOf("Lineas")+8));
						lon = Double.valueOf(marker.getSnippet().substring(marker.getSnippet().indexOf("Longitud")+10,marker.getSnippet().indexOf("Linea")));
						
						
						
					}
					
//						
					rb = (RatingBar) popup.findViewById(R.id.ratingBar1);
					rb.setEnabled(true);
					
					BaseDeDatos bd =
				            new BaseDeDatos(getApplicationContext(), "DBEstacion", null, 1);
				 
				        SQLiteDatabase db = bd.getWritableDatabase();
				        //Si hemos abierto correctamente la base de datos
				        if(db != null)
				        {
				        	Cursor cu = db.rawQuery("SELECT * FROM Estacion e WHERE e.latitud = "+lat+" and e.longitud = "+lon, null);
				            if (cu.moveToFirst()) {
				            		rb.setRating(cu.getInt(4));
				            		Log.d("",cu.getInt(4)+ " "+cu.getString(6));

				            }
				        
				           cu.close();
				            //Cerramos la base de datos
				            db.close();
				        }
				}
				return(popup);
			}
		});
		//Al hacer click en el popup se dirige el flujo de la aplicación a la actividad que muestra toda la información de una estación
		fragment.getMap().setOnInfoWindowClickListener(new OnInfoWindowClickListener() {          
	        public void onInfoWindowClick(Marker marker) {
	        	Intent i = new Intent(MainActivity.this,Info.class);
	        	String direccion = marker.getTitle();
	        	if(marker.getSnippet() != null) {
					String trans = marker.getSnippet().substring(12,marker.getSnippet().indexOf("Telefono"));
		        	
					String telefono = marker.getSnippet().substring(marker.getSnippet().indexOf("Telefono")+9,marker.getSnippet().indexOf("Descripcion"));
		        	
		        	Double lat = Double.valueOf(marker.getSnippet().substring(marker.getSnippet().indexOf("Latitud")+9,marker.getSnippet().indexOf("Longitud")));
					
					i.putExtra("transporte", trans);
					i.putExtra("ciudad", ciudad);
					i.putExtra("pais", pais);
					if (telefono != null)
						i.putExtra("telefono", telefono);
					else i.putExtra("telefono", "-1");
		            i.putExtra("direccion", direccion);
		            i.putExtra("latitud", lat);
	
					if (trans.contains("Aparcamiento")) {
						Double lon = Double.valueOf(marker.getSnippet().substring(marker.getSnippet().indexOf("Longitud")+10,marker.getSnippet().indexOf("Accesibilidad")));
						i.putExtra("longitud", lon);
						String acces = marker.getSnippet().substring(marker.getSnippet().indexOf("Accesibilidad")+15, marker.getSnippet().indexOf(", Plazas totales"));
						String total = marker.getSnippet().substring(marker.getSnippet().indexOf("totales")+9, marker.getSnippet().indexOf(", Plazas libres"));
			            String libres = marker.getSnippet().substring(marker.getSnippet().indexOf("libres")+8);
		            
			            i.putExtra("acces", acces);
			            i.putExtra("total", total);
			            i.putExtra("libres", libres);
					}
					else if (trans.contains("Bicicletas")) {
						Double lon = Double.valueOf(marker.getSnippet().substring(marker.getSnippet().indexOf("Longitud")+10,marker.getSnippet().indexOf("Nº")));
						i.putExtra("longitud", lon);
						String anclajes = marker.getSnippet().substring(marker.getSnippet().indexOf("Anclajes")+10, marker.getSnippet().indexOf(", Plazas libres"));
			            String libres = marker.getSnippet().substring(marker.getSnippet().indexOf("libres")+8);
		            
			            i.putExtra("anclajes", anclajes);
			            i.putExtra("bicis", libres);
					}
					else {
						Double lon = Double.valueOf(marker.getSnippet().substring(marker.getSnippet().indexOf("Longitud")+10,marker.getSnippet().indexOf("Linea")));
						i.putExtra("longitud", lon);
						String lin = marker.getSnippet().substring(marker.getSnippet().indexOf("Linea")+8);
						i.putExtra("lineas", lin);
					}
		            startActivity(i);
	        	}
	        }
	    });
		
		fragment.getMap().moveCamera(CameraUpdateFactory.newLatLngZoom((new LatLng(latitude, longitude)),zoom));
	}



	/**
	 * Añadimos un punto que no tiene porque ser la ubicación de una estación
	 */
	private void añadirPunto() {
		if(fragment.getMap() != null) {

			fragment.getMap().setMyLocationEnabled(true);
			fragment.getMap().moveCamera(CameraUpdateFactory.newLatLngZoom((new LatLng(latitude, longitude)), 15));

			fragment.getMap().addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(dirpunto));
		}
	}
	
/**
 * Clase que gestiona el Drawer Navigation
 *
 */
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

			default:
				return super.onOptionsItemSelected(item);
		
		}
	}




/**
 * Clase que contiene y crea el fragmento del mapa
 *
 */
public  class FragmentMapa extends Fragment {
	private View rootView;
	
	public FragmentMapa() {}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_fragment_mapa, container, false);
        //creamos el fragmento
		fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		//si se hace click en el botón de obtener mi localización, obtenemos la ubicación del dispositivo
		fragment.getMap().setOnMyLocationButtonClickListener(new OnMyLocationButtonClickListener() {
			
			@Override
			public boolean onMyLocationButtonClick() {
				// TODO Auto-generated method stub
				
				latitude = fragment.getMap().getMyLocation().getLatitude();
				longitude = fragment.getMap().getMyLocation().getLongitude();
				geolocalizacion = true;
				obtenerGeolocalizacion();
				return true;
			}
		});
        return rootView;
    }
	


}

	/**
	 * Busca las estaciones de transporte en el servidor dependiendo de lo que se desea buscar en cada momento
	 * @param nom: indica el nombre de la información a la que se desea acceder
	 */
	private void buscaTransportes(String nom) {
		// TODO Auto-generated method stub
		ProgressDialog progress = new ProgressDialog(this);
		progress.setMessage("Buscando, por favor espere...");
		new LoadParadaTask(progress, nom).execute();
	
	}
	
	/**
	 * Clase que realiza todas las operaciones de llamada al servicio web
	 *
	 */
private class LoadParadaTask extends AsyncTask<Void, Void, String> {
		
		ProgressDialog progress; //Barra de progreso
		String nom;
		

		public LoadParadaTask(ProgressDialog progress, String nom) {
			// TODO Auto-generated constructor stub
			this.progress = progress;
			 this.nom = nom;
		}


		/**
		 * Método que se ejecuta antes de realizar las llamadas a servidor. En 
		 * este caso se muestra una barra de progreso mientras se realiza la llamada
		 */
		public void onPreExecute() {
			if (!isFinishing()){
				progress.show();
			}
		}

		/**
		 * Se realiza la conexión con el servidor
		 */
		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
				//iniciamos la conexión cliente
				HttpClient cliente = new DefaultHttpClient();
				//declaramos la url a la que acceder
				String url = "";
				if (nom.equals("Aparcamiento")) {
					url = constantes.aparcamiento+"ciudad="+ciudad+"&pais="+pais;
					zoom = 12;
				}
				else if (nom.equals("Bicicletas")) {
					url = constantes.bicicleta+"ciudad="+ciudad+"&pais="+pais;
					zoom = 12;
				}
				else if (nom.equals("Taxi")) {
					url = constantes.taxi+"ciudad="+ciudad+"&pais="+pais;
					zoom = 12;
				}
				else if (nom.equals("direccion")) { 
//					if(direccion != null)
//						url = constantes.cercanas+"ciudad="+ciudad+"&pais="
//							+pais+"&direccion="+espacio(direccion);
//					else 
						url = constantes.cercanas+"ciudad="+ciudad+"&pais="
								+pais+"&latitud="+latitude+"&longitud="+longitude;
						zoom = 16;
				}
				else if (nom.equals("linea")) {
					url = constantes.paradas+"ciudad="+ciudad+"&pais="+pais+"&transporte="+transporte+"&linea="+espacio(linea);
					zoom = 12;
				}
				else if (nom.equals("Horarios")) 
					url = constantes.horario+"ciudad="+ciudad+"&pais="
							+pais;
				else 
					url = constantes.transportes+ciudad;
			
				//Se declara una petición get
					HttpGet peticion = new HttpGet(url);
					// ejecuta una petición get
					 InputStream is = null;
					 String result = "";
					try {
						//Se conecta con el servidor para obtener los datos deseados
						HttpResponse respuesta = cliente.execute(peticion);
						int status = respuesta.getStatusLine().getStatusCode();
						if (status == 0) {
							result = "No ha conectado con el servidor";
						}
						else {
							is = respuesta.getEntity().getContent();
							if (is != null) {
								//Se pone la respuesta obtenida en un string
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
				
				

			
		}


		/**
		 * Método que procesa los datos
		 * @param response: String que contiene los datos que ha devuelto el servidor
		 */
		protected void onPostExecute(String response) {
			try {
				//paramos la barra de progreso
				progress.cancel();
				progress.dismiss();
				
				//Si el servidor no esta conectado o no se puede acceder se muestra un mensaje de error
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
					//Pasamos los datos a un objeto JSON
					JSONObject json = new JSONObject(response);
					//Obtenemos la estaciones
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
								else if (!a.transporte.equals("Taxi")) {
									JSONArray lin = j.getJSONArray("lineas");
									for (int k = 0; k < lin.length(); k++){
										if (!lin.getString(k).isEmpty())
											a.lineas.add(lin.getString(k));
									}

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
							a.transporte = "Aparcamiento";
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
							a.transporte = "Taxi";
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
	
	/**
	 * Convierte un stream en string
	 * @param is: Contiene los datos que se han obtenido del servidor
	 * @return String que contiene los datos transformados
	 */
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
	
	/**
	 * Cambia los espacios por %20 para que la url este bien formada
	 * @param np: string al que se debe realizar el cambio
	 * @return nuevo string con los espacios cambiados por %20
	 */
	private String espacio(String np) {
		// TODO Auto-generated method stub
		String res = np.replaceAll(" ", "%20");

		return res;
	}
	
	
	
}

