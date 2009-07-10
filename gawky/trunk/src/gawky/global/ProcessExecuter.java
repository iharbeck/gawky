package gawky.global;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class ProcessExecuter {

	public static String run(String command) throws Exception
	{
		StringBuilder buf = new StringBuilder();
		
		String line;

		Process p = Runtime.getRuntime().exec(command);

		BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

		while ((line = input.readLine()) != null) {
			buf.append(line).append("\n");
		}

		input.close();

		return buf.toString();
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println( 
				ProcessExecuter.run("tree.com") +
				ProcessExecuter.run(new String[] {"command.com", "/c", "dir"}, "c:/work/gawky/")
		);
	}
	
	public static String run (String[] action, String folder) throws Exception
	{
		StringBuilder buf = new StringBuilder();

		List command = Arrays.asList(action);

        ProcessBuilder builder = new ProcessBuilder(command);
        builder.directory(new File(folder));

        final Process process = builder.start();
        
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;

        while ((line = br.readLine()) != null) {
          buf.append(line).append("\n");
        }
        
        return buf.toString();
	}
}
