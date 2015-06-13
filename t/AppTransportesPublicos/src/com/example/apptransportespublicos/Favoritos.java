package com.example.apptransportespublicos;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

public class Favoritos extends FragmentActivity{


	private FragmentTabHost tabHost;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_busqueda_parada);
		android.app.ActionBar bar = getActionBar();
		//for color
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0033CD")));
		Bundle b = getIntent().getExtras();
		
		
		tabHost= (FragmentTabHost) findViewById(android.R.id.tabhost);
		tabHost.setup(this,
		getSupportFragmentManager(),android.R.id.tabcontent);
	
		tabHost.addTab(tabHost.newTabSpec("Estaciones").setIndicator("Estaciones"),
			TabFavoritos.class, b);
		tabHost.addTab(tabHost.newTabSpec("Rutas").setIndicator("Rutas"),
			TabFavoritos.class, b);
		}
}
