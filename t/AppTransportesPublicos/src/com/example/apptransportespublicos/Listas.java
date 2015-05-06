package com.example.apptransportespublicos;



import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Listas extends ActionBarActivity {

	ListView lista;
	private AdaptadorParadas adapt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listas);
		android.app.ActionBar bar = getActionBar();
		//for color
		lista = (ListView)findViewById(R.id.listView1);
		adapt = new AdaptadorParadas(this);
		
	}

	private class AdaptadorParadas extends ArrayAdapter {
	
		public AdaptadorParadas(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		// TODO Auto-generated constructor stub
	}

	Activity context;
  	
  	AdaptadorParadas(Activity context) {
  		super(context, R.layout.activity_nombre_listas );
  		this.context = context;
  	}
  	
  	public View getView(int position, View convertView, ViewGroup parent) {
			
		View item = convertView;
		if(item == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			item = inflater.inflate(R.layout.activity_nombre_listas, null);
      		
      		TextView text = (TextView)item.findViewById(R.id.nom);
      		
      		text.setText("Barcelona");
      		text.setText("Madrid");
      		text.setText("Bilbao");
		
		
		}
		return(item);
  	}
  }	
	
}
