package com.example.apptransportespublicos;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
	
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,

	        Bundle savedInstanceState) {
	       
		 v = inflater.inflate(R.layout.activity_tab, container, false);
		 transporte = this.getTag();
		 ciudad = this.getArguments().get("ciudad").toString();
//		 Spinner s1  = (Spinner) v.findViewById(R.id.spinnerLineas);
		 addItemsOnSpinner1();
		 addItemsOnSpinner2();
//		 addListenerOnButton();
		 addListenerOnSpinnerItemSelection();
		 boton = (Button) v.findViewById(R.id.buttonBusqueda);
		 boton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				list1 = spinner1.getSelectedItem().toString();
				list2 = spinner2.getSelectedItem().toString();
				Intent i = new Intent(getActivity(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("ciudad", ciudad);
                i.putExtra("Anterior", "busqueda");
                i.putExtra("transporte", transporte);
                i.putExtra("linea", list1);
                i.putExtra("parada", list2);
                startActivity(i);
                try {
					this.finalize();
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		 return v;
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
				Toast.makeText(parent.getContext(), 
						"OnItemSelectedListener2 : " + parent.getItemAtPosition(position).toString(),
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}

	private void addListenerOnButton() {
		// TODO Auto-generated method stub
		
	}

	private void addItemsOnSpinner1() {
		// TODO Auto-generated method stub
		Spinner s2 = (Spinner) v.findViewById(R.id.spinnerLineas);
		List<String> list = new ArrayList<String>();
		list.add("list 1");
		list.add("list 2");
		list.add("list 3");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
			android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s2.setAdapter(dataAdapter);
	}

	// add items into spinner dynamically
	  public void addItemsOnSpinner2() {
	 
		Spinner s2 = (Spinner) v.findViewById(R.id.spinnerParadas);
		List<String> list = new ArrayList<String>();
		list.add("list 1");
		list.add("list 2");
		list.add("list 3");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
			android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s2.setAdapter(dataAdapter);
	  }
}
