package gawky.incubator;

public class RegBuilder {
		
	public static String regbuilder(String dummy) 
	{
		StringBuilder buf = new StringBuilder();
		
		for(int i=0; i < dummy.length(); i++) {
			switch (dummy.charAt(i)) {
			case '.':
				buf.append("\\.");
				break;
			case '?':
				buf.append(".");
				break;
			case '*':
				buf.append(".+");
				break;
			default:
				buf.append(dummy.charAt(i));
				break;
			}
		}
		
		return buf.toString();
	}
	
	public static void main(String[] args) {
		String pat = "?nzeige*.doc";
		
		// . > \\.
		// ? > .
		// * > .+
		
		
		String toto = ".nzeige.+\\.doc";
		
		System.out.println(toto);
		System.out.println(regbuilder(pat) );
		
		String org = "Anzeige1.doc";
		
		System.out.println(org.matches(toto));
		System.out.println(org.matches(regbuilder(pat)));
	}
}
