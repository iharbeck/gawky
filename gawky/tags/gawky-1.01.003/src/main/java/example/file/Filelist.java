package example.file;

import java.io.File;

public class Filelist
{
	// Files and directories statistics
	private static int allElementsCount = 0;
	private static int directoriesCount = 0;

	public static void main(String[] args)
	{
		long start = System.currentTimeMillis();
		String directoryPath = "c:/sources";

		listDirectory(new File(directoryPath));

		System.out.println("\n-----------------------------------------------");
		System.out.println("Directory stats");
		System.out.println("-----------------------------------------------");
		System.out.println("Directories: " + directoriesCount);
		System.out.println("Files: " + (allElementsCount - directoriesCount));
		
		System.out.println("DONE " + (System.currentTimeMillis() - start));
	}

	public final static void listDirectory(File directoryPath)
	{
		for(File contents : directoryPath.listFiles())
		{
			allElementsCount++;

			// Directory path for files and directories
			//contents = directoryPath + "/" + contents;

			// Display full path names
			// System.out.println(contents);

			// For directories go recursively down
			if(contents.isDirectory()) // && !contents.getName().startsWith("."))
			{
				directoriesCount++;

				// Go down to next directory
				listDirectory(contents);
			}
		}
	}
}
