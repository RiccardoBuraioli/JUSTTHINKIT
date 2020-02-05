package pattern;


public class Singleton {

	private static Singleton instance = null;
	
	public static Singleton getSingletonInstance() {
		if (Singleton.instance == null)
			Singleton.instance = new Singleton();
		return instance;
	}

}
