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

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;


public class Tab extends Fragment{

	View v;
	private Button boton;
	private String list1;
	private String list2;
	private Spinner spinner1;
	private Spinner spinner2;
	private String transporte;
	private String ciudad;
	private String pais;
	private String linea;
	private List<String> list = new ArrayList<String>();
	private List<String> paradas = new ArrayList<String>();
	private List<EstructuraPublica> estaciones = new ArrayList<EstructuraPublica>();
	
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,

	        Bundle savedInstanceState) {
	       
		 v = inflater.inflate(R.layout.activity_tab, container, false);
		 transporte = this.getTag();
		 ciudad = this.getArguments().get("ciudad").toString();
		 pais = this.getArguments().get("pais").toString();
//		 Spinner s1  = (Spinner) v.findViewById(R.id.spinnerLineas);
		 addItems("Spinner1");

		 addListenerOnSpinnerItemSelection();
		 boton = (Button) v.findViewById(R.id.buttonBusqueda);
		 boton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				list1 = spinner1.getSelectedItem().toString();
				list2 = spinner2.getSelectedItem().toString();
				EstructuraPublica ep = buscarEstacion();
				Intent i = new Intent(getActivity(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("ciudad", ciudad);
                i.putExtra("Anterior", "busqueda");
                i.putExtra("transporte", transporte);
                if (ep != null) {
                	
	                i.putExtra("descripcion", ep.descripcion);
	                i.putExtra("direccion", ep.direccion);
	                i.putExtra("latitud", ep.latitud);
	                i.putExtra("longitud", ep.longitud);
	                i.putExtra("paisTab", ep.pais);
	                i.putExtra("region", ep.region);
	                i.putExtra("telefono", ep.telefono);
                }
                startActivity(i);
                try {
					this.finalize();
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			private EstructuraPublica buscarEstacion() {
				// TODO Auto-generated method stub
				EstructuraPublica ep = new EstructuraPublica();
				for (int i = 0; i < estaciones.size(); i++) {
					if(estaciones.get(i).descripcion.equals(list2)) {
						return estaciones.get(i);
					}
				}
				return ep;
			}
		});
		 return v;
	 }

	private void addItems(String selec) {
		// TODO Auto-generated method stub
		ProgressDialog progress = new ProgressDialog(v.getContext());
		progress.setMessage("Buscando, por favor espere...");
		new LoadParadaTask(progress, selec).execute();
	}

	private void addListenerOnSpinnerItemSelection() {
		// TODO Auto-generated method stub
		spinner1 = (Spinner) v.findViewById(R.id.spinnerLineas);
		spinner2 = (Spinner) v.findViewById(R.id.spinnerParadas);
		spinner1.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(parent.getContext(), 
						"OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
						Toast.LENGTH_SHORT).show();
				linea = parent.getItemAtPosition(position).toString();
				addItems("Spinner2");
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
		});
		spinner2.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}


	private void addItemsOnSpinner1() {
		// TODO Auto-generated method stub
		Spinner s2 = (Spinner) v.findViewById(R.id.spinnerLineas);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
			android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s2.setAdapter(dataAdapter);
	}

	// add items into spinner dynamically
	  public void addItemsOnSpinner2() {
	 
		Spinner s2 = (Spinner) v.findViewById(R.id.spinnerParadas);
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
			android.R.layout.simple_spinner_item, paradas);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s2.setAdapter(dataAdapter);
	  }
	  
	  private class LoadParadaTask extends AsyncTask<Void, Void, String> {
			
			private ProgressDialog progress;
			private String selec;

			
			public LoadParadaTask(ProgressDialog progress, String selec) {
				// TODO Auto-generated constructor stub
				this.progress = progress;
				this.selec = selec;
			}


			public void onPreExecute() {
				progress.show();
				
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
					
					HttpClient cliente = new DefaultHttpClient();
					String url;
					if (selec.equals("Spinner1"))url = constantes.lineas+"ciudad="+ciudad+"&pais="+pais+"&transporte="+transporte;
					else url = constantes.paradas+"ciudad="+ciudad+"&pais="+pais+"&transporte="+transporte+"&linea="+espacio(linea);

					HttpGet peticion = new HttpGet(url);
					// ejecuta una petición get
					 InputStream is = null;
					 String result = "";
					try {

						HttpResponse respuesta = cliente.execute(peticion);

						is = respuesta.getEntity().getContent();
						if (is != null) {
							result = convertInputtoString(is);
							
							
							
						}
						else {
							result = "No ha funcionado";
						}
						return result;
					
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}
					
//					return HttpRequest.get(params[0]).accept("application/json").body();
				
			}



			protected void onPostExecute(String response) {
				try {
					//linea.setText(response);
					progress.dismiss();
					JSONObject json = new JSONObject(response);
					if (selec.equals("Spinner1")) {
						JSONArray js = json.getJSONArray("nombres");
						
						for (int i = 0; i < js.length(); ++ i) {
							JSONObject j = js.getJSONObject(i);
							list.add(j.getString("nombre")); 
							
						}
				
						
			       		addItemsOnSpinner1();
					}
					else {
						JSONArray js = json.getJSONArray("Estaciones");
						paradas = new ArrayList<String>();
						estaciones = new ArrayList<EstructuraPublica>();
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
							if(!paradas.contains(j.getString("direccion"))) {
								if(j.getString("descripcion").isEmpty()) {
									Geocoder g = new Geocoder(getActivity());
									
									try {
										
										List<Address> fromLocationName = g.getFromLocation(a.latitud, a.longitud, 1);
										if (!fromLocationName.isEmpty()) {
											a.direccion = fromLocationName.get(0).getAddressLine(0);
										}
										
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								paradas.add(j.getString("direccion"));
							}
							estaciones.add(a);
							
						}
						
			       		addItemsOnSpinner2();
					}
//					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					Log.e("Error nuestro", e.toString());	
				}
				catch (Exception e) {
					// TODO Auto-generated catch block
					Log.e("Error nuestro", e.toString());
				}

			}
		}

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
		
		private String espacio(String np) {
			// TODO Auto-generated method stub
			String res = np.replaceAll(" ", "%20");

			return res;
		}
			
}
