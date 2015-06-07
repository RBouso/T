package com.example.apptransportespublicos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;


import com.example.conexion.constantes;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class BusquedaParada extends FragmentActivity {

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
			Tab.class, b);
			
		}
		if (transportes.contains("Metro")) {
			tabHost.addTab(tabHost.newTabSpec("Metro").setIndicator("Metro"),
			Tab.class, b);
		}
		if (transportes.contains("Tranvia"))
			tabHost.addTab(tabHost.newTabSpec("Tranvia").setIndicator("Tranvia"),
			Tab.class, b);
		if (transportes.contains("Ferrocarriles"))
			tabHost.addTab(tabHost.newTabSpec("Ferrocarriles").setIndicator("Ferrocarriles"),
			Tab.class, b);
		if (transportes.contains("Funicular"))
			tabHost.addTab(tabHost.newTabSpec("Funicular").setIndicator("Funicular"),
			Tab.class, b);
		if (transportes.contains("Teleferico"))
			tabHost.addTab(tabHost.newTabSpec("Teleferico").setIndicator("Teleferico"),
			Tab.class, b);
	
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
