package com.example.apptransportespublicos;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Paradas  extends ActionBarActivity {
	ListView lista;
	private ArrayAdapter<String> adapt;
	private ArrayList<String> list;
	private String ciudad;
	private String transporte;
	private String linea;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listas);
		android.app.ActionBar bar = getActionBar();
		//for color
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0033CD")));
		lista = (ListView)findViewById(R.id.listView1);
		list = new ArrayList<String>();
		
		Bundle b = getIntent().getExtras();
		ciudad = b.getString("ciudad");
		transporte = b.getString("transporte");
		linea = b.getString("linea");
		String [] valores = {"Navas", "La sagrera", "Torras i Bages"};
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
	                Intent i = new Intent(Paradas.this, MainActivity.class);
	                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                i.putExtra("ciudad", ciudad);
	                i.putExtra("transporte", transporte);
	                i.putExtra("parada", itemValue);
	                startActivity(i);
	                finish();
	                
            	
              }

         }); 
	}

}
