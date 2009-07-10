package example.jasper;

import java.util.ArrayList;

public class Sample 
{
	String 	  owner;
	String 	  name;
	ArrayList pos = new ArrayList();
	
	public Sample(String name) {
		this.name = name;
	}
	
	public Sample() {
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public ArrayList getPos() {
		return pos;
	}

	public void setPos(ArrayList pos) {
		this.pos = pos;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

}
