package dk.itu.info.relationship;

import dk.pervasive.jcaf.relationship.TimedRelationship;

public class Arrived extends TimedRelationship {
	
    private String source;
    
	public Arrived() {
		super();
	}

	public Arrived(String source) {
		this();
		this.source = source;
	}

    /**
     * @return Returns the source.
     */
    public String getSource() {
        return source;
    }
    
    public void setSource(String source) {
        this.source = source;
    }

	public String toXML() {
		return 	"<arrived souce=\"" + getSource() + "\" time=\"" + getTime() + "\" />";
	}

    public boolean equals(Object obj) {
        if (obj instanceof Arrived) {
            Arrived loc = (Arrived) obj;
            return loc.getSource().equals(getSource());
        }

        return super.equals(obj);
    }
    
    public int hashCode() {
        return getSource().hashCode();
    }
    
    public String toString() {
        return "arrived [" + getSource() + "]";
    }
    
}
