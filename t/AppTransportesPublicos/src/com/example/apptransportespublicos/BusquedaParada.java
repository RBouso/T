package com.example.apptransportespublicos;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBarActivity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class BusquedaParada extends FragmentActivity {

	private FragmentTabHost tabHost;
	private boolean bus = false;
	private boolean metro = false;
	private boolean tran = false;
	private boolean ferro = false;
	private boolean funi = false;
	private boolean tele = false;
	private boolean taxi = false;
	private boolean park = false;
	private boolean bici = false;
	
	public final static String URL = "http://192.168.1.130:8084/ServidorTransportes/Transportes?wsdl";
	public static final String NAMESPACE = "http://sTransportes";
	public static final String SOAP_ACTION_PREFIX = "/";
	 private static final String METHOD = "getTransportes";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_busqueda_parada);
		android.app.ActionBar bar = getActionBar();
		//for color
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0033CD")));
		
		tabHost= (FragmentTabHost) findViewById(android.R.id.tabhost);
		tabHost.setup(this,
		getSupportFragmentManager(),android.R.id.tabcontent);
		buscaTransportes();
		if (bus)
			tabHost.addTab(tabHost.newTabSpec("Bus").setIndicator("Bus"),
			Tab.class, null);
		if (metro)
			tabHost.addTab(tabHost.newTabSpec("Metro").setIndicator("Metro"),
			Metro.class, null);
		if (tran)
			tabHost.addTab(tabHost.newTabSpec("Tranvia").setIndicator("Tranvia"),
			Tran.class, null);
		if (ferro)
			tabHost.addTab(tabHost.newTabSpec("Ferrocarriles").setIndicator("Ferrocarriles"),
			Ferro.class, null);
		if (funi)
			tabHost.addTab(tabHost.newTabSpec("Funicular").setIndicator("Funicular"),
			Funicular.class, null);
		if (tele)
			tabHost.addTab(tabHost.newTabSpec("Teleferico").setIndicator("Teleferico"),
			Teleferico.class, null);
		if (taxi)
			tabHost.addTab(tabHost.newTabSpec("Taxi").setIndicator("Taxi"),
			Taxi.class, null);
		if (park)
			tabHost.addTab(tabHost.newTabSpec("Aparcamiento").setIndicator("Aparcamiento"),
			Aparcamiento.class, null);
		if (bici)
			tabHost.addTab(tabHost.newTabSpec("Bicicleta").setIndicator("Bicicletas"),
			Bicicletas.class, null);

		}
	
	private void buscaTransportes() {
		// TODO Auto-generated method stub
		AsyncTaskRunner runner = new AsyncTaskRunner();
		  runner.execute();
		bus = true;
		metro = true;
	
	}
	
	private class AsyncTaskRunner extends AsyncTask<String, String, String>        {

		   private String resp;
		   @Override
		   protected String doInBackground(String... params) {
		     publishProgress("Loading contents..."); // Calls onProgressUpdate()
		     try {
		       // SoapEnvelop.VER11 is SOAP Version 1.1 constant
		       SoapSerializationEnvelope envelope = new 
		    		   SoapSerializationEnvelope(SoapEnvelope.VER11) ; 
		              SoapObject request = new SoapObject(NAMESPACE, METHOD);
		       //bodyOut is the body object to be sent out with this envelope
		       envelope.bodyOut = request;
		       
		       HttpTransportSE transport = new HttpTransportSE(URL);
		       try {
		         transport.call(NAMESPACE + SOAP_ACTION_PREFIX + METHOD, envelope);
		       } catch (IOException e) {
		         e.printStackTrace();
		       } catch (XmlPullParserException e) {
		         e.printStackTrace();
		     }
		   //bodyIn is the body object received with this envelope
		   if (envelope.bodyIn != null) {
		     //getProperty() Returns a specific property at a certain index.
		     SoapPrimitive resultSOAP = (SoapPrimitive) ((SoapObject) envelope.bodyIn).getProperty(0);
		     resp=resultSOAP.toString();
		   }
		 } catch (Exception e) {
		   e.printStackTrace();
		   resp = e.getMessage();
		 }
		 return resp;
		      }

		  /**
		   * 
		   * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		   */
		  @Override
		  protected void onPostExecute(String result) {
		 // execution of result of Long time consuming operation
		 // In this example it is the return value from the web service
		 Log.d("salidas",result);
		  }

		  /**
		   * 
		   * @see android.os.AsyncTask#onPreExecute()
		   */
		  @Override
		  protected void onPreExecute() {
		 // Things to be done before execution of long running operation. For
		 // example showing ProgessDialog
		  }

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
