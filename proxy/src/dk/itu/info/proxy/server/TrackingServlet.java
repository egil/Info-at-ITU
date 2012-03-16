package dk.itu.info.proxy.server;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;

public class TrackingServlet extends HttpServlet {
	private static final long serialVersionUID = 85440686344539318L;
	//private static Cache cache;	
	
	static {
        ObjectifyService.register(ClientDevice.class);
    }
	
//	private static Cache getCache() {
//		if(cache == null) {			
//			try {
//				CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
//				cache = cacheFactory.createCache(Collections.emptyMap());
//			} catch (CacheException e) {
//				//e.printStackTrace();
//			}	        
//		}
//		return cache;
//	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String uri = req.getRequestURI();
		String macAddress = req.getQueryString();

		if (uri.equalsIgnoreCase("/getallclients")) {
			getAllClients(resp);
		} else if (uri.equalsIgnoreCase("/leaving")) {
			doLeaving(macAddress, resp);
		} else if (uri.equalsIgnoreCase("/entering")) {
			// get current users google account name or redirect them
			// to the login page if they are not signed in to google
//			UserService userService = UserServiceFactory.getUserService();
//			User user = userService.getCurrentUser();
//			
//			if (user != null) {
//				doEntering(user.getNickname(), macAddress, resp);
//			} else {
//				resp.sendRedirect(userService.createLoginURL("/"));
//			}
			
			int i = macAddress.indexOf('&');
			String name = macAddress.substring(i+1);
			macAddress = macAddress.substring(0, i);
			doEntering(name, macAddress, resp);
		} else if (uri.equalsIgnoreCase("/ping")) {
			doPong(macAddress, resp);
		} else if (uri.equalsIgnoreCase("/update")) {
			//doUpdate(resp);
			resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
		} else {
			resp.setContentType("text/plain");
			resp.getWriter().println("Hello, world");
		}
	}

	private void doEntering(String username, String macAddress, HttpServletResponse resp) {
		if(macAddress == null || macAddress.isEmpty()) {
			doReturnMissingArgument(resp);
			return;
		}
		
		Objectify ofy = ObjectifyService.begin();
		
		// Test if device is already being tracked, if true, go to doPong();	
		ClientDevice device = ofy.find(ClientDevice.class, macAddress);
		if(device != null) {
			doPong(device, resp);
			return;
		}
		
		// Add device to tracking list
		device = new ClientDevice(macAddress, username, new Date());
		ofy.put(device);
		
		// Notify listeners of changes
		//doNotifyListeners();
		
		resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
	}

	private void doLeaving(String macAddress, HttpServletResponse resp) {
		if(macAddress == null || macAddress.isEmpty()) {
			doReturnMissingArgument(resp);
			return;
		}
		
		Objectify ofy = ObjectifyService.begin();
		
		// Find the device
		ClientDevice device = ofy.find(ClientDevice.class, macAddress);
		if(device == null) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		ofy.delete(device);
		
		// Notify listeners of changes
		//doNotifyListeners();
		
		resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
	}

	private void doPong(String macAddress, HttpServletResponse resp) {
		if(macAddress == null || macAddress.isEmpty()) {
			doReturnMissingArgument(resp);
			return;
		}
		
		Objectify ofy = ObjectifyService.begin();
		ClientDevice device = ofy.find(ClientDevice.class, macAddress);
		if(device != null) {
			doPong(device, resp);
		} else {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}
	
	private void doPong(ClientDevice device, HttpServletResponse resp) {
		Objectify ofy = ObjectifyService.begin();
		
		// update clients last seen timestamp
		device.lastseen = new Date();
		ofy.put(device);
		
		resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
	}

	private void getAllClients(HttpServletResponse resp) throws IOException {
		Objectify ofy = ObjectifyService.begin();
		
		Query<ClientDevice> devices = ofy.query(ClientDevice.class);
		
		StringBuilder res = new StringBuilder();
		res.append("[");
		for (ClientDevice cd : devices) {
			if(res.length() > 1) {
				res.append(", ");
			}
			res.append("{");
			res.append("mac: ");
			res.append("\""); res.append(cd.mac); res.append("\""); 
			res.append(", username: ");
			res.append("\""); res.append(cd.username); res.append("\""); 
			res.append(", lastseen: ");
			res.append("\""); res.append(cd.lastseen); res.append("\"");
			res.append("}");
		}
		res.append("]");
		
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setContentType("application/json");
		resp.getWriter().print(res.toString());
	}

//	private void doUpdate(HttpServletResponse resp) {
//		Cache c = getCache();
//		int counter = 0;
//		
//		while(counter < 10) {
//			if(c.get("notify") != null) {
//				c.remove("notify");
//				try {
//					getAllClients(resp);
//				} catch (IOException e) { } 
//				return;
//			}
//			try {
//				this.wait(5000);
//			} catch (InterruptedException e) { }
//			counter++;
//		}
//		
//		resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
//	}
//	
//
//	private void doNotifyListeners() {
//		Cache c = getCache();
//		c.put("notify", true);
//		this.notifyAll();
//	}
	
	private void doReturnMissingArgument(HttpServletResponse resp) {
		resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);		
	}
}
