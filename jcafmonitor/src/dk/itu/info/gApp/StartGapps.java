package dk.itu.info.gApp;

import java.rmi.RemoteException;

public class StartGapps {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new Thread(new Gapps("info@itu")).start();
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
