package com.simplygood.toronto;


import java.io.InputStream;
import java.util.Vector;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import com.simplygood.toronto.parsers.SAXParserVenues;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Window;

public class SGSplashActivity extends Activity {
	
	Context con;
	public static Vector x;
	
	  protected void onCreate(Bundle icicle) {
		    super.onCreate(icicle);
		    requestWindowFeature(Window.FEATURE_NO_TITLE);
		    setContentView(R.layout.sgsplash);
		    con = this;
	        final SAXParserFactory factory = SAXParserFactory.newInstance();
	        
		    Thread th = new Thread(){ 
		    	public void run(){
		    		try{ 
		                SAXParser parser = factory.newSAXParser();
		                SAXParserVenues images = new SAXParserVenues();
		                AssetManager assetManager = getAssets();
		                InputStream in = assetManager.open("XML.xml");
		                parser.parse(new InputSource(in), images);
		                x = images.getParsedData(); 
		                
		    		}catch(Exception err){
		    			
		    		}
		    		
		    		SGIntroScreen.results = x;
		    		Intent inte = new Intent(con,SGIntroScreen.class);
		    		startActivity(inte);
		    		finish();
		    	}
		    };
		    
		    th.start();
	  }

}
