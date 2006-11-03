package example.file;

import gawky.file.Scp;

public class TestScp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scp.copytohost("host", "user", "pass", "lfile", "rfile");
	}

}
