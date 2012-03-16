package dk.itu.info.listner;

import java.rmi.RemoteException;
import java.util.HashMap;

import dk.itu.info.entity.Room;
import dk.itu.info.entity.Visitor;
import dk.itu.info.relationship.Arrived;
import dk.itu.info.relationship.Left;
import dk.pervasive.jcaf.ContextEvent;
import dk.pervasive.jcaf.ContextService;
import dk.pervasive.jcaf.EntityListener;
import dk.pervasive.jcaf.relationship.Located;

public class RoomListener implements EntityListener {

	final Located located = new Located("located");
	final Arrived arrived = new Arrived("arrived");
	final Left left = new Left("Left");
	private Room room;

	private Visitor isAtBoard = null;
	private ContextService cs;
	private HashMap<Visitor, String> visitors;

	public RoomListener(Room room, ContextService cs) {
		this.room = room;
		this.cs = cs;
		this.visitors = this.getVisitors();
	}

	private HashMap<Visitor, String> getVisitors() {
		HashMap<Visitor, String> vs = new HashMap<Visitor, String>();
		Visitor v = new Visitor("E4B0219CE1AD", "jrjensen84@gmail.com");
		vs.put(v, "Hej Jonas her er noget info til dig!!!");
		return vs;
	}

	@Override
	public void contextChanged(ContextEvent event) {
		Room r = ((Room) event.getItem());
		Visitor v = (Visitor) event.getEntity();
		if (r.equals(room)) {
			if (isAtBoard == null) {
				if (event.getRelationship().equals(arrived)) {
					System.out
							.println("A user have arrived at " + room.getId());
				} else if (((Located) event.getRelationship()).equals(located)) {
					isAtBoard = (Visitor) event.getEntity();
					if (this.visitors.containsKey(isAtBoard)) {
						System.out.println(this.visitors.get(isAtBoard));
					}
				}
			}
		} else if (v.equals(isAtBoard)) {
			isAtBoard = null;
			System.out.println(v.getName() + " has left the display and everything removes");
			if(event.getRelationship().equals(left)){
				System.out.println(v.getName() + " has left ITU");
			}
		}
		

	}
}
