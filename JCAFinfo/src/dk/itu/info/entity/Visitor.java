package dk.itu.info.entity;

import dk.itu.info.item.Presentation;
import dk.pervasive.jcaf.ContextEvent;
import dk.pervasive.jcaf.ContextItem;
import dk.pervasive.jcaf.entity.Person;

public class Visitor extends Person {
	private String id;
	private String name;
	public Visitor() {
		super();
	}
	/**
	 * Constructor for Visitor.
	 * @param id
	 */ 
	public Visitor(String id) {
		super(id);
		this.id = id;
	}

	/**
	 * Constructor for Visitor.
	 * @param id
	 * @param name
	 */
	public Visitor(String id, String name) {
		super(id, name);
		this.id = id;
		this.name = name;
	}	
	
	@Override
	public String getEntityInfo() {
		return "ID: "+ getId() + "\nName: "+ getName();
	}
	
	@Override
	public void contextChanged(ContextEvent event) {
		super.contextChanged(event);

		ContextItem item = event.getItem();
		if(item != null) {
			
			if(item instanceof Presentation) {
				System.out.println("Name: " + getName() + " @ Presentation: " + ((Presentation)event.getItem()).getName());
			} else {
				
			} if(item instanceof Room) {
				System.out.println("Name: " + getName() + " in Room: " + ((Room)event.getItem()).toString());
			}
			
		}
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 5;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Visitor other = (Visitor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}
