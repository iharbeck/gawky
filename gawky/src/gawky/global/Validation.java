package gawky.global;

public class Validation {
	public static boolean isEmtpy(String value) {
		return value == null || value.trim().equals("");
	}
}
