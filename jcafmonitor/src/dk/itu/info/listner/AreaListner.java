package dk.itu.info.listner;

import java.rmi.RemoteException;

import dk.itu.info.entity.Room;
import dk.itu.info.entity.Visitor;
import dk.pervasive.jcaf.EntityNotFoundException;
import dk.pervasive.jcaf.impl.RemoteEntityListenerImpl;
import dk.pervasive.jcaf.util.AbstractContextClient;

public class AreaListner extends AbstractContextClient {
	private RemoteEntityListenerImpl listener1;

	public AreaListner(String service_uri) {
		super(service_uri);
		this.createAreas();

		try {
			// 000ea50050b8
			listener1 = new RemoteEntityListenerImpl();
			listener1.addEntityListener(new RoomListener(
					(Room) getContextService().getEntity("itu.zone2.zone2c"),
					getContextService()));
			getContextService().addEntityListener(listener1, Visitor.class);

			System.out.println("har tilføjet ting");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// } catch (EntityNotFoundException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new AreaListner("info@itu");

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
}
