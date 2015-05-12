package com.example.apptransportespublicos;



import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Listas extends ActionBarActivity {

	ListView lista;
	private ArrayAdapter<String> adapt;
	private ArrayList<String> list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listas);
		android.app.ActionBar bar = getActionBar();
		//for color
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0033CD")));
		lista = (ListView)findViewById(R.id.listView1);
		list = new ArrayList<String>();
		String [] valores = {"Barcelona", "Madrid", "Bilbao"};
		Arrays.sort(valores);
		Bundle b = getIntent().getExtras();
		String ciudad = b.getString("ciudad");
		final int pos = buscarCiudad(ciudad, valores);
		
		adapt = new ArrayAdapter<String>(this,
	              R.layout.activity_nombre_listas, R.id.nom, valores) ;
		lista.setAdapter(adapt);
	
		
		  // ListView Item Click Listener
        lista.setOnItemClickListener(new OnItemClickListener() {

              @Override
              public void onItemClick(AdapterView<?> parent, View view,
                 int position, long id) {
            	  
            	  if (pos != -1) {
  					
  					parent.getChildAt(pos).setEnabled(false);
  					Toast.makeText(getApplicationContext(),
  			                  "Position :"+pos+"  ListItem : " +lista.getChildAt(pos).isEnabled(), Toast.LENGTH_LONG)
  			                  .show();
  				}
                
            	if (pos != position) {
	               // ListView Clicked item index
	               int itemPosition     = position;
	               
	               // ListView Clicked item value
	               String  itemValue    = (String) lista.getItemAtPosition(position);
	                  
	               
	                // Show Alert 
	                Toast.makeText(getApplicationContext(),
	                  "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
	                  .show();
	                Intent i = new Intent(Listas.this, MainActivity.class);
	                i.putExtra("ciudad", itemValue);
	                i.putExtra("Anterior", "ciudades");
	                startActivity(i);
	                finish();
            	}
              }

         }); 
	}
	
	private int buscarCiudad(String ciudad, String[] valores) {
		// TODO Auto-generated method stub
		int pos = -1;
		Toast.makeText(getApplicationContext(),
                "Position :"+ciudad , Toast.LENGTH_LONG)
                .show();
		for (int i = 0; i < valores.length && pos == -1; i++) 
			if(valores[i].equals(ciudad))
				pos = i;
		
		return pos;
	}
	

	

	
	
}
