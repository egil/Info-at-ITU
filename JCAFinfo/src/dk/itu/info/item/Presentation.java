package dk.itu.info.item;

import dk.pervasive.jcaf.item.AbstractContextItem;

public class Presentation extends AbstractContextItem {

	private String name;
	
	public Presentation(String id, String name) {
		super(id);
		
		this.name = name;
	}
	
	public String getName() {
		return name;
	}


	/**
	 * TODO: implement the accuracy as needed!!
	 */
	@Override
	public double getAccuracy() {
		return super.getAccuracy();
	}
	
	
	
	public String toString(){
		return "Presentation name: "+ name;
	}
	
	@Override
	public String toXML() {
		return "<presentation id=\"" + getId() + "\">" + "<location>" + name + "</location>" + "</presentation>";
	}

}
