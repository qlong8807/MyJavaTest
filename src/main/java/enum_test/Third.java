package enum_test;

public enum Third {
	Spring("sp","aa","bb"),Summer("su"),Autumn("au"),Winter("wi");
	
	private String name;
	
	private Third(String name) {
		this.name = name;
	}
	private Third(String a,String b,String c) {
		// TODO Auto-generated constructor stub
	}
}
