package gawky.database.generator;


public class IDGeneratorSEQUENCE extends IDGeneratorSQL 
{
	public IDGeneratorSEQUENCE(String sequence) {
		super("SELECT " + sequence + ".currval FROM dual");
	}
}
