package gawky.file;

public interface URLInterface {
	public void send(String url, String sourcepath) throws Exception;
	public void retrieve(String url, String targetpath) throws Exception;
}
