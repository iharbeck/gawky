package gawky.database.generator;

public class IDGenerator {

	String seq = null;
	
	public IDGenerator() {
		this.seq = "?";
	}
	public IDGenerator(String seq) {
		this.seq = seq;
	}
	public String getSequence() {
		return seq;
	}
	
}
