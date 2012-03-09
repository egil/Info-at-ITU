package dk.itu.info.proxy.server;

import java.util.Date;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Cached;

@Cached
public class ClientDevice {
	@Id String mac;
	String username;
	Date lastseen;
	
	public ClientDevice() {}
	
	public ClientDevice(String mac, String username, Date lastseen) {
		this.mac = mac;
		this.username = username;
		this.lastseen = lastseen;
	}
}
