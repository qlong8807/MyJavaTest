package enum_test;

public enum Second {
	Spring("sp"),Summer("su"),Autumn("au"),Winter("wi");
	
	private String name;
	
	Second(String name) {
		this.name = name;
	}
	
	public static Second fromValue(String name) {
		for(Second s : Second.values()) {
			if(s.getTypeName().equals(name)) {
				return s;
			}
		}
		return null;
	}
	
	public String getTypeName() {
		return this.name;
	}
}
