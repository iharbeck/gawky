package example.jasper;

import java.util.ArrayList;

public class Sample 
{
	String 	  owner;
	String 	  name;
	ArrayList<Sample> pos = new ArrayList<Sample>();
	
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

	public ArrayList<Sample> getPos() {
		return pos;
	}

	public void setPos(ArrayList<Sample> pos) {
		this.pos = pos;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

}
