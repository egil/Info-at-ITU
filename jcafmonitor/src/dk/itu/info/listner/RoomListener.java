package dk.itu.info.listner;

import java.util.HashMap;

import dk.itu.info.entity.Room;
import dk.itu.info.entity.Visitor;
import dk.itu.info.relationship.Arrived;
import dk.pervasive.jcaf.ContextEvent;
import dk.pervasive.jcaf.ContextService;
import dk.pervasive.jcaf.EntityListener;
import dk.pervasive.jcaf.relationship.Located;

public class RoomListener implements EntityListener {

	final Located located = new Located("located");
	final Arrived arrived = new Arrived("arrived");
	private Room room;
	private ContextService cs;
	private HashMap<Visitor, String> visitors;

	public RoomListener(Room room, ContextService cs) {
		this.room = room;
		this.cs = cs;
		this.visitors = this.getVisitors();
	}

	private HashMap<Visitor, String> getVisitors() {
		HashMap<Visitor, String> vs = new HashMap<Visitor, String>();
//		System.out.println(new Visitor("000ea50050b8", "jrjensen84").hashCode());
		vs.put(new Visitor("000ea50050b8", "jrjensen84"),
				"Hej Jonas her er noget info til dig!!!");
		
		return vs;
	}

	@Override
	public void contextChanged(ContextEvent event) {
		Room r = ((Room) event.getItem());
		if (r.equals(room)) {
			System.out.println("Er i rum");
			if (event.getRelationship() == arrived) {
				System.out.println("A user have arrived at " + room.getId());
			} else if (event.getRelationship() == located) {
				System.out.println("En er located");
				if (this.visitors.containsKey((Visitor) event.getEntity())) {
					System.out.println(this.visitors.get((Visitor) event
							.getEntity()));
				}
			}
		}
		System.out.println(event.getEntity().getId() + " has "
				+ event.getRelationship().toString() + " at floor " + r.getFloor()
				+ " in sector " + r.getSector());

	}
}
