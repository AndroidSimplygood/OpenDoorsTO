package com.simplygood.toronto;

import java.util.List;
import java.util.Locale;

import com.simplygood.toronto.dataset.SGVenueDataSet;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class SGInfoScreen extends Activity {
    
	TextView txt1;
	TextView txt2;
	TextView txt3;
	TextView txt4;
	TextView txt5;
	TextView txt6;
	ImageView map;
	ImageView call;
	ImageView share;
	private LocationManager locationManager;
    private static Location location;
    private static SGVenueDataSet ds;
    GoogleAnalyticsTracker tracker;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locationdetails);
        
        tracker = GoogleAnalyticsTracker.getInstance();
        tracker.start(getResources().getString(R.string.google_code), 30,this);
        
        
        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE); 
        ds = (SGVenueDataSet) SGIntroScreen.sharedObjects.get("location");
        txt1 = (TextView) findViewById(R.id.TextView01);
        txt2 = (TextView) findViewById(R.id.TextView02);
        txt3 = (TextView) findViewById(R.id.TextView03);
        txt4 = (TextView) findViewById(R.id.TextView04);
        txt5 = (TextView) findViewById(R.id.TextView05);
        txt6 = (TextView) findViewById(R.id.TextView06);
        
        map = (ImageView) findViewById(R.id.ImageView02);
        call = (ImageView) findViewById(R.id.call);
        share = (ImageView) findViewById(R.id.share);
        
        
        txt1.setText(ds.BuildingName.replace("\n", ""));
        txt2.setText(ds.propertyDescriptor.replace("\n", "") +"\n\n "+ds.eventInfo.replace("\n", ""));
        txt3.setText("Architecture : "+ds.architectureType.replace("\n", ""));
        txt4.setText("Sat : "+ds.SaturdayHours.replace("\n", "")+" ("+ds.SaturdaLastImitance.replace("\n", "")+" Last Admission) \n"+"Sun : "+ds.SundayHours.replace("\n", "")+" ("+ds.SundayLastImitance.replace("\n", "")+" Last Admission) ");
        txt5.setText("Subway : "+ds.subway.replace("\n", ""));
        txt6.setText("Street Car : "+ds.streetcar.replace("\n", ""));
        tracker.trackPageView(ds.BuildingName.replace("\n", "").replace(" ", ""));
        
        map.setOnClickListener(new OnClickListener(){ 

			
			public void onClick(View v) {
    			 
        		try{ 
        			
        	      	tracker.trackEvent(
        	        		"App",  // Category
        	                "Map",  // Action
        	                "Map Location", // Label 
        	                1);
        			
        	        location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
        	        
                    if(location== null){
            			location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
            		}	
        	        
            		Geocoder geocoder = new Geocoder(SGInfoScreen.this, Locale.getDefault());
            		String geo = ds.Address;
            		List<Address> addresses = null;
            		List<Address> addresses2 = null;
            		
            		addresses2 = geocoder.getFromLocation(Double.valueOf(location.getLatitude()),Double.valueOf(location.getLongitude()), 1);
    				addresses = geocoder.getFromLocationName(geo, 1);
            			
            				Address from;
            				Address to;
            		
            		if(addresses !=null){
            			from = addresses.get(0);
            		}else{
            			from = new Address(new Locale("en"));
            			from.setAddressLine(0, geo);
            			from.setPostalCode("");
            			from.setCountryName("canada");
            		}
            		
            		if(addresses2 !=null){
            			to = addresses2.get(0);
            		}else{
            			to = new Address(new Locale("en"));
            			to.setAddressLine(0, geo);
            			to.setPostalCode("");
            			to.setCountryName("Canada");
            		}
            		
            				SGInfoScreen.this.startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://maps.google.com/maps?f=d&saddr="+to.getAddressLine(0)+",Toronto,Canada&daddr="+from.getAddressLine(0)+"Toronto,Canada&hl=en")));
            		
            		}catch(Exception err){
            			//Log.e("location error",err.toString());
            		} 
        		
        		}
				
			});
        
        
        call.setOnClickListener(new OnClickListener(){

			
			public void onClick(View v) {
				
	 	      	tracker.trackEvent(
    	        		"App",  // Category
    	                "Call",  // Action
    	                "Call Location", // Label
    	                1);  
				
				Intent intent = new Intent(android.content.Intent.ACTION_CALL);
				intent.setData(Uri.parse("tel:"+ds.PhoneNumber.replaceAll("-", "")));
				SGInfoScreen.this.startActivity(intent);	
				
			}
        
        });
        
        share.setOnClickListener(new OnClickListener(){

			
			public void onClick(View v) {
				
					 final Intent intent = new Intent(Intent.ACTION_SEND);
					 
			 	      	tracker.trackEvent(
		    	        		"App",  // Category
		    	                "Share",  // Action
		    	                "Share Location", // Label
		    	                1);  

					 intent.setType("text/plain");
					 intent.putExtra(Intent.EXTRA_SUBJECT, "Doors Open TO");
					 intent.putExtra(Intent.EXTRA_TEXT, "Attending "+ds.BuildingName.replace("\n", "")+" via Android T.O doors open app");

					 startActivity(Intent.createChooser(intent, "Share your plans"));
				
				
			}
        
        });
        
    }
	
	  @Override
	  protected void onDestroy() {
	    super.onDestroy();
	    // Stop the tracker when it is no longer needed.
	    tracker.stop();
	  }
    
    
}
