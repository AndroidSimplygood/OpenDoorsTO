package com.simplygood.toronto.parsers;


import java.util.Hashtable;
import java.util.Vector;

import org.xml.sax.Attributes; 
import org.xml.sax.SAXException; 
import org.xml.sax.helpers.DefaultHandler; 

import com.simplygood.toronto.dataset.SGVenueDataSet;


public class SAXParserVenues extends DefaultHandler{ 

     // =========================================================== 
     // Fields 
     // =========================================================== 
	 private StringBuilder builder;
     private boolean in_key = false; 
     private boolean in_innertag = false; 
     private boolean in_mytag = false; 
     private Hashtable values = new Hashtable();
     private String key;
     private String value;
     private Vector<SGVenueDataSet> items =new Vector<SGVenueDataSet>();
     private SGVenueDataSet myParsedExampleDataSet = new SGVenueDataSet();
     private static String name = "";

     // =========================================================== 
     // Getter & Setter 
     // =========================================================== 

     public Vector getParsedData() { 
          return this.items; 
     } 

     // =========================================================== 
     // Methods 
     // =========================================================== 
     @Override
     public void startDocument() throws SAXException {
         super.startDocument();
         builder = new StringBuilder();
     }

     @Override 
     public void endDocument() throws SAXException { 
          // Nothing to do 
     } 

     @Override
     public void startElement(String uri, String localName, String nameTag,
             Attributes attributes) throws SAXException {
         super.startElement(uri, localName, name, attributes);
         
         if (localName.equalsIgnoreCase("viewentry")){
             this.myParsedExampleDataSet = new SGVenueDataSet();
         }else if(localName.equalsIgnoreCase("entrydata")){
        	 name = attributes.getValue("name");
         }
         
     }
     
      
     /** Gets be called on closing tags like: 
      * </tag> */ 
     @Override 
     public void endElement(String namespaceURI, String localName, String qName) 
               throws SAXException { 
    	 super.endElement(namespaceURI, localName, qName);
         if (this.myParsedExampleDataSet != null){
             
        	 if (localName.equalsIgnoreCase("text")){
        		 
        		 if(name.equals("txtPA1aBldName")){
        			 myParsedExampleDataSet.BuildingName = builder.toString();
        		 }else if(name.equals("radS3zDistrict")){
        			 myParsedExampleDataSet.District = builder.toString();
        		 }else if(name.equals("txtPA1bBldAddr")){
        			 myParsedExampleDataSet.Address = builder.toString();
        		 }else if(name.equals("txtPA1dBldPhone")){
        			 myParsedExampleDataSet.PhoneNumber = builder.toString();
        		 }else if(name.equals("$4")){
        			 myParsedExampleDataSet.SaturdayHours = builder.toString();
        		 }else if(name.equals("$12")){
        			 myParsedExampleDataSet.SaturdaLastImitance = builder.toString();
        		 }else if(name.equals("$5")){
        			 myParsedExampleDataSet.SundayHours = builder.toString();
        		 }else if(name.equals("$13")){
        			 myParsedExampleDataSet.SundayLastImitance = builder.toString();
        		 }else if(name.equals("$11")){
        			 myParsedExampleDataSet.newThisYear = builder.toString();
        		 }else if(name.equals("mmoS3oBldDesc")){
        			 myParsedExampleDataSet.propertyDescriptor = builder.toString();
        		 }else if(name.equals("mmoS3sBldExper")){
        			 myParsedExampleDataSet.eventInfo = builder.toString();
        		 }else if(name.equals("$27")){
        			 myParsedExampleDataSet.buildingType = builder.toString();
        		 }else if(name.equals("$28")){
        			 myParsedExampleDataSet.architectureType = builder.toString();
        		 }else if(name.equals("$18")){
        			 myParsedExampleDataSet.architect = builder.toString();
        		 }else if(name.equals("txtS3aaPhotoInt")){
        			 myParsedExampleDataSet.photography = builder.toString();
        		 }else if(name.equals("txtS3abPhotoIntTripod")){
        			 myParsedExampleDataSet.tripod = builder.toString();
        		 }else if(name.equals("txtS3aeFilmInt")){
        			 myParsedExampleDataSet.video = builder.toString();
        		 }else if(name.equals("txtS3mSubway")){
        			 myParsedExampleDataSet.subway = builder.toString();
        		 }else if(name.equals("txtS3nStreetcar")){
        			 myParsedExampleDataSet.streetcar = builder.toString();
        		 }else if(name.equals("$24")){
        			 myParsedExampleDataSet.accessable = builder.toString();
        		 }else if(name.equals("$25")){
        			 myParsedExampleDataSet.washroom = builder.toString(); 
        		 }else if(name.equals("$26")){
        			 myParsedExampleDataSet.parking = builder.toString();
        		 }
             }else if(localName.equals("viewentry")){
            	 items.add(myParsedExampleDataSet);
             }
             
             builder.setLength(0);    
         }
     } 
      
     @Override
     public void characters(char[] ch, int start, int length)
             throws SAXException {
         super.characters(ch, start, length);
         builder.append(ch, start, length);
     }
}