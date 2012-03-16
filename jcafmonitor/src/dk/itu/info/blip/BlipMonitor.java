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

import dk.itu.info.entity.Room;
import dk.itu.info.entity.Visitor;
import dk.itu.info.relationship.Arrived;
import dk.pervasive.jcaf.Entity;
import dk.pervasive.jcaf.relationship.Located;
import dk.pervasive.jcaf.util.AbstractMonitor;

public class BlipMonitor extends AbstractMonitor {

	/**
	 * 
	 */
	final Located located = new Located("located");
	final Arrived arrived = new Arrived("arrived");
	private static final long serialVersionUID = 1L;

	public BlipMonitor(String service_uri) throws RemoteException {
		super(service_uri);
		this.createAreas();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void monitor(String arg0) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		while(true){
			System.out.println("Starter blip");
		try {
			Entity[] existingVistors = getContextService()
					.getAllEntitiesByType(Visitor.class).getEntities();
			for (Entity e : existingVistors) {
				Visitor v = (Visitor) e;
				String adr = getBlipAddress(v.getId());
				System.out.println(v.getId());
				System.out.println(adr);
				if (adr != null) {
					Room r = (Room) getContextService().getEntity(adr);
					if (getContextService().getContext(v.getId())
							.containsRelationship(arrived)) {
						if (getContextService().getContext(v.getId())
								.getContextItem(arrived).equals(r)) {
							getContextService().addContextItem(v.getId(),
									located, r);
						} else {
							getContextService().addContextItem(v.getId(),
									arrived, r);
						}
					} else {
						getContextService().addContextItem(v.getId(), arrived,
								r);
					}
				}
			}
			Thread.sleep(15000);

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	private String getBlipAddress(String bluetoothAdr) {
		String localAddr = null;

		try {

			HttpClient httpclient = new DefaultHttpClient();
			String URL = "http://pit.itu.dk:7331/location-of/" + bluetoothAdr;

			HttpGet httpget = new HttpGet(URL);
			HttpResponse response;

			response = httpclient.execute(httpget);
			// Get hold of the response entity
			HttpEntity entity = response.getEntity();
			if (entity != null) {

				// A Simple JSON Response Read
				InputStream instream = entity.getContent();
				String result = convertStreamToString(instream);
				if (!result.contains("error")) {
					// A Simple JSONObject Creation
					JSONObject json = new JSONObject(result);
					localAddr = json.getString("location");
					instream.close();
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
		} finally {
			return localAddr;
		}
	}

	private void createAreas() {
		try {

			if (getContextService().getAllEntitiesByType(Room.class).size() == 0) {
				getContextService().addEntity(
						new Room("itu", 0, 'A', 1));
				getContextService().addEntity(
						new Room("itu.zone4.zone4b", 4, 'B', 1));
				getContextService().addEntity(
						new Room("itu.zone4.zone4d", 4, 'D', 1));
				getContextService().addEntity(
						new Room("itu.zone4.zone4e", 4, 'E', 1));
				getContextService().addEntity(
						new Room("itu.zone4.zone4c", 4, 'C', 1));
				getContextService().addEntity(
						new Room("itu.zone4.zone4c1", 4, 'C', 1));

				getContextService().addEntity(
						new Room("itu.zone3.zone3b", 3, 'B', 1));
				getContextService().addEntity(
						new Room("itu.zone3.zone3d", 3, 'D', 1));
				getContextService().addEntity(
						new Room("itu.zone3.zone3e", 3, 'E', 1));
				getContextService().addEntity(
						new Room("itu.zone3.zone3c", 3, 'C', 1));

				getContextService().addEntity(
						new Room("itu.zone2.zone2b", 2, 'B', 1));
				getContextService().addEntity(
						new Room("itu.zone2.zone2d", 2, 'D', 1));
				getContextService().addEntity(
						new Room("itu.zone2.zone2e", 2, 'E', 1));
				getContextService().addEntity(
						new Room("itu.zone2.zone2c", 2, 'C', 1));

				getContextService().addEntity(
						new Room("itu.zone5.zone5b", 5, 'B', 1));
				getContextService().addEntity(
						new Room("itu.zone5.zone5d", 5, 'D', 1));
				getContextService().addEntity(
						new Room("itu.zone5.zone5e", 5, 'E', 1));
				getContextService().addEntity(
						new Room("itu.zone5.zone5c", 5, 'C', 1));

				getContextService().addEntity(
						new Room("itu.zone0.zoneaud2", 0, 'B', 1));
				getContextService().addEntity(
						new Room("itu.zone0.zoneaud1", 0, 'C', 2));
				getContextService().addEntity(
						new Room("itu.zone0.zonekanvindfang", 0, 'D', 1));
				getContextService().addEntity(
						new Room("itu.zone0.zonekandisk", 0, 'D', 2));
				getContextService().addEntity(
						new Room("itu.zone0.zonekanspisesal", 0, 'D', 3));

				getContextService().addEntity(
						new Room("itu.zone0.zoneanalog", 0, 'B', 2));

				getContextService().addEntity(
						new Room("itu.zone0.zonedornord", 0, 'A', 1));
				getContextService().addEntity(
						new Room("itu.zone0.zone0c", 0, 'C', 1));
				getContextService().addEntity(
						new Room("itu.zone0.zonedorsyd", 0, 'E', 1));
				getContextService().addEntity(
						new Room("itu.zone0.zonescroll", 0, 'E', 2));

				getContextService().addEntity(
						new Room("itu.zone1.zone1c", 1, 'C', 1));
				getContextService().addEntity(
						new Room("itu.zone-1.zonekaeldersyd", 1, 'B', 1));

				/*
				 * Other locations to be implemented:
				 * location-name=itu.zone2.zone2m28
				 * location-name=itu.zone2.zone2m31
				 * location-name=itu.zone1.zonelaesesal
				 * location-name=itu.zone2.zone2m18
				 * location-name=itu.zone2.zone2m52
				 * location-name=itu.zone-1.zonekaeldernord
				 */
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
