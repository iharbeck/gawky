package gawky.file;

public interface URLInterface {
	public void send(String url, String sourcepath) throws Exception;
	public String[] retrieve(String url, String targetpath) throws Exception;
	public String[] retrieve(String url, String targetpath, boolean simulate) throws Exception;
}
