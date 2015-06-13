package com.example.apptransportespublicos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class Horario extends Fragment{

	private View v;
	
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
	private List<String> sentido = new ArrayList<String>();
	
	private DatePicker dp;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,

	        Bundle savedInstanceState) {
	       
		 v = inflater.inflate(R.layout.activity_horario, container, false);
		 transporte = this.getTag();
		 ciudad = this.getArguments().get("ciudad").toString();
		 pais = this.getArguments().get("pais").toString();
		 addItems("Spinner1");

		 addListenerOnSpinnerItemSelection();
		 boton = (Button) v.findViewById(R.id.buttonHorario);
		 dp = (DatePicker) v.findViewById(R.id.datePicker1);
		 dp.setCalendarViewShown(false);
		 dp.setSpinnersShown(true);
		 
		 boton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				list1 = spinner1.getSelectedItem().toString();
				list2 = spinner2.getSelectedItem().toString();
				int mes = dp.getMonth();
				String primera = "/";
				if (mes < 10) primera = "/0";
				String segunda = "/";
				if (dp.getDayOfMonth() < 10)  segunda = "/0";
				String fecha = dp.getYear()+primera+ dp.getMonth()+segunda+dp.getDayOfMonth();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				String currentDateandTime = sdf.format(new Date());
				if(currentDateandTime.compareTo(fecha) == -1) {
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					
					builder.setMessage("Por favor, introduzca una fecha posterior o igual a la actual.")
					        .setTitle("Fecha anterior a la actual")
					        .setCancelable(false)
					        .setNeutralButton("Aceptar",
					                new DialogInterface.OnClickListener() {
					                    public void onClick(DialogInterface dialog, int id) {
					                        dialog.cancel();
					                    }
					                });
					AlertDialog alert = builder.create();
					alert.show();
				}
				else {
					Intent i = new Intent(getActivity(), ListaHorario.class);
					
	                i.putExtra("ciudad", ciudad);
	                i.putExtra("pais", pais);
	                i.putExtra("transporte", transporte);
	                i.putExtra("linea", list1);
	                i.putExtra("sentido", list2);
	                
	                i.putExtra("fecha", fecha);
	                startActivity(i);
				}

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
		spinner1 = (Spinner) v.findViewById(R.id.spinnerLineasHorario);
		spinner2 = (Spinner) v.findViewById(R.id.spinnerSentido);
		dp = (DatePicker) v.findViewById(R.id.datePicker1);

		spinner1.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.d("sp1", "entor");
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
		Spinner s2 = (Spinner) v.findViewById(R.id.spinnerLineasHorario);
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
			android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s2.setAdapter(dataAdapter);

	}

	// add items into spinner dynamically
	  public void addItemsOnSpinner2() {
	 
		Spinner s2 = (Spinner) v.findViewById(R.id.spinnerSentido);
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
			android.R.layout.simple_spinner_item, sentido);
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
				
				
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
					
					HttpClient cliente = new DefaultHttpClient();
					String url;
					if (selec.equals("Spinner1"))url = constantes.horario+"ciudad="+ciudad+"&pais="+pais+"&transporte="+transporte;
					else url = constantes.horario+"ciudad="+ciudad+"&pais="+pais+"&transporte="+transporte+"&linea="+espacio(linea);
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
					
				
			}



			protected void onPostExecute(String response) {
				try {

					JSONObject json = new JSONObject(response);
					JSONArray js = json.getJSONArray("nombres");
						
					for (int i = 0; i < js.length(); ++ i) {
						JSONObject j = js.getJSONObject(i);
						if (selec.equals("Spinner1"))list.add(j.getString("nombre")); 
						else {
							if (i == 0) sentido = new ArrayList<String>();
							sentido.add(j.getString("nombre")); 
						}
					}
					if (selec.equals("Spinner1"))addItemsOnSpinner1();
					else addItemsOnSpinner2();

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
