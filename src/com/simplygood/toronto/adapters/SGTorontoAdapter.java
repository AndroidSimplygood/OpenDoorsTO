package com.simplygood.toronto.adapters;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import com.simplygood.toronto.R;
import com.simplygood.toronto.dataset.SGVenueDataSet;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SGTorontoAdapter extends BaseAdapter { 
    /** The parent context */ 
       private Context myContext; 
       private Vector<SGVenueDataSet> venueCollection;
       int page;
       LayoutInflater inflater;
 
       public SGTorontoAdapter(Context c,Vector<SGVenueDataSet>  venues,int pageNumber) { 
    	   this.myContext = c; 
    	   this.venueCollection = venues;
    	   page = pageNumber;
    	   inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    } 

       public int getCount() { return this.venueCollection.size();} 

       /* Use the array-Positions as unique IDs */ 
       public Object getItem(int position) { return position; } 
       public long getItemId(int position) { return position; }
       
       public View getView(int position, View convertView, ViewGroup parent) { 
           SGVenueDataSet i = venueCollection.get(position);
           LinearLayout location;
           
           if(convertView == null){
        	   location = (LinearLayout)inflater.inflate(R.layout.locationrow,null);
               
           }else{
        	   location = (LinearLayout) convertView;
           }
           
           TextView header = (TextView) location.findViewById(R.id.building);
           TextView locationText = (TextView) location.findViewById(R.id.location);
           
           header.setText(i.BuildingName.replace("\n", ""));
           locationText.setText(i.Address.replace("\n", ""));
           location.setTag(i);
           
           return location; 
       } 

   } 