package gawky.database.sqlholder;

public class CallerClassName 
{
	private final static CustomSecurityManager customSecurityManager = new CustomSecurityManager() ;

	static class CustomSecurityManager extends SecurityManager 
	{
		public Class getCallerClassName(int callStackDepth) 
		{
			return getClassContext()[callStackDepth];
		}
	}
	
	public static Class getCallerClass(int callStackDepth) 
	{
		return customSecurityManager.getCallerClassName(callStackDepth);
	}
}