package dk.itu.info.relationship;

import dk.pervasive.jcaf.relationship.TimedRelationship;

public class Left extends TimedRelationship {
		
	    private String source;
	    
		public Left() {
			super();
		}

		public Left(String source) {
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
			return 	"<left souce=\"" + getSource() + "\" time=\"" + getTime() + "\" />";
		}

	    public boolean equals(Object obj) {
	        if (obj instanceof Left) {
	            Left loc = (Left) obj;
	            return loc.getSource().equals(getSource());
	        }

	        return super.equals(obj);
	    }
	    
	    public int hashCode() {
	        return getSource().hashCode();
	    }
	    
	    public String toString() {
	        return "Left [" + getSource() + "]";
	    }
	    
	}

