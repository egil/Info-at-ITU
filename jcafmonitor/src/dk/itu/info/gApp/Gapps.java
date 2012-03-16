package dk.itu.info.gApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dk.itu.info.entity.Room;
import dk.itu.info.entity.Visitor;
import dk.itu.info.relationship.Left;
import dk.pervasive.jcaf.Entity;
import dk.pervasive.jcaf.util.AbstractMonitor;

public class Gapps extends AbstractMonitor {
	final Left left = new Left("Left");
	public Gapps(String service_uri) throws RemoteException {
		super(service_uri);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void monitor(String arg0) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		HttpClient httpclient = new DefaultHttpClient();
		String URL = "http://info-at-itu-proxy.appspot.com/getallclients";
		HttpGet httpget = new HttpGet(URL);
		HttpResponse response;
		try {
			while (true) {
				System.out.println("Starter med at hente");
				response = httpclient.execute(httpget);
				// // Get hold of the response entity
				HttpEntity entity = response.getEntity();
				if (entity != null) {

					// A Simple JSON Response Read
					InputStream instream = entity.getContent();

					JSONArray jsar = new JSONArray(
							convertStreamToString(instream));

					ArrayList<Visitor> allVisitors = new ArrayList<Visitor>();
					for (int i = 0; i < jsar.length(); i++) {
						JSONObject jsob = jsar.getJSONObject(i);
						allVisitors.add(new Visitor(jsob.getString("mac"), jsob
								.getString("username")));
					}

					Entity[] existingVistors = getContextService()
							.getAllEntitiesByType(Visitor.class).getEntities();
					boolean isRegistred;
					for (Visitor v : allVisitors) {
						isRegistred = false;
						for (Entity e : existingVistors) {
							if (v.equals(e)) {
								isRegistred = true;
							}
						}
						if (!isRegistred) {
							getContextService().addEntity(v);
						}
					}

					for (Entity e : existingVistors) {
						isRegistred = false;
						for (Visitor v : allVisitors) {
							if (e.equals(v)) {
								isRegistred = true;
							}
						}
						if (!isRegistred) {
							System.out.println("bruger har forladt");
							Room r = (Room) getContextService().getEntity("itu");
							getContextService().addContextItem(e.getId(), left, r);
							Thread.sleep(1000L);
							getContextService().removeEntity(e);
						}
					}
					
					Entity[] allVistors = getContextService().getAllEntitiesByType(Visitor.class).getEntities();
		        	for(Entity e:allVistors){
		        		Visitor oneVis = (Visitor)e;
		        		System.out.println(oneVis.getEntityInfo());
		        	}
					
					instream.close();
					Thread.sleep(15000);
				}
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
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String convertStreamToString(InputStream is) {
		/*
		 * To convert the InputStream to String we use the
		 * BufferedReader.readLine() method. We iterate until the BufferedReader
		 * return null which means there's no more data to read. Each line will
		 * appended to a StringBuilder and returned as String.
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
