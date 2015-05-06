package com.example.apptransportespublicos;

import java.util.ArrayList;
import java.util.List;
import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

import android.support.v4.app.ActionBarDrawerToggle;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
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
//	private NavigationAdapter NavAdapter;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
		Log.d("onclick", " stOnItem "+ NavList.callOnClick()+" "+NavList.getChoiceMode()
				);

		NavList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

		        

				Log.d("onclick", position+ " entor");
//				 Toast.makeText(getApplicationContext(), "holaaaaaaaa", Toast.LENGTH_SHORT).show();
				 Intent i;
				 switch (position) {
				    case 0:
				        break;
				    case 1:
				    	 i = new Intent(MainActivity.this, Listas.class);
					     startActivity(i);
					     break;
				    case 2:
				    	i = new Intent(MainActivity.this, BusquedaParada.class);
					     startActivity(i);
					     break;
				    case 3:
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


	private void selectItem(int i) {
	// TODO Auto-generated method stub
		if (i == 0){
			android.app.Fragment fragment1 = new FragmentMapa();
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.content_frame, fragment1).commit();
			
		}
        // update selected item and title, then close the drawer
        NavList.setItemChecked(i, true);

        NavDrawerLayout.closeDrawer(NavList);
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


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_fragment_mapa, container, false);
        fragment = new SupportMapFragment();
//      getSupportFragmentManager().beginTransaction()
//              .add(android.R.id.content, fragment).commit();
//        obtenerGeolocalizacion();
        return rootView;
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
				añadirPunto();
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

}
}

