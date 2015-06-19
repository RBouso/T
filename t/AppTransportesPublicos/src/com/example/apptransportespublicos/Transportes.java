package com.example.apptransportespublicos;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.app.SearchManager;
import android.widget.SearchView;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.widget.SearchView.OnQueryTextListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Transportes extends Activity implements OnQueryTextListener {
	ListView lista;//listview que contiene la lista de transportes
	private ArrayAdapter<String> adapt; //adaptador de listas de string
	private ArrayList<String> transportes;//lista de transportes 
	private String ciudad;//nombre de la ciudad
	private String pais;//nombre del país
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listas);
		android.app.ActionBar bar = getActionBar();

		//for color
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0033CD")));
		lista = (ListView)findViewById(R.id.listView1);
		transportes = new ArrayList<String>();
		
		Bundle b = getIntent().getExtras();
		ciudad = b.getString("ciudad");
		pais = b.getString("pais");

		transportes = b.getStringArrayList("transportes");
		//ordena y añade la lista de transportes al listview
		String[] valores = new String[transportes.size()];
		valores = transportes.toArray(valores);
		Arrays.sort(valores);
		adapt = new ArrayAdapter<String>(this,
	              R.layout.activity_nombre_listas, R.id.nom, valores) ;
		lista.setAdapter(adapt);
		
		
		  //Si se clica en uno de los transportes, en el caso de que sea aparcamiento, taxi o
		//bicicleta se regresa a la actividad principal para mostrar todas las estaciones. Por
		//el contrario, si es cualquier otro transporte se abre una nueva actividad mostrando 
		//todas las líneas que contiene ese transporte.
        lista.setOnItemClickListener(new OnItemClickListener() {

              @Override
              public void onItemClick(AdapterView<?> parent, View view,
                 int position, long id) {
            	  
            	  
	               // ListView Clicked item index
	               int itemPosition     = position;
	               
	               // ListView Clicked item value
	               String  itemValue    = (String) lista.getItemAtPosition(position);
	              
	               
	               if (itemValue.equals("Aparcamiento") || itemValue.equals("Bicicletas")
	            		   || itemValue.equals("Taxi")) {
	            	   Intent i = new Intent(Transportes.this,MainActivity.class);
	            	   i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            	   i.putExtra("Anterior", "transportes");
	            	   i.putExtra("ciudad", ciudad);
	            	   i.putExtra("pais", pais);
	            	   i.putExtra("transporte", itemValue);
	            	   startActivity(i);
	            	   finish();
	               }
	               
	               else {
		                Intent i = new Intent(Transportes.this, Lineas.class);
		                i.putExtra("ciudad", ciudad);
		                i.putExtra("transporte", itemValue);
		                
		                i.putExtra("pais", pais);
		                startActivity(i);
	               } 
            	
              }

         }); 
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.

		getMenuInflater().inflate(R.menu.listas, menu);
		SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
		MenuItem searchMenuItem = menu.findItem(R.id.menu_busqueda_list);
		SearchView searchView = (SearchView) searchMenuItem.getActionView();
		
		searchView.setSearchableInfo(searchManager.
		                getSearchableInfo(getComponentName()));
		searchView.setOnQueryTextListener(this);
	
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		switch (id) {
			case( R.id.action_settings):
				return true;
			case (R.id.menu_busqueda_list):
				
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}


	@Override
	public boolean onQueryTextChange(String arg0) {
		// TODO Auto-generated method stub
		 Transportes.this.adapt.getFilter().filter(arg0);
		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		Transportes.this.adapt.getFilter().filter(arg0);
		return true;
	}

}
