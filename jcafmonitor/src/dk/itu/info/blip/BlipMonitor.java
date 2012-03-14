package dk.itu.info.blip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.rmi.RemoteException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import dk.pervasive.jcaf.util.AbstractMonitor;

public class BlipMonitor extends AbstractMonitor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public BlipMonitor(String service_uri) throws RemoteException {
		super(service_uri);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void monitor(String arg0) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
			 HttpClient httpclient = new DefaultHttpClient();
//			 String URL = "http://pit.itu.dk:7331/location-of/"+ myBluetoothAdr;
			 String URL = "http://pit.itu.dk:7331/location-of/1093E90FDD3B";
			 String localAddr = null;
			 HttpGet httpget = new HttpGet(URL);
			 HttpResponse response;
			 try {
	        	 response = httpclient.execute(httpget);
	        	  // Get hold of the response entity
	             HttpEntity entity = response.getEntity();
	             if (entity != null) {
	            	 
	                 // A Simple JSON Response Read
	                 InputStream instream = entity.getContent();
	                 String result= convertStreamToString(instream);

	                 // A Simple JSONObject Creation
	                 JSONObject json=new JSONObject(result);
	                System.out.println("<jsonobject>\n"+json.toString()+"\n</jsonobject>");
	//  
//	                 // A Simple JSONObject Parsing
//	                 localAddr = json.getString("location");
////	                 Log.i("PervLoc",localAddr);
////	                 JSONArray nameArray=json.names();
////	                 JSONArray valArray=json.toJSONArray(nameArray);
////	                 for(int i=0;i<valArray.length();i++)
////	                 {
////	                     Log.i("PervLoc","<jsonname"+i+">\n"+nameArray.getString(i)+"\n</jsonname"+i+">\n"
////	                             +"<jsonvalue"+i+">\n"+valArray.getString(i)+"\n</jsonvalue"+i+">");
////	                 }
	////  
////	                 // A Simple JSONObject Value Pushing
////	                 json.put("sample key", "sample value");

	                 
//	                 getContextService().addContextItem("visitor2@itu.dk", arrived, getContextService().getEntity("itu.zone4.zone4c1") );
	                 instream.close();
	             }

			} catch (ClientProtocolException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			} catch (IOException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			} catch (JSONException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
			 
		}
		private String convertStreamToString(InputStream is) {
	        /*
	         * To convert the InputStream to String we use the BufferedReader.readLine()
	         * method. We iterate until the BufferedReader return null which means
	         * there's no more data to read. Each line will appended to a StringBuilder
	         * and returned as String.
	         */
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	        StringBuilder sb = new StringBuilder();
	 
	        String line = null;
	        try {
	            while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                is.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        return sb.toString();
	    }
	

}
