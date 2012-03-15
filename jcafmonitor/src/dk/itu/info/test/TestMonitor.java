package dk.itu.info.test;

import java.rmi.RemoteException;

import dk.itu.info.entity.*;
import dk.itu.info.relationship.Arrived;
import dk.pervasive.jcaf.Entity;
import dk.pervasive.jcaf.relationship.Located;
import dk.pervasive.jcaf.util.AbstractContextClient;

public class TestMonitor extends AbstractContextClient {

	public TestMonitor(String service_uri) {
		super(service_uri);
		final Located located = new Located("located");
		final Arrived arrived = new Arrived("arrived");

//		Entity[] allVistors;
		try {
//			allVistors = getContextService()
//					.getAllEntitiesByType(Visitor.class).getEntities();
//			System.out.println("Disse er pt på nettet");
//			for (Entity e : allVistors) {
//				Visitor oneVis = (Visitor) e;
//				System.out.println(oneVis.getEntityInfo());
//
//			}
			
			Visitor v = new Visitor("000ea50050b8","jrjensen84");
			this.createAreas();
			
//			Room r = (Room)getContextService().getEntity("itu.zone4.zone4c1");
			getContextService().addContextItem(v.getId(), located, (Room)getContextService().getEntity("itu.zone4.zone4c1"));
			System.out.println("er færdig tilføjet ting!!");

		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private void createAreas() {
		try {
			
			if (getContextService().getAllEntitiesByType(Room.class).size() == 0) {
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


	@Override
	public void run() {

	}
	
	public static void main(String[] args) {
		new TestMonitor("info@itu");

	}
	

}
