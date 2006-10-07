package gawky.file;

public interface LineHandler {
	public void processLine(String line) throws CancelException;
}
