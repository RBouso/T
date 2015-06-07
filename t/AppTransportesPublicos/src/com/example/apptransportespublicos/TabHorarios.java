package com.example.apptransportespublicos;

import java.util.ArrayList;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.MenuItem;

public class TabHorarios extends FragmentActivity{
	private FragmentTabHost tabHost;

	private ArrayList<String> transportes = new ArrayList<String>();
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_busqueda_parada);
		android.app.ActionBar bar = getActionBar();
		//for color
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0033CD")));
		Bundle b = getIntent().getExtras();
		transportes = b.getStringArrayList("transportes");
		
		tabHost= (FragmentTabHost) findViewById(android.R.id.tabhost);
		tabHost.setup(this,
		getSupportFragmentManager(),android.R.id.tabcontent);
		
	
		if (transportes.contains("Autobus")) {
			tabHost.addTab(tabHost.newTabSpec("Autobus").setIndicator("Autobus"),
			Horario.class, b);
			
		}
		if (transportes.contains("Metro")) {
			tabHost.addTab(tabHost.newTabSpec("Metro").setIndicator("Metro"),
					Horario.class, b);
		}
		if (transportes.contains("Tranvia"))
			tabHost.addTab(tabHost.newTabSpec("Tranvia").setIndicator("Tranvia"),
					Horario.class, b);
		if (transportes.contains("Ferrocarriles"))
			tabHost.addTab(tabHost.newTabSpec("Ferrocarriles").setIndicator("Ferrocarriles"),
					Horario.class, b);
		if (transportes.contains("Funicular"))
			tabHost.addTab(tabHost.newTabSpec("Funicular").setIndicator("Funicular"),
					Horario.class, b);
		if (transportes.contains("Teleferico"))
			tabHost.addTab(tabHost.newTabSpec("Teleferico").setIndicator("Teleferico"),
					Horario.class, b);
	
		}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.busqueda_parada, menu);
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
	
}
