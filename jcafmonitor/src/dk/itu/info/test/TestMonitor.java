package dk.itu.info.test;

import java.rmi.RemoteException;

import dk.itu.info.entity.*;
import dk.pervasive.jcaf.Entity;
import dk.pervasive.jcaf.util.AbstractContextClient;

public class TestMonitor extends AbstractContextClient {

	public TestMonitor(String service_uri) {
		super(service_uri);
		Entity[] allVistors;
		try {
			allVistors = getContextService()
					.getAllEntitiesByType(Visitor.class).getEntities();
			System.out.println("Disse er pt på nettet");
			for (Entity e : allVistors) {
				Visitor oneVis = (Visitor) e;
				System.out.println(oneVis.getEntityInfo());

			}

		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void run() {

	}
	
	public static void main(String[] args) {
		new TestMonitor("info@itu");

	}

}
