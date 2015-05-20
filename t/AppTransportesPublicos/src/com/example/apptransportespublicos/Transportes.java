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
import android.support.v7.app.ActionBarActivity;

import android.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Transportes extends Activity implements OnQueryTextListener {
	ListView lista;
	private ArrayAdapter<String> adapt;
	private ArrayList<String> transportes;
	private String ciudad;
	private String pais;
	
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
		String[] valores = new String[transportes.size()];
		valores = transportes.toArray(valores);
		Arrays.sort(valores);
		adapt = new ArrayAdapter<String>(this,
	              R.layout.activity_nombre_listas, R.id.nom, valores) ;
		lista.setAdapter(adapt);
		
		
		  // ListView Item Click Listener
        lista.setOnItemClickListener(new OnItemClickListener() {

              @Override
              public void onItemClick(AdapterView<?> parent, View view,
                 int position, long id) {
            	  
            	  
	               // ListView Clicked item index
	               int itemPosition     = position;
	               
	               // ListView Clicked item value
	               String  itemValue    = (String) lista.getItemAtPosition(position);
	                 
	               // Show Alert 
	                Toast.makeText(getApplicationContext(),
	                  "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
	                  .show();
	               
	               if (itemValue.equals("Aparcamiento") || itemValue.equals("Bicicletas")
	            		   || itemValue.equals("Taxi")) {
	            	   Intent i = new Intent(Transportes.this,MainActivity.class);
	            	   
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
//		Toast.makeText(getApplicationContext(), "estoy en el menu", Toast.LENGTH_LONG).show();
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
