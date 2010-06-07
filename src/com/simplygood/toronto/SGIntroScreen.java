package com.simplygood.toronto;

import java.util.HashMap;
import java.util.Vector;


import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import com.simplygood.toronto.adapters.SGTorontoAdapter;
import com.simplygood.toronto.dataset.SGVenueDataSet;

import android.app.Activity;
import android.app.SearchManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SGIntroScreen extends Activity {
    /** Called when the activity is first created. */
	
    public static HashMap<String, Object> sharedObjects = new HashMap<String, Object>();
    GoogleAnalyticsTracker tracker;
    public static Vector results;
    private Vector x = new Vector();
   
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tracker = GoogleAnalyticsTracker.getInstance();
        tracker.start(getResources().getString(R.string.google_code), 30,this);
       Intent intent = getIntent();

        
        try {
        	x = results;
        }catch(Exception err){
        	
        }
        
        ListView list = (ListView) findViewById(R.id.ListView01);
        
        
        // Is this being called from a search request?
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
        	
        	
          String query = intent.getStringExtra(SearchManager.QUERY).toLowerCase();
          
			      	tracker.trackEvent(
			        		"App",  // Category
			                "Search",  // Action
			                query, // Label
			                1);  
			          
			          Vector temp = new Vector();
			          TextView searchText = (TextView) findViewById(R.id.searchResult);
			          
			          
			          for(int i=0;i<x.size();i++){
			        	  
			        	  SGVenueDataSet dsTest = (SGVenueDataSet) x.get(i);
			        	  int add = 0;
			        	  
			        	  if(dsTest.BuildingName.toLowerCase().contains(query)){
			        		  temp.add(dsTest);
			        	  }else if(dsTest.District.toLowerCase().contains(query)){
			        		  temp.add(dsTest);
			        	  }else if(dsTest.eventInfo.toLowerCase().contains(query)){
			        		  temp.add(dsTest);
			        	  }else if(dsTest.propertyDescriptor.toLowerCase().contains(query)){
			        		  temp.add(dsTest);
			        	  } 
			          }
			          
			          x = temp;
			          
			          if(x.size()< 1){
			        	  searchText.setText("No results for term :"+query);
			        	  list.setVisibility(View.GONE);
			        	  searchText.setVisibility(View.VISIBLE);
			          }
			          
        }else{
            tracker.trackEvent(
            		"App",  // Category
                    "Open",  // Action
                    "appOpen", // Label
                    1);       // Value
        }

		     try{
		            
		            
		    		list.setCacheColorHint(Color.TRANSPARENT);
		            SGTorontoAdapter adapter = new SGTorontoAdapter(this,x,0);
		            list.setAdapter(adapter);
		            
		            
		            list.setOnItemClickListener(new OnItemClickListener(){
		    			
		        		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
		        			
		        		SGVenueDataSet ar = (SGVenueDataSet) arg1.getTag();
		        		sharedObjects.put("location", ar);
		        		
		        		Intent inte = new Intent(SGIntroScreen.this,SGInfoScreen.class);
		        		startActivity(inte);
		        		
		        			
		        		        				
		        	}});
		
		            
		        } catch (Exception e) {
		            throw new RuntimeException(e);
		        } 
    }
    
    @Override
    protected void onDestroy() {
      super.onDestroy(); 
      // Stop the tracker when it is no longer needed.
      tracker.stop();
      System.exit(0);
    }
    
}