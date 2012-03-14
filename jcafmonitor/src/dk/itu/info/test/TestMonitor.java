package dk.itu.info.test;

import dk.pervasive.jcaf.util.AbstractContextClient;

public class TestMonitor extends AbstractContextClient {

	public TestMonitor(String service_uri) {
		super(service_uri);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		new TestMonitor("info@itu");
		
	}

}
