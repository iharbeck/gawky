package example.file;

/*
	import java.io.IOException;
 	import java.nio.file.FileVisitResult;
	import java.nio.file.FileVisitor;
	import java.nio.file.Files;
	import java.nio.file.Path;
	import java.nio.file.Paths;
	import java.nio.file.SimpleFileVisitor;
	import java.nio.file.attribute.BasicFileAttributes;
*/
/** Recursive listing with SimpleFileVisitor in JDK 7. */
public class Visitor
{
	/*
	public static void main(String... aArgs) throws IOException{
	  
	 long start = System.currentTimeMillis();
	 
	String ROOT = "C:/work";
	  FileVisitor<Path> fileProcessor = new ProcessFile();
	  Files.walkFileTree(Paths.get(ROOT), fileProcessor);
	  
	  System.out.println("DONE " + (System.currentTimeMillis() - start));
	}

	private static final class ProcessFile extends SimpleFileVisitor<Path> {
	  @Override public FileVisitResult visitFile(
	    Path aFile, BasicFileAttributes aAttrs
	  ) throws IOException {
	    //System.out.println("Processing file:" + aFile);
	    return FileVisitResult.CONTINUE;
	  }
	  
	  @Override  public FileVisitResult preVisitDirectory(
	    Path aDir, BasicFileAttributes aAttrs
	  ) throws IOException {
	    //System.out.println("Processing directory:" + aDir);
	    return FileVisitResult.CONTINUE;
	  }
	}
	*/
}
